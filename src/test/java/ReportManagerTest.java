import entity.Organization;
import entity.Product;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import report.ReportManager;

import javax.swing.text.html.StyleSheet;
import java.sql.Date;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ReportManagerTest {
    private static @NotNull ReportManager reportManager;
    private static final @NotNull Date BEGIN = Date.valueOf("2019-03-06");
    private static final @NotNull Date END = Date.valueOf("2022-05-03");

    @BeforeAll
    static void init(){
        reportManager = new ReportManager();
    }
    @Test
    void getProvidersByCountProducts(){
        List<Organization> organizationList = new ArrayList<>();
        organizationList.add(new Organization(1, "Provider 1", 1287, 258193));
        organizationList.add(new Organization(2, "Provider 2", 1589, 158538));
        organizationList.add(new Organization(4, "Provider 4", 1481, 614783));

        assertEquals(organizationList, reportManager.getProvidersByCountProducts());

    }
    @Test
    void getProvidersWithCountProductsByValue(){
        List<Organization> organizationList = new ArrayList<>();
        organizationList.add(new Organization(2, "Provider 2", 1589, 158538));
        organizationList.add(new Organization(1, "Provider 1", 1287, 258193));

        assertEquals(organizationList, reportManager.getProvidersWithCountProductsByValue(2));
    }
    @Test
    void getAveragePrice(){

    }
    @Test
    void getProductsForPeriod(){
        Map<Organization, List<Product>> map = new HashMap<>();
        List<Product> productsByProvider1 = new ArrayList<>();
        List<Product> productsByProvider2 = Arrays.asList(new Product(2, "Item 2", 112));
        productsByProvider1.add(new Product(5,"Item 5", 242));
        productsByProvider1.add(new Product(4, "Item 4", 353));
        productsByProvider1.add(new Product(1, "Item 1", 101));
        map.put(new Organization(1, "Provider 1", 1287, 258193), productsByProvider1);
        map.put(new Organization(2, "Provider 2", 1589, 158538), productsByProvider2);

        assertEquals(map, reportManager.getProductsForPeriod(BEGIN, END));
    }


}
