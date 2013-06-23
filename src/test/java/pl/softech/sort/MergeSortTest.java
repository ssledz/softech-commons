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
package pl.softech.sort;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 */
public class MergeSortTest {
    
    @Test
    public void testIntSort() {
        
        MergeSort<Integer> instance = new MergeSort<Integer>();
        
        Integer[] arr = { 3, 1, 6, 8, 77, 1234, 2, 4 };
        
        Comparator<Integer> intComparator = new Comparator<Integer>() {

            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
            
        };
        
        instance.sort(arr, intComparator);
        
        assertArrayEquals(new Integer[] { 1, 2, 3, 4, 6, 8, 77, 1234}, arr);
        
        Random rnd = new Random();
        
        arr = new Integer[1000];
        for(int i = 0; i < arr.length; i++) {
            arr[i] = rnd.nextInt();
        }
        
        Integer[] expected = new Integer[1000];
        System.arraycopy(arr, 0, expected, 0, arr.length);
        
        Arrays.sort(expected, intComparator);
        instance.sort(arr, intComparator);
        
        assertArrayEquals(expected, arr);
        
    }
    
    
}