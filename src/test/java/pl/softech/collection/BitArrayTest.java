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
public class BitArrayTest {

    /**
     * Test of set method, of class BitArray.
     */
    @Test
    public void testSet() {
        BitArray ba = new BitArray(40);

        int[] bit2set = {1, 4, 12, 11, 34, 21, 1, 4};

        for (int i : bit2set) {
            ba.set(i);
        }

        for (int i : bit2set) {
            assertTrue(ba.isSet(i));
        }


    }

    /**
     * Test of unset method, of class BitArray.
     */
    @Test
    public void testUnset() {
        BitArray ba = new BitArray(40);

        int[] bit2set = {1, 4, 12, 11, 34, 21, 1, 4};
        int[] bit2unset = {1, 4, 11};

        for (int i : bit2set) {
            ba.set(i);
        }

        for (int i : bit2unset) {
            ba.unset(i);
        }

        for (int i : bit2unset) {
            assertFalse(ba.isSet(i));
        }

    }

    /**
     * Test of toString method, of class BitArray.
     */
    @Test
    public void testToString() {
        BitArray ba = new BitArray(10);
        
        int[] bit2set = {1, 3, 6, 2};
        
        for (int i : bit2set) {
            ba.set(i);
        }
        
        assertEquals("0111001000", ba.toString());
        
        ba.unset(2);
        
        assertEquals("0101001000", ba.toString());
    }
}