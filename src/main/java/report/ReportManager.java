package report;

import commons.JDBCCredentials;
import entity.Organization;
import entity.Product;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportManager {
    private static final @NotNull JDBCCredentials CREDS = JDBCCredentials.DEFAULT;
    private static Connection connection;

    public ReportManager() {
        try {
            connection = DriverManager.getConnection(CREDS.getUrl(), CREDS.getLogin(), CREDS.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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

    public List<Organization> getProvidersWithCountProductsByValue(int value){
        List<Organization> organizations = new ArrayList<>();
        try (var statement = connection.createStatement()){
            try(var resultSet = statement.executeQuery(
                    "SELECT name, SUM(count) as count FROM organizations\n" +
                            "INNER JOIN invoices ON organizations.id = invoices.org_id" +
                            "INNER JOIN positions ON positions.invoice_id = invoices.id" +
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

    public Integer getAveragePrice(Date begin, Date end){
        try (var preparedStatement = connection.prepareStatement(
                "SELECT name, AVG(price) as avg_price FROM products " +
                "INNER JOIN positions ON products.id = product_id " +
                "INNER JOIN invoices ON positions.id = invoice_id " +
                "GROUP BY name HAVING invoices.date BETWEEN ? AND ?"))
        {
            preparedStatement.setDate(1, begin);
            preparedStatement.setDate(2, end);
            try(var resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()){
                    return resultSet.getInt("avg_price");
                }
                else {
                    throw new IllegalStateException("No records");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException("No records");
    }

    public Map<Organization, List<Product>> getProductsForPeriod(Date begin, Date end){
        Map<Organization, List<Product>> products = new HashMap<>();
        try (var preparedStatement = connection.prepareStatement(
                "SELECT products.name as product_name, organizations.name as org_name FROM organizations" +
                        "LEFT JOIN positions ON positions.id = invoice_id" +
                        "LEFT JOIN products ON positions.product_id = products.id" +
                        "LEFT JOIN invoices ON invoices.org_id = organizations.id AND invoices.date BETWEEN ? AND ?" +
                        "GROUP BY organizations.name, products.name ORDER BY products.name "))
        {
            preparedStatement.setDate(1, begin);
            preparedStatement.setDate(2, end);
            try(var resultSet = preparedStatement.getResultSet()){
                while(resultSet.next()){
                    Organization organization = new Organization(
                            resultSet.getInt("id"),
                            resultSet.getString("org_name"),
                            resultSet.getInt("inn"),
                            resultSet.getInt("checking_account"));
                    if (!products.containsKey(organization)){
                        products.put(organization, new ArrayList<>());
                    }
                    Product product = null;
                    if (resultSet.getString("name") != null){
                        product = new Product(
                                resultSet.getInt("id"),
                                resultSet.getString("product_name"),
                                resultSet.getInt("code"));
                    }
                    products.get(organization).add(product);

                }
                return products;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public Map<Organization, List<Product>> getCountAndPrice(Date begin, Date end){
        Map<Organization, List<Product>> products = new HashMap<>();
        try (var preparedStatement = connection.prepareStatement(
                "SELECT organization.name as org_name, organization.inn, product.name as product_name, product.code" +
                        "FROM organizations" +
                        "LEFT JOIN invoices ON invoices.org_id = organizations.id AND invoices.date BETWEEN ? AND ?" +
                        "LEFT JOIN positions ON positions.invoice_id = invoices.id" +
                        "LEFT JOIN products ON products.id = positions.product_id"))
        {
            preparedStatement.setDate(1, begin);
            preparedStatement.setDate(2, end);
            try(var resultSet = preparedStatement.getResultSet()){
                while(resultSet.next()){
                    Organization organization = new Organization(
                            resultSet.getInt("id"),
                            resultSet.getString("org_name"),
                            resultSet.getInt("inn"),
                            resultSet.getInt("checking_account")
                    );
                    Product product = null;
                    if (resultSet.getString("product_name") != null) {
                        product = new Product(
                                resultSet.getInt("id"),
                                resultSet.getString("product_name"),
                                resultSet.getInt("code"));
                    }
                    if (!products.containsKey(organization)){
                        products.put(organization, new ArrayList<>());
                    }
                    if (product != null){
                        products.get(organization).add(product);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
}
