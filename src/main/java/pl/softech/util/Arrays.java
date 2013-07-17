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
package pl.softech.util;

import java.util.Comparator;

/**
 *
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 */
public class Arrays {

    /**
     * array[p..r]
     */
    private static <T> int binSearch0(T[] array, T value, int p, int r, Comparator<T> comparator) {

        if (p <= r) {

            int q = (p + r) >>> 1;

            int cmp = comparator.compare(value, array[q]);
            
            if (cmp < 0) {
                return binSearch0(array, value, p, q - 1, comparator);
            } else if (cmp > 0) {
                return binSearch0(array, value, q + 1, r, comparator);
            }

            return q;



        }

        return -1;

    }
    
    private static <T> int binSearch1(T[] array, T value, int p, int r, Comparator<T> comparator) {
        
        int left = p;
        int right = r;
        
        while(left <= right) {
            
            int q = (left + right) >>> 1;

            int cmp = comparator.compare(value, array[q]);
            
            if(cmp < 0) {
                
                right = q - 1;
                
            } else if(cmp > 0) {
                
                left = q + 1;
                
            } else {
                
                return q;
                
            }
            
        }
        
        return -1;
        
    }

    public static <T> int binSearch(T[] array, T value, Comparator<T> comparator) {
        return binSearch1(array, value, 0, array.length - 1, comparator);
    }
    
    /**
     * Transforms one array into another
     * 
     * @param array input array
     * @param function transformation
     * @return transformed array
     */
    public static <I, R> R[] transform(I[] array, IFunction<I, R> function) {
        
        R[] r = (R[]) new Object[array.length];
        
        for(int i = 0; i < r.length; i++) {
            r[i] = function.apply(array[i]);
        }
        
        
        return r;
    }
}
