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

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Random;
import static junit.framework.Assert.*;

/**
 *
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 * @since 1.0
 */
public class SampleDataGenerator {

    @Retention(RetentionPolicy.RUNTIME)
    public @interface SampleMark {

        public String name() default "";

        public int order() default -1;

        public boolean visible() default false;
    }

    public static class Vehicle {

        protected Engine engine;

        public Vehicle(Engine motor) {
            this.engine = motor;
        }
    }

    public static class Oil {

        private static final String[] PRODUCENT_NAME = {
            "Sample Company 1", "Sample Company 2", "Sample Company 3",
            "Sample Company 3"
        };

        public static String getSampleVendorName(Random random) {
            StringBuffer buff = new StringBuffer();
            for (int i = 0; i < 5 + random.nextInt(10); i++) {
                buff.append((char) ('a' + random.nextInt('z' - 'a')));
            }
            return buff.toString();
        }
        @SampleMark
        public String vendorName;
        @SampleMark
        public String producentName;

        public Oil() {
        }

        public Oil(String vendorName, String producentName) {
            this.vendorName = vendorName;
            this.producentName = producentName;
        }

        public static void assertOil(Oil o, String vendorName, String productName) {

            assertEquals(o.vendorName, vendorName);
            assertEquals(o.producentName, productName);

        }

        public static void main(String[] args) {

            System.out.println(getSampleVendorName(new Random()));

        }
    }

    public static class Engine {

        private static final String[] NAME = {
            "Sample Name 1", "Sample Name 2", "Sample Name 3", "Sample Name 4"
        };
        @SampleMark
        public String productName;
        @SampleMark
        public Oil oil;

        public Engine(String name, Oil oil) {
            this.productName = name;
            this.oil = oil;
        }

        public static void assertEngine(Engine e, String productName) {
            assertEquals(e.productName, productName);
        }
    }

    public static class Car extends Vehicle {

        @SampleMark(name = "Very nervous Driver", order = 10)
        public Driver driver;

        public Car(Engine engine, Driver driver) {
            super(engine);
            this.driver = driver;
        }

        public Driver getDriver() {
            return driver;
        }

        @SampleMark
        public Engine getEngine() {
            return engine;
        }

        public void setEngine(Engine engine) {
            this.engine = engine;
        }
    }

    public static class Driver extends Person {

        @SampleMark(name = "Has driver Driving Licence", visible = true)
        public boolean hasDrivingLicense;

        public Driver(String firstname, String lastname, int age, boolean hasDrivingLicense) {
            super(firstname, lastname, age);
            this.hasDrivingLicense = hasDrivingLicense;
        }

        public static void assertDriver(Driver d, String lastname, float weight,
                int age, boolean hasDrivingLicense) {

            assertPerson(d, lastname, weight, age);
            assertEquals(d.hasDrivingLicense, hasDrivingLicense);

        }
    }

    public static class Person {

        private static final String[] FIRST_NAMES = {
            "Alan", "Monika", "Wojtek", "Grzesiek", "Bogdan",
            "Slawek", "Ewa", "Adam", "Tomek", "Jurek", "Bozena",
            "Krysia", "Marysia"
        };
        private static final String[] LAST_NAME = {
            "Rock", "Mandi", "Rosol", "Green", "Black", "Beer"
        };
        private static final int MAX_AGE = 100;
        private static final int MIN_AGE = 10;
        private static final int MAX_WEIGHT = 100;
        private static final int MIN_WEIGHT = 30;
        protected String firstname;
        protected String lastname;
        protected int age;
        @SampleMark(name = "Weight in kg", order = 5)
        public float weight;

        public Person(String firstname, String lastname, int age, float weight) {
            this(firstname, lastname, age);
            this.weight = weight;
        }

        public Person(String firstname, String lastname, int age) {
            this.firstname = firstname;
            this.lastname = lastname;
            this.age = age;
        }

        @SampleMark(visible = true)
        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        @SampleMark(name = "Age in Years", visible = true)
        public int getAge() {
            return age;
        }

        public static void assertPerson(Person p, String lastname, float weight,
                int age) {

            assertEquals(p.lastname, lastname);
            assertEquals(p.weight, weight);
            assertEquals(p.age, age);
        }
    }

    public static float getSampleWeight(Random rand, float exclude) {

        do {

            float weight =
                    Person.MIN_WEIGHT + rand.nextInt(Person.MAX_WEIGHT - Person.MIN_WEIGHT + 1);

            if (weight != exclude) {
                return weight;
            }

        } while (true);

    }

    public static int getSampleAge(Random rand, int exclude) {

        do {

            int age = Person.MIN_AGE + rand.nextInt(Person.MAX_AGE - Person.MIN_AGE + 1);
            if (age != exclude) {
                return age;
            }

        } while (true);

    }

    private static String getSampleTabString(Random rand, String[] tab, String exclude) {

        do {

            String lastName = tab[rand.nextInt(tab.length)];
            if (!lastName.equals(exclude)) {
                return lastName;
            }

        } while (true);

    }

    public static String getSampleProductName(Random rand, String exclude) {

        return getSampleTabString(rand, Engine.NAME, exclude);

    }

    public static String getSampleLastName(Random rand, String exclude) {

        return getSampleTabString(rand, Person.LAST_NAME, exclude);

    }

    public static Collection<Person> getSamplePersons(int quantity, Random rand) {
        Collection<Person> ret = new LinkedList<Person>();
        for (int i = 0; i < quantity; i++) {
            ret.add(new Person(
                    Person.FIRST_NAMES[rand.nextInt(Person.FIRST_NAMES.length)],
                    Person.LAST_NAME[rand.nextInt(Person.LAST_NAME.length)],
                    Person.MIN_AGE + rand.nextInt(Person.MAX_AGE - Person.MIN_AGE + 1),
                    Person.MIN_WEIGHT + rand.nextInt(Person.MAX_WEIGHT - Person.MIN_WEIGHT + 1)));
        }
        return ret;
    }

    public static Collection<Driver> getSampleDrivers(int quantity, Random rand) {

        Collection<Driver> ret = new LinkedList<Driver>();
        for (int i = 0; i < quantity; i++) {
            ret.add(new Driver(
                    Person.FIRST_NAMES[rand.nextInt(Person.FIRST_NAMES.length)],
                    Person.LAST_NAME[rand.nextInt(Person.LAST_NAME.length)],
                    Person.MIN_AGE + rand.nextInt(Person.MAX_AGE - Person.MIN_AGE + 1),
                    rand.nextBoolean()));
        }
        return ret;

    }

    public static Collection<Oil> getSampleOil(int quantity, Random rand) {
        Collection<Oil> ret = new LinkedList<Oil>();
        for (int i = 0; i < quantity; i++) {
            ret.add(new Oil(
                    Oil.getSampleVendorName(rand),
                    Oil.PRODUCENT_NAME[rand.nextInt(Oil.PRODUCENT_NAME.length)]));
        }

        return ret;
    }

    public static Collection<Engine> getSampleEngine(int quantity, Random rand) {
        Collection<Engine> ret = new LinkedList<Engine>();
        for (int i = 0; i < quantity; i++) {
            for (Oil o : getSampleOil(quantity, rand)) {
                ret.add(new Engine(
                        Engine.NAME[rand.nextInt(Engine.NAME.length)],
                        o));
            }
        }
        return ret;
    }

    public static Collection<Car> getSampleCars(int quantity, Random rand) {

        Collection<Car> ret = new LinkedList<Car>();
        for (int i = 0; i < quantity; i++) {
            for (Driver driver : getSampleDrivers(quantity, rand)) {
                for (Engine e : getSampleEngine(quantity, rand)) {
                    ret.add(new Car(e, driver));
                }
            }
        }

        return ret;
    }
}
