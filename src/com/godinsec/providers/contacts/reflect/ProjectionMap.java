package com.godinsec.providers.contacts.reflect;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ProjectionMap extends HashMap<String, String> {
    private String[] mColumns;

    public ProjectionMap() {
    }

    public static ProjectionMap.Builder builder() {
        return new ProjectionMap.Builder();
    }

    public String[] getColumnNames() {
        return this.mColumns;
    }

    private void putColumn(String alias, String column) {
        super.put(alias, column);
    }

    public String put(String key, String value) {
        throw new UnsupportedOperationException();
    }

    public void putAll(Map<? extends String, ? extends String> map) {
        throw new UnsupportedOperationException();
    }

    public static class Builder {
        private ProjectionMap mMap = new ProjectionMap();

        public Builder() {
        }

        public ProjectionMap.Builder add(String column) {
            this.mMap.putColumn(column, column);
            return this;
        }

        public ProjectionMap.Builder add(String alias, String expression) {
            this.mMap.putColumn(alias, expression + " AS " + alias);
            return this;
        }

        public ProjectionMap.Builder addAll(String[] columns) {
            String[] arr = columns;
            int len = columns.length;

            for(int i = 0; i < len; ++i) {
                String column = arr[i];
                this.add(column);
            }

            return this;
        }

        public ProjectionMap.Builder addAll(ProjectionMap map) {
            Iterator i = map.entrySet().iterator();

            while(i.hasNext()) {
                Entry entry = (Entry)i.next();
                this.mMap.putColumn((String)entry.getKey(), (String)entry.getValue());
            }

            return this;
        }

        public ProjectionMap build() {
            String[] columns = new String[this.mMap.size()];
            this.mMap.keySet().toArray(columns);
            Arrays.sort(columns);
            this.mMap.mColumns = columns;
            return this.mMap;
        }
    }
}