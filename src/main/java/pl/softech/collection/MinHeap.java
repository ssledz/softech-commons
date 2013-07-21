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

import java.util.Comparator;

/**
 *
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 */
public class MinHeap<T> implements IHeap<T> {

    private IHeap<T> maxHeap;
    
    public MinHeap(T[] elements, Comparator<T> comparator) {
        maxHeap = new MaxHeap<T>(elements, wrapComparator(comparator));
    }

    public MinHeap(int initialSize, Comparator<T> comparator) {
        maxHeap = new MaxHeap<T>(initialSize, wrapComparator(comparator));
    }
    
    private static<T> Comparator<T> wrapComparator(final Comparator<T> comparator) {
        
        return new Comparator<T>() {

            @Override
            public int compare(T o1, T o2) {
                return -comparator.compare(o1, o2);
            }
        };
    }
    
    @Override
    public T extractTop() {
        return maxHeap.extractTop();
    }

    @Override
    public void addElement(T element) {
        maxHeap.addElement(element);
    }

    @Override
    public boolean hasMore() {
        return maxHeap.hasMore();
    }

    @Override
    public void changeKeyAt(int index) {
        maxHeap.changeKeyAt(index);
    }

    @Override
    public void buildHeap() {
        maxHeap.buildHeap();
    }
    
}
