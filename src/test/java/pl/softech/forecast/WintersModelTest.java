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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * 
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 * @since 1.0
 * 
 */
public class WintersModelTest {

	@Test
	public void testPredict() {

		double[] y = { 508.14, 321.83, 208.50, 409.02, 441.29, 352.59, 216.50,
				328.85, 345.18, 200.07, 157.15, 380.99, 609.92, 328.64, 250.50,
				528.02, 529.84, 407.81, 344.14, 561.03, 755.41, 527.67, 389.55,
				658.47 };

		double[] yEst = { 508.140, 321.830, 208.500, 409.020, 481.075, 239.055,
				175.631, 387.362, 431.043, 162.579, 45.607, 300.301, 444.766,
				374.298, 244.844, 460.707, 620.242, 388.869, 298.355, 543.292,
				672.129, 564.856, 453.820, 632.671, 668.140, 735.276, 802.413,
				869.550 };

		double[] f = { 0, 0, 0, 361.87, 310.94, 347.22, 353.52,
				305.11, 233.26, 225.12, 265.91, 301.57, 397.59, 386.95, 401.61,
				453.95, 419.73, 440.28, 479.20, 506.79, 575.83, 582.59, 568.63,
				601.00 };

		double[] s = { 0, 0, 0, -27.0650, -31.8392,
				-18.2150, -13.3107, -20.3321, -30.6356, -26.1367, -12.7516,
				-3.0689, 16.7496, 11.2707, 11.9494, 20.0270, 9.1788, 11.4516,
				16.9458, 19.0743, 29.0681, 24.6058, 16.8934, 19.9892 };

		double[] c = { 146.267, -40.043, -153.373, 47.147, 146.267, -40.043,
				-153.373, 47.147, 146.267, -40.043, -153.373, 47.147, 146.267,
				-40.043, -153.373, 47.147, 146.267, -40.043, -153.373, 47.147,
				146.267, -40.043, -153.373, 47.147 };

		WintersModel wm = new WintersModel(y);
		double[] pred = wm.compute(0.60000, 0.20000, 0).predict(4);

		assertArrayEquals(f, wm.f, 0.01);
		assertArrayEquals(s, wm.s, 0.01);
		assertArrayEquals(c, wm.c, 0.01);
		
		assertEquals(4, wm.guessR());
		assertEquals(72.552691, wm.error(), 0.00001);
		
		assertEquals(y.length + 4, pred.length);

		assertArrayEquals(yEst, pred, 0.01);

	}

}
