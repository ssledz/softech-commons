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
import org.junit.Test;

/**
 *
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 */
public class BinaryTreeTest {
    
    public BinaryTreeTest() {
    }

    /**
     * Test of add method, of class BinaryTree.
     */
    @Test
    public void testAdd() {
        BinaryTree<Integer, String> bt = new BinaryTree<Integer, String>(new Comparator<Integer>() {
            
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });
        
        
        bt.add(5, "5");
        bt.add(1, "1");
        bt.add(7, "7");
        bt.add(4, "4");
        bt.add(2, "2");
        bt.add(3, "3");
        bt.add(6, "6");
        
        //bt.printAll();
        
        for(IEntry<Integer, String> el : bt) {
            System.out.println(el.getValue());
        }
    }
}