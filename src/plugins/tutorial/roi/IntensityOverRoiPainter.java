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
package plugins.tutorial.roi;

import icy.canvas.IcyCanvas;
import icy.image.IcyBufferedImage;
import icy.painter.Overlay;
import icy.roi.ROI2D;
import icy.roi.ROI2DShape;
import icy.sequence.Sequence;
import icy.type.collection.array.Array1DUtil;
import icy.util.ShapeUtil;
import icy.util.ShapeUtil.ShapeConsumer;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;

/**
 * This painter draws an intensity profile of the image over a polyline
 * 
 * @author Fabrice de Chaumont and Stephane Dallongeville
 */
public class IntensityOverRoiPainter extends Overlay
{
    public IntensityOverRoiPainter()
    {
        super("Intensity test overlay");
    }

    @Override
    public void paint(Graphics2D g, Sequence sequence, IcyCanvas canvas)
    {
        // create a graphics object so that we can then dispose it at the end of the paint to clean
        // all change performed in the paint,
        // like transform, color change, stroke (...).
        Graphics2D g2 = (Graphics2D) g.create();

        for (ROI2D roi : sequence.getROI2Ds())
        {
            if (roi instanceof ROI2DShape)
                computeIntensityROI((ROI2DShape) roi, g2, sequence, canvas);
        }

        g2.dispose();
    }

    private void computeIntensityROI(ROI2DShape roi, final Graphics2D g, final Sequence sequence, final IcyCanvas canvas)
    {
        ShapeUtil.consumeShapeFromPath(roi.getPathIterator(null), new ShapeConsumer()
        {
            @Override
            public boolean consume(Shape shape)
            {
                if (shape instanceof Line2D)
                    drawHisto((Line2D) shape, g, sequence, canvas);

                return true; // continue
            }
        });
    }

    void drawHisto(Line2D line, Graphics2D g, Sequence sequence, final IcyCanvas canvas)
    {
        for (int component = 0; component < sequence.getSizeC(); component++)
        {
            // create histo data
            int distance = (int) line.getP1().distance(line.getP2());

            double vx = (line.getP2().getX() - line.getP1().getX()) / distance;
            double vy = (line.getP2().getY() - line.getP1().getY()) / distance;

            double[] data = new double[distance];

            double x = line.getP1().getX();
            double y = line.getP1().getY();

            for (int i = 0; i < distance; i++)
            {
                IcyBufferedImage image = canvas.getCurrentImage();
                if (image.isInside((int) x, (int) y))
                {
                    data[i] = Array1DUtil.getValue(image.getDataXY(component), image.getOffset((int) x, (int) y),
                            image.isSignedDataType());
                }
                else
                {
                    data[i] = 0;
                }

                x += vx;
                y += vy;
            }

            AffineTransform originalTransform = g.getTransform();

            Polygon polygon = new Polygon();
            polygon.addPoint(0, 0);
            for (int i = 0; i < distance; i++)
                // pity polygon does not support this with double...
                polygon.addPoint(i, (int) data[i]);
            polygon.addPoint(distance, 0);

            g.setColor(Color.white);
            if (sequence.getSizeC() != 1)
            {
                if (component == 0)
                    g.setColor(Color.red);
                if (component == 1)
                    g.setColor(Color.green);
                if (component == 2)
                    g.setColor(Color.blue);
            }

            // transform to put the painter at the right place
            g.translate(line.getX1(), line.getY1());
            g.rotate(Math.atan2(vy, vx), 0, 0);

            g.draw(polygon);

            g.setTransform(originalTransform);
        }
    }
}
