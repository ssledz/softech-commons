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
package pl.softech.forecast;

import java.util.Arrays;
import static pl.softech.math.stat.Stat.*;


/**
 * Winters model can be used for time series 
 * describing the developmental trends, seasonal variations and random fluctuations
 * 
 * http://en.wikipedia.org/wiki/Exponential_smoothing 
 * 
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 * @since 1.0
 * 
 */
public class WintersModel {

	/**
         * Smoothed value of the forecasted variable at time t
	 * (Wygładzona wartość zmiennej prognozowanej na moment t)
	 */
	double[] f;
	/**
         *  Increment smoothed trend at the moment t
	 *  (Wygładzona wartość przyrostu trendu na moment t)
	 */
	double[] s;
	/**
	 * Seasonality index rating for the moment t
         * (Ocena wskaźnika sezonowości na moment t)
	 */
	double[] c;
	
	/**
	 * Estimation
	 */
	private double[] yEst;
	
	/**
	 * Time series
	 */
	private final double[] y;
	
	/**
	 * Number of moments forming a seasonal cycle
	 */
	private int r;
	
	public WintersModel(double[] y) {
		this.y = y;
		
		this.yEst = new double[y.length];
		this.f = new double[y.length];
		this.s = new double[y.length];
		this.c = new double[y.length];
	}

	private void init() {
		Arrays.fill(f, 0);
		Arrays.fill(s, 0);
		Arrays.fill(c, 0);
		Arrays.fill(yEst, 0);
	}
	
	public int guessR() {
		double[] acorr = acorr(y);
		int last = 0;
		int sum = 0;
		int counter = 0;
		for(int i = 1; i < acorr.length - 1; i++) {
			
			if(acorr[i] - acorr[i - 1] > 0 && acorr[i + 1] - acorr[i] < 0) {
				sum += i - last;
				last = i;
				counter++;
			}
			
		}
		
		return Math.round(sum / counter);
	}
	
	public WintersModel compute(double alpha, double beta, double gamma) {
	
		init();
		
		r = guessR();
		
		f[r - 1] = sum(y, 0, r) / r;
		s[r - 1] = sum(y, r, 2 * r) / r - f[r - 1];
		
		for(int i = 0; i < r; i++) {
			yEst[i] = y[i];
			c[i] = y[i] - f[r - 1];
		}
		
		for(int i = r; i < y.length; i++) {
			f[i] = alpha * (y[i] - c[i - r]) + (1 - alpha) * (f[i - 1] + s[i - 1]);
		    s[i] = beta * (f[i] - f[i - 1]) + (1 - beta) * s[i - 1];
		    c[i] = gamma * (y[i] - f[i]) + (1 - gamma) * c[i - r];
		    yEst[i] = f[i - 1] + s[i - 1] + c[i - r]; 
		}
		
		return this;
	}
	
	/**
	 * Square mean error
	 */
	public double error() {
		return Math.sqrt(sum(power(minus(yEst, y), 2),r, y.length) / (y.length - r));
	}
	
	public double[] predict(int n) {
		
		double[] ret = Arrays.copyOf(yEst, yEst.length + n);
		
		int last = y.length - 1;
		
		for(int i = y.length; i < ret.length; i++) {
			
			int tmp = (i - last) % r;
			ret[i] = f[last] + s[last] * (i - last) + c[last] * (tmp == 0 ? r : tmp);
			
		}
		
		return ret;
	}
	
}
