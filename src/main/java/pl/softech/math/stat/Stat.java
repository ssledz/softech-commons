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

import java.util.Arrays;

/**
 *
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 * @since 1.0
 */
public class Stat {

	public static double sum(double[] arg) {
		
		return sum(arg, 0, arg.length);
	}
	
	public static double sum(double[] arg, int from, int to) {
		double sum = 0;
		for (int i = from; i < to; i++) {
			sum += arg[i];
		}
		return sum;
	}

	public static double mean(double[] arg) {

		if (arg == null || arg.length == 0) {
			return Double.NaN;
		}

		return sum(arg) / arg.length;

	}

	private static double[] plus(double[] arg1, double[] arg2) {

		double[] arr = Arrays.copyOf(arg1, arg1.length);

		for (int i = 0; i < arr.length; i++) {
			arr[i] += arg2[i];
		}
		return arr;

	}

	public static double[] minus(double[] arg1, double[] arg2) {
		return plus(arg1, multiply(arg2, -1));
	}

	private static double[] multiply(double[] arg1, double arg2) {
		double[] arr = Arrays.copyOf(arg1, arg1.length);
		for (int i = 0; i < arr.length; i++) {
			arr[i] *= arg2;
		}
		return arr;
	}

	private static double[] multiply(double[] arg1, double[] arg2) {
		double[] arr = Arrays.copyOf(arg1, arg1.length);
		for (int i = 0; i < arr.length; i++) {
			arr[i] *= arg2[i];
		}
		return arr;
	}

	private static double[] minus(double[] arg1, double arg2) {
		double[] arr = Arrays.copyOf(arg1, arg1.length);
		for (int i = 0; i < arr.length; i++) {
			arr[i] -= arg2;
		}
		return arr;
	}

	public static double[] power(double[] arg1, double arg2) {
		double[] arr = Arrays.copyOf(arg1, arg1.length);
		for (int i = 0; i < arr.length; i++) {
			arr[i] = Math.pow(arr[i], arg2);
		}
		return arr;
	}

	/**
	 * Variation
	 */
	public static double var(double[] arg) {
		return sum(power(minus(arg, mean(arg)), 2)) / (arg.length - 1);
	}

	/**
	 * Standard deviation
	 */
	public static double stdev(double[] arg) {
		return Math.sqrt(var(arg));
	}

	/**
	 * Covariance. Bisased estimates
	 * 
	 * http://en.wikipedia.org/wiki/Covariance
	 */
	public static double biasedcov(double[] arg1, double[] arg2) {
		return mean(multiply(arg1, arg2)) - mean(arg1) * mean(arg2);
	}
	
	/**
	 * Covariance. Unbisased estimates
	 * 
	 * http://en.wikipedia.org/wiki/Covariance
	 */
	public static double cov(double[] arg1, double[] arg2) {
		return sum(multiply(minus(arg1, mean(arg1)), minus(arg2, mean(arg2))))
				/ (arg1.length - 1);
	}

	/**
	 * Correlation. 
	 * 
	 * http://en.wikipedia.org/wiki/Correlation
	 */
	public static double corr(double[] arg1, double[] arg2) {
		return cov(arg1, arg2) / Math.sqrt(var(arg1) * var(arg2));
	}

	/**
	 * Auto Correlation
	 * 
	 * http://en.wikipedia.org/wiki/Autocorrelation
	 */
	public static double[] acorr(double[] arg1) {
		double mean = mean(arg1);
		double var = var(arg1);
		double[] acorr = new double[arg1.length];
		for (int i = 0; i < arg1.length; i++) {
			acorr[i] = 0;
			for (int j = 0; j < arg1.length - i; j++) {
				acorr[i] += (arg1[j] - mean) * (arg1[j + i] - mean);
			}
			acorr[i] /= (arg1.length * var);
		}
		return acorr;
	}

}
