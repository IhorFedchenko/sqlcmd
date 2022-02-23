package ua.com.juja.sqlcmd.model;

import java.util.Arrays;

public class DataSetImpl implements DataSet {

    static class Data {
        private String name;
        private Object value;

        public Data(String name, Object value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public Object getValue() {
            return value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Data data = (Data) o;

            if (name != null ? !name.equals(data.name) : data.name != null) return false;
            return value != null ? value.equals(data.value) : data.value == null;
        }

        @Override
        public int hashCode() {
            int result = name != null ? name.hashCode() : 0;
            result = 31 * result + (value != null ? value.hashCode() : 0);
            return result;
        }
    }

    public Data[] data = new Data[100]; //TODO remove magic number 100
    public int index = 0;

    @Override
    public void put(String name, Object value) {
        data[index++] = new Data(name, value);
    }

    @Override
    public Object[] getValues() {
        Object[] result = new Object[index];
        for (int i = 0; i < index; i++) {
            result[i] = data[i].getValue();
        }
        return result;
    }

    @Override
    public String[] getNames() {
        String[] result = new String[index];
        for (int i = 0; i < index; i++) {
            result[i] = data[i].getName();
        }
        return result;
    }

    @Override
    public String toString() {
        return "DataSet{" + System.lineSeparator() +
                "names:" + Arrays.toString(getNames()) + System.lineSeparator() +
                "values:" + Arrays.toString(getValues()) + System.lineSeparator() +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DataSetImpl dataSet = (DataSetImpl) o;

        if (index != dataSet.index){
            return false;
        }
        return Arrays.equals(data, dataSet.data);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(data);
        result = 31 * result + index;
        return result;
    }
}
