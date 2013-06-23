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
public class MergeSort<T> implements ISort<T> {

    /**
     * arr[p..q] i arr[q + 1..r]
     */
    private void merge(T[] arr, int p, int q, int r, Comparator<T> comparator) {

        Object[] left = new Object[q - p + 2];
        Object[] right = new Object[r - q + 1];

        System.arraycopy(arr, p, left, 0, q - p + 1);
        System.arraycopy(arr, q + 1, right, 0, r - q);

        for (int k = p, i = 0, j = 0; k <= r; k++) {

            if (comparator.compare((T) left[i], (T) right[j]) <= 0) {
                
                arr[k] = (T) left[i++];
                
            } else {
                
                arr[k] = (T) right[j++];
                
            }

        }

    }

    /**
     * arr[p..r] size = r - p + 1
     */
    private void mergeSort(T[] arr, int p, int r, Comparator<T> comparator) {

        if (p < r) {

            int q = (p + r) / 2;

            mergeSort(arr, p, q, comparator);
            mergeSort(arr, q + 1, r, comparator);
            merge(arr, p, q, r, comparator);

        }

    }

    @Override
    public void sort(T[] arr, final Comparator<T> comparator) {

        mergeSort(arr, 0, arr.length - 1, new Comparator<T>() {
            @Override
            public int compare(T o1, T o2) {

                if (o1 == null) {
                    return 1;
                }

                if (o2 == null) {
                    return -1;
                }

                return comparator.compare(o1, o2);
            }
        });

    }
}
