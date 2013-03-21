package pl.softech.reflection;

import java.util.Collection;
import java.util.Map;
import java.util.Random;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pl.softech.reflection.SampleDataGenerator.Car;
import pl.softech.reflection.SampleDataGenerator.Driver;
import pl.softech.reflection.SampleDataGenerator.Engine;
import pl.softech.reflection.SampleDataGenerator.Oil;
import pl.softech.reflection.SampleDataGenerator.Person;
import pl.softech.reflection.SampleDataGenerator.SampleMark;

/**
 *
 * @author Sławomir Śledź
 * @since 1.0
 */
public class MetaDataTest {

    private static IMetaDataFactory<SampleMark> factory;

    private static Random random;

    private Collection<SampleDataGenerator.Person> persons;
    private Collection<SampleDataGenerator.Driver> drivers;
    private Collection<SampleDataGenerator.Car> cars;

    public MetaDataTest() { }

    @BeforeClass
    public static void setUpClass() throws Exception {
        factory = new MetaDataFactory<SampleMark>(SampleMark.class);
        random = new Random(23456);
    }

    
    @Before
    public void setUp() {
        persons = SampleDataGenerator.getSamplePersons(10, random);
        drivers = SampleDataGenerator.getSampleDrivers(10, random);
        cars = SampleDataGenerator.getSampleCars(5, random);
    }

    /**
     * Test of setValue method, of class IMetaData.
     */
    @Test
    public void testSetValue() throws Exception {

        Map<String,IMetaData<SampleMark>> name2MetaData;
        name2MetaData = factory.class2MetaDataByFullPath(Person.class);

        for(Person p : persons) {

            float weight = SampleDataGenerator.getSampleWeight(random, p.weight);
            int age = p.getAge();
            String lastName = p.getLastname();
            
            IMetaData<SampleMark> metaData =
                    name2MetaData.get("pl.softech.reflection.SampleDataGenerator$Person|weight");
            metaData.setValue(p, weight);
            Person.assertPerson(p, lastName, weight, age);
        }
        
        for(Person p : persons) {

            float weight = p.weight;
            int age = p.getAge();
            String lastName =
                    SampleDataGenerator.getSampleLastName(random, p.getLastname());
            IMetaData<SampleMark> metaData =
                    name2MetaData.get("pl.softech.reflection.SampleDataGenerator$Person|lastname");
            metaData.setValue(p, lastName);
            Person.assertPerson(p, lastName, weight, age);
        }

        name2MetaData = factory.class2MetaDataByFullPath(Car.class);
        for(Car c : cars) {

            Driver driver = c.getDriver();
            String lastName = driver.getLastname();
            float weight = SampleDataGenerator.getSampleWeight(random, driver.weight);
            int age = driver.getAge();
            boolean hasDrivingLicense = driver.hasDrivingLicense;

            Engine engine = c.getEngine();
            String productName =
                    SampleDataGenerator.getSampleProductName(random, engine.productName);

            Oil oil = engine.oil;
            String producentName = oil.producentName;
            String vendorName = oil.vendorName;

            IMetaData<SampleMark> metaData =
                    name2MetaData.get("pl.softech.reflection.SampleDataGenerator$Car|driver|weight");

            metaData.setValue(c, weight);

            metaData =
                    name2MetaData.get("pl.softech.reflection.SampleDataGenerator$Car|engine|productName");

            metaData.setValue(c, productName);

            Driver.assertDriver(driver, lastName, weight, age,
                    hasDrivingLicense);

            Engine.assertEngine(engine, productName);

            Oil.assertOil(oil, vendorName, producentName);

        }

    }

    /**
     * Test of getValue method, of class IMetaData.
     */
    @Test
    public void testGetValue() throws Exception {
        
        Map<String,IMetaData<SampleMark>> name2MetaData;
        name2MetaData = factory.class2MetaDataByFullPath(Person.class);

        assertEquals(3, name2MetaData.size());

        for(Person p : persons) {

            Person.assertPerson(p,
                    (String) name2MetaData.get("pl.softech.reflection.SampleDataGenerator$Person|lastname").getValue(p),
                    (Float) name2MetaData.get("pl.softech.reflection.SampleDataGenerator$Person|weight").getValue(p),
                    (Integer) name2MetaData.get("pl.softech.reflection.SampleDataGenerator$Person|age").getValue(p)
            );
        }

        name2MetaData = factory.class2MetaDataByFullPath(Driver.class);

        assertEquals(4, name2MetaData.size());

        for(Driver d : drivers) {

            Driver.assertDriver(d,
                    (String) name2MetaData.get("pl.softech.reflection.SampleDataGenerator$Driver|lastname").getValue(d),
                    (Float) name2MetaData.get("pl.softech.reflection.SampleDataGenerator$Driver|weight").getValue(d),
                    (Integer) name2MetaData.get("pl.softech.reflection.SampleDataGenerator$Driver|age").getValue(d),
                    (Boolean) name2MetaData.get("pl.softech.reflection.SampleDataGenerator$Driver|hasDrivingLicense").getValue(d)
            );

        }

        name2MetaData = factory.class2MetaDataByFullPath(Car.class);

        assertEquals(10, name2MetaData.size());

        for(Car c : cars) {
            Driver.assertDriver(c.getDriver(),
                    (String) name2MetaData.get("pl.softech.reflection.SampleDataGenerator$Car|driver|lastname").getValue(c),
                    (Float) name2MetaData.get("pl.softech.reflection.SampleDataGenerator$Car|driver|weight").getValue(c),
                    (Integer) name2MetaData.get("pl.softech.reflection.SampleDataGenerator$Car|driver|age").getValue(c),
                    (Boolean) name2MetaData.get("pl.softech.reflection.SampleDataGenerator$Car|driver|hasDrivingLicense").getValue(c)
            );
            Driver.assertDriver((Driver)name2MetaData.get("pl.softech.reflection.SampleDataGenerator$Car|driver").getValue(c),
                    (String) name2MetaData.get("pl.softech.reflection.SampleDataGenerator$Car|driver|lastname").getValue(c),
                    (Float) name2MetaData.get("pl.softech.reflection.SampleDataGenerator$Car|driver|weight").getValue(c),
                    (Integer) name2MetaData.get("pl.softech.reflection.SampleDataGenerator$Car|driver|age").getValue(c),
                    (Boolean) name2MetaData.get("pl.softech.reflection.SampleDataGenerator$Car|driver|hasDrivingLicense").getValue(c)
            );
            Engine.assertEngine(c.getEngine(),
                    (String) name2MetaData.get("pl.softech.reflection.SampleDataGenerator$Car|engine|productName").getValue(c));
            Engine.assertEngine((Engine)name2MetaData.get("pl.softech.reflection.SampleDataGenerator$Car|engine").getValue(c),
                    (String) name2MetaData.get("pl.softech.reflection.SampleDataGenerator$Car|engine|productName").getValue(c));

            Oil.assertOil(c.getEngine().oil, 
                    (String) name2MetaData.get("pl.softech.reflection.SampleDataGenerator$Car|engine|oil|vendorName").getValue(c),
                    (String) name2MetaData.get("pl.softech.reflection.SampleDataGenerator$Car|engine|oil|producentName").getValue(c));
            Oil.assertOil((Oil) name2MetaData.get("pl.softech.reflection.SampleDataGenerator$Car|engine|oil").getValue(c),
                    (String) name2MetaData.get("pl.softech.reflection.SampleDataGenerator$Car|engine|oil|vendorName").getValue(c),
                    (String) name2MetaData.get("pl.softech.reflection.SampleDataGenerator$Car|engine|oil|producentName").getValue(c));
        }

    }

}