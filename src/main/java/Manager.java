import entity.Organization;
import entity.Product;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Manager {
    private final @NotNull Connection connection;

    public Manager(@NotNull Connection connection) {
        this.connection = connection;
    }

    //??????? ?????? 10 ??????????? ?? ?????????? ????????????? ??????
    public List<Organization> getProvidersByCountProducts(){
        List<Organization> organizations = new ArrayList<>();
        try (var statement = connection.createStatement()){
            try(var resultSet = statement.executeQuery(
                    "SELECT name, SUM(count) as count FROM organizations " +
                            "INNER JOIN invoices ON organizations.id = invoices.org_id " +
                            "INNER JOIN positions ON positions.invoice_id = invoices.id  " +
                            "GROUP BY name ORDER BY count DESC LIMIT 10"))
            {
                while (resultSet.next()) {
                    organizations.add(new Organization(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getInt("inn"),
                            resultSet.getInt("checking_account")));
                }
                return organizations;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return organizations;

    }

    //??????? ??????????? ? ??????????? ????????????? ?????? ???? ?????????? ???????? (????? ? ??? ?????????? ?????? ????????? ????????????? ????????).
    public List<Organization> getProvidersWithCountProductsByValue(int value){
        List<Organization> organizations = new ArrayList<>();
        try (var statement = connection.createStatement()){
            try(var resultSet = statement.executeQuery(
                    "SELECT name, SUM(count) as count FROM organizations\n" +
                            "INNER JOIN invoices ON organizations.id = invoices.org_id\n" +
                            "INNER JOIN positions ON positions.invoice_id = invoices.id \n" +
                            "GROUP BY name HAVING SUM(count) = " + value))
            {
                while (resultSet.next()) {
                    organizations.add(new Organization(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getInt("inn"),
                            resultSet.getInt("checking_account")));
                }
                return organizations;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return organizations;
    }
}
