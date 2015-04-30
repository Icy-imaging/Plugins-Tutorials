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
import icy.canvas.IcyCanvas2D;
import icy.gui.dialog.MessageDialog;
import icy.gui.frame.progress.AnnounceFrame;
import icy.painter.Overlay;
import icy.plugin.abstract_.PluginActionable;
import icy.sequence.Sequence;

import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

/**
 * This tutorial displays a fancy marked animation at each click.
 * 
 * @author Fabrice de Chaumont
 * @author Stephane Dallongeville
 */
public class AnimatedOverlayTutorial extends PluginActionable
{
    @Override
    public void run()
    {
        // Get the current sequence focused. Stored to remove
        Sequence sequence = getActiveSequence();

        if (sequence == null) // no sequence has been found.
        {
            MessageDialog.showDialog("This example needs a sequence to start. Please load an image file.",
                    MessageDialog.INFORMATION_MESSAGE);
            return;
        }

        new AnnounceFrame("Click over image to put marks");

        // we add a painter which will listen to mouse click and create the OneMarkPainter objects.
        sequence.addOverlay(new ListeningClickOverlay());
    }

    /**
     * This inner class is designed to listen to click event on a sequence, and place a
     * OneMarkPainter on it.
     */
    class ListeningClickOverlay extends Overlay
    {
        public ListeningClickOverlay()
        {
            super("Click test overlay");
        }

        @Override
        public void mouseClick(MouseEvent e, Point2D imagePoint, IcyCanvas canvas)
        {
            // check if we are dealing with a 2D canvas and we have a valid image position
            if ((canvas instanceof IcyCanvas2D) && (imagePoint != null))
            {
                // get all sequence where the overlay is attached
                for (Sequence sequence : getSequences())
                    sequence.addOverlay(new AnimatedOverlay(imagePoint));
            }
        }
    }
}
