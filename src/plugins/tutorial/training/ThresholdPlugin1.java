package plugins.tutorial.training;

import icy.gui.dialog.MessageDialog;
import icy.image.IcyBufferedImage;
import icy.plugin.abstract_.PluginActionable;
import icy.sequence.Sequence;
import plugins.kernel.roi.roi2d.ROI2DArea;

public class ThresholdPlugin1 extends PluginActionable
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

        ROI2DArea roi = new ROI2DArea();
        // consider first image only here
        IcyBufferedImage image = sequence.getFirstImage();

        for (int x = 0; x < sequence.getSizeX(); x++)
        {
            for (int y = 0; y < sequence.getSizeY(); y++)
            {
                if (image.getData(x, y, 0) >= threshold)
                {
                    roi.addPoint(x, y);
                }
            }
        }

        sequence.addROI(roi);
    }

}
