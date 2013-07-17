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
import pl.softech.collection.BitArray;

/**
 *
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 */
public class SieveOfEratosthenes implements Iterable<Integer> {

    private BitArray numbers;
    private boolean processed = false;

    /**
     * Primes will be searched in the 1..n-1 
     * 
     * @param n Exclusive Upper bound of the set. 
     */
    public SieveOfEratosthenes(int n) {
        numbers = new BitArray(n);
    }

    private void process() {

        if (processed) {
            return;
        }

        int max = (int) Math.sqrt(numbers.size());
        for (int i = 2; i <= max; i++) {

            if (!numbers.isSet(i)) {
                for (int j = i * 2; j < numbers.size(); j += i) {
                    numbers.set(j);
                }
            }
        }

        processed = true;

    }

    @Override
    public Iterator<Integer> iterator() {

        process();

        return new Iterator<Integer>() {
            
            int it = 0;
            int prime = it;

            @Override
            public boolean hasNext() {

                if (prime > it) {
                    return true;
                }

                for (int i = it + 1; i < numbers.size(); i++) {
                    if (!numbers.isSet(i)) {
                        prime = i;
                        return true;
                    }
                }

                return false;
            }

            @Override
            public Integer next() {
                
                hasNext();
                
                it = prime;
                return prime;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
    }

    public static void main(String[] args) {

        for (int prime : new SieveOfEratosthenes((100))) {
            System.out.println(prime);
        }

    }
}
