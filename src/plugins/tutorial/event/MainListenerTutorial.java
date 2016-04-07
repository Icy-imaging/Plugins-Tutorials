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
package plugins.tutorial.event;

import icy.gui.frame.IcyFrameAdapter;
import icy.gui.frame.IcyFrameEvent;
import icy.gui.frame.TitledFrame;
import icy.gui.main.GlobalOverlayListener;
import icy.gui.main.GlobalPluginListener;
import icy.gui.main.GlobalROIListener;
import icy.gui.main.GlobalSequenceListener;
import icy.gui.main.GlobalViewerListener;
import icy.gui.viewer.Viewer;
import icy.main.Icy;
import icy.painter.Overlay;
import icy.plugin.abstract_.Plugin;
import icy.plugin.abstract_.PluginActionable;
import icy.roi.ROI;
import icy.sequence.Sequence;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

/**
 * This class demonstrate how use the MainListener to track global event
 * on Viewer, Sequence, ROI and Painter objects
 * 
 * @author Stephane
 * @author Fab
 */
public class MainListenerTutorial extends PluginActionable
{
    @Override
    public void run()
    {
        // build a simple titled frame
        final TitledFrame frame = new TitledFrame("Main Event logger", true, true);

        // we need a text area to put out event informations
        final JTextArea area = new JTextArea();
        area.setWrapStyleWord(true);
        area.setEditable(false);

        // better to have a scroll pane as many events can occurs
        final JScrollPane scrollPane = new JScrollPane(area);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        // build the global event listeners
        final GlobalSequenceListener globalSequenceListener = new GlobalSequenceListener()
        {
            @Override
            public void sequenceOpened(Sequence sequence)
            {
                area.append("Sequence " + sequence + " opened\n");
                area.setCaretPosition(area.getDocument().getLength());
            }

            @Override
            public void sequenceClosed(Sequence sequence)
            {
                area.append("Sequence " + sequence + " closed\n");
                area.setCaretPosition(area.getDocument().getLength());
            }
        };

        final GlobalViewerListener globalViewerListener = new GlobalViewerListener()
        {
            @Override
            public void viewerOpened(Viewer viewer)
            {
                area.append("Viewer " + viewer + " opened\n");
                area.setCaretPosition(area.getDocument().getLength());
            }

            @Override
            public void viewerClosed(Viewer viewer)
            {
                area.append("Viewer " + viewer + " closed\n");
                area.setCaretPosition(area.getDocument().getLength());
            }
        };

        final GlobalOverlayListener globalOverlayListener = new GlobalOverlayListener()
        {
            @Override
            public void overlayRemoved(Overlay overlay)
            {
                area.append("Overlay " + overlay + " removed\n");
                area.setCaretPosition(area.getDocument().getLength());
            }

            @Override
            public void overlayAdded(Overlay overlay)
            {
                area.append("Overlay " + overlay + " added\n");
                area.setCaretPosition(area.getDocument().getLength());
            }
        };

        final GlobalROIListener globalROIListener = new GlobalROIListener()
        {
            @Override
            public void roiRemoved(ROI roi)
            {
                area.append("ROI " + roi + " removed\n");
                area.setCaretPosition(area.getDocument().getLength());
            }

            @Override
            public void roiAdded(ROI roi)
            {
                area.append("ROI " + roi + " added\n");
                area.setCaretPosition(area.getDocument().getLength());
            }
        };

        final GlobalPluginListener globalPluginListener = new GlobalPluginListener()
        {
            @Override
            public void pluginStarted(Plugin plugin)
            {
                area.append("Plugin " + plugin + " started\n");
                area.setCaretPosition(area.getDocument().getLength());
            }

            @Override
            public void pluginEnded(Plugin plugin)
            {
                area.append("Plugin " + plugin + " ended\n");
                area.setCaretPosition(area.getDocument().getLength());
            }
        };

        // add the global event listeners
        Icy.getMainInterface().addGlobalSequenceListener(globalSequenceListener);
        Icy.getMainInterface().addGlobalViewerListener(globalViewerListener);
        Icy.getMainInterface().addGlobalROIListener(globalROIListener);
        Icy.getMainInterface().addGlobalOverlayListener(globalOverlayListener);
        Icy.getMainInterface().addGlobalPluginListener(globalPluginListener);

        frame.addFrameListener(new IcyFrameAdapter()
        {
            @Override
            public void icyFrameClosed(IcyFrameEvent e)
            {
                // remove the listeners so there is no more reference on plugin instance
                Icy.getMainInterface().removeGlobalSequenceListener(globalSequenceListener);
                Icy.getMainInterface().removeGlobalViewerListener(globalViewerListener);
                Icy.getMainInterface().removeGlobalROIListener(globalROIListener);
                Icy.getMainInterface().removeGlobalOverlayListener(globalOverlayListener);
                Icy.getMainInterface().removeGlobalPluginListener(globalPluginListener);
            }
        });

        // add the scroll pane to the frame
        frame.getMainPanel().add(scrollPane);
        // set default size
        frame.setSize(700, 300);
        // add the frame to the interface
        addIcyFrame(frame);
        // center frame
        frame.center();
        // make it visible
        frame.setVisible(true);
        // get focus
        frame.requestFocus();
    }
}
