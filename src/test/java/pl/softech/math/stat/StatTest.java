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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * 
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 * @since 1.0
 * 
 */
public class StatTest {

	@Test
	public void testSum() {

		double[] arg = { 1, 3, 5, 6, -1, -3, 3, 44, 1, 2, 3, -3 };

		assertEquals(61, Stat.sum(arg), 0.0);

	}

	@Test
	public void testMean() {

		double[] arg = { 1, 3, 5, 6, -1, -3, 3, 44, 1, 2, 3, -3 };

		assertEquals(5.0833, Stat.mean(arg), 0.0001);
	}

	@Test
	public void testVar() {

		double[] arg = { 1, 3, 5, 6, -1, -3, 3, 44, 1, 2, 3, -3 };

		assertEquals(158.08, Stat.var(arg), 0.01);
	}

	@Test
	public void testStdev() {

		double[] arg = { 1, 3, 5, 6, -1, -3, 3, 44, 1, 2, 3, -3 };

		assertEquals(12.573, Stat.stdev(arg), 0.001);
	}

	@Test
	public void testCov() {

		double[] arg1 = { 1, 3, 5, 6, -1, -3, 3, 44, 1, 2, 3, -3 };
		double[] arg2 = { 2, 4, 5, 2, -1, -3, 1, 22, 3, 3, 1, -1 };

		assertEquals(77.621, Stat.cov(arg1, arg2), 0.001);
	}

	@Test
	public void testCorr() {

		double[] arg1 = { 1, 3, 5, 6, -1, -3, 3, 44, 1, 2, 3, -3 };
		double[] arg2 = { 2, 4, 5, 2, -1, -3, 1, 22, 3, 3, 1, -1 };

		assertEquals(0.97209, Stat.corr(arg1, arg2), 0.00001);
	}

	@Test
	public void testAcorr() {

		double[] arg = { 508.14, 321.83, 208.50, 409.02, 441.29, 352.59,
				216.50, 328.85, 345.18, 200.07, 157.15, 380.99, 609.92, 328.64,
				250.50, 528.02, 529.84, 407.81, 344.14, 561.03, 755.41, 527.67,
				389.55, 658.47 };

		double[] expected = { 0.958333, 0.307123, -0.135146, 0.272861,
				0.495310, 0.083910, -0.271265, 0.062640, 0.337270, -0.122279,
				-0.425111, -0.110864, 0.024621, -0.244454, -0.332082,
				-0.087027, 0.025337, -0.118734, -0.181279, -0.051741, 0.051434,
				-0.063979, -0.040872, 0.045160 };

		assertArrayEquals(expected, Stat.acorr(arg), 0.000001);

	}

}
