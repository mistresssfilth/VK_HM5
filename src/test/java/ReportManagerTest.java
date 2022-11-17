import entity.Organization;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import report.ReportManager;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ReportManagerTest {
    private static @NotNull ReportManager reportManager;
    private static final @NotNull Date BEGIN = Date.valueOf("2020-05-03");
    private static final @NotNull Date END = Date.valueOf("2022-12-12");

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


}
