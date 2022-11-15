
import dao.PositionDAO;
import entity.Position;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PositionDAOTest {
    private static @NotNull PositionDAO positionDAO;

    @BeforeAll
    static void init(){
        positionDAO = new PositionDAO();
    }
    @Test
    void getById() {
        Position position = new Position(1, 1100, 1, 1, 1);
        assertEquals(position, positionDAO.getById(position.getId()));
    }
    @Test
    void save() {
        Position position = new Position(6, 1100, 1, 1, 1);
        positionDAO.save(position);
        assertEquals(position, positionDAO.getById(position.getId()));
        positionDAO.delete(position);
    }
    @Test
    void getAll() {
        List<Position> positionList = new ArrayList<>();
        positionList.add(new Position(1, 1100, 1, 1, 1));
        positionList.add(new Position(2, 1300, 4, 2, 1));
        positionList.add(new Position(3, 12200, 3, 1, 3));
        positionList.add(new Position(4, 3369, 2, 4, 4));
        positionList.add(new Position(5, 390, 5, 10, 2));

        assertEquals(positionList, positionDAO.getAll());
    }
    @Test
    void delete() {
        Position position = new Position(6, 1100, 1, 1, 1);
        positionDAO.save(position);
        positionDAO.delete(position);
        assertNull(positionDAO.getById(position.getId()));
    }

}
