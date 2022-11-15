package dao;

import commons.JDBCCredentials;
import entity.Invoice;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"NotNullNullableValidation", "SqlNoDataSourceInspection", "SqlResolve"})
public final class InvoiceDAO implements DAO<Invoice> {

    private static final @NotNull JDBCCredentials CREDS = JDBCCredentials.DEFAULT;
    private static  Connection connection;

    public InvoiceDAO() {
        try {
            connection = DriverManager.getConnection(CREDS.getUrl(), CREDS.getLogin(), CREDS.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Invoice> getAll() {
        final var result = new ArrayList<Invoice>();
        try(var statement = connection.createStatement()){
            try(var resultSet = statement.executeQuery("SELECT * FROM invoices")){
                while(resultSet.next()){
                    result.add(new Invoice(resultSet.getInt("id"), resultSet.getDate("date"), resultSet.getInt("org_id")));
                }
                return result;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Invoice getById(@NotNull int id) {
        try (var statement = connection.createStatement()){
            try(var resultSet = statement.executeQuery("SELECT * FROM invoices WHERE id = " + id)){
                if (resultSet.next())
                    return new Invoice(resultSet.getInt("id"), resultSet.getDate("date"), resultSet.getInt("org_id"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(@NotNull Invoice entity) {
        try(var preparedStatement = connection.prepareStatement("INSERT INTO invoices(id, date, org_id) VALUES (?, ?, ?)")) {
            preparedStatement.setInt(1, entity.getId());
            preparedStatement.setDate(2, (Date) entity.getDate());
            preparedStatement.setInt(3, entity.getOrgId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(@NotNull Invoice entity) {
        try(var preparedStatement = connection.prepareStatement("DELETE FROM invoices WHERE id = ?")) {
            preparedStatement.setInt(1, entity.getId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
