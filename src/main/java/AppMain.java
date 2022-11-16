import commons.FlywayInit;
import commons.JDBCCredentials;
import org.jetbrains.annotations.NotNull;


public class AppMain {
    public static void main(@NotNull String @NotNull[] args) {
        FlywayInit.initDb();
    }
}
