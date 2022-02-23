package ua.com.juja.sqlcmd.model;

import java.util.*;

public class DataSetImpl implements DataSet {

    private Map<String, Object> data = new LinkedHashMap<String, Object>();

    @Override
    public void put(String name, Object value) {
        data.put(name, value);
    }

    @Override
    public List<Object> getValues() {
        return new ArrayList<Object>(data.values());
    }

    @Override
    public Set<String> getNames() {
        return data.keySet();
    }

    @Override
    public String toString() {
        return "DataSet{" + System.lineSeparator() +
                "names:" + getNames() + System.lineSeparator() +
                "values:" + getValues() + System.lineSeparator() +
                "}";
    }

}
