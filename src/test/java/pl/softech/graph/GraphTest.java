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

import java.util.Arrays;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 */
public class GraphTest {

    /**
     * Test of transpose method, of class Graph.
     */
    @Test
    public void testTranspose() {
        final Graph<Vertex, Edge> graph = new Graph<Vertex, Edge>(15);

        for (int i = 0; i < 5; i++) {
            graph.addVertex(new Vertex());
        }

        graph.addEdges(new Edge(0, 0), new Edge(0, 1), new Edge(0, 2));
        graph.addEdges(new Edge(1, 2));
        graph.addEdges(new Edge(2, 3));

        Runnable beforeTranspose = new Runnable() {
            @Override
            public void run() {

                assertEquals(0, graph.getEdges(3).size());

                assertEquals(1, graph.getEdges(2).size());
                assertEquals(3, graph.getEdges(2).get(0).vVertexIndex);

                assertEquals(1, graph.getEdges(1).size());
                assertEquals(2, graph.getEdges(1).get(0).vVertexIndex);

                assertEquals(3, graph.getEdges(0).size());
                int[] verticies = {
                    graph.getEdges(0).get(0).vVertexIndex,
                    graph.getEdges(0).get(1).vVertexIndex,
                    graph.getEdges(0).get(2).vVertexIndex
                };
                Arrays.sort(verticies);
                assertArrayEquals(new int[]{0, 1, 2}, verticies);

            }
        };

        beforeTranspose.run();

        graph.transpose();

        assertEquals(1, graph.getEdges(3).size());
        assertEquals(2, graph.getEdges(3).get(0).vVertexIndex);

        assertEquals(1, graph.getEdges(0).size());
        assertEquals(0, graph.getEdges(0).get(0).vVertexIndex);

        assertEquals(1, graph.getEdges(1).size());
        assertEquals(0, graph.getEdges(1).get(0).vVertexIndex);

        assertEquals(2, graph.getEdges(2).size());
        int[] verticies = {graph.getEdges(2).get(0).vVertexIndex, graph.getEdges(2).get(1).vVertexIndex};
        Arrays.sort(verticies);
        assertArrayEquals(new int[]{0, 1}, verticies);

        graph.transpose();
        beforeTranspose.run();

    }

    @Test
    public void testClone() {

        Graph<Vertex, Edge> graph = new Graph<Vertex, Edge>(15);

        for (int i = 0; i < 4; i++) {
            graph.addVertex(new Vertex());
        }
        
        graph.addEdges(new Edge(1, 0), new Edge(1, 2), new Edge(1, 3));
        graph.addEdges(new Edge(3, 1));
        
        Graph<Vertex, Edge> clone = graph.clone();
        
        assertEquals(graph.vertices.length, clone.vertices.length);
        assertEquals(graph.getVertexQuantity(), clone.getVertexQuantity());
        assertEquals(graph.getEdges(1).size(), clone.getEdges(1).size());
        assertEquals(graph.getEdges(3).size(), clone.getEdges(3).size());
        assertEquals(graph.getEdges(3).get(0).vVertexIndex, clone.getEdges(3).get(0).vVertexIndex);

    }
}