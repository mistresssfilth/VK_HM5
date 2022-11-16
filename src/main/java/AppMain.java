import commons.FlywayInit;
import commons.JDBCCredentials;
import entity.Organization;
import entity.Product;
import org.jetbrains.annotations.NotNull;
import report.ReportManager;


public class AppMain {
    private static final @NotNull JDBCCredentials CREDS = JDBCCredentials.DEFAULT;

    public static void main(@NotNull String @NotNull[] args) {
        FlywayInit.initDb();
        ReportManager reportManager =  new ReportManager();
        System.out.println("Report #1");
        for (Organization organization : reportManager.getProvidersByCountProducts()){
            System.out.println(organization.getName() + "\t\t" + organization.getInn() + "\t\t" + organization.getCheckingAccount());
        }
    }
}
