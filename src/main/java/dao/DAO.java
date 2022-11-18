package dao;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface DAO<T> {

    List<T> getAll();
    T getById(@NotNull int id);
    void save(@NotNull T entity);
    void delete (@NotNull T entity);
    void update(@NotNull T entity);

}
