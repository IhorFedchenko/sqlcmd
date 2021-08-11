package ua.com.juja.sqlcmd.model;

import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        Set<String> tableNames = new HashSet<String>();
        try {
            tableNames = manager.getTableNames();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertEquals("[cars, ISO639-3, users]", tableNames.toString());
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
        try {
            manager.create("users", input);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<DataSet> sets = null;
        try {
            sets = manager.getTableData("users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertEquals(1, sets.size());

        DataSet user = sets.get(0);
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
        try {
            manager.create("users", input);
        } catch (SQLException e) {
//           do nothing
        }

        // when
        DataSet newValue = new DataSet();
        newValue.put("name", "Ivanupdater");
        newValue.put("email", "ivan_update@gmail.com");
        try {
            manager.update("users", 13, newValue);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // then
        List<DataSet> users = null;
        try {
            users = manager.getTableData("users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertEquals(1, users.size());

        DataSet user = users.get(0);
        assertEquals("[id, name, email]", Arrays.toString(user.getNames()));
        assertEquals("[13, Ivanupdater, ivan_update@gmail.com]", Arrays.toString(user.getValues()));
    }

    @Test
    public void test_is_connected(){
        assertTrue(manager.isConnected());
    }

    @Test(expected = SQLException.class)
    public void text_expected_sql_exception_when_clear_table_does_not_exist() throws SQLException {
        manager.clear("qqq");
    }
}
