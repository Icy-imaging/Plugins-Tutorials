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
package plugins.tutorial.gui;

import icy.gui.dialog.MessageDialog;
import icy.gui.frame.sequence.SequenceActionFrame;
import icy.plugin.abstract_.Plugin;
import icy.plugin.interface_.PluginImageAnalysis;
import icy.sequence.Sequence;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

/**
 * This example demonstrates a simple use of the SequenceActionFrame object
 * 
 * @author Stephane & Fab
 *
 * \page tuto14 Tutorial: How to use a template to have a simple plugin with an action button.
 * 
 * \code
 * 
 * public class SequenceActionFrameExample extends Plugin implements PluginImageAnalysis
{

    @Override
    public void compute()
    {
        // build a default action frame
        final SequenceActionFrame mainFrame = new SequenceActionFrame("Example", true);

        // define action to do when OK button is pressed
        mainFrame.setOkAction(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                // get selected sequence
                final Sequence sequence = mainFrame.getSequence();

                // no sequence
                if (sequence == null)
                    MessageDialog.showDialog("No sequence selected");
                else
                    MessageDialog.showDialog("You have selected : " + sequence.getName());
            }
        });

        // define if the frame should be closed after OK action is done (default = true)
        mainFrame.setCloseAfterAction(true);

        // build your GUI here
        mainFrame.getMainPanel().add(new JLabel("Set whatever you want here"));

        // add the frame to the interface
        addIcyFrame(mainFrame);
        // center frame
        mainFrame.center();
        // make it visible
        mainFrame.setVisible(true);
        // and get focus
        mainFrame.requestFocus();
    }
}

 * 
 * \endcode
 * 
 * @formatter:off
 * 
 */
public class SequenceActionFrameExample extends Plugin implements PluginImageAnalysis
{
    /**
     * @formatter:on
     */
    @Override
    public void compute()
    {
        // build a default action frame
        final SequenceActionFrame mainFrame = new SequenceActionFrame("Example", true);

        // define action to do when OK button is pressed
        mainFrame.setOkAction(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                // get selected sequence
                final Sequence sequence = mainFrame.getSequence();

                // no sequence
                if (sequence == null)
                    MessageDialog.showDialog("No sequence selected");
                else
                    MessageDialog.showDialog("You have selected : " + sequence.getName());
            }
        });

        // define if the frame should be closed after OK action is done (default = true)
        mainFrame.setCloseAfterAction(true);

        // build your GUI here
        mainFrame.getMainPanel().add(new JLabel("Set whatever you want here"));

        // add the frame to the interface
        addIcyFrame(mainFrame);
        // center frame
        mainFrame.center();
        // make it visible
        mainFrame.setVisible(true);
        // and get focus
        mainFrame.requestFocus();
    }
}
