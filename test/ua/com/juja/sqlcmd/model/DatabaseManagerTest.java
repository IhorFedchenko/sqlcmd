package ua.com.juja.sqlcmd.model;

import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public abstract class DatabaseManagerTest {

    private DatabaseManager manager;

    @Before
    public void setup(){
        manager = getDatabaseManager();
        manager.connect("anytest", "postgres", "post");
    }

    public abstract DatabaseManager getDatabaseManager();

    @Test
    public void test_get_all_table_names() {
        String[] tableNames = manager.getTableNames();
        assertEquals("[users, cars]", Arrays.toString(tableNames));
    }

    @Test
    public void test_get_table_data() {
        try {
            manager.clear("users");
        } catch (SQLException e) {
//         do nothing
        }
        DataSet input = new DataSet();
        input.put("id", 13);
        input.put("name", "Ivan");
        input.put("email", "ivan@gmail.com");
        manager.create("users", input);

        DataSet[] sets = manager.getTableData("users");
        assertEquals(1, sets.length);

        DataSet user = sets[0];
        assertEquals("[13, Ivan, ivan@gmail.com]", Arrays.toString(user.getValues()));
        assertEquals("[id, name, email]", Arrays.toString(user.getNames()));
    }

    @Test
    public void testUpdateTableData() {
        try {
            manager.clear("users");
        } catch (SQLException e) {
//           do nothing
        }
        DataSet input = new DataSet();
        input.put("id", 13);
        input.put("name", "Ivan");
        input.put("email", "ivan@gmail.com");
        manager.create("users", input);

        // when
        DataSet newValue = new DataSet();
        newValue.put("name", "Ivanupdater");
        newValue.put("email", "ivan_update@gmail.com");
        manager.update("users", 13, newValue);

        // then
        DataSet[] users = manager.getTableData("users");
        assertEquals(1, users.length);

        DataSet user = users[0];
        assertEquals("[id, name, email]", Arrays.toString(user.getNames()));
        assertEquals("[13, Ivanupdater, ivan_update@gmail.com]", Arrays.toString(user.getValues()));
    }

    @Test
    public void test_is_connecte(){
        assertTrue(manager.isConnected());
    }
}
