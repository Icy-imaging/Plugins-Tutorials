package plugins.tutorial.training;

import icy.gui.dialog.MessageDialog;
import icy.plugin.abstract_.PluginActionable;
import icy.roi.BooleanMask2D;
import icy.sequence.Sequence;
import icy.type.collection.array.Array1DUtil;
import plugins.kernel.roi.roi2d.ROI2DArea;

public class ThresholdPlugin2 extends PluginActionable
{

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

        // fixed threshold value
        int threshold = 40;

        // consider first image and first channel only here
        double[] doubleArray = Array1DUtil.arrayToDoubleArray(sequence.getDataXY(0, 0, 0), sequence.isSignedDataType());
        boolean[] mask = new boolean[doubleArray.length];

        for (int i = 0; i < doubleArray.length; i++)
            mask[i] = (doubleArray[i] >= threshold);

        BooleanMask2D mask2d = new BooleanMask2D(sequence.getBounds2D(), mask);
        ROI2DArea roi = new ROI2DArea(mask2d);

        sequence.addROI(roi);
    }

}
