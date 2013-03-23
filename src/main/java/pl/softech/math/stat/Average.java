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


/**
 *
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 * @since 1.0
 */
public class Average {

    /**
     * Simple Moving Average
     */
    public static double[] sma(double[] arg, int count) {
        
        double[] ret = new double[arg.length];
        double sum = 0;
        int l = 0;
        for(;  l < arg.length && Double.isNaN(arg[l]); l++) {
            ret[l] = Double.NaN;
        }
        int i = l;
        for(; i <  l + count - 1 && i < arg.length; i++) {
            ret[i] = Double.NaN;
            sum += arg[i];
        }
        
        for(int j = l, k = i; k < arg.length; k++, j++) {
            sum += arg[k];
            ret[k] = sum/count;
            sum -= arg[j];
        }
     
        return ret;
    }
    
    /**
     * Linear Weighted Moving Average
     */
   public static double[] lwma(double[] arg, int count) {
        
        double[] ret = new double[arg.length];
        int div = 0;
        double weightedSum = 0;
        double sum = 0;
        
        int l = 0;
        for(; l < arg.length && Double.isNaN(arg[l]); l++) {
            ret[l] = Double.NaN;
        }
        
        int i = l;
        
        for(int m = 1; i < l + count - 1 && i < arg.length; i++, m++) {
            sum += arg[i];
            ret[i] =  Double.NaN;
            weightedSum += m * arg[i];
            
            div += m ;
        }
        
        div += count;
        
        for(int j = l, k = i; k < arg.length; k++, j++) {
            weightedSum += count * arg[k];
            ret[k] = weightedSum / div;
            sum += arg[k];
            weightedSum -= sum;
            sum -= arg[j];
        }
        
        return ret;
        
    }
    
    /**
     * Exponential Moving Average
     */
   public static double[] ema(double[] arg, double weight, int count) {
       
       double[] weights = new double[count];
       double[] ret = new double[arg.length];
       double div = 0;
       double weightedSum = 0;
       
       int l = 0;
       for(; l < arg.length && Double.isNaN(arg[l]); l++) {
           ret[l] = Double.NaN;
       }
       
       int i = l;
       
       for(int p = 0; i < l + count - 1 && i < arg.length; i++, p++) {
           
           weights[p] = Math.pow(weight, p + 1);
           ret[i] = Double.NaN;
           div += weights[p];
           weightedSum += weights[p] *  arg[i];
           
       }
       
       weights[count - 1] = Math.pow(weight, count);
       
       div += weights[count - 1];
       
       for(int j = l + 1, o = i; o < arg.length; o++, j++) {
           
           weightedSum += weights[count - 1] * arg[o];
           ret[o] = weightedSum / div;
           
           weightedSum = 0;
           for(int k = j, n = 0;  k < j + count - 1; k++, n++) {
               
               weightedSum += weights[n] * arg[k];
               
           } 
           
       }
       
       return ret;
       
   }
    
    /**
     * Volume Weighted Moving Average
     */
    public static double[] vwma(double[] arg, double[] volume, int count) {
        
        double[] ret = new double[arg.length];
        int div = 0;
        double weightedSum = 0;
        
        int l = 0;
        for(; l < arg.length && Double.isNaN(arg[l]); l++) {
            ret[l] = Double.NaN;
        }
        
        int i = l;
        
        for(; i < l + count - 1 && i < arg.length; i++) {
            ret[i] = Double.NaN;;
            weightedSum += volume[i] * arg[i];
            
            div += volume[i];
        }
        
        for(int j = l, k = i; k < arg.length; k++, j++) {
            div += volume[k];
            weightedSum += volume[k] * arg[k];
            ret[k] = weightedSum / div;
            
            weightedSum -= arg[j] * volume[j];
            div -= volume[j];
        }
        
        return ret;
        
    }
    
}
