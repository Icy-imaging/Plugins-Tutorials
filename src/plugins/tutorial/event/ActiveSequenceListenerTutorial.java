/**
 * 
 */
package plugins.tutorial.event;

import icy.gui.frame.IcyFrameAdapter;
import icy.gui.frame.IcyFrameEvent;
import icy.gui.frame.TitledFrame;
import icy.gui.main.ActiveSequenceListener;
import icy.gui.main.ActiveViewerListener;
import icy.gui.viewer.Viewer;
import icy.gui.viewer.ViewerEvent;
import icy.main.Icy;
import icy.plugin.abstract_.PluginActionable;
import icy.sequence.Sequence;
import icy.sequence.SequenceEvent;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

/**
 * @author Stephane
 */
public class ActiveSequenceListenerTutorial extends PluginActionable
{
    @Override
    public void run()
    {
        // build a simple titled frame
        final TitledFrame frame = new TitledFrame("Active Sequence/Viewer Event logger", true, true);

        // we need a text area to put out event informations
        final JTextArea area = new JTextArea();
        area.setWrapStyleWord(true);
        area.setEditable(false);

        // better to have a scroll pane as many events can occurs
        final JScrollPane scrollPane = new JScrollPane(area);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        // build the focused sequence event listener
        final ActiveSequenceListener focusedSequenceListener = new ActiveSequenceListener()
        {
            @Override
            public void sequenceActivated(Sequence sequence)
            {
                area.append("Sequence " + sequence + " activated\n");
                area.setCaretPosition(area.getDocument().getLength());
            }

            @Override
            public void sequenceDeactivated(Sequence sequence)
            {
                area.append("Sequence " + sequence + " deactivated\n");
                area.setCaretPosition(area.getDocument().getLength());
            }

            @Override
            public void activeSequenceChanged(SequenceEvent event)
            {
                area.append("Active sequence changed : [" + event.getType() + ", " + event.getSourceType() + "]\n");
                area.setCaretPosition(area.getDocument().getLength());
            };
        };

        // build the focused sequence event listener
        final ActiveViewerListener focusedViewerListener = new ActiveViewerListener()
        {
            @Override
            public void viewerActivated(Viewer viewer)
            {
                area.append("Viewer " + viewer + " activated\n");
                area.setCaretPosition(area.getDocument().getLength());
            }

            @Override
            public void viewerDeactivated(Viewer viewer)
            {
                area.append("Viewer " + viewer + " deactivated\n");
                area.setCaretPosition(area.getDocument().getLength());
            }

            @Override
            public void activeViewerChanged(ViewerEvent event)
            {
                area.append("Active viewer changed : [" + event.getType() + ", " + event.getDim() + "]\n");
                area.setCaretPosition(area.getDocument().getLength());
            }
        };

        // add the focused sequence event listener
        Icy.getMainInterface().addActiveSequenceListener(focusedSequenceListener);
        // add the focused viewer event listener
        Icy.getMainInterface().addActiveViewerListener(focusedViewerListener);

        frame.addFrameListener(new IcyFrameAdapter()
        {
            @Override
            public void icyFrameClosed(IcyFrameEvent e)
            {
                // remove the focused sequence listener to not maintain any reference on plugin
                // instance
                Icy.getMainInterface().removeActiveSequenceListener(focusedSequenceListener);
                // remove the focused viewer listener to not maintain any reference on plugin
                // instance
                Icy.getMainInterface().removeActiveViewerListener(focusedViewerListener);
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
