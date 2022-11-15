import commons.JDBCCredentials;
import dao.InvoiceDAO;
import entity.Invoice;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InvoiceDAOTest {
    private static @NotNull InvoiceDAO invoiceDAO;

    @BeforeAll
    static void init(){
        invoiceDAO = new InvoiceDAO();
    }
    @Test
    void getById() {
        Invoice invoice =  new Invoice(1, Date.valueOf("2022-05-03"), 1);
        assertEquals(invoice, invoiceDAO.getById(invoice.getId()));
    }
    @Test
    void save() {
        Invoice invoice =  new Invoice(6, Date.valueOf("2022-05-03"), 1);
        invoiceDAO.save(invoice);
        assertEquals(invoice, invoiceDAO.getById(invoice.getId()));
        invoiceDAO.delete(invoice);
    }
    @Test
    void getAll() {
        List<Invoice> invoiceList = new ArrayList<>();
        invoiceList.add(new Invoice(1, Date.valueOf("2022-05-03"), 1));
        invoiceList.add(new Invoice(2, Date.valueOf("2021-08-10"), 1));
        invoiceList.add(new Invoice(3, Date.valueOf("2022-11-13"), 4));
        invoiceList.add(new Invoice(4, Date.valueOf("2019-03-06"), 2));
        invoiceList.add(new Invoice(5, Date.valueOf("2022-11-08"), 3));

        assertEquals(invoiceList, invoiceDAO.getAll());
    }
    @Test
    void delete() {
        Invoice invoice =  new Invoice(6, Date.valueOf("2022-05-03"), 1);
        invoiceDAO.save(invoice);
        invoiceDAO.delete(invoice);
        assertNull(invoiceDAO.getById(invoice.getId()));
    }
}
