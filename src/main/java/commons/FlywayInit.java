package commons;

import org.flywaydb.core.Flyway;
import org.jetbrains.annotations.NotNull;

public final class FlywayInit {
    public static final @NotNull JDBCCredentials CREDS = JDBCCredentials.DEFAULT;

    public static void initDb(){
        final Flyway flyway = Flyway.configure()
                .dataSource(
                        CREDS.getUrl(),
                        CREDS.getLogin(),
                        CREDS.getPassword()
                )
                .cleanDisabled(false)
                .locations("db")
                .load();
        flyway.clean();
        flyway.migrate();
    }
}
