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
package pl.softech.math;

import java.util.Iterator;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 */
public class SieveOfEratosthenesTest {
    
    /**
     * Test of iterator method, of class SieveOfEratosthenes.
     */
    @Test
    public void testPrimes() {
        
        int upperBount = 1000000;
        Iterator<Integer> primeIt1 = new SieveOfEratosthenes(upperBount).iterator();
        Iterator<Integer> primeIt2 = new NaivePrimes(upperBount).iterator();
        
        while(primeIt1.hasNext()) {
            assertEquals(primeIt2.next(), primeIt1.next());
        }
         
    }

}