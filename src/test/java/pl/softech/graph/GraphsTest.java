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

        directedGraph.addEdges(new Edge(0, 1));
        directedGraph.addEdges(new Edge(1, 5), new Edge(1, 3));
        directedGraph.addEdges(new Edge(3, 4), new Edge(3, 2));
        directedGraph.addEdges(new Edge(4, 5));

        graph.addEdges(new Edge(0, 1), new Edge(0, 2));
        graph.addEdges(new Edge(1, 0), new Edge(1, 2));
        graph.addEdges(new Edge(2, 3), new Edge(2, 0), new Edge(2, 1));
        graph.addEdges(new Edge(3, 4), new Edge(3, 2));
        graph.addEdges(new Edge(4, 3));



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

        lgraph.addEdges(new Edge(0, 1));
        lgraph.addEdges(new Edge(1, 2), new Edge(1, 4), new Edge(1, 5));
        lgraph.addEdges(new Edge(2, 3), new Edge(2, 6));
        lgraph.addEdges(new Edge(3, 2), new Edge(3, 7));
        lgraph.addEdges(new Edge(4, 0), new Edge(4, 5));
        lgraph.addEdges(new Edge(5, 6));
        lgraph.addEdges(new Edge(6, 5), new Edge(6, 7));
        lgraph.addEdges(new Edge(7, 7));

        Vertex[] vertices = Graphs.scc(lgraph);

        assertArrayEquals(new Integer[]{0, 4, 1, 2, 3, 6, 5, 7},
                Arrays.transform(vertices, new IFunction<Vertex, Integer>() {
            @Override
            public Integer apply(Vertex input) {
                return input.index;
            }
        }));

        //4 strongly connected components
        assertArrayEquals(new Integer[]{null, 0, 4, null, 2, null, 6, null},
                Arrays.transform(vertices, new IFunction<Vertex, Integer>() {
            @Override
            public Integer apply(Vertex input) {
                return input.parent == null ? null : input.parent.index;
            }
        }));

    }

    @Test
    public void testMstKruskal() {

        class WeightedEdge extends Edge {

            int weight;

            public WeightedEdge(int uVertexIndex, int vVertexIndex, int weight) {
                super(uVertexIndex, vVertexIndex);
                this.weight = weight;
            }
        }

        Graph<Vertex, WeightedEdge> lgraph = new Graph<Vertex, WeightedEdge>(9);
        for (int i = 0; i < 9; i++) {
            lgraph.addVertex(new Vertex());
        }

        lgraph.addEdges(new WeightedEdge(0, 1, 4), new WeightedEdge(0, 7, 8));
        lgraph.addEdges(new WeightedEdge(1, 0, 4), new WeightedEdge(1, 2, 8), new WeightedEdge(1, 7, 11));
        lgraph.addEdges(new WeightedEdge(2, 1, 8), new WeightedEdge(2, 3, 7), new WeightedEdge(2, 5, 4), new WeightedEdge(2, 8, 2));
        lgraph.addEdges(new WeightedEdge(3, 2, 7), new WeightedEdge(3, 4, 9), new WeightedEdge(3, 5, 14));
        lgraph.addEdges(new WeightedEdge(4, 3, 9), new WeightedEdge(4, 5, 10));
        lgraph.addEdges(new WeightedEdge(5, 2, 4), new WeightedEdge(5, 3, 14), new WeightedEdge(5, 4, 10), new WeightedEdge(5, 6, 2));
        lgraph.addEdges(new WeightedEdge(6, 5, 2), new WeightedEdge(6, 7, 1), new WeightedEdge(6, 8, 6));
        lgraph.addEdges(new WeightedEdge(7, 0, 8), new WeightedEdge(7, 1, 11), new WeightedEdge(7, 6, 1), new WeightedEdge(7, 8, 7));
        lgraph.addEdges(new WeightedEdge(8, 2, 2), new WeightedEdge(8, 6, 6), new WeightedEdge(8, 7, 7));

        List<WeightedEdge> edges = Graphs.mstKruskal(lgraph, new Comparator<WeightedEdge>() {
            @Override
            public int compare(WeightedEdge o1, WeightedEdge o2) {
                return o1.weight - o2.weight;
            }
        });

        int weight = 0;
        for (WeightedEdge we : edges) {
//            System.out.println(String.format("%d -> %d (%d)", we.uVertexIndex, we.vVertexIndex, we.weight));
            weight += we.weight;
        }

        assertArrayEquals(new Integer[][] {
            //{ u, v, weight(u,v) }
            {6, 7, 1},
            {2, 8, 2},
            {6, 5, 2},
            {0, 1, 4},
            {2, 5, 4},
            {3, 2, 7},
            {1, 2, 8},
            {3, 4, 9}
        }, Arrays.transform(edges.toArray(new WeightedEdge[0]), new IFunction<WeightedEdge, Integer[]>() {
            
            @Override
            public Integer[] apply(WeightedEdge input) {
                return new Integer[]{input.uVertexIndex, input.vVertexIndex, input.weight};
            }
            
        }));
        
        assertEquals(37, weight);

    }
}