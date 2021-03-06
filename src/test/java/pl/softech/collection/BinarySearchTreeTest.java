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
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 */
public class BinarySearchTreeTest {
    
    public BinarySearchTreeTest() {
    }

    /**
     * Test of add method, of class BinaryTree.
     */
    @Test
    public void testAdd() {
        BinarySearchTree<Integer, String> bt = new BinarySearchTree<Integer, String>(new Comparator<Integer>() {
            
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });
        
        assertTrue(bt.isEmpty());
        
        bt.put(5, "5");
        bt.put(1, "1");
        bt.put(7, "7");
        bt.put(4, "4");
        bt.put(2, "2");
        bt.put(3, "3");
        bt.put(6, "6");
        
        assertFalse(bt.isEmpty());
        
        int[] expected = {1, 2, 3, 4, 5, 6, 7};
        int i = 0;
        for(Map.Entry<Integer, String> el : bt) {
            assertEquals(expected[i++], el.getKey().intValue());
        }
    }
}