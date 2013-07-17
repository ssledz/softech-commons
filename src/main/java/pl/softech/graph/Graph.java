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
public class Graph<V extends Vertex, E extends Edge> implements Iterable<V> {

    V[] vertexes;
    private int vertexQuantity;
    private List<E>[] edges;
    
    int time;

    public Graph(int initialCapacity) {
        vertexes = (V[]) new Vertex[initialCapacity];
        edges = (List<E>[]) new List[vertexes.length];
    }
    
    public Graph(V[] vertexes) {
        this.vertexes = vertexes;
        for(int i = 0; i < vertexes.length; i++) {
            vertexes[i].index = i;
        }
        vertexQuantity = vertexes.length;
        edges = (List<E>[]) new List[vertexes.length];
    }
    
    public void addVertex(V vertex) {
        vertexes[vertexQuantity] = vertex;
        vertex.index = vertexQuantity;
        vertexQuantity++;
    }

    public void addEdge(int vertexIndex, E edge) {
        if (edges[vertexIndex] == null) {
            edges[vertexIndex] = new LinkedList<E>();
        }
        edges[vertexIndex].add(edge);
    }

    public int getVertexQuantity() {
        return vertexQuantity;
    }


    List<E> getEdges(int vertexIndex) {
        if(edges[vertexIndex] == null) {
            return Collections.EMPTY_LIST;
        }
        return edges[vertexIndex];
    }

    public void addEdge(V vertex, E edge) {
        addEdge(vertex.index, edge);
    }

    public void addEdges(int vertexIndex, E... edges) {
        for (E edge : edges) {
            addEdge(vertexIndex, edge);
        }
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
                return vertexes[it++];
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("Not supported, because immutable");
            }
        };
    }
    
}
