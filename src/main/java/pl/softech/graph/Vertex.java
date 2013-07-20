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
package pl.softech.graph;

/**
 *
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 */
public class Vertex implements Cloneable {

    public static final int COLOUR_WHITE = 0;
    public static final int COLOUR_GREY = 1;
    public static final int COLOUR_BLACK = 2;
    public static final int MAX_DISTANCE = Integer.MAX_VALUE;
    protected int colour;
    protected int index;
    protected int distance;
    protected Vertex parent;
    /**
     * Label time, step number of calculations in which the vertex is visited
     */
    protected int startTime;
    /**
     * label time, step number of calculations in which the neighborhood list of vertices is examined
     */
    protected int endTime;

    public int getDistance() {
        return distance;
    }

    public int getIndex() {
        return index;
    }

    public void paintBlack() {
        colour = COLOUR_BLACK;
    }

    public void paintWhite() {
        colour = COLOUR_WHITE;
    }

    public void paintGrey() {
        colour = COLOUR_GREY;
    }

    @Override
    public Vertex clone() {
        Vertex v = null;
        try {
            v = (Vertex) super.clone();
            v.parent = null;
            v.distance = 0;
            v.colour = COLOUR_WHITE;
            v.startTime = 0;
            v.endTime = 0;
        } catch (CloneNotSupportedException ex) {
            ex.printStackTrace();
        }
        return v;
    }
}
