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

import java.util.Map;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

/**
 *
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 */
public class TestCollectionUtil {

    public static class Key {

        private int key;

        public Key(int key) {
            this.key = key;
        }

        @Override
        public int hashCode() {
            return 1;
        }

        @Override
        public boolean equals(Object obj) {

            if (obj == this) {
                return true;
            }

            if (obj instanceof Key) {

                Key tmp = (Key) obj;
                return tmp.key == key;

            }

            return false;

        }
    }

    public static void doHashTableHashCodeCollisionTest(Map<Key, String> hashTableToTest) {

        hashTableToTest.put(new Key(1), "One");
        hashTableToTest.put(new Key(2), "Two");
        hashTableToTest.put(new Key(3), "Three");

        assertEquals("One", hashTableToTest.get(new Key(1)));
        assertEquals("Two", hashTableToTest.get(new Key(2)));
        assertEquals("Three", hashTableToTest.get(new Key(3)));

        assertEquals("Two", hashTableToTest.remove(new Key(2)));
        assertNull(hashTableToTest.get(new Key(2)));

    }
}
