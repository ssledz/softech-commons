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
public class LinkedList<T> implements IList<T> {

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

    private class Node {

        T data;
        Node next;

        Node() {
        }

        Node(T data) {
            this.data = data;
        }
    }
    private Node head = new Node();

    @SuppressWarnings("empty-statement")
    @Override
    public void add(T element) {

        Node t;

        for (t = head; t.next != null; t = t.next) {
            ;
        }

        t.next = new Node(element);
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
            }
        };

    }
}
