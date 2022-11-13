package dao;

import entity.Product;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"NotNullNullableValidation", "SqlNoDataSourceIncpection", "SqlResolve"})
public final class ProductDAO implements DAO<Product> {
    private final @NotNull Connection connection;

    public ProductDAO(@NotNull Connection connection) {
        this.connection = connection;
    }

    @Override
    public @NotNull List<Product> getAll() {
        final var result = new ArrayList<Product>();
        try(var statement = connection.createStatement()){
            try(var resultSet = statement.executeQuery("SELECT * FROM products")){
                while(resultSet.next()){
                    result.add(new Product(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("code")));
                }
                return result;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return result;

    }

    @Override
    public @NotNull Product getById(@NotNull int id) {
        try (var statement = connection.createStatement()){
            try(var resultSet = statement.executeQuery("SELECT * FROM products WHERE id = " + id)){
                if (resultSet.next())
                    return new Product(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("code"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        throw new IllegalStateException("Record with id " + id + "not found");
    }

    @Override
    public void save(@NotNull Product entity) {
        try(var preparedStatement = connection.prepareStatement("INSERT INTO products(id, name, code) VALUES (?, ?, ?)")) {
            preparedStatement.setInt(1, entity.getId());
            preparedStatement.setString(2,entity.getName());
            preparedStatement.setString(3, entity.getCode());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void delete(@NotNull Product entity) {

    }

}
