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

import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 */
public class DirectHashTableTest {

    public DirectHashTableTest() {
    }

    @Test
    public void testHashTable() {

        Map<Integer, String> table = new DirectHashTable<Integer, String>(10);

        table.put(1, "One");
        table.put(2, "Two");
        table.put(3, "Three");
        
        assertEquals(3, table.size());

        assertEquals("One", table.get(1));
        assertEquals("Two", table.get(2));
        assertEquals("Three", table.get(3));

        assertEquals("Two", table.remove(2));
        assertEquals(2, table.size());
        assertNull(table.get(2));
        

        assertEquals("One", table.put(1, "One1"));
        assertEquals("One1", table.get(1));
        assertEquals(2, table.size());
        
        table.remove(1);
        table.remove(3);
        
        assertTrue(table.isEmpty());

    }

    @Test
    public void testHashCodeCollision() {
        TestCollectionUtil.doHashTableHashCodeCollisionTest(new DirectHashTable<TestCollectionUtil.Key, String>(10));
    }
}