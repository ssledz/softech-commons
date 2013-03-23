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
package pl.softech.reflection;

import java.util.Iterator;
import java.util.Map;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pl.softech.reflection.SampleDataGenerator.SampleMark;

/**
 *
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 * @since 1.0
 */
public class MetaDataFactoryTest {

    public MetaDataFactoryTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    private void assertEqualsIMetaData(IMetaData<SampleMark> metaData, String name,
            int order, boolean visible) {

        assertTrue(metaData != null);
        assertTrue(metaData.getAnnotation() != null);
        assertEquals(metaData.getAnnotation().name(), name);
        assertEquals(metaData.getAnnotation().order(), order);
        assertEquals(metaData.getAnnotation().visible(), visible);

    }

    @Before
    public void setUp() {
    }

    /**
     * Test of class2MetaDataByName method, of class MetaDataFactory.
     */
    @Test
    public void testClass2MetaDataByFullPath1() {
        System.out.println("testClass2MetaDataByFullPath1");

        IMetaDataFactory<SampleMark> factory;

        factory = new MetaDataFactory<SampleMark>(SampleMark.class);

        Map<String, IMetaData<SampleMark>> name2MetaData =
                factory.class2MetaDataByFullPath(SampleDataGenerator.Person.class);

        assertEquals(3, name2MetaData.size());
        assertEqualsIMetaData(name2MetaData.get("pl.softech.reflection.SampleDataGenerator$Person|weight"), "Weight in kg", 5, false);
        assertEqualsIMetaData(name2MetaData.get("pl.softech.reflection.SampleDataGenerator$Person|lastname"), "", -1, true);
        assertEqualsIMetaData(name2MetaData.get("pl.softech.reflection.SampleDataGenerator$Person|age"), "Age in Years", -1, true);

        Iterator<String> it = name2MetaData.keySet().iterator();
        assertEquals("pl.softech.reflection.SampleDataGenerator$Person|age", it.next());
        assertEquals("pl.softech.reflection.SampleDataGenerator$Person|lastname", it.next());
        assertEquals("pl.softech.reflection.SampleDataGenerator$Person|weight", it.next());

        name2MetaData =
                factory.class2MetaDataByFullPath(SampleDataGenerator.Car.class);

        assertEquals(10, name2MetaData.size());
        it = name2MetaData.keySet().iterator();
        assertEquals("pl.softech.reflection.SampleDataGenerator$Car|driver", it.next());
        assertEquals("pl.softech.reflection.SampleDataGenerator$Car|driver|age", it.next());
        assertEquals("pl.softech.reflection.SampleDataGenerator$Car|driver|hasDrivingLicense", it.next());
        assertEquals("pl.softech.reflection.SampleDataGenerator$Car|driver|lastname", it.next());
        assertEquals("pl.softech.reflection.SampleDataGenerator$Car|driver|weight", it.next());
        assertEquals("pl.softech.reflection.SampleDataGenerator$Car|engine", it.next());
        assertEquals("pl.softech.reflection.SampleDataGenerator$Car|engine|oil", it.next());
        assertEquals("pl.softech.reflection.SampleDataGenerator$Car|engine|oil|producentName", it.next());
        assertEquals("pl.softech.reflection.SampleDataGenerator$Car|engine|oil|vendorName", it.next());
        assertEquals("pl.softech.reflection.SampleDataGenerator$Car|engine|productName", it.next());
    }

    @Test
    public void testClass2MetaDataByFullPath2() {
        System.out.println("testClass2MetaDataByFullPath2");

        IMetaDataFactory<SampleMark> factory;

        factory = new MetaDataFactory<SampleMark>(SampleMark.class);

        Map<String, IMetaData<SampleMark>> name2MetaData =
                factory.class2MetaDataByFullPath(SampleDataGenerator.Car.class);

        Iterator<String> it = null;

        assertEquals(10, name2MetaData.size());
        it = name2MetaData.keySet().iterator();
        assertEquals("pl.softech.reflection.SampleDataGenerator$Car|driver", it.next());
        assertEquals("pl.softech.reflection.SampleDataGenerator$Car|driver|age", it.next());
        assertEquals("pl.softech.reflection.SampleDataGenerator$Car|driver|hasDrivingLicense", it.next());
        assertEquals("pl.softech.reflection.SampleDataGenerator$Car|driver|lastname", it.next());
        assertEquals("pl.softech.reflection.SampleDataGenerator$Car|driver|weight", it.next());
        assertEquals("pl.softech.reflection.SampleDataGenerator$Car|engine", it.next());
        assertEquals("pl.softech.reflection.SampleDataGenerator$Car|engine|oil", it.next());
        assertEquals("pl.softech.reflection.SampleDataGenerator$Car|engine|oil|producentName", it.next());
        assertEquals("pl.softech.reflection.SampleDataGenerator$Car|engine|oil|vendorName", it.next());
        assertEquals("pl.softech.reflection.SampleDataGenerator$Car|engine|productName", it.next());
    }
}