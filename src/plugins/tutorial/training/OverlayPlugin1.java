/**
 * 
 */
package plugins.tutorial.training;

import icy.canvas.IcyCanvas;
import icy.gui.dialog.MessageDialog;
import icy.painter.Overlay;
import icy.plugin.abstract_.PluginActionable;
import icy.sequence.Sequence;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

/**
 * @author Stephane
 */
public class OverlayPlugin1 extends PluginActionable
{

    public class SimpleCrossOverlay extends Overlay
    {
        public SimpleCrossOverlay()
        {
            super("Simple cross");
        }

        @Override
        public void paint(Graphics2D g, Sequence sequence, IcyCanvas canvas)
        {
            if (g != null)
            {
                // paint a yellow cross all over the image
                g.setColor(Color.YELLOW);
                g.setStroke(new BasicStroke(5));
                g.drawLine(0, 0, sequence.getWidth(), sequence.getHeight());
                g.drawLine(0, sequence.getHeight(), sequence.getWidth(), 0);
            }
        }

        @Override
        public void mouseClick(MouseEvent e, Point2D point, IcyCanvas canvas)
        {
            // remove the overlay when the user clicks on the image
            remove();
        }
    }

    @Override
    public void run()
    {// get the current sequence having focus.
        Sequence sequence = getActiveSequence();

        // check if a sequence is opened
        if (sequence == null)
        {
            MessageDialog.showDialog("Please open a sequence to use this plugin.", MessageDialog.WARNING_MESSAGE);
            return;
        }

        sequence.addOverlay(new SimpleCrossOverlay());
    }
}
