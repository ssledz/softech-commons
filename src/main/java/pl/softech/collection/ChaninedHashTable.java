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

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 */
public class ChaninedHashTable<K,V> implements IMap<K, V> {

    private class Entry {
        K key;
        V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
    
    private List<Entry>[] table;

    public ChaninedHashTable(int initialCapacity) {

        table = (List<Entry>[]) new List[initialCapacity];

    }

    private int hash(K key) {
        return key.hashCode();
    }
    
    private int indexOf(K key) {
        return hash(key) % table.length;
    }

    @Override
    public void add(K key, V value) {
        
        V tmp = search(key);
        if(tmp != null) {
            throw new RuntimeException(String.format("Value %s already exists with key %s", tmp, key));
        }
        
        int idx = indexOf(key);
        if (table[idx] == null) {
            table[idx] = new LinkedList<Entry>();
        }
        table[idx].add(new Entry(key, value));
    }

    @Override
    public V delete(K key) {
        List<Entry> list = table[indexOf(key)];
        if (list == null) {
            return null;
        }

        Iterator<Entry> it = list.iterator();
        while (it.hasNext()) {
            Entry e = it.next();
            if (e.key.equals(key)) {
                it.remove();
                return e.value;
            }
        }


        return null;
    }

    @Override
    public V search(K key) {
        int idx = indexOf(key);
        if (table[idx] != null) {

            for (Entry e : table[idx]) {
                if(e.key.equals(key)) {
                    return e.value;
                }
            }

        }
        return null;
    }
}
