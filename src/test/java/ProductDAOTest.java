import dao.InvoiceDAO;
import dao.ProductDAO;
import entity.Product;
import entity.Product;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ProductDAOTest {
    private static @NotNull ProductDAO productDAO;

    @BeforeAll
    static void init(){
        productDAO = new ProductDAO();
    }
    @Test
    void getById() {
        Product product = new Product(1, "Item 1", 101);
        assertEquals(product, productDAO.getById(product.getId()));
    }
    @Test
    void save() {
        Product product = new Product(6, "Item 1", 101);
        productDAO.save(product);
        assertEquals(product, productDAO.getById(product.getId()));
        productDAO.delete(product);
    }
    @Test
    void getAll() {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product(1, "Item 1", 101));
        productList.add(new Product(2, "Item 2", 112));
        productList.add(new Product(3, "Item 3", 141));
        productList.add(new Product(4, "Item 4", 353));
        productList.add(new Product(5, "Item 5", 242));

        assertEquals(productList, productDAO.getAll());
    }
    @Test
    void delete() {
        Product product = new Product(6, "Item 1", 101);
        productDAO.save(product);
        productDAO.delete(product);
        assertNull(productDAO.getById(product.getId()));
    }
}
