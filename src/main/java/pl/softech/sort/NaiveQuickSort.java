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
 *
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 */
public class NaiveQuickSort<T> implements ISort<T> {

    private void swap(T[] arr, int i, int j) {
        T tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
    
    private int partition(T[] arr, int p, int r, Comparator<T> comparator) {

        int q = (p + r) / 2;

        for(int i = p; i < q; i++) {
            
            if(comparator.compare(arr[i], arr[q]) > 0) {
                swap(arr, i, q);
            }
            
        }
        
        for(int i = q + 1; i <= r; i++) {
            
            if(comparator.compare(arr[i], arr[q]) < 0) {
                swap(arr, i, q + 1);
                swap(arr, q, q + 1);
                q++;
            }
            
        }
        
        return q;
    }

    private void quickSort(T[] arr, int p, int r, Comparator<T> comparator) {

        if (p < r) {

            int q = partition(arr, p, r, comparator);

            quickSort(arr, p, q - 1, comparator);
            quickSort(arr, q + 1, r, comparator);
        }

    }

    @Override
    public void sort(T[] arr, Comparator<T> comparator) {
        
        quickSort(arr, 0, arr.length - 1, comparator);
        
    }
}
