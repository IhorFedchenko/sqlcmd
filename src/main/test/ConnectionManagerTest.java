import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class ConnectionManagerTest {

    private ConnectionManager manager;

    @Before
    public void setup(){
        manager = new ConnectionManager();
        manager.connect("anytest", "postgres", "post");
    }

    @Test
    public void test_get_all_table_names(){
        String[] tableNames = manager.getTableNames();
        assertEquals("[users, cars]", Arrays.toString(tableNames));
    }
}
