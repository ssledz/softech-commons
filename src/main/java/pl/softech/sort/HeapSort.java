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
import pl.softech.collection.Heap;

/**
 * O(nlog(n))
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 */
public class HeapSort<T> implements ISort<T> {

    @Override
    public void sort(T[] arr, Comparator<T> comparator) {
        Heap<T> heap = new Heap<T>(arr, comparator);
        
        heap.buildHeap();
        
        while(heap.hasMore()) {
            heap.extractTop();
        }
    }
}
