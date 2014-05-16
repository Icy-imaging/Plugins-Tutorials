/**
 * 
 */
package plugins.tutorial.training;

import icy.gui.dialog.MessageDialog;
import icy.image.IcyBufferedImage;
import icy.plugin.abstract_.PluginActionable;
import icy.roi.ROI2D;
import icy.sequence.Sequence;

import java.util.List;

/**
 * @author Stephane
 */
public class MeanIntensityPlugin1 extends PluginActionable
{

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run()
    {
        // get the current sequence having focus.
        Sequence sequence = getActiveSequence();

        // check if a sequence is opened
        if (sequence == null)
        {
            MessageDialog.showDialog("Please open a sequence to use this plugin.", MessageDialog.WARNING_MESSAGE);
            return;
        }

        // retrieve 2D rois attached to this sequence
        List<ROI2D> rois = sequence.getROI2Ds();

        if (rois.size() == 0)
        {
            MessageDialog.showDialog("The sequence should have a ROI to use this plugin.",
                    MessageDialog.WARNING_MESSAGE);
            return;
        }

        // get the first 2D roi
        ROI2D roi = rois.get(0);

        // consider first image only here
        IcyBufferedImage image = sequence.getFirstImage();
        double mean = 0;
        double sample = 0;

        for (int x = 0; x < sequence.getSizeX(); x++)
        {
            for (int y = 0; y < sequence.getSizeY(); y++)
            {
                if (roi.contains(x, y))
                {
                    mean += image.getData(x, y, 0);
                    sample++;
                }
            }
        }

        System.out.println("mean intensity over ROI: " + (mean / sample));
    }
}
