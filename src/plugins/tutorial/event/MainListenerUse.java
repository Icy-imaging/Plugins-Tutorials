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
import icy.gui.main.MainAdapter;
import icy.gui.main.MainEvent;
import icy.gui.main.MainListener;
import icy.main.Icy;
import icy.plugin.abstract_.PluginActionable;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

/** 
 * This class demonstrate how use the MainListener to track global event
 * on Viewer, Sequence, ROI and Painter objects
 *           
 * @author Stephane & Fab
 *
 * \page tuto15 Tutorial: How to listen to all major events of ICY.
 *              Painter added/removed, plugin started/closed, roi, focus, viewer (...)
 * \code
 * 
public class MainListenerUse extends PluginActionable
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

        // build the global event listener
        final MainListener mainListener = new MainAdapter()
        {
            @Override
            public void painterAdded(MainEvent event)
            {
                area.append("painterAdded event : " + event + "\n");
                area.setCaretPosition(area.getDocument().getLength());
            }

            @Override
            public void painterRemoved(MainEvent event)
            {
                area.append("painterRemoved event : " + event + "\n");
                area.setCaretPosition(area.getDocument().getLength());
            }

            @Override
            public void pluginClosed(MainEvent event)
            {
                area.append("pluginClosed event : " + event + "\n");
                area.setCaretPosition(area.getDocument().getLength());
            }

            @Override
            public void pluginOpened(MainEvent event)
            {
                area.append("pluginOpened event : " + event + "\n");
                area.setCaretPosition(area.getDocument().getLength());
            }

            @Override
            public void roiAdded(MainEvent event)
            {
                area.append("roiAdded event : " + event + "\n");
                area.setCaretPosition(area.getDocument().getLength());
            };

            @Override
            public void roiRemoved(MainEvent event)
            {
                area.append("roiRemoved event : " + event + "\n");
                area.setCaretPosition(area.getDocument().getLength());
            }

            @Override
            public void sequenceFocused(MainEvent event)
            {
                area.append("sequenceFocused event : " + event + "\n");
                area.setCaretPosition(area.getDocument().getLength());
            }

            @Override
            public void sequenceClosed(MainEvent event)
            {
                area.append("sequenceClosed event : " + event + "\n");
                area.setCaretPosition(area.getDocument().getLength());
            }

            @Override
            public void sequenceOpened(MainEvent event)
            {
                area.append("sequenceOpened event : " + event + "\n");
                area.setCaretPosition(area.getDocument().getLength());
            }

            @Override
            public void viewerClosed(MainEvent event)
            {
                area.append("viewerClosed event : " + event + "\n");
                area.setCaretPosition(area.getDocument().getLength());
            }

            @Override
            public void viewerFocused(MainEvent event)
            {
                area.append("viewerFocused event : " + event + "\n");
                area.setCaretPosition(area.getDocument().getLength());
            }

            @Override
            public void viewerOpened(MainEvent event)
            {
                area.append("viewerOpened event : " + event + "\n");
                area.setCaretPosition(area.getDocument().getLength());
            }
        };

        // add the global event listener
        Icy.getMainInterface().addListener(mainListener);

        frame.addFrameListener(new IcyFrameAdapter()
        {
            @Override
            public void icyFrameClosed(IcyFrameEvent e)
            {
                // remove the main listener so there is no more reference on plugin instance
                Icy.getMainInterface().removeListener(mainListener);
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

 * 
 * \endcode
 * 
 * @formatter:off
 * 
 */

public class MainListenerUse extends PluginActionable
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

        // build the global event listener
        final MainListener mainListener = new MainAdapter()
        {
            @Override
            public void painterAdded(MainEvent event)
            {
                area.append("painterAdded event : " + event + "\n");
                area.setCaretPosition(area.getDocument().getLength());
            }

            @Override
            public void painterRemoved(MainEvent event)
            {
                area.append("painterRemoved event : " + event + "\n");
                area.setCaretPosition(area.getDocument().getLength());
            }

            @Override
            public void pluginClosed(MainEvent event)
            {
                area.append("pluginClosed event : " + event + "\n");
                area.setCaretPosition(area.getDocument().getLength());
            }

            @Override
            public void pluginOpened(MainEvent event)
            {
                area.append("pluginOpened event : " + event + "\n");
                area.setCaretPosition(area.getDocument().getLength());
            }

            @Override
            public void roiAdded(MainEvent event)
            {
                area.append("roiAdded event : " + event + "\n");
                area.setCaretPosition(area.getDocument().getLength());
            };

            @Override
            public void roiRemoved(MainEvent event)
            {
                area.append("roiRemoved event : " + event + "\n");
                area.setCaretPosition(area.getDocument().getLength());
            }

            @Override
            public void sequenceFocused(MainEvent event)
            {
                area.append("sequenceFocused event : " + event + "\n");
                area.setCaretPosition(area.getDocument().getLength());
            }

            @Override
            public void sequenceClosed(MainEvent event)
            {
                area.append("sequenceClosed event : " + event + "\n");
                area.setCaretPosition(area.getDocument().getLength());
            }

            @Override
            public void sequenceOpened(MainEvent event)
            {
                area.append("sequenceOpened event : " + event + "\n");
                area.setCaretPosition(area.getDocument().getLength());
            }

            @Override
            public void viewerClosed(MainEvent event)
            {
                area.append("viewerClosed event : " + event + "\n");
                area.setCaretPosition(area.getDocument().getLength());
            }

            @Override
            public void viewerFocused(MainEvent event)
            {
                area.append("viewerFocused event : " + event + "\n");
                area.setCaretPosition(area.getDocument().getLength());
            }

            @Override
            public void viewerOpened(MainEvent event)
            {
                area.append("viewerOpened event : " + event + "\n");
                area.setCaretPosition(area.getDocument().getLength());
            }
        };

        // add the global event listener
        Icy.getMainInterface().addListener(mainListener);

        frame.addFrameListener(new IcyFrameAdapter()
        {
            @Override
            public void icyFrameClosed(IcyFrameEvent e)
            {
                // remove the main listener so there is no more reference on plugin instance
                Icy.getMainInterface().removeListener(mainListener);
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
