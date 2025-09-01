package hexlet.code.shop;

import hexlet.code.shop.Product;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class ProductDAO {
    private Connection connection;

    public ProductDAO(Connection conn) {
        connection = conn;
    }

    public void save(Product product) throws SQLException {
        var sql = "INSERT INTO products (title, description, price) VALUES (?, ?, ?)";
        try (var preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, product.getTitle());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setInt(3, product.getPrice());
            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                product.setId(generatedKeys.getInt(1));
            } else {
                throw new SQLException("DB have not returned an id after saving an entity");
            }
        }
    }

    public Optional<Product> find(String title) throws SQLException {
        var sql = "SELECT * FROM products WHERE title = ?";
        try (var stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, title);
            var resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                var id = resultSet.getInt("id");
                var description = resultSet.getString("description");
                var price = resultSet.getInt("price");
                var product = new Product(title, description, price);
                product.setTitle(title);
                return Optional.of(product);
            }
            return Optional.empty();
        }
    }
}