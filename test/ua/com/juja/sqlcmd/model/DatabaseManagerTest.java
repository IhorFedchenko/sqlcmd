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
    public void setup() throws SQLException {
        manager = getDatabaseManager();
        manager.connect("anytest", "postgres", "post");
    }

    public abstract DatabaseManager getDatabaseManager();

    @Test
    public void test_get_all_table_names() throws SQLException {
        Set<String> tableNames = new HashSet<String>();
        tableNames = manager.getTableNames();
        assertEquals("[cars, languages, users]", tableNames.toString());
    }

    @Test
    public void test_get_table_data() throws SQLException {
        manager.clear("users");
        DataSet input = new DataSetImpl();
        input.put("id", 13);
        input.put("name", "Ivan");
        input.put("email", "ivan@gmail.com");
        manager.create("users", input);
        List<DataSet> sets = null;
        sets = manager.getTableData("users");
        assertEquals(1, sets.size());

        DataSet user = sets.get(0);
        assertEquals("[13, Ivan, ivan@gmail.com]", Arrays.toString(user.getValues()));
        assertEquals("[id, name, email]", Arrays.toString(user.getNames()));
    }

    @Test
    public void testUpdateTableData() throws SQLException {
        manager.clear("users");
        DataSet input = new DataSetImpl();
        input.put("id", 13);
        input.put("name", "Ivan");
        input.put("email", "ivan@gmail.com");
        manager.create("users", input);

        // when
        DataSet newValue = new DataSetImpl();
        newValue.put("name", "Ivanupdater");
        newValue.put("email", "ivan_update@gmail.com");
        manager.update("users", 13, newValue);

        // then
        List<DataSet> users = null;
        users = manager.getTableData("users");
        assertEquals(1, users.size());
        DataSet user = users.get(0);
        assertEquals("[id, name, email]", Arrays.toString(user.getNames()));
        assertEquals("[13, Ivanupdater, ivan_update@gmail.com]", Arrays.toString(user.getValues()));
    }

    @Test
    public void test_is_connected() {
        assertTrue(manager.isConnected());
    }

    @Test(expected = SQLException.class)
    public void text_expected_sql_exception_when_clear_table_does_not_exist() throws SQLException {
        manager.clear("qqq");
    }

    @Test
    public void testGetTableColumns() throws SQLException {
        assertEquals("[id, name, email]", manager.getTableColumns("users").toString());
    }
}
