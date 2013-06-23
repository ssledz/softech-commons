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
import static org.junit.Assert.assertArrayEquals;

/**
 *
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 */
public class TestSortUtil {

    public static void doIntRandomSortTest(ISort<Integer> algorithm, int size) {

        Integer[] arr = SampleDataForSortGenerator.generateIntArr(size);
        Integer[] expected = SampleDataForSortGenerator.sortIntArray(arr);
        algorithm.sort(arr, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });
        assertArrayEquals(expected, arr);

    }
}
