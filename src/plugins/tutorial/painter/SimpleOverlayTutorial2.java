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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import icy.canvas.IcyCanvas;
import icy.canvas.IcyCanvas2D;
import icy.gui.dialog.MessageDialog;
import icy.gui.frame.progress.AnnounceFrame;
import icy.painter.Overlay;
import icy.plugin.abstract_.PluginActionable;
import icy.sequence.Sequence;
import icy.type.point.Point5D;

/**
 * This plugin shows interaction of painters with users.
 * 
 * @author Fabrice de Chaumont
 */
public class SimpleOverlayTutorial2 extends PluginActionable
{
    // Icy call this method when user launch a plugin
    @Override
    public void run()
    {
        // get the current active sequence
        Sequence sequence = getActiveSequence();

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

        // add a new "Interaction Test" overlay to the sequence
        sequence.addOverlay(new InteractionTestOverlay());
    }

    // our painter extends AbstractPainter as it provides painter facilities
    class InteractionTestOverlay extends Overlay
    {
        int xm, ym;
        boolean dragging;
        int xt, yt;

        public InteractionTestOverlay()
        {
            super("Simple overlay");

            dragging = false;
        }

        @Override
        public void keyPressed(KeyEvent e, Point5D.Double imagePoint, IcyCanvas canvas)
        {
            // check if we are dealing with a 2D canvas
            if (canvas instanceof IcyCanvas2D)
            {
                // display key press information in output window
                System.out.print("Plugin Simple Overlay : ");
                System.out.println("Keypressed : " + e);
            }
        }

        @Override
        public void mouseClick(MouseEvent e, Point5D.Double imagePoint, IcyCanvas canvas)
        {
            // check if we are dealing with a 2D canvas and we have a valid image position
            if ((canvas instanceof IcyCanvas2D) && (imagePoint != null))
            {
                // display mouse click information in output window
                System.out.print("Plugin Simple Overlay : ");
                System.out.println("mouseClick : " + e);

                // test if we are in the close box
                int x = (int) imagePoint.getX();
                int y = (int) imagePoint.getY();

                // remove the overlay from all sequence if we click in the specific area
                if ((x < 10) && (x > 0) && (y < 10) && (y > 0))
                    remove();
            }
        }

        @Override
        public void mouseDrag(MouseEvent e, Point5D.Double imagePoint, IcyCanvas canvas)
        {
            // check if we are dealing with a 2D canvas
            if ((canvas instanceof IcyCanvas2D) && (imagePoint != null))
            {
                // update mouse drag position
                xt = (int) imagePoint.getX();
                yt = (int) imagePoint.getY();

                dragging = true;

                // notify painter changed as dragging property and coordinates may be changed
                painterChanged();
            }
        }

        @Override
        public void mouseMove(MouseEvent e, Point5D.Double imagePoint, IcyCanvas canvas)
        {
            // check if we are dealing with a 2D canvas
            if ((canvas instanceof IcyCanvas2D) && (imagePoint != null))
            {
                // update mouse position
                xm = (int) imagePoint.getX();
                ym = (int) imagePoint.getY();

                dragging = false;

                // notify painter changed as dragging property and coordinates may be changed
                painterChanged();
            }
        }

        @Override
        public void paint(Graphics2D g, Sequence sequence, IcyCanvas canvas)
        {
            // check if we are dealing with a 2D canvas and we have a valid Graphics object
            if ((canvas instanceof IcyCanvas2D) && (g != null))
            {
                final IcyCanvas2D canvas2D = (IcyCanvas2D) canvas;

                // display informative text about closing painter
                g.setColor(Color.darkGray);
                g.drawString("<--- click here to close SimpleOverlay", 21, 11);
                g.setColor(Color.white);
                g.drawString("<--- click here to close SimpleOverlay", 20, 10);

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
