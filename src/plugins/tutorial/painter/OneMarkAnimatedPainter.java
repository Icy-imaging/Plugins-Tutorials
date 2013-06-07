/*
 * Copyright 2010, 2011 Institut Pasteur.
 * 
 * This file is part of ICY.
 * 
 * ICY is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * ICY is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with ICY. If not, see <http://www.gnu.org/licenses/>.
 */
package plugins.tutorial.painter;

import icy.canvas.IcyCanvas;
import icy.main.Icy;
import icy.painter.Overlay;
import icy.sequence.Sequence;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import javax.swing.Timer;

/**
 * Fancy mark to demonstrate the capabilities of painters.
 * 
 * @author Fabrice de Chaumont and Stephane Dallongeville
 */
public class OneMarkAnimatedPainter extends Overlay implements ActionListener
{
    Timer timer = new Timer(10, this);
    float current = 0;
    Point2D center;
    Point2D mouse;
    Color color;
    Color color2;
    double currentRotation = 0;

    public OneMarkAnimatedPainter(Point2D imagePoint)
    {
        super("Animated mark");

        // this.sequence = sequence;
        center = (Point2D) imagePoint.clone();
        mouse = new Point2D.Double(0, 0);
        float randomH = (float) Math.random();
        color = Color.getHSBColor(randomH, 1f, 1f);
        color2 = Color.getHSBColor(randomH + 0.5f, 1f, 1f);
        timer.start();
    }

    @Override
    public void paint(Graphics2D g, Sequence sequence, IcyCanvas canvas)
    {

        AffineTransform initialTransform = g.getTransform();
        Stroke initialStroke = g.getStroke();

        g.rotate(currentRotation, center.getX(), center.getY());
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        {
            g.setColor(color);
            float ray = current * 30f;
            Ellipse2D ellipse = new Ellipse2D.Double(center.getX() - ray, center.getY() - ray, ray * 2, ray * 2);

            g.setStroke(new BasicStroke(5));

            g.draw(ellipse);
        }

        {
            g.setColor(color2);
            float ray = current * 30f;

            Arc2D arc = new Arc2D.Double(Arc2D.PIE);
            arc.setFrame(center.getX() - ray * 1.5f, center.getY() - ray * 1.5f, ray * 3, ray * 3);
            arc.setAngleStart(0);
            arc.setAngleExtent(45);

            g.setStroke(new BasicStroke(5 * current));
            g.draw(arc);
        }

        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, current));

        {
            g.setColor(color2);
            float ray = 62f - current * 30f;
            Ellipse2D ellipse = new Ellipse2D.Double(center.getX() - ray, center.getY() - ray, ray * 2, ray * 2);
            g.setStroke(new BasicStroke(5 * (1 - current / 2)));
            g.draw(ellipse);
        }

        String text;
        if (mouse.distance(center) < 30)
        {
            text = "Mouse position X:" + (int) mouse.getX() + " Y:" + (int) mouse.getY();
        }
        else
        {
            text = "This is a painter example";
        }

        g.rotate(1.6, center.getX(), center.getY());
        for (int i = 0; i < text.length(); i++)
        {
            g.rotate(0.2, center.getX(), center.getY());
            String t = text.substring(i, i + 1);
            // This lead to a memory leak, due to a bug in the JVM 1.6 cache of transformed fonts.
            // (bug reported)
            // we chose not to care about it for this example.
            g.drawString(t, (int) (center.getX()), (int) (center.getY() - 40));
        }

        g.setTransform(initialTransform);
        g.setStroke(initialStroke);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (current < 0.90)
            current += 0.05f;

        currentRotation += 0.02d;

        for (Sequence sequence : Icy.getMainInterface().getSequencesContaining(this))
        {
            sequence.painterChanged(this);
        }
    }

    @Override
    public void mouseMove(MouseEvent e, Point2D imagePoint, IcyCanvas canvas)
    {
        mouse = (Point2D) imagePoint.clone();
    }
}
