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
import icy.main.Icy;
import icy.painter.Painter;
import icy.plugin.abstract_.PluginActionable;
import icy.sequence.Sequence;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 *         This example displays a simple cross over the sequence.
 *
 * @author Fabrice de Chaumont & Stephane Dallongeville
 * 
 * 
 * \page tuto4 Tutorial: How to create a simple painter over a sequence (using interface)
 *
 * \code
 * 
public class SimplePainter01 extends PluginActionable implements Painter
{
    // automatically called when plugin is launched from ICY for "PluginImageAnalysis" type plugin
    @Override
    public void run()
    {
        // get the current sequence focused
        // we store it for easier painter remove on mouse click
        Sequence sequence = getFocusedSequence();

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

        // as "this" implements Painter. The painter is active after being added.
        sequence.addPainter(this);
    }

    @Override
    public void paint(Graphics2D g, Sequence sequence, IcyCanvas canvas)
    {
        // check if we are dealing with a canvas 2D
        if (canvas instanceof Canvas2D)
        {
            // display a big yellow cross all over the sequence
            g.setColor(Color.YELLOW);
            g.setStroke(new BasicStroke(5));
            g.drawLine(0, 0, sequence.getWidth(), sequence.getHeight());
            g.drawLine(0, sequence.getHeight(), sequence.getWidth(), 0);
        }
    }

    @Override
    public void keyPressed(KeyEvent e, Point2D imagePoint, IcyCanvas canvas)
    {

    }

    @Override
    public void keyReleased(KeyEvent e, Point2D imagePoint, IcyCanvas canvas)
    {

    }

    @Override
    public void mouseClick(MouseEvent e, Point2D imagePoint, IcyCanvas canvas)
    {
        // tools are provided to avoid keeping a global reference on the sequence
        // which could lead to a memory leak

        // get list of sequence containing this painter
        ArrayList<Sequence> sequenceList = Icy.getMainInterface().getSequencesContaining(this);
        // remove painter from sequence
        for (Sequence sequence : sequenceList)
            sequence.removePainter(this);
    }

    @Override
    public void mouseDrag(MouseEvent e, Point2D imagePoint, IcyCanvas canvas)
    {

    }

    @Override
    public void mouseMove(MouseEvent e, Point2D imagePoint, IcyCanvas canvas)
    {

    }

    @Override
    public void mousePressed(MouseEvent e, Point2D imagePoint, IcyCanvas canvas)
    {

    }

    @Override
    public void mouseReleased(MouseEvent e, Point2D imagePoint, IcyCanvas canvas)
    {

    }
}

 * \endcode 
 * 
 * @formatter:off
 */
public class SimplePainter01 extends PluginActionable implements Painter
{
    // automatically called when plugin is launched from ICY for "PluginImageAnalysis" type plugin
    @Override
    public void run()
    {
        // get the current sequence focused
        // we store it for easier painter remove on mouse click
        Sequence sequence = getFocusedSequence();

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

        // as "this" implements Painter. The painter is active after being added.
        sequence.addPainter(this);
    }

    @Override
    public void paint(Graphics2D g, Sequence sequence, IcyCanvas canvas)
    {
        // check if we are dealing with a canvas 2D
        if (canvas instanceof Canvas2D)
        {
            // display a big yellow cross all over the sequence
            g.setColor(Color.YELLOW);
            g.setStroke(new BasicStroke(5));
            g.drawLine(0, 0, sequence.getWidth(), sequence.getHeight());
            g.drawLine(0, sequence.getHeight(), sequence.getWidth(), 0);
        }
    }

    @Override
    public void keyPressed(KeyEvent e, Point2D imagePoint, IcyCanvas canvas)
    {

    }

    @Override
    public void keyReleased(KeyEvent e, Point2D imagePoint, IcyCanvas canvas)
    {

    }

    @Override
    public void mouseClick(MouseEvent e, Point2D imagePoint, IcyCanvas canvas)
    {
        // tools are provided to avoid keeping a global reference on the sequence
        // which could lead to a memory leak

        // get list of sequence containing this painter
        ArrayList<Sequence> sequenceList = Icy.getMainInterface().getSequencesContaining(this);
        // remove painter from sequence
        for (Sequence sequence : sequenceList)
            sequence.removePainter(this);
    }

    @Override
    public void mouseDrag(MouseEvent e, Point2D imagePoint, IcyCanvas canvas)
    {

    }

    @Override
    public void mouseMove(MouseEvent e, Point2D imagePoint, IcyCanvas canvas)
    {

    }

    @Override
    public void mousePressed(MouseEvent e, Point2D imagePoint, IcyCanvas canvas)
    {

    }

    @Override
    public void mouseReleased(MouseEvent e, Point2D imagePoint, IcyCanvas canvas)
    {

    }
}
