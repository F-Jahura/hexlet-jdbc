package hexlet.code;

import hexlet.code.course.Course;
import hexlet.code.course.CourseDAO;
import hexlet.code.shop.Catalog;
import hexlet.code.shop.DataInitializer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class App {
        public static void main (String[] args) throws SQLException {
            /*var conn = DriverManager.getConnection("jdbc:h2:mem:hexlet_test");

            var sql = "CREATE TABLE users (id BIGINT PRIMARY KEY AUTO_INCREMENT, username VARCHAR(255), phone VARCHAR(255))";
            var statement = conn.createStatement();
            statement.execute(sql);
            statement.close();

            var sql2 = "INSERT INTO users (username, phone) VALUES ('tommy', '123456789')";
            var statement2 = conn.createStatement();
            statement2.executeUpdate(sql2);
            statement2.close();

            var sql3 = "SELECT * FROM users";
            var statement3 = conn.createStatement();
            var resultSet = statement3.executeQuery(sql3);
            while (resultSet.next()) {
                System.out.println(resultSet.getString("username"));
                System.out.println(resultSet.getString("phone"));
            }
            statement3.close();

            conn.close();*/

            try (var conn = DriverManager.getConnection("jdbc:h2:mem:hexlet_test")) {

                var sql = "CREATE TABLE users (id BIGINT PRIMARY KEY AUTO_INCREMENT, username VARCHAR(255), phone VARCHAR(255))";
                try (var statement = conn.createStatement()) {
                    statement.execute(sql);
                }

                var sql2 = "INSERT INTO users (username, phone) VALUES ('tommy', '123456789')";
                try (var statement2 = conn.createStatement()) {
                    statement2.executeUpdate(sql2);
                }

                var sql3 = "SELECT * FROM users";
                try (var statement3 = conn.createStatement()) {
                    var resultSet = statement3.executeQuery(sql3);
                    while (resultSet.next()) {
                        System.out.println(resultSet.getString("username"));
                        System.out.println(resultSet.getString("phone"));
                    }
                }
            }

            //////////////////shop test
            try (var conn = DriverManager.getConnection("jdbc:h2:./hexlet")) {
                DataInitializer.init(conn);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            for (var arg : args) {
                try {
                    System.out.println(Catalog.getProduct(arg));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }


        ////////////////////preparestatement
            List<String> products = List.of("computer", "mobile phone", "tv", "kettle");


            try (var conn = DriverManager.getConnection("jdbc:h2:mem:hexlet")) {

                var sql = "CREATE TABLE products "
                        + "(id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255))";

                try (var statement = conn.createStatement()) {
                    statement.execute(sql);
                }

                // BEGIN (write your solution here)
                var sql4 = "INSERT INTO products (name) VALUES (?)";
                try (var preparedStatement = conn.prepareStatement(sql4)) {
                    preparedStatement.setString(1, "computer");
                    preparedStatement.executeUpdate();

                    preparedStatement.setString(1, "kettle");
                    preparedStatement.executeUpdate();


                    preparedStatement.setString(1, "mobile phone");
                    preparedStatement.executeUpdate();

                    preparedStatement.setString(1, "tv");
                    preparedStatement.executeUpdate();

                }
                // END

                var sql3 = "DELETE FROM products WHERE id = 1";

                try (var statement3 = conn.createStatement()) {
                    var resultSet = statement3.executeUpdate(sql3);

                        System.out.println("id is deleted");
                }
            }


            ///Course

            try (var conn = DriverManager.getConnection("jdbc:h2:mem:hexlet")) {

                var sql = "CREATE TABLE courses"
                        + "(id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255), description TEXT)";

                try (var statement = conn.createStatement()) {
                    statement.execute(sql);
                }

                var dao = new CourseDAO(conn);
                var course = new Course("Java", "Java is the best language");
                dao.save(course);

                System.out.println(dao.getEntities());
            }

    }
}