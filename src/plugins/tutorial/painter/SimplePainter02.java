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

import icy.canvas.Canvas2D;
import icy.canvas.IcyCanvas;
import icy.gui.dialog.MessageDialog;
import icy.gui.frame.progress.AnnounceFrame;
import icy.painter.AbstractPainter;
import icy.plugin.abstract_.PluginActionable;
import icy.sequence.Sequence;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * 
 *         This plugin shows interaction of painters with users.
 *         
 * @author Fabrice de Chaumont
 * 
 * \page tuto5 Tutorial: Creating a painter by extending AbstractPainter and managing drag
 * 
 * Using the AbstractPainter remove a lot of useless code if you don't need all the feedback provided by painters.
 * 
 * \code
 * 
public class SimplePainter02 extends PluginActionable
{
    // the plugin will be called by ICY through this function
    @Override
    public void run()
    {
        // get the current sequence focused
        Sequence sequence = getFocusedSequence();

        // no sequence has been found
        if (sequence == null)
        {
            // display an information message as we need an opened sequence
            MessageDialog.showDialog("This example needs a sequence to start. Please load an image file.",
                    MessageDialog.INFORMATION_MESSAGE);
            return;
        }

        // display an announcement with Plugin description
        new AnnounceFrame(
                "This example displays a custom mouse cursor and draw a single line on drag operation. It also displays a line along the diagonal current view");

        // create custom MyPainter painter
        MyPainter myPainter = new MyPainter();
        // add the painter to the sequence
        sequence.addPainter(myPainter);
    }

    // our painter extends AbstractPainter as it provides painter facilities
    class MyPainter extends AbstractPainter
    {
        int xm, ym;
        boolean dragging = false;
        int xt, yt;

        @Override
        public void keyPressed(KeyEvent e, Point2D imagePoint, IcyCanvas q)
        {
            // display key press information in output window
            System.out.print("Plugin Simple Painter : ");
            System.out.println("Keypressed : " + e);
        }

        @Override
        public void mouseClick(MouseEvent e, Point2D p, IcyCanvas q)
        {
            // display mouse click information in output window
            System.out.print("Plugin Simple Painter : ");
            System.out.println("mouseClick : " + e);

            // test if we are in the close box
            int x = (int) p.getX();
            int y = (int) p.getY();
            if ((x < 10) && (x > 0) && (y < 10) && (y > 0))
                // detach painter from all sequence (AbstractPainter method)
                detachFromAll();
        }

        @Override
        public void mouseDrag(MouseEvent e, Point2D p, IcyCanvas q)
        {
            // update mouse drag position
            xt = (int) p.getX();
            yt = (int) p.getY();

            dragging = true;

            // notify painter changed (AbstractPainter) as dragging property
            // and coordinates may be changed
            changed();
        }

        @Override
        public void mouseMove(MouseEvent e, Point2D p, IcyCanvas q)
        {
            // update mouse position
            xm = (int) p.getX();
            ym = (int) p.getY();

            dragging = false;

            // notify painter changed (AbstractPainter) as dragging property
            // and coordinates may be changed
            changed();
        }

        @Override
        public void paint(Graphics2D g, Sequence sequence, IcyCanvas canvas)
        {
            // check if we are dealing with a canvas 2D
            if (canvas instanceof Canvas2D)
            {
                Canvas2D canvas2D = (Canvas2D) canvas;

                // display informative text about closing painter
                g.setColor(Color.darkGray);
                g.drawString("<--- click here to close SimplePainter", 21, 11);
                g.setColor(Color.white);
                g.drawString("<--- click here to close SimplePainter", 20, 10);

                // display the cross cursor
                g.setStroke(new BasicStroke(3));
                g.setColor(Color.black);
                g.drawLine(xm - 5, ym - 5, xm + 5, ym + 5);
                g.drawLine(xm - 5, ym + 5, xm + 5, ym - 5);
                g.setStroke(new BasicStroke(1));
                g.setColor(Color.yellow);
                g.drawLine(xm - 5, ym - 5, xm + 5, ym + 5);
                g.drawLine(xm - 5, ym + 5, xm + 5, ym - 5);

                // if we're dragging, display the dragging line
                if (dragging)
                    g.drawLine(xm, ym, xt, yt);

                // get visible rectangle of canvas
                final Rectangle2D visibleRect = canvas2D.getImageVisibleRect();

                if (!visibleRect.isEmpty())
                {
                    // draw a line all across the canvas
                    g.drawLine((int) visibleRect.getMinX(), (int) visibleRect.getMinY(), (int) visibleRect.getMaxX(),
                            (int) visibleRect.getMaxY());
                }

                // display close box
                g.drawRect(0, 0, 10, 10);
            }
        }
    }
}

 * \endcode
 *         
 * @formatter:off
 */
public class SimplePainter02 extends PluginActionable
{
    // the plugin will be called by ICY through this function
    @Override
    public void run()
    {
        // get the current sequence focused
        Sequence sequence = getFocusedSequence();

        // no sequence has been found
        if (sequence == null)
        {
            // display an information message as we need an opened sequence
            MessageDialog.showDialog("This example needs a sequence to start. Please load an image file.",
                    MessageDialog.INFORMATION_MESSAGE);
            return;
        }

        // display an announcement with Plugin description
        new AnnounceFrame(
                "This example displays a custom mouse cursor and draw a single line on drag operation. It also displays a line along the diagonal current view");

        // create custom MyPainter painter
        MyPainter myPainter = new MyPainter();
        // add the painter to the sequence
        sequence.addPainter(myPainter);
    }

    // our painter extends AbstractPainter as it provides painter facilities
    class MyPainter extends AbstractPainter
    {
        int xm, ym;
        boolean dragging = false;
        int xt, yt;

        @Override
        public void keyPressed(KeyEvent e, Point2D imagePoint, IcyCanvas q)
        {
            // display key press information in output window
            System.out.print("Plugin Simple Painter : ");
            System.out.println("Keypressed : " + e);
        }

        @Override
        public void mouseClick(MouseEvent e, Point2D p, IcyCanvas q)
        {
            // display mouse click information in output window
            System.out.print("Plugin Simple Painter : ");
            System.out.println("mouseClick : " + e);

            // test if we are in the close box
            int x = (int) p.getX();
            int y = (int) p.getY();
            if ((x < 10) && (x > 0) && (y < 10) && (y > 0))
                // detach painter from all sequence (AbstractPainter method)
                detachFromAll();
        }

        @Override
        public void mouseDrag(MouseEvent e, Point2D p, IcyCanvas q)
        {
            // update mouse drag position
            xt = (int) p.getX();
            yt = (int) p.getY();

            dragging = true;

            // notify painter changed (AbstractPainter) as dragging property
            // and coordinates may be changed
            changed();
        }

        @Override
        public void mouseMove(MouseEvent e, Point2D p, IcyCanvas q)
        {
            // update mouse position
            xm = (int) p.getX();
            ym = (int) p.getY();

            dragging = false;

            // notify painter changed (AbstractPainter) as dragging property
            // and coordinates may be changed
            changed();
        }

        @Override
        public void paint(Graphics2D g, Sequence sequence, IcyCanvas canvas)
        {
            // check if we are dealing with a canvas 2D
            if (canvas instanceof Canvas2D)
            {
                Canvas2D canvas2D = (Canvas2D) canvas;

                // display informative text about closing painter
                g.setColor(Color.darkGray);
                g.drawString("<--- click here to close SimplePainter", 21, 11);
                g.setColor(Color.white);
                g.drawString("<--- click here to close SimplePainter", 20, 10);

                // display the cross cursor
                g.setStroke(new BasicStroke(3));
                g.setColor(Color.black);
                g.drawLine(xm - 5, ym - 5, xm + 5, ym + 5);
                g.drawLine(xm - 5, ym + 5, xm + 5, ym - 5);
                g.setStroke(new BasicStroke(1));
                g.setColor(Color.yellow);
                g.drawLine(xm - 5, ym - 5, xm + 5, ym + 5);
                g.drawLine(xm - 5, ym + 5, xm + 5, ym - 5);

                // if we're dragging, display the dragging line
                if (dragging)
                    g.drawLine(xm, ym, xt, yt);

                // get visible rectangle of canvas
                final Rectangle2D visibleRect = canvas2D.getImageVisibleRect();

                if (!visibleRect.isEmpty())
                {
                    // draw a line all across the canvas
                    g.drawLine((int) visibleRect.getMinX(), (int) visibleRect.getMinY(), (int) visibleRect.getMaxX(),
                            (int) visibleRect.getMaxY());
                }

                // display close box
                g.drawRect(0, 0, 10, 10);
            }
        }
    }
}
