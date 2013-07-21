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
public class PriorityQueueTest {

    private Comparator<Integer> intComparator = new Comparator<Integer>() {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o1.compareTo(o2);
        }
    };

    /**
     * Test of extractMin method, of class PriorityQueue.
     */
    @Test
    public void testExtractMin() {
        PriorityQueue<String, Integer> pq = new PriorityQueue<String, Integer>(50, intComparator);

        pq.addElement(10, "a");
        pq.addElement(1, "b");
        pq.addElement(11, "c");
        pq.addElement(0, "d");


        assertEquals("d", pq.extractMin().value);
        assertEquals("b", pq.extractMin().value);
        assertEquals("a", pq.extractMin().value);
        assertEquals("c", pq.extractMin().value);
    }

    /**
     * Test of changeKeyAt method, of class PriorityQueue.
     */
    @Test
    public void testChangeKeyAt() {
        
        PriorityQueue<String, Integer> pq = new PriorityQueue<String, Integer>(50, intComparator);

        pq.addElement(10, "a");
        pq.addElement(1, "b");
        pq.addElement(11, "c");
        pq.addElement(0, "d");
        
        assertEquals("d", pq.peekMin().value);
        pq.changeKeyAt(pq.peekMin().index, 12);
        assertEquals("b", pq.peekMin().value);
        pq.changeKeyAt(pq.peekMin().index, 13);
        
        assertEquals("a", pq.extractMin().value);
        pq.changeKeyAt(pq.peekMin().index, 14);
        
        assertEquals("d", pq.extractMin().value);
        assertEquals("b", pq.extractMin().value);
        assertEquals("c", pq.extractMin().value);
        
    }
}