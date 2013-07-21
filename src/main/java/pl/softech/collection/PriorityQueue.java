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

import java.util.Comparator;

/**
 *
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 */
public class PriorityQueue<K, V> {

    public static class Entry<K, V> {

        private K key;
        private V value;
        private int index;

        private Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public int getIndex() {
            return index;
        }
        
    }
    private int heapSize;
    private Entry<K, V>[] elements;
    private Comparator<K> comparator;

    public PriorityQueue(int initialSize, Comparator<K> comparator) {
        this.elements = (Entry<K, V>[]) new Entry[initialSize];
        this.comparator = comparator;
    }

    private int leftChild(int parent) {
        return (parent << 1) + 1;
    }

    private int rightChild(int parent) {
        return (parent + 1) << 1;
    }

    private int parent(int child) {

        return (child - 1) >> 1;

    }

    private void swap(int i, int j) {
        Entry<K, V> tmp = elements[i];
        tmp.index = j;
        elements[i] = elements[j];
        elements[i].index = i;
        elements[j] = tmp;
    }

    private void shiftDown(int heapSize) {
        shiftDown(heapSize, 0);
    }

    public Entry<K, V> peekMin() {
        if(heapSize == 0) {
            return null;
        }
        return elements[0];
    }
    
    public boolean hasMore() {
        return heapSize > 0;
    }
    
    public Entry<K, V> extractMin() {

        if (heapSize == 0) {
            return null;
        }

        heapSize--;
        Entry<K, V> tmp = elements[0];
        elements[0] = elements[heapSize];
        shiftDown(heapSize);
        return tmp;

    }

    private void shiftUp(int index) {

        if (index == 0) {
            return;
        }

        int i = index;
        int j;
        while (i > 0) {

            //heap(0..i-1)
            j = parent(i);
            if (comparator.compare(elements[j].key, elements[i].key) <= 0) {
                return;
            }
            swap(i, j);
            i = j;
        }

    }

    private void shiftDown(int heapSize, int index) {

        int i;
        int j = index;

        do {

            i = j;
            //heap(1..heapSize - 1)
            int left = leftChild(i);

            if (left < heapSize) {

                int right = rightChild(i);

                if (comparator.compare(elements[j].key, elements[left].key) > 0) {
                    j = left;
                }

                if (right < heapSize && comparator.compare(elements[j].key, elements[right].key) > 0) {
                    j = right;
                }


            }

            swap(i, j);


        } while (i != j);

    }

    public void changeKeyAt(int index, K key) {

        elements[index].key = key;

        if (index > 0 && comparator.compare(elements[parent(index)].key, elements[index].key) > 0) {
            shiftUp(index);
            return;
        }

        shiftDown(heapSize, index);
    }
    
    public Entry<K, V> addElement(K key, V value) {
        Entry<K, V> e = new Entry<K, V>(key, value);
        e.index = heapSize;
        elements[heapSize] = e;
        heapSize++;
        shiftUp(heapSize - 1);
        return e;
    }
}
