package dao;

import entity.Organization;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class OrganizationDAO implements DAO<Organization> {
    @Override
    public List<Organization> getAll() {
        return null;
    }

    @Override
    public Organization getById(@NotNull int id) {
        return null;
    }

    @Override
    public void save(@NotNull Organization entity) {

    }

    @Override
    public void delete(@NotNull Organization entity) {

    }
}
