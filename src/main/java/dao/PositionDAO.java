package dao;

import commons.JDBCCredentials;
import entity.Organization;
import entity.Position;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class PositionDAO implements DAO<Position> {
    private static final @NotNull JDBCCredentials CREDS = JDBCCredentials.DEFAULT;
    private static  Connection connection;

    public PositionDAO() {
        try {
            connection = DriverManager.getConnection(CREDS.getUrl(), CREDS.getLogin(), CREDS.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Position> getAll() {
        final var result = new ArrayList<Position>();
        try(var statement = connection.createStatement()){
            try(var resultSet = statement.executeQuery("SELECT * FROM positions")){
                while(resultSet.next()){
                    result.add(new Position(
                            resultSet.getInt("id"),
                            resultSet.getInt("price"),
                            resultSet.getInt("product_id"),
                            resultSet.getInt("count"),
                            resultSet.getInt("invoice_id")));
                }
                return result;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Position getById(@NotNull int id) {
        try (var statement = connection.createStatement()){
            try(var resultSet = statement.executeQuery("SELECT * FROM positions WHERE id = " + id)){
                if (resultSet.next())
                    return new Position(
                            resultSet.getInt("id"),
                            resultSet.getInt("price"),
                            resultSet.getInt("product_id"),
                            resultSet.getInt("count"),
                            resultSet.getInt("invoice_id"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;    }

    @Override
    public void save(@NotNull Position entity) {
        try(var preparedStatement = connection.prepareStatement("INSERT INTO positions(id, price, product_id, count, invoice_id) VALUES (?, ?, ?,?, ?)")) {
            preparedStatement.setInt(1, entity.getId());
            preparedStatement.setInt(2, entity.getPrice());
            preparedStatement.setInt(3, entity.getProductId());
            preparedStatement.setInt(4, entity.getCount());
            preparedStatement.setInt(5, entity.getInvoiceId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void update(@NotNull Position entity) {
        try(var preparedStatement = connection.prepareStatement("UPDATE positions SET price = ?, count = ? WHERE id = ?")) {
            preparedStatement.setInt(1, entity.getPrice());
            preparedStatement.setInt(2, entity.getCount());
            preparedStatement.setInt(3, entity.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(@NotNull Position entity) {
        try(var preparedStatement = connection.prepareStatement("DELETE FROM positions WHERE id = ?")) {
            preparedStatement.setInt(1, entity.getId());
            if (preparedStatement.executeUpdate() == 0)
                throw new IllegalStateException("Record not found");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
