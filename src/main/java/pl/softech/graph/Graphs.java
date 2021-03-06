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

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import pl.softech.collection.IHeap;
import pl.softech.collection.MaxHeap;
import pl.softech.collection.MinHeap;
import pl.softech.collection.PriorityQueue;

/**
 *
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 */
public class Graphs {

    /**
     * Breadth-first search
     */
    public static void bfs(Graph<Vertex, Edge> grapf, int vertexIndex) {

        for (Vertex v : grapf.vertices) {
            v.paintWhite();
            v.parent = null;
            v.distance = Vertex.MAX_DISTANCE;
        }

        Vertex s = grapf.vertices[vertexIndex];
        s.distance = 0;
        s.parent = null;
        Queue<Vertex> q = new LinkedList<Vertex>();
        q.add(s);

        while (!q.isEmpty()) {

            Vertex u = q.remove();
            for (Edge e : grapf.getEdges(u.index)) {

                Vertex v = grapf.vertices[e.vVertexIndex];
                if (v.colour == Vertex.COLOUR_WHITE) {
                    v.paintGrey();
                    v.distance = u.distance + 1;
                    v.parent = u;
                    q.add(v);
                }
            }
            u.paintBlack();
        }

    }

    private static void dfs(Vertex vertex, Graph<Vertex, Edge> graph) {

        vertex.startTime = ++graph.time;
        vertex.paintGrey();

        for (Edge e : graph.getEdges(vertex.index)) {

            Vertex v = graph.vertices[e.vVertexIndex];
            if (v.colour == Vertex.COLOUR_WHITE) {
                v.parent = vertex;
                dfs(v, graph);
            }

        }

        vertex.paintBlack();
        vertex.endTime = ++graph.time;

    }

    /**
     * Depth-first search
     */
    public static void dfs(Graph<Vertex, Edge> graph) {
        dfs(graph, graph.vertices);
    }

    private static void dfs(Graph<Vertex, Edge> graph, Vertex[] vertices) {
        for (Vertex v : graph.vertices) {
            v.paintWhite();
            v.parent = null;
            v.startTime = 0;
            v.endTime = 0;
        }
        graph.time = 0;
        for (Vertex v : vertices) {
            if (v.colour == Vertex.COLOUR_WHITE) {
                dfs(v, graph);
            }
        }
    }

    /**
     * O(E + VlgV)
     */
    public static Vertex[] topologicalSort(Graph<Vertex, Edge> graph) {

        //traversing graph O(V+E)
        dfs(graph);
        Vertex[] vertices = new Vertex[graph.getVertexQuantity()];
        MaxHeap<Vertex> heap = new MaxHeap<Vertex>(vertices, new Comparator<Vertex>() {
            @Override
            public int compare(Vertex o1, Vertex o2) {
                return o2.endTime - o1.endTime;
            }
        });

        //building heap O(VlgV)
        for (Vertex v : graph) {
            heap.addElement(v);
        }

        //extracting all elements O(V)
        while (heap.hasMore()) {
            heap.extractTop();
        }

        //overall O(V+VlgV + V + E) = O(E + VlgV)

        return vertices;
    }

    /**
     * Strongly connected components
     *
     * @return array of strongly connected components in following order [v[0](parent==null)...v[i-1]
     * v[i](parent==null)...v[j-1] v[j](parent==null)...] Each verticle with parent == null is a head of strongly
     * connected component
     */
    public static Vertex[] scc(final Graph<Vertex, Edge> graph) {

        dfs(graph);
        Graph<Vertex, Edge> tgraph = graph.clone();
        tgraph.transpose();

        Vertex[] vertices = new Vertex[graph.getVertexQuantity()];

        MaxHeap<Vertex> heap = new MaxHeap<Vertex>(vertices, new Comparator<Vertex>() {
            @Override
            public int compare(Vertex o1, Vertex o2) {
                return graph.vertices[o2.index].endTime - graph.vertices[o1.index].endTime;
            }
        });

        //building heap O(VlgV)
        for (Vertex v : tgraph) {
            heap.addElement(v);
        }

        //extracting all elements O(V)
        while (heap.hasMore()) {
            heap.extractTop();
        }

        dfs(tgraph, vertices);

        heap.setComparator(new Comparator<Vertex>() {
            @Override
            public int compare(Vertex o1, Vertex o2) {
                return o1.startTime - o2.startTime;
            }
        });

        for (Vertex v : tgraph) {
            heap.addElement(v);
        }

        //extracting all elements O(V)
        while (heap.hasMore()) {
            heap.extractTop();
        }

        return vertices;
    }

    /**
     * Returns a representant of the set to which vertex belongs
     *
     * disjoint tree sets with path compression
     */
    private static Vertex findSet(Vertex vertex) {

        if (vertex.parent == null) {
            return vertex;
        }

        Vertex root = findSet(vertex.parent);
        vertex.parent = root;

        return root;

    }

    /**
     * Joins two sets of vertices
     *
     * disjoint tree sets with path compression
     */
    private static void union(Vertex u, Vertex v) {

        Vertex setU = findSet(u);
        Vertex setV = findSet(v);
        setU.parent = setV;

    }

    /**
     * Minimum Spanning tree by Kruskal
     *
     */
    public static <E extends Edge> List<E> mstKruskal(Graph<Vertex, E> graph, final Comparator<E> edgeComparator) {

        List<E> minimumSpanningTree = new LinkedList<E>();

        E[] edges = (E[]) new Edge[graph.getEdgesQuantity()];

        IHeap<E> heap = new MinHeap<E>(edges, new Comparator<E>() {
            @Override
            public int compare(E o1, E o2) {
                return edgeComparator.compare(o1, o2);
            }
        });

        for (Vertex v : graph) {
            v.parent = null;

            for (E e : graph.getEdges(v.index)) {
                heap.addElement(e);
            }

        }

        while (heap.hasMore()) {

            E edge = heap.extractTop();
            Vertex u = graph.vertices[edge.uVertexIndex];
            Vertex v = graph.vertices[edge.vVertexIndex];

            if (findSet(u) != findSet(v)) {

                minimumSpanningTree.add(edge);
                union(u, v);

            }
        }

        return minimumSpanningTree;

    }

    /**
     * 
     * @param zero egde with weight 0
     * @param max the maximum weight of the edge. Must be greater than max weight edge in the graph
     */
    public static <E extends Edge> List<E> mstPrim(Graph<Vertex, E> graph, Vertex root, final Comparator<E> edgeComparator, E zero, E max) {

        List<E> minimumSpanningTree = new LinkedList<E>();

        PriorityQueue<E, Vertex> pq = new PriorityQueue<E, Vertex>(graph.getVertexQuantity(), edgeComparator);

        Map<Vertex, PriorityQueue.Entry<E, Vertex>> vertex2entry = new HashMap<Vertex, PriorityQueue.Entry<E, Vertex>>();

        for (Vertex u : graph) {
            u.parent = null;
            u.colour = Vertex.COLOUR_WHITE;
            vertex2entry.put(u, pq.addElement(max, u));
        }
        
        pq.changeKeyAt(vertex2entry.get(root).getIndex(), zero);
        
        while (pq.hasMore()) {

            PriorityQueue.Entry<E, Vertex> ue = pq.extractMin();
            Vertex u = ue.getValue();
            for(E edge : graph.getEdges(u.index)) {
                
                Vertex v = graph.vertices[edge.vVertexIndex];
                PriorityQueue.Entry<E, Vertex> ve = vertex2entry.get(v);
                if(v.colour == Vertex.COLOUR_WHITE && edgeComparator.compare(edge, ve.getKey()) < 0) {
                    
                    v.parent = u;
                    pq.changeKeyAt(ve.getIndex(), edge);
                    
                } 
                
            }
            
            u.paintBlack();

        }

        for(Vertex v : graph) {
            
            if(v.parent != null) {
                
                for(E edge : graph.getEdges(v.parent.index)) {
                    
                    if(edge.vVertexIndex == v.index) {
                        
                        minimumSpanningTree.add(edge);
                        
                    }
                    
                }
                
            }
            
        }

        return minimumSpanningTree;

    }
}
