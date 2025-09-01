package hexlet.code.shop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.NoSuchElementException;

public class Catalog {
    private static ProductDAO productDAO;

    // Статический метод инициализации (если нужен)
    public static void initProductDAO(ProductDAO dao) {
        productDAO = dao;
    }

    // Статический блок, который выполнится один раз
    static {
        try {
            Connection connection = DriverManager.getConnection("jdbc:h2:./hexlet");
            productDAO = new ProductDAO(connection);
        } catch (SQLException e) {
            e.printStackTrace();
            // Обработка исключения при подключении к базе
        }
    }

    public static String getProduct(String productName) throws SQLException {
        var productOpt = productDAO.find(productName);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            return "Id: " + product.getId()
                    + "\nTitle: " + product.getTitle()
                    + "\nDescription: " + product.getDescription()
                    + "\nPrice: " + product.getPrice();
        } else {
            throw new NoSuchElementException("Product " + productName + " not found");
        }
    }
}
