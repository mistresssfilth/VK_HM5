package dao;

import commons.JDBCCredentials;
import entity.Invoice;
import entity.Organization;
import entity.Product;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class OrganizationDAO implements DAO<Organization> {
    private static final @NotNull JDBCCredentials CREDS = JDBCCredentials.DEFAULT;
    private static Connection connection;

    public OrganizationDAO() {
        try {
            connection = DriverManager.getConnection(CREDS.getUrl(), CREDS.getLogin(), CREDS.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    public List<Organization> getAll() {
        final var result = new ArrayList<Organization>();
        try(var statement = connection.createStatement()){
            try(var resultSet = statement.executeQuery("SELECT * FROM organizations")){
                while(resultSet.next()){
                    result.add(new Organization(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getInt("inn"), resultSet.getInt("checking_account")));
                }
                return result;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Organization getById(@NotNull int id) {
        try (var statement = connection.createStatement()){
            try(var resultSet = statement.executeQuery("SELECT * FROM organizations WHERE id = " + id)){
                if (resultSet.next())
                    return new Organization(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getInt("inn"), resultSet.getInt("checking_account"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(@NotNull Organization entity) {
        try(var preparedStatement = connection.prepareStatement("INSERT INTO organizations(id, name, inn, checking_account) VALUES (?, ?, ?, ?)")) {
            preparedStatement.setInt(1, entity.getId());
            preparedStatement.setString(2, entity.getName());
            preparedStatement.setInt(3, entity.getInn());
            preparedStatement.setInt(4, entity.getCheckingAccount());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(@NotNull Organization entity) {
        try(var preparedStatement = connection.prepareStatement("UPDATE organizations SET name = ? WHERE id = ?")) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setInt(2, entity.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void delete(@NotNull Organization entity) {
        try(var preparedStatement = connection.prepareStatement("DELETE FROM organizations WHERE id = ?")) {
            preparedStatement.setInt(1, entity.getId());
            if (preparedStatement.executeUpdate() == 0)
                throw new IllegalStateException("Record not found");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
