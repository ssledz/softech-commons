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

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 */
public class GraphVisualisator extends JPanel {

    private final Graph<Vertex, Edge> graph;

    public GraphVisualisator(Graph graph) {
        this.graph = graph;
    }

    /**
     * Simple painter algorithm
     */
    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;

        double dangle = 2 * Math.PI / graph.getVertexQuantity();
        double angle = 0;

        int w = getWidth();
        int h = getHeight();

        double r = (w > h ? h : w) / 2;
        double rVertex = r * 0.1;
        r = r - 2 * rVertex;

        double centerX = w / 2;
        double centerY = h / 2;

        double vertexWidth = 2 * rVertex;
        double vertexHeight = 2 * rVertex;

        Point2D[] vcs = new Point2D[graph.getVertexQuantity()];

        for (Vertex v : graph.vertices) {

            double cx = centerX + r * Math.cos(angle);
            double cy = centerY + r * Math.sin(angle);
            vcs[v.index] = new Point2D.Double(cx, cy);
            angle += dangle;
            
        }
        
        for (Vertex v : graph.vertices) {
            Point2D p1 = vcs[v.index];

            g2.setColor(Color.BLUE);

            for (Edge e : graph.getEdges(v.index)) {
                Point2D p2 = vcs[e.vVertexIndex];
                Line2D line = new Line2D.Double(p1, p2);
                g2.draw(line);
            }

        }
        
        g2.setFont(g2.getFont().deriveFont(16.0f).deriveFont(Font.BOLD));
        for (Vertex v : graph.vertices) {
            Point2D p = vcs[v.index];
            double cx = p.getX();
            double cy = p.getY();
            
            Ellipse2D ellipse = new Ellipse2D.Double(cx - rVertex, cy - rVertex, vertexWidth, vertexHeight);
            g2.setColor(Color.RED);
            g2.fill(ellipse);
            g2.setColor(Color.BLACK);
            g2.drawString("" + v.index, (float) cx, (float) cy);
            
        }




    }

    public static void draw(Graph graph, int width, int height) {

        JFrame f = new JFrame();
        f.setContentPane(new GraphVisualisator(graph));
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(width, height);
        f.setVisible(true);

    }
}
