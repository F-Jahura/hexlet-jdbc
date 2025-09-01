package hexlet.code.course;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CourseDAO {
    private Connection connection;

    public CourseDAO(Connection connection) {
        this.connection = connection;
    }

    public void save(Course course) throws SQLException {
        var sql = "INSERT INTO courses (name, description) VALUES (?, ?)";
        try (
                var preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, course.getName());
            preparedStatement.setString(2, course.getDescription());
            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                course.setId(generatedKeys.getLong(1));
            } else {
                throw new SQLException("DB have not returned an id after saving an entity");
            }
        }
    }

    public Optional<Course> find(Long id) throws SQLException {
        var sql = "SELECT * FROM courses WHERE id = ?";
        try (var stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            var resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                var name = resultSet.getString("name");
                var description = resultSet.getString("description");
                var course = new Course(name, description);
                course.setId(id);
                return Optional.of(course);
            }
            return Optional.empty();
        }
    }

    // BEGIN (write your solution here)
    public List<Course> getEntities() throws SQLException {
        List<Course> courses = new ArrayList<>();
        var sql = "SELECT * FROM courses";
        try (var stmt = connection.prepareStatement(sql)) {
            //stmt.setLong(1, id);
            var resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                var id = resultSet.getLong("id");
                var name = resultSet.getString("name");
                var description = resultSet.getString("description");
                var course = new Course(name, description);
                course.setId(id);
                courses.add(course);
            }
        }
        return courses;
    }


    public Optional<Course> delete(Long id) throws SQLException {
        var selectSql = "SELECT * FROM courses WHERE id = ?";
        var deleteSql = "DELETE FROM courses WHERE id = ?";

        try (var selectStmt = connection.prepareStatement(selectSql);
             var deleteStmt = connection.prepareStatement(deleteSql)) {

            // Получаем данные курса, прежде чем удалить
            selectStmt.setLong(1, id);
            try (var resultSet = selectStmt.executeQuery()) {
                if (resultSet.next()) {
                    var name = resultSet.getString("name");
                    var description = resultSet.getString("description");
                    var course = new Course(name, description);
                    course.setId(id);

                    // Выполняем удаление
                    deleteStmt.setLong(1, id);
                    int rowsAffected = deleteStmt.executeUpdate();
                    if (rowsAffected > 0) {
                        return Optional.of(course);
                    }
                }
            }
            return Optional.empty();
        }
    }
}
