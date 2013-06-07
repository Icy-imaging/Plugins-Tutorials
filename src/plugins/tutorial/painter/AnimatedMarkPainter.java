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
import icy.gui.dialog.MessageDialog;
import icy.gui.frame.progress.AnnounceFrame;
import icy.main.Icy;
import icy.painter.Overlay;
import icy.plugin.abstract_.PluginActionable;
import icy.sequence.Sequence;

import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;


/**
 * This tutorial displays a fancy marked animation at each click.
 * 
 * @author Fabrice de Chaumont / Stephane Dallongeville
 */
public class AnimatedMarkPainter extends PluginActionable
{
    @Override
    public void run()
    {
        // Get the current sequence focused. Stored to remove
        Sequence sequence = getFocusedSequence();

        if (sequence == null) // no sequence has been found.
        {
            MessageDialog.showDialog("This example needs a sequence to start. Please load an image file.",
                    MessageDialog.INFORMATION_MESSAGE);
            return;
        }

        new AnnounceFrame("Click over image to put marks");

        // we add a painter which will listen to mouse click and create the OneMarkPainter objects.
        sequence.addPainter(new ListeningClickOverlay());
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
            for (Sequence sequence : Icy.getMainInterface().getSequencesContaining(this))
                sequence.addPainter(new OneMarkAnimatedPainter(imagePoint));
        }
    }
}
