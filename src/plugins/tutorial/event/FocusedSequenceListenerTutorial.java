/**
 * 
 */
package plugins.tutorial.event;

import icy.gui.frame.IcyFrameAdapter;
import icy.gui.frame.IcyFrameEvent;
import icy.gui.frame.TitledFrame;
import icy.gui.main.FocusedSequenceListener;
import icy.gui.main.FocusedViewerListener;
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
public class FocusedSequenceListenerTutorial extends PluginActionable
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

        // build the focused sequence event listener
        final FocusedSequenceListener focusedSequenceListener = new FocusedSequenceListener()
        {
            @Override
            public void focusChanged(Sequence sequence)
            {
                area.append("Focus changed to : " + sequence + "\n");
                area.setCaretPosition(area.getDocument().getLength());
            }

            @Override
            public void focusedSequenceChanged(SequenceEvent event)
            {
                area.append("Focused sequence changed : [" + event.getType() + ", " + event.getSourceType() + "]\n");
                area.setCaretPosition(area.getDocument().getLength());
            };
        };

        // build the focused sequence event listener
        final FocusedViewerListener focusedViewerListener = new FocusedViewerListener()
        {
            @Override
            public void focusChanged(Viewer viewer)
            {
                area.append("Focus changed to : " + viewer + "\n");
                area.setCaretPosition(area.getDocument().getLength());
            }

            @Override
            public void focusedViewerChanged(ViewerEvent event)
            {
                area.append("Focused viewer changed : [" + event.getType() + ", " + event.getDim() + "]\n");
                area.setCaretPosition(area.getDocument().getLength());
            }
        };

        // add the focused sequence event listener
        Icy.getMainInterface().addFocusedSequenceListener(focusedSequenceListener);
        // add the focused viewer event listener
        Icy.getMainInterface().addFocusedViewerListener(focusedViewerListener);

        frame.addFrameListener(new IcyFrameAdapter()
        {
            @Override
            public void icyFrameClosed(IcyFrameEvent e)
            {
                // remove the focused sequence listener to not maintain any reference on plugin
                // instance
                Icy.getMainInterface().removeFocusedSequenceListener(focusedSequenceListener);
                // remove the focused viewer listener to not maintain any reference on plugin
                // instance
                Icy.getMainInterface().removeFocusedViewerListener(focusedViewerListener);
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
