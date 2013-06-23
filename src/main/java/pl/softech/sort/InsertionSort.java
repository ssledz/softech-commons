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

import java.util.Comparator;

/**
 * O(n^2)
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 */
public class InsertionSort<T> implements ISort<T> {

    @Override
    public void sort(T[] arr, Comparator<T> comparator) {

        for (int j = 1; j < arr.length; j++) {
            
            T key = arr[j];
            
            int i = j - 1;
            while(i >= 0 && comparator.compare(arr[i], key) > 0) {
                
                arr[i + 1] = arr[i];
                i--;
                
            }
            arr[i + 1] = key;
        }

    }
}
