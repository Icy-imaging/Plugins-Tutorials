/**
 * 
 */
package plugins.tutorial.training;

import icy.gui.dialog.MessageDialog;
import icy.plugin.abstract_.PluginActionable;
import icy.roi.ROI2D;
import icy.sequence.Sequence;
import icy.sequence.SequenceDataIterator;

import java.util.List;

/**
 * @author Stephane
 */
public class MeanIntensityPlugin3 extends PluginActionable
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

        // create an iterator to iterate through all pixels contained in the ROI
        SequenceDataIterator iterator = new SequenceDataIterator(sequence, roi);
        double mean = 0;
        double sample = 0;

        while (!iterator.done())
        {
            mean += iterator.get();
            iterator.next();
            sample++;
        }

        System.out.println("mean intensity over ROI: " + (mean / sample));
    }
}
