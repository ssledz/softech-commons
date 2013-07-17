/*
 * Copyright 2013 Sławomir Śledź <slawomir.sledz@sof-tech.pl>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pl.softech.collection;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 */
public class DirectHashTable<K, V> implements Map<K, V> {

    private class Entry<K, V> implements Map.Entry<K, V> {

        K key;
        V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
    private Entry<K, V>[] table;
    private int size;

    public DirectHashTable(int initialCapacity) {
        table = new Entry[initialCapacity];
    }

    private int hash(Object key, int index) {
        return (key.hashCode() + index) % table.length;
    }

    @Override
    public V put(K key, V value) {

        for (int i = 0; i < table.length; i++) {

            int index = hash(key, i);
            if (table[index] == null) {
                table[index] = new Entry(key, value);
                size++;
                return null;
            } else if (key.equals(table[index].key)) {
                V ret = table[index].value;
                table[index].value = value;
                return ret;
            }

        }

        throw new RuntimeException("There is no enough space to store key and value");

    }

    private int indexOf(Object key) {
        for (int i = 0; i < table.length; i++) {
            int index = hash(key, i);
            if (table[index] != null && key.equals(table[index].key)) {
                return index;
            }
        }
        return -1;
    }

    @Override
    public V remove(Object key) {

        int index = indexOf(key);

        if (index > -1) {
            V value = table[index].value;
            table[index] = null;
            size--;
            return value;
        }

        return null;
    }

    @Override
    public V get(Object key) {
        int index = indexOf(key);

        if (index > -1) {
            return table[index].value;
        }

        return null;
    }
    
    
    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean containsValue(Object value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<V> values() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
