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

/**
 *
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 */
public class LinkedList<T> implements IList<T>, IStack<T> {

    private class Node {

        T data;
        Node next;

        Node() {
        }

        Node(T data) {
            this.data = data;
            size++;
        }
    }
    private Node head = new Node();
    private Node tail = head;
    private int size = 0;

    @Override
    public boolean remove(T element) {
        Iterator<T> it = iterator();
        while (it.hasNext()) {

            if (element.equals(it.next())) {
                it.remove();
                return true;
            }

        }
        return false;
    }

    @Override
    public int indexOf(T element) {
        int index = 0;
        for (T e : this) {
            if (element.equals(e)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    @Override
    public boolean contains(T element) {
        return indexOf(element) >= 0;
    }

    @Override
    public void add(T element) {
        add(size, element);
    }

    @Override
    public Iterator<T> iterator() {

        return new Iterator<T>() {
            Node it = head;
            Node prev;

            @Override
            public boolean hasNext() {
                return it.next != null;
            }

            @Override
            public T next() {
                prev = it;
                it = it.next;
                return it.data;
            }

            @Override
            public void remove() {
                prev.next = it.next;
                size--;
            }
        };

    }

    @Override
    public void add(int index, T element) {

        Node node = new Node(element);

        if (size <= index) {
            tail.next = node;
            tail = node;
            return;
        }

        Node t;
        int i;
        for (i = 0, t = head; i < index && t.next != null; i++, t = t.next) {
            ;
        }

        if (t.next == null) {

            t.next = node;
            tail = node;

        } else {

            node.next = t.next;
            t.next = node;

        }

    }

    private void assertIndex(int index) {

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(String.format("Index must be from %d to %d", 0, size - 1));
        }

    }

    @Override
    public T get(int index) {

        assertIndex(index);

        if (index == size - 1) {
            return tail.data;
        }

        int i = 0;
        for (T element : this) {
            if (i++ == index) {
                return element;
            }
        }

        throw new IndexOutOfBoundsException(String.format("Index must be from %d to %d", 0, size - 1));
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeAt(int index) {

        assertIndex(index);

        Iterator<T> it = iterator();
        T element = null;
        for (int i = 0; i <= index; i++) {
            element = it.next();
        }
        it.remove();

        return element;
    }

    @Override
    public void push(T element) {
        add(0, element);
    }

    @Override
    public T pop() {
        return removeAt(0);
    }

    @Override
    public T peek() {
        if(size == 0) {
            return null;
        }
        return get(0);
    }
}
