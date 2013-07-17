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
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 */
public class ChaninedHashTable<K, V> implements Map<K, V> {

    private class Entry implements Map.Entry {

        K key;
        V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public Object getKey() {
            return key;
        }

        @Override
        public Object getValue() {
            return value;
        }

        @Override
        public Object setValue(Object value) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
    private IList<Entry>[] table;
    private int size;

    public ChaninedHashTable(int initialCapacity) {

        table = (IList<Entry>[]) new IList[initialCapacity];

    }

    private int hash(Object key) {
        return key.hashCode();
    }

    private int indexOf(Object key) {
        return hash(key) % table.length;
    }

    @Override
    public V put(K key, V value) {

        V tmp = get(key);
        if (tmp != null) {
            remove(key);
        }

        int idx = indexOf(key);
        if (table[idx] == null) {
            table[idx] = new LinkedList<Entry>();
        }
        table[idx].add(new Entry(key, value));

        size++;

        return tmp;
    }

    @Override
    public V remove(Object key) {
        IList<Entry> list = table[indexOf(key)];
        if (list == null) {
            return null;
        }

        Iterator<Entry> it = list.iterator();
        while (it.hasNext()) {
            Entry e = it.next();
            if (e.key.equals(key)) {
                it.remove();
                size--;
                return e.value;
            }
        }


        return null;
    }

    @Override
    public V get(Object key) {
        int idx = indexOf(key);
        if (table[idx] != null) {

            for (Entry e : table[idx]) {
                if (e.key.equals(key)) {
                    return e.value;
                }
            }

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
