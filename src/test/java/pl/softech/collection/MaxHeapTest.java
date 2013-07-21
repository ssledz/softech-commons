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
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 */
public class MaxHeapTest {

    private Comparator<Integer> intComparator = new Comparator<Integer>() {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o1.compareTo(o2);
        }
    };

    @Test
    public void testHasMore() {

        MaxHeap<Integer> heap = new MaxHeap<Integer>(10, intComparator);

        assertFalse(heap.hasMore());
        heap.addElement(1);
        assertTrue(heap.hasMore());

    }

    @Test
    public void testAddElement() {

        MaxHeap<Integer> heap = new MaxHeap<Integer>(10, intComparator);
        heap.addElement(1);
        heap.addElement(4);
        heap.addElement(2);

        int[] arr = new int[3];
        int i = 0;
        while (heap.hasMore()) {
            arr[i++] = heap.extractTop();
        }

        assertArrayEquals(new int[]{4, 2, 1}, arr);

    }

    /**
     * Test of extractTop method, of class Heap.
     */
    @Test
    public void testExtractTop() {

        MaxHeap<Integer> heap = new MaxHeap<Integer>(10, intComparator);
        heap.addElement(1);
        heap.addElement(4);
        heap.addElement(2);
        heap.addElement(9);
        heap.addElement(4);

        assertEquals(9, heap.extractTop().intValue());
        assertEquals(4, heap.extractTop().intValue());
        assertEquals(4, heap.extractTop().intValue());

    }
    
    @Test
    public void testBuildHeap() {
        MaxHeap<Integer> heap = new MaxHeap<Integer>(new Integer[]{5, 6, 1, 4, 9, 8, 3, 6}, intComparator);
        heap.buildHeap();
        
        int[] arr = new int[8];
        int i = 0;
        while (heap.hasMore()) {
            arr[i++] = heap.extractTop();
        }

        assertArrayEquals(new int[]{9, 8, 6, 6, 5, 4, 3, 1}, arr);
        
    }
    
    @Test
    public void testChangeKeyAt() {
        
        Integer[] arr = new Integer[]{5, 6, 1, 4, 9, 8, 3, 6};
        MaxHeap<Integer> heap = new MaxHeap<Integer>(arr, intComparator);
        heap.buildHeap();
        
        assertArrayEquals(new Integer[]{9, 6, 8, 6, 5, 1, 3, 4}, arr);
        
        arr[1] = 0;
        heap.changeKeyAt(1);
        assertArrayEquals(new Integer[]{9, 6, 8, 4, 5, 1, 3, 0}, arr);
        
        arr[0] = 7;
        heap.changeKeyAt(0);
        assertArrayEquals(new Integer[]{8, 6, 7, 4, 5, 1, 3, 0}, arr);
        
        arr[5] = 11;
        heap.changeKeyAt(5);
        assertArrayEquals(new Integer[]{11, 6, 8, 4, 5, 7, 3, 0}, arr);
        
        while (heap.hasMore()) {
            heap.extractTop();
        }
        
        assertArrayEquals(new Integer[]{0, 3, 4, 5, 6, 7, 8, 11}, arr);
        
    }
}