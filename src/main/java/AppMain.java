import commons.FlywayInit;
import commons.JDBCCredentials;
import entity.Organization;
import entity.Product;
import org.jetbrains.annotations.NotNull;
import report.ReportManager;

import javax.sound.midi.Soundbank;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AppMain {
    private static final @NotNull JDBCCredentials CREDS = JDBCCredentials.DEFAULT;

    public static void main(@NotNull String @NotNull[] args) {
        FlywayInit.initDb();

        Date begin = Date.valueOf("2020-05-03");
        Date end = Date.valueOf("2022-12-12");

        ReportManager reportManager =  new ReportManager();
        System.out.println("Report #1");
        for (Organization organization : reportManager.getProvidersByCountProducts()){
            System.out.println(organization.getName() + "\t\t" + organization.getInn() + "\t\t" + organization.getCheckingAccount());
        }
        System.out.println("\nReport #2");
        List<Organization> organizationsList = reportManager.getProvidersWithCountProductsByValue(3);
        for(Organization organization : organizationsList){
            if (organization == null){
                System.out.println("---");
                continue;
            }
            System.out.println(organization.getName() + "\t\t" + organization.getInn() + "\t\t" + organization.getCheckingAccount());
        }

        System.out.println("\nReport #4");
        Integer averagePrice = reportManager.getAveragePrice(begin, end);
        System.out.println("Average price: " + averagePrice);

       System.out.println("\nReport #5");
        Map<Organization, List<Product>> map = reportManager.getProductsForPeriod(begin, end);
        for (Map.Entry<Organization, List<Product>> entry : map.entrySet()){
            System.out.println(entry.getKey().getName() + "\t\t" + entry.getKey().getInn() + "\t\t" + entry.getKey().getCheckingAccount());
            if (entry.getValue().size() == 0)
                System.out.println("0");
            else {
                for (Product product : entry.getValue())
                    System.out.println(product.getName() + "\t\t" + product.getCode());
            }
        }
    }
}
