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
                    "SELECT organizations.id, name, inn, checking_account, SUM(count) as count FROM organizations " +
                            "INNER JOIN invoices ON organizations.id = invoices.org_id " +
                            "INNER JOIN positions ON positions.invoice_id = invoices.id " +
                            "GROUP BY organizations.id, name, inn, checking_account ORDER BY count DESC LIMIT 10"))
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
        try (var statement = connection.prepareStatement("SELECT organizations.id as org_id, " +
                "organizations.name as org_name, inn, checking_account FROM organizations " +
                "INNER JOIN invoices ON organizations.id = invoices.org_id " +
                "INNER JOIN positions ON positions.invoice_id = invoices.id " +
                "WHERE count > ?")){
            statement.setInt(1, value);
            statement.executeQuery();
            try(var resultSet = statement.getResultSet())
            {
                while (resultSet.next()) {
                    organizations.add(new Organization(
                            resultSet.getInt("org_id"),
                            resultSet.getString("org_name"),
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

    public Integer getAveragePrice(Date begin, Date end, int id){
        try (var preparedStatement = connection.prepareStatement(
                "SELECT AVG(price) as avg_price FROM positions " +
                "INNER JOIN invoices ON invoices.id = positions.invoice_id " +
                "WHERE invoices.date BETWEEN ? AND ? AND product_id = ?"))
        {
            preparedStatement.setDate(1, begin);
            preparedStatement.setDate(2, end);
            preparedStatement.setInt(3, id);
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
                "SELECT products.id as pr_id, products.name as product_name, products.code as code, " +
                        "organizations.id as org_id, organizations.name as org_name, organizations.inn as inn, " +
                        "organizations.checking_account as checking_account FROM organizations " +
                        "LEFT JOIN invoices ON invoices.org_id = organizations.id " +
                        "LEFT JOIN positions ON positions.invoice_id = invoices.id " +
                        "LEFT JOIN products ON positions.product_id = products.id " +
                        "WHERE invoices.date BETWEEN ? AND ?"))
        {
            preparedStatement.setDate(1, begin);
            preparedStatement.setDate(2, end);
            try(var resultSet = preparedStatement.executeQuery()){
                while(resultSet.next()){
                    Organization organization = new Organization(
                            resultSet.getInt("org_id"),
                            resultSet.getString("org_name"),
                            resultSet.getInt("inn"),
                            resultSet.getInt("checking_account"));
                    if (!products.containsKey(organization)){
                        products.put(organization, new ArrayList<>());
                    }
                    Product product = null;
                    if (resultSet.getString("product_name") != null){
                        product = new Product(
                                resultSet.getInt("pr_id"),
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
                "SELECT products.id as prod_id, products.name as product_name, products.code, SUM(count), price " +
                        "FROM products " +
                        "LEFT JOIN invoices ON invoices.org_id = organizations.id AND invoices.date BETWEEN ? AND ?" +
                        "LEFT JOIN positions ON positions.invoice_id = invoices.id " +
                        "LEFT JOIN products ON products.id = positions.product_id"))
        {
            preparedStatement.setDate(1, begin);
            preparedStatement.setDate(2, end);
            try(var resultSet = preparedStatement.getResultSet()){
                while(resultSet.next()){
                    Product product = new Product(
                                resultSet.getInt("prod_id"),
                                resultSet.getString("product_name"),
                                resultSet.getInt("code"));
                    }
                }
            }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
}
