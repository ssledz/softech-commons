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
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 */
public class BinarySearchTree<K, V> implements Iterable<Map.Entry<K, V>>, Map<K, V> {

    private static class Entry<K, V> implements Map.Entry<K, V> {

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

    private class Node {

        K key;
        V value;
        Node left, right;

        public Node() {
        }

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
    
    private Node head;
    private Comparator<K> comparator;
    private int size;

    public BinarySearchTree(Comparator<K> comparator) {
        this.comparator = comparator;
        this.head = new Node();
    }

    private V add0(Node node, K key, V value) {

        int cmp = comparator.compare(key, node.key);
        V ret = null;

        if (cmp < 0) {

            if (node.left == null) {

                node.left = new Node(key, value);

            } else {

                ret = add0(node.left, key, value);

            }

        } else if (cmp > 0) {

            if (node.right == null) {

                node.right = new Node(key, value);

            } else {

                ret = add0(node.right, key, value);

            }

        } else {
            
            return node.value;
            
        }
        
        return ret;

    }

    @Override
    public V put(K key, V value) {

        if (head.key == null) {
            head.key = key;
            head.value = value;
            size++;
            return null;
        }

        V ret = add0(head, key, value);
        
        if(ret == null) {
            size++;
        }
        
        return ret;
    }

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {

        return new Iterator<Map.Entry<K, V>>() {
            IStack<Node> stack = new LinkedList<Node>();
            Node node = head;
            boolean hasNext = true;

            @Override
            public boolean hasNext() {
                return stack.peek() != null || node != null;
            }

            @Override
            public Map.Entry<K, V> next() {

                while (hasNext) {

                    if (node != null) {

                        stack.push(node);
                        node = node.left;

                    } else {

                        node = stack.pop();
                        Entry<K, V> e = new Entry<K, V>(node.key, node.value);
                        node = node.right;
                        return e;

                    }

                }

                return null;

            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
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
    public V get(Object key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public V remove(Object key) {
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
