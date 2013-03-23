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
package pl.softech.math.stat;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 * @since 1.0
 * 
 */
public class AverageTest {

    @Test
    public void testSma() {
        
        double[] arg = {
                3567.31,
                3580.09,
                3554.52,
                3594.74,
                3609.24,
                3580.94,
                3649.1,
                3738.09,
                3747,
                3755.45
        };
        
        double[] expected5sum = {
                Double.NaN,
                Double.NaN,
                Double.NaN,
                Double.NaN,
                3581.18,
                3583.91,
                3597.71,
                3634.42,
                3664.87,
                3694.12
        };
        
        Assert.assertArrayEquals(expected5sum, Average.sma(arg, 5), 0.01);
        
        arg = new double[] {
                Double.NaN,
                Double.NaN,
                Double.NaN,
                3567.31,
                3580.09,
                3554.52,
                3594.74,
                3609.24,
                3580.94,
                3649.1,
                3738.09,
                3747,
                3755.45
        };
        
        expected5sum = new double[] {
                Double.NaN,
                Double.NaN,
                Double.NaN,
                Double.NaN,
                Double.NaN,
                Double.NaN,
                Double.NaN,
                3581.18,
                3583.91,
                3597.71,
                3634.42,
                3664.87,
                3694.12
        };
        
        Assert.assertArrayEquals(expected5sum, Average.sma(arg, 5), 0.01);
        
        arg = new double[] { 3567.31 };
        double[] expected1sum = { 3567.31 };
        double[] expected2sum = { Double.NaN };
        
        Assert.assertArrayEquals(expected1sum, Average.sma(arg, 1), 0.01);
        
        Assert.assertArrayEquals(expected2sum, Average.sma(arg, 2), 0.01);
        
    }
    
    @Test
    public void testLwma() {
        
        double[] arg = {
                3567.31,
                3580.09,
                3554.52,
                3594.74,
                3609.24,
                3580.94,
                3649.1,
                3738.09,
                3747,
                3755.45
        };
        
        double[] expected5sum = {
                Double.NaN,
                Double.NaN,
                Double.NaN,
                Double.NaN,
                3587.75,
                3587.67,
                3609.40,
                3656.19,
                3693.72,
                3723.91

                
        };
        
        Assert.assertArrayEquals(expected5sum, Average.lwma(arg, 5), 0.01);
        
        arg = new double[] {
                Double.NaN,
                Double.NaN,
                3567.31,
                3580.09,
                3554.52,
                3594.74,
                3609.24,
                3580.94,
                3649.1,
                3738.09,
                3747,
                3755.45
        };
        
        expected5sum = new double[] {
                Double.NaN,
                Double.NaN,
                Double.NaN,
                Double.NaN,
                Double.NaN,
                Double.NaN,
                3587.75,
                3587.67,
                3609.40,
                3656.19,
                3693.72,
                3723.91
        };
        
        Assert.assertArrayEquals(expected5sum, Average.lwma(arg, 5), 0.01);
        
        arg = new double[] { 3567.31 };
        double[] expected1sum = { 3567.31 };
        double[] expected2sum = { Double.NaN };
        
        Assert.assertArrayEquals(expected1sum, Average.lwma(arg, 1), 0.01);
        
        Assert.assertArrayEquals(expected2sum, Average.lwma(arg, 2), 0.01);
        
    }
    
    @Test
    public void testEma() {
        
        double[] arg = {
                3567.31,
                3580.09,
                3554.52,
                3594.74,
                3609.24,
                3580.94,
                3649.1,
                3738.09,
                3747,
                3755.45
        };
        
        double[] expected5sum = {
                Double.NaN,
                Double.NaN,
                Double.NaN,
                Double.NaN,
                3608.42,
                3582.31,
                3645.76,
                3733.47,
                3746.32,
                3754.99

        };
        
        Assert.assertArrayEquals(expected5sum, Average.ema(arg, 20, 5), 0.01);
        
        arg = new double[] {
                Double.NaN,
                Double.NaN,
                Double.NaN,
                Double.NaN,
                Double.NaN,
                Double.NaN,
                3567.31,
                3580.09,
                3554.52,
                3594.74,
                3609.24,
                3580.94,
                3649.1,
                3738.09,
                3747,
                3755.45
        };
        
        expected5sum = new double[] {
                Double.NaN,
                Double.NaN,
                Double.NaN,
                Double.NaN,
                Double.NaN,
                Double.NaN,
                
                Double.NaN,
                Double.NaN,
                Double.NaN,
                Double.NaN,
                3608.42,
                3582.31,
                3645.76,
                3733.47,
                3746.32,
                3754.99

        };
        
        Assert.assertArrayEquals(expected5sum, Average.ema(arg, 20, 5), 0.01);
        
        arg = new double[] { 3567.31 };
        double[] expected1sum = { 3567.31 };
        double[] expected2sum = { Double.NaN };
        
        Assert.assertArrayEquals(expected1sum, Average.ema(arg, 20, 1), 0.01);
        
        Assert.assertArrayEquals(expected2sum, Average.ema(arg, 20, 2), 0.01);
        
    }

    @Test
    public void testVwma() {
        
        double[][] arg = {
                {
                    3567.31, 3580.09, 3554.52, 3594.74, 3609.24, 
                    3580.94, 3649.10, 3738.09, 3747.00, 3755.45
                },
                {
                    1000, 1180, 23554, 32594, 36092, 
                    13580, 3149, 138, 1747, 4135
                }
        };
        
        double[] expected5sum = {
                Double.NaN,
                Double.NaN,
                Double.NaN,
                Double.NaN,
                3589.78,
                3588.86,
                3590.70,
                3600.90,
                3609.23,
                3635.80

        };
        
        Assert.assertArrayEquals(expected5sum, Average.vwma(arg[0],arg[1], 5), 0.01);
        
        double[][] arg2 = {
                {
                    Double.NaN, Double.NaN, 3567.31, 3580.09, 3554.52, 3594.74, 3609.24, 
                    3580.94, 3649.10, 3738.09, 3747.00, 3755.45
                },
                {
                    Double.NaN, Double.NaN, 1000, 1180, 23554, 32594, 36092, 
                    13580, 3149, 138, 1747, 4135
                }
        };
        
        expected5sum = new double[] {
                Double.NaN,
                Double.NaN,
                
                Double.NaN,
                Double.NaN,
                Double.NaN,
                Double.NaN,
                3589.78,
                3588.86,
                3590.70,
                3600.90,
                3609.23,
                3635.80

        };
        
        Assert.assertArrayEquals(expected5sum, Average.vwma(arg2[0],arg2[1], 5), 0.01);
        
        arg = new double[][] { {3567.31}, {1000} };
        double[] expected1sum = { 3567.31 };
        double[] expected2sum = { Double.NaN };
        
        Assert.assertArrayEquals(expected1sum, Average.vwma(arg[0], arg[1], 1), 0.01);
        
        Assert.assertArrayEquals(expected2sum, Average.vwma(arg[0], arg[1], 2), 0.01);
        
    }
    
}
