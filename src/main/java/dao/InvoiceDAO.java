package dao;

import entity.Invoice;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class InvoiceDAO implements DAO<Invoice> {
    @Override
    public List<Invoice> getAll() {
        return null;
    }

    @Override
    public Invoice getById(@NotNull int id) {
        return null;
    }

    @Override
    public void save(@NotNull Invoice entity) {

    }

    @Override
    public void delete(@NotNull Invoice entity) {

    }
}
