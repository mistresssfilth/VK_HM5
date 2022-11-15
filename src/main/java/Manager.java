import entity.Organization;
import entity.Product;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.Date;
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

    public List<Product> getAveragePrice(Date begin, Date end){
        List<Product> products = new ArrayList<>();
        try (var preparedStatement = connection.prepareStatement(
                "SELECT name, AVG(price) as avg_price FROM products " +
                "INNER JOIN positions ON products.id = product_id " +
                "INNER JOIN invoices ON positions.id = invoice_id " +
                "GROUP BY name HAVING invoices.date BETWEEN ? AND ?"))
        {
            preparedStatement.setDate(1, begin);
            preparedStatement.setDate(2, end);
            try(var resultSet = preparedStatement.getResultSet()){
                while(resultSet.next()){
                    products.add(new Product(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getInt("code")));
                }
                return products;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public List<Product> getProductsForPeriod(Date begin, Date end){
        List<Product> products = new ArrayList<>();
        try (var preparedStatement = connection.prepareStatement(
                "SELECT products.name, organizations.name FROM organizations" +
                        "LEFT JOIN invoices ON invoices.org_id = organizations.id AND invoices.date BETWEEN ? AND ?" +
                        "LEFT JOIN positions ON positions.id = invoice_id" +
                        "LEFT JOIN products ON positions.product_id = products.id" +
                        "GROUP BY organizations.name, products.name ORDER BY products.name "))
        {
            preparedStatement.setDate(1, begin);
            preparedStatement.setDate(2, end);
            try(var resultSet = preparedStatement.getResultSet()){
                while(resultSet.next()){
                    products.add(new Product(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getInt("code")));
                }
                return products;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public List<Product> getCountAndPrice(Date begin, Date end){
        List<Product> products = new ArrayList<>();
        try (var preparedStatement = connection.prepareStatement(
                ""))
        {
            preparedStatement.setDate(1, begin);
            preparedStatement.setDate(2, end);
            try(var resultSet = preparedStatement.getResultSet()){
                while(resultSet.next()){
                    products.add(new Product(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getInt("code")));
                }
                return products;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
}
