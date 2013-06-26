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
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 */
public class LinkedListTest {
    
    /**
     * Test of add method, of class LinkedList.
     */
    @Test
    public void testLinkedList() {
       
        IList<Integer> list = new LinkedList<Integer>();
        
        for(int element : list) {
            fail();
        }
        
        list.add(1);
        list.add(2);
        
        int[] expected = {1, 2};
        int i = 0;
        for(int element : list) {
            assertEquals(expected[i++], element);
        }
        
        Iterator<Integer> it = list.iterator();
        
        assertEquals(1, it.next().intValue());
        assertEquals(2, it.next().intValue());
        
        list.add(21);
        list.add(27);
        
        assertEquals(21, it.next().intValue());
        assertEquals(27, it.next().intValue());
        
        assertEquals(2, list.indexOf(21));
        assertTrue(list.contains(21));
        
        list.remove(21);
        
        assertFalse(list.contains(21));
        
    }

    
}