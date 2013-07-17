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
import java.util.LinkedList;
import java.util.Queue;
import pl.softech.collection.Heap;

/**
 *
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 */
public class Graphs {
    
    /**
     * Breadth-first search
     */
    public static void bfs(Graph<Vertex, Edge> grapf, int vertexIndex) {
        
        for(Vertex v : grapf.vertices) {
            v.paintWhite();
            v.parent = null;
            v.distance = Vertex.MAX_DISTANCE;
        }
        
        Vertex s = grapf.vertices[vertexIndex];
        s.distance = 0;
        s.parent = null;
        Queue<Vertex> q = new LinkedList<Vertex>();
        q.add(s);
        
        while(!q.isEmpty()) {
            
            Vertex u = q.remove();
            for(Edge e : grapf.getEdges(u.index)) {
                
                Vertex v = grapf.vertices[e.vertexIndex];
                if(v.colour == Vertex.COLOUR_WHITE) {
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
        
        for(Edge e : graph.getEdges(vertex.index)) {
            
            Vertex v = graph.vertices[e.vertexIndex];
            if(v.colour == Vertex.COLOUR_WHITE) {
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
        
        for(Vertex v : graph.vertices) {
            v.paintWhite();
            v.parent = null;
            v.startTime = 0;
            v.endTime = 0;
        }
        graph.time = 0;
        for(Vertex v : graph.vertices) {
            if(v.colour == Vertex.COLOUR_WHITE) {
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
        Vertex[] vertexes = new Vertex[graph.getVertexQuantity()];
        Heap<Vertex> heap = new Heap<Vertex>(vertexes, new Comparator<Vertex>() {
            @Override
            public int compare(Vertex o1, Vertex o2) {
                return o2.endTime - o1.endTime;
            }
        });
        
        //building heap O(VlgV)
        for(Vertex v : graph) {
            heap.addElement(v);
        }
        
        //extracting all elements O(V)
        while(heap.hasMore()) {
            heap.extractTop();
        }
        
        //overall O(V+VlgV + V + E) = O(E + VlgV)
        
        return vertexes;
    }
    
    
}
