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

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 */
public class Graph<V extends Vertex, E extends Edge> implements Iterable<V>, Cloneable {

    V[] vertices;
    private int vertexQuantity;
    private int edgesQuantity;
    private List<E>[] edges;
    int time;

    public Graph(int initialCapacity) {
        vertices = (V[]) new Vertex[initialCapacity];
        edges = (List<E>[]) new List[vertices.length];
    }

    public Graph(V[] vertexes) {
        this.vertices = vertexes;
        for (int i = 0; i < vertexes.length; i++) {
            vertexes[i].index = i;
        }
        vertexQuantity = vertexes.length;
        edges = (List<E>[]) new List[vertexes.length];
    }

    public void addVertex(V vertex) {
        vertices[vertexQuantity] = vertex;
        vertex.index = vertexQuantity;
        vertexQuantity++;
    }

    public void addEdge(E edge) {
        if (edges[edge.uVertexIndex] == null) {
            edges[edge.uVertexIndex] = new LinkedList<E>();
        }
        edgesQuantity++;
        edges[edge.uVertexIndex].add(edge);
    }

    public int getVertexQuantity() {
        return vertexQuantity;
    }

    public int getEdgesQuantity() {
        return edgesQuantity;
    }
    
    
    List<E> getEdges(int vertexIndex) {
        if (edges[vertexIndex] == null) {
            return Collections.EMPTY_LIST;
        }
        return edges[vertexIndex];
    }

    public void addEdges(E... edges) {
        for (E edge : edges) {
            addEdge(edge);
        }
    }

    public V[] getVertices() {
        V[] arr = (V[]) new Object[vertices.length];
        System.arraycopy(vertices, 0, arr, 0, vertices.length);
        return arr;
    }

    /**
     * Transposition (u, v) -> (v, u)
     */
    public void transpose() {

        List<E>[] ledges = (List<E>[]) new List[vertices.length];

        for (V v : this) {

            if (edges[v.index] == null) {
                continue;
            }

            Iterator<Edge> it = (Iterator<Edge>) edges[v.index].iterator();
            while (it.hasNext()) {
                Edge e = it.next();
                if (ledges[e.vVertexIndex] == null) {
                    ledges[e.vVertexIndex] = new LinkedList<E>();
                }
                ledges[e.vVertexIndex].add((E) e);
                e.vVertexIndex = v.index;
                it.remove();
            }

        }
        this.edges = ledges;
    }

    @Override
    public Iterator<V> iterator() {
        return new Iterator<V>() {
            int it = 0;

            @Override
            public boolean hasNext() {
                return it < vertexQuantity;
            }

            @Override
            public V next() {
                return vertices[it++];
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("Not supported, because immutable");
            }
        };
    }

    @Override
    public Graph<V, E> clone() {
        
        Graph<V, E> graph = new Graph<V, E>(vertices.length);
        graph.vertexQuantity = vertexQuantity;
        
        for (int i = 0; i < vertexQuantity; i++) {
            graph.vertices[i] = (V) vertices[i].clone();
        }
        
        for(V v : this) {
            
            if(edges[v.index] == null) {
                continue;
            }
            
            graph.edges[v.index] = new LinkedList<E>();
            for(E e : edges[v.index]) {
                graph.edges[v.index].add((E) e.clone());
            }
            
        }


        return graph;
    }
}
