package hexlet.code.music;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SongDAO {
    private Connection connection;

    public SongDAO(Connection conn) {
        connection = conn;
    }

    public void save(Song song) throws SQLException {
        var sql = "INSERT INTO songs (title, singer_name) VALUES (?, ?)";
        try (var preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, song.getTitle());
            preparedStatement.setString(2, song.getSinger_name());
            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                song.setId(generatedKeys.getInt(1));
            } else {
                throw new SQLException("DB have not returned an id after saving an entity");
            }
        }
    }

    public List<Song> find(String title) throws SQLException {
        List<Song> songs = new ArrayList<>();
        var sql = "SELECT * FROM songs WHERE LOWER(title) LIKE LOWER(?) ORDER BY title";
        try (var stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + title + "%");
            var resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                var id = resultSet.getInt("id");
                var titleFromDB = resultSet.getString("title");
                var singer_name = resultSet.getString("singer_name");
                var song = new Song(titleFromDB, singer_name);
                song.setId(id);
                songs.add(song);
            }
            return songs;
        }
    }
}
