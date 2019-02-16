import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class ConnectionManagerTest {

    private ConnectionManager manager;

    @Before
    public void setup() {
        manager = new ConnectionManager();
        manager.connect("anytest", "postgres", "post");
    }

    @Test
    public void test_get_all_table_names() {
        String[] tableNames = manager.getTableNames();
        assertEquals("[users, cars]", Arrays.toString(tableNames));
    }

    @Test
    public void test_get_table_data() {
        manager.clear("users");
        DataSet input = new DataSet();
        input.put("id", 13);
        input.put("name", "Ivan");
        input.put("email", "ivan@gmail.com");
        manager.create(input);

        DataSet[] sets = manager.getTableData("users");
        assertEquals(1, sets.length);

        DataSet user = sets[0];
        assertEquals("[13, Ivan, ivan@gmail.com]", Arrays.toString(user.getValues()));
        assertEquals("[id, name, email]", Arrays.toString(user.getNames()));
    }
}
