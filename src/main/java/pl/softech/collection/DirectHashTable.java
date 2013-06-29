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

/**
 *
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 */
public class DirectHashTable<K, V> implements IMap<K, V> {

    private class Entry<K, V> {

        K key;
        V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
    private Entry<K, V>[] table;

    public DirectHashTable(int initialCapacity) {
        table = new Entry[initialCapacity];
    }

    private int hash(K key, int index) {
        return (key.hashCode() + index) % table.length;
    }

    @Override
    public void add(K key, V value) {

        for (int i = 0; i < table.length; i++) {

            int index = hash(key, i);
            if (table[index] == null) {
                table[index] = new Entry(key, value);
                return;
            } else if (key.equals(table[index].key)) {
                throw new RuntimeException(String.format("Value %s already exists with key %s", table[index].value, key));
            }

        }

        throw new RuntimeException("There is no enough space to store key and value");

    }

    private int indexOf(K key) {
        for (int i = 0; i < table.length; i++) {
            int index = hash(key, i);
            if (table[index] != null && key.equals(table[index].key)) {
                return index;
            }
        }
        return -1;
    }

    @Override
    public V delete(K key) {

        int index = indexOf(key);

        if (index > -1) {
            V value = table[index].value;
            table[index] = null;
            return value;
        }

        return null;
    }

    @Override
    public V search(K key) {
        int index = indexOf(key);

        if (index > -1) {
            return table[index].value;
        }

        return null;
    }
}
