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
import java.awt.event.MouseEvent;

import icy.canvas.IcyCanvas;
import icy.canvas.IcyCanvas2D;
import icy.gui.dialog.MessageDialog;
import icy.gui.frame.progress.AnnounceFrame;
import icy.painter.Overlay;
import icy.plugin.abstract_.PluginActionable;
import icy.sequence.Sequence;
import icy.type.point.Point5D;

/**
 * This example displays a simple cross over the sequence.
 * 
 * @author Fabrice de Chaumont
 * @author Stephane Dallongeville
 */
public class SimpleOverlayTutorial1 extends PluginActionable
{
    private class SimpleCrossOverlay extends Overlay
    {
        public SimpleCrossOverlay()
        {
            super("Simple cross");
        }

        @Override
        public void paint(Graphics2D g, Sequence sequence, IcyCanvas canvas)
        {
            // check if we are dealing with a 2D canvas and we have a valid Graphics object
            if ((canvas instanceof IcyCanvas2D) && (g != null))
            {
                // display a big yellow cross all over the sequence
                g.setColor(Color.YELLOW);
                g.setStroke(new BasicStroke(5));
                g.drawLine(0, 0, sequence.getWidth(), sequence.getHeight());
                g.drawLine(0, sequence.getHeight(), sequence.getWidth(), 0);
            }
        }

        @Override
        public void mouseClick(MouseEvent e, Point5D.Double imagePoint, IcyCanvas canvas)
        {
            // check if we are dealing with a 2D canvas
            if (canvas instanceof IcyCanvas2D)
            {
                // tools are provided to avoid keeping a global reference on the sequence
                // which could lead to a memory leak

                // remove painter from all sequence where it is attached
                remove();
            }
        }
    }

    // automatically called when plugin is launched from ICY for "PluginImageAnalysis" type plugin
    @Override
    public void run()
    {
        // get the current active sequence
        // we store it for easier painter remove on mouse click
        Sequence sequence = getActiveSequence();

        // no sequence has been found ?
        if (sequence == null)
        {
            // display an information message as we need an opened sequence
            MessageDialog.showDialog("This example needs a sequence to start. Please load an image file.",
                    MessageDialog.INFORMATION_MESSAGE);
            return;
        }

        // display an announcement with Plugin description
        new AnnounceFrame(
                "This example display a yellow cross over the sequence in 2D. Click over the sequence to remove the painter.");

        // Add the cross overlay, it becomes active after being added.
        sequence.addOverlay(new SimpleCrossOverlay());
    }
}
