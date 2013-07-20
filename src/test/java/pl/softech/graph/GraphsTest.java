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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import pl.softech.util.Arrays;
import pl.softech.util.IFunction;

/**
 *
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 */
public class GraphsTest {

    private int vertexQuantity = 6;
    private Graph<Vertex, Edge> graph = new Graph<Vertex, Edge>(vertexQuantity);
    private Graph<Vertex, Edge> directedGraph = new Graph<Vertex, Edge>(vertexQuantity);

    public GraphsTest() {

        for (int i = 0; i < vertexQuantity; i++) {
            graph.addVertex(new Vertex());
            directedGraph.addVertex(new Vertex());
        }

        directedGraph.addEdges(0, new Edge(1));
        directedGraph.addEdges(1, new Edge(5), new Edge(3));
        directedGraph.addEdges(3, new Edge(4), new Edge(2));
        directedGraph.addEdges(4, new Edge(5));

        graph.addEdges(0, new Edge(1), new Edge(2));
        graph.addEdges(1, new Edge(0), new Edge(2));
        graph.addEdges(2, new Edge(3), new Edge(0), new Edge(1));
        graph.addEdges(3, new Edge(4), new Edge(2));
        graph.addEdges(4, new Edge(3));



    }

    /**
     * Test of Breadth-first search method, of class Graphs.
     */
    @Test
    public void testBfs() {

        int[][] src2Distance = {
            {0, 1, 1, 2, 3, Vertex.MAX_DISTANCE},
            {1, 0, 1, 2, 3, Vertex.MAX_DISTANCE},
            {1, 1, 0, 1, 2, Vertex.MAX_DISTANCE},
            {2, 2, 1, 0, 1, Vertex.MAX_DISTANCE},
            {3, 3, 2, 1, 0, Vertex.MAX_DISTANCE},
            {Vertex.MAX_DISTANCE, Vertex.MAX_DISTANCE, Vertex.MAX_DISTANCE,
                Vertex.MAX_DISTANCE, Vertex.MAX_DISTANCE, 0}
        };

        for (Vertex v : graph) {
            Graphs.bfs(graph, v.getIndex());
            int[] distance = new int[vertexQuantity];
            for (Vertex u : graph) {
                distance[u.getIndex()] = u.getDistance();
            }
            assertArrayEquals(src2Distance[v.getIndex()], distance);
        }


    }

    /**
     * Test of dfs method, of class Graphs.
     */
    @Test
    public void testDfs() {

        final int[] expectedVisited = {0, 1, 2, 3, 4, 5};

        Graphs.dfs(graph);

        List<Vertex> vs = new ArrayList<Vertex>();

        for (Vertex v : graph) {
            vs.add(v);
        }

        Collections.sort(vs, new Comparator<Vertex>() {
            @Override
            public int compare(Vertex o1, Vertex o2) {
                return o1.startTime - o2.startTime;
            }
        });

        int i = 0;
        for (Vertex v : vs) {
            assertEquals(expectedVisited[i++], v.index);
        }

    }

    @Test
    public void testTopologicalSort() {

        //topological sorted vertices
        final Integer[] expected = {0, 1, 3, 2, 4, 5};

        Vertex[] vertices = Graphs.topologicalSort(directedGraph);

        assertArrayEquals(expected, Arrays.transform(vertices, new IFunction<Vertex, Integer>() {
            @Override
            public Integer apply(Vertex input) {
                return input.index;
            }
        }));

    }

    @Test
    public void testScc() {

        Graph<Vertex, Edge> lgraph = new Graph<Vertex, Edge>(8);
        for (int i = 0; i < 8; i++) {
            lgraph.addVertex(new Vertex());
        }

        lgraph.addEdges(0, new Edge(1));
        lgraph.addEdges(1, new Edge(2), new Edge(4), new Edge(5));
        lgraph.addEdges(2, new Edge(3), new Edge(6));
        lgraph.addEdges(3, new Edge(2), new Edge(7));
        lgraph.addEdges(4, new Edge(0), new Edge(5));
        lgraph.addEdges(5, new Edge(6));
        lgraph.addEdges(6, new Edge(5), new Edge(7));
        lgraph.addEdges(7, new Edge(7));

        Vertex[] vertices = Graphs.scc(lgraph);

        assertArrayEquals(new Integer[]{0, 4, 1, 2, 3, 6, 5, 7},
                
                Arrays.transform(vertices, new IFunction<Vertex, Integer>() {
                    
                    @Override
                    public Integer apply(Vertex input) {
                        return input.index;
                    }
                })
        );

        //4 strongly connected components
        assertArrayEquals(new Integer[]{null, 0, 4, null, 2, null, 6, null}, 
                
                Arrays.transform(vertices, new IFunction<Vertex, Integer>() {
                    
                    @Override
                    public Integer apply(Vertex input) {
                        return input.parent == null ? null : input.parent.index;
                    }
                })
        );

    }
}