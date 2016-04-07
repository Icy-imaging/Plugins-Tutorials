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
import icy.gui.frame.ActionFrame;
import icy.gui.frame.IcyFrameAdapter;
import icy.gui.frame.IcyFrameEvent;
import icy.gui.main.ActiveSequenceListener;
import icy.gui.util.GuiUtil;
import icy.main.Icy;
import icy.plugin.abstract_.PluginActionable;
import icy.sequence.Sequence;
import icy.sequence.SequenceEvent;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Plugin Example: A simple interface with some features.
 * 
 * @author Fabrice
 * @author Stephane
 */
public class GuiBuildExample01 extends PluginActionable implements ActionListener
{
    /**
     * gui
     */
    private ActionFrame frame;

    @Override
    public void run()
    {
        // build frame and others controls
        frame = new ActionFrame("Plugin with panel", true, true);
        final JPanel mainPanel = frame.getMainPanel();
        final JLabel sequenceLabel = new JLabel("", SwingConstants.CENTER);
        final JLabel sequenceNumberLabel = new JLabel();

        // build an active sequence listener
        final ActiveSequenceListener activeSequenceListener = new ActiveSequenceListener()
        {
            @Override
            public void sequenceActivated(Sequence sequence)
            {
                if (sequence != null)
                    sequenceLabel.setText(sequence.getName());
                else
                    sequenceLabel.setText("no sequence");

                // update the number of opened sequence
                sequenceNumberLabel.setText("" + getSequences().size());
            }

            @Override
            public void sequenceDeactivated(Sequence sequence)
            {

            }

            @Override
            public void activeSequenceChanged(SequenceEvent event)
            {

            }
        };

        // get selected sequence
        final Sequence sequenceFocused = getActiveSequence();

        // if we have one, show his name in the label
        if (sequenceFocused != null)
            sequenceLabel.setText(sequenceFocused.getName());
        else
            sequenceLabel.setText("no sequence");

        // create center panel as a big PAGE panel made from severals LINE panel
        final JPanel centerPanel = GuiUtil.createPageBoxPanel(Box.createVerticalStrut(4),
                GuiUtil.createLineBoxPanel(new JLabel("CurrentSequenceFocused : "), sequenceLabel),
                Box.createVerticalStrut(4),
                GuiUtil.createLineBoxPanel(new JLabel("Number of current sequence opened : "), sequenceNumberLabel),
                Box.createVerticalGlue(), Box.createVerticalStrut(4));
        // set a border to center panel
        centerPanel.setBorder(BorderFactory.createEtchedBorder());

        // panel in CENTER alignment can be resized both in width and height
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        frame.setOkAction(this);
        // don't want frame closed after action done
        frame.setCloseAfterAction(false);

        // add active sequence listener
        // WARNING : don't forget to remove it when plugin exit or instance will never die
        Icy.getMainInterface().addActiveSequenceListener(activeSequenceListener);

        // add a listener to frame events
        frame.addFrameListener(new IcyFrameAdapter()
        {
            // called when frame is closed
            @Override
            public void icyFrameClosed(IcyFrameEvent e)
            {
                // remove the listener so there is no more reference on plugin instance
                Icy.getMainInterface().removeActiveSequenceListener(activeSequenceListener);
            }
        });

        // set size
        frame.setSize(new Dimension(480, 340));
        // add frame to application desktop
        addIcyFrame(frame);
        // center
        frame.center();
        // and finally make it visible
        frame.setVisible(true);
        // get focus
        frame.requestFocus();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        MessageDialog.showDialog("Perform an action", "Number of sequence : " + getSequences().size());
    }
}
