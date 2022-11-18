import dao.OrganizationDAO;;
import entity.Organization;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class OrganizationDAOTest {
    private static @NotNull OrganizationDAO organizationDAO;

    @BeforeAll
    static void init(){
        organizationDAO = new OrganizationDAO();
    }
    @Test
    void getById() {
        Organization organization =  new Organization(1, "Provider 1", 1287, 258193);
        assertEquals(organization, organizationDAO.getById(organization.getId()));
    }
    @Test
    void save() {
        Organization organization =  new Organization(6, "Provider 1", 1287, 258193);
        organizationDAO.save(organization);
        assertEquals(organization, organizationDAO.getById(organization.getId()));
        organizationDAO.delete(organization);
    }
    @Test
    void getAll() {
        List<Organization> organizationList = new ArrayList<>();
        organizationList.add(new Organization(1, "Provider 1", 1287, 258193));
        organizationList.add(new Organization(2, "Provider 2", 1589, 158538));
        organizationList.add(new Organization(3, "Provider 3", 1940, 563139));
        organizationList.add(new Organization(4, "Provider 4", 1481, 614783));
        organizationList.add(new Organization(5, "Provider 5", 1417, 157850));

        assertEquals(organizationList, organizationDAO.getAll());
    }
    @Test
    void update() {
        Organization organization =  new Organization(6, "Provider 1", 1287, 258193);
        organizationDAO.save(organization);
        organization.setName("Provider 29");
        organizationDAO.update(organization);
        assertEquals(organization, organizationDAO.getById(organization.getId()));
        organizationDAO.delete(organization);
    }
    @Test
    void delete() {
        Organization organization =  new Organization(6, "Provider 1", 1287, 258193);
        organizationDAO.save(organization);
        organizationDAO.delete(organization);
        assertNull(organizationDAO.getById(organization.getId()));
    }
}
