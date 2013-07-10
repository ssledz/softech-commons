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
public class Heap<T> {

    private int heapSize;
    private T[] elements;
    private Comparator<T> comparator;

    public Heap(T[] elements, Comparator<T> comparator) {
        this.elements = elements;
        this.comparator = comparator;
        heapSize = 0;
    }

    public Heap(int initialSize, Comparator<T> comparator) {
        this.elements = (T[]) new Object[initialSize];
        this.comparator = comparator;
        heapSize = 0;
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
        T tmp = elements[i];
        elements[i] = elements[j];
        elements[j] = tmp;
    }

    public boolean hasMore() {
        return heapSize > 0;
    }

    public void addElement(T element) {
        elements[heapSize] = element;
        heapSize++;
        shiftUp(heapSize - 1);
    }

    public T extractTop() {

        if (heapSize == 0) {
            return null;
        }

        heapSize--;
        swap(0, heapSize);
        shiftDown(heapSize);
        return elements[heapSize];

    }

    private void shiftDown(int heapSize) {

        int i;
        int j = 0;

        do {

            i = j;
            //heap(1..heapSize - 1)
            int left = leftChild(i);

            if (left < heapSize) {

                int right = rightChild(i);

                if (comparator.compare(elements[j], elements[left]) < 0) {
                    j = left;
                }

                if (right < heapSize && comparator.compare(elements[j], elements[right]) < 0) {
                    j = right;
                }


            }

            swap(i, j);


        } while (i != j);

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
            if (comparator.compare(elements[j], elements[i]) >= 0) {
                return;
            }
            swap(i, j);
            i = j;
        }

    }

    public void buildHeap() {

        for (int i = 1; i < elements.length; i++) {

            //heap(0..i - 1)
            shiftUp(i);
        }

        heapSize = elements.length;

    }
}
