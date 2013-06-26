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

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 */
public class ChaninedHashTableTest {

    /**
     * Test of add method, of class HashTable.
     */
    @Test
    public void testHashTable() {

        ChaninedHashTable<Integer,String> table = new ChaninedHashTable<Integer, String>(10);
        
        table.add(1, "One");
        table.add(2, "Two");
        table.add(3, "Three");
        
        assertEquals("One", table.search(1));
        assertEquals("Two", table.search(2));
        assertEquals("Three", table.search(3));
        
        assertEquals("Two", table.delete(2));
        assertNull(table.search(2));
        
        try {
            table.add(1, "One");
            fail();
        } catch(Exception e) {
        }
        
        
    }
}