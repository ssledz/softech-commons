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
package pl.softech.math;

import java.util.Arrays;

/**
 * f = a0 + a1x + a2x^2 + a3x^3 + ... + anx^n factors = [a0 a2 a3 ... an]
 * 
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 * @since 1.0
 */
public class Polynomial implements Cloneable {

    private double[] factors;

    public Polynomial(int size) {
        this.factors = new double[size];
    }

    public Polynomial(double[] factors) {
        this.factors = factors;
    }

    public int rank() {

        if (factors[factors.length - 1] != 0) {
            return factors.length - 1;
        }

        for (int i = factors.length - 2; i > 0; i--) {
            if (factors[i] == 0) {
                return i;
            }
        }

        return 0;
    }

    public static Polynomial plus(Polynomial p1, Polynomial p2) {

        Polynomial ret = new Polynomial(Arrays.copyOf(p1.factors, Math.max(p1.rank(), p2.rank()) + 1));

        for (int i = 0; i < ret.factors.length && i < p2.factors.length; i++) {
            ret.factors[i] += p2.factors[i];
        }
        return ret;
    }

    public static Polynomial plus(Polynomial... p) {

        Polynomial tmp = p[0];

        for (int i = 1; i < p.length; i++) {
            tmp = plus(tmp, p[i]);
        }

        return tmp;
    }

    public static Polynomial multiply(Polynomial... p) {

        Polynomial tmp = p[0];

        for (int i = 1; i < p.length; i++) {
            tmp = multiply(tmp, p[i]);
        }

        return tmp;
    }

    public static Polynomial multiply(Polynomial p1, Polynomial p2) {

        int size = 1 + p1.rank() + p2.rank();

        Polynomial ret = new Polynomial(size);

        for (int i = 0; i < size; i++) {

            // splot
            for (int j = i, k = 0; j >= 0; j--, k++) {

                if (k >= p1.factors.length || j >= p2.factors.length) {
                    continue;
                }

                ret.factors[i] += p2.factors[j] * p1.factors[k];
            }

        }

        return ret;
    }

    @Override
    public Polynomial clone() {

        Polynomial p = null;
        try {
            p = (Polynomial) super.clone();
            p.factors = factors.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return p;
    }

    @Override
    public String toString() {
        return "Polynomial [factors=" + Arrays.toString(factors) + "]";
    }

    public static void main(String[] args) {
        Polynomial p0 = new Polynomial(new double[] { 1 });
        Polynomial p1 = new Polynomial(new double[] { 0, 1 });
        Polynomial p2 = new Polynomial(new double[] { 1, 1 });
        Polynomial p3 = new Polynomial(new double[] { 1, 1, 1 });

        System.out.println(p1);
        System.out.println(p2);
        System.out.println(multiply(p1, p2));
        System.out.println(multiply(p1, p1, p1));
        System.out.println(multiply(p0, p0, p0));
        System.out.println(multiply(p1, p0, p2));
        System.out.println(multiply(p1, p3));
        System.out.println(multiply(p1, p3, p0));

        System.out.println(multiply(p1, p3, p0));
        
        System.out.println(plus(p0, p1, p2, p0));
        System.out.println(plus(p0, p1, p2, p3));

    }

}
