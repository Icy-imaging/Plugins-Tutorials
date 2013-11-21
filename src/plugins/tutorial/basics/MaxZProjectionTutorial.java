package plugins.tutorial.basics;

import icy.gui.dialog.MessageDialog;
import icy.image.IcyBufferedImage;
import icy.math.ArrayMath;
import icy.plugin.abstract_.PluginActionable;
import icy.sequence.Sequence;
import icy.type.collection.array.Array1DUtil;

public class MaxZProjectionTutorial extends PluginActionable
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

        // create the new empty result sequence
        Sequence result = new Sequence(sequence.getName() + "- Z projection");

        // start sequence modification
        result.beginUpdate();
        try
        {
            // for each frame
            for (int t = 0; t < sequence.getSizeT(); t++)
            {
                // get the Z maximum projection
                IcyBufferedImage image = getMaxZProjection(sequence, t);
                // set result at T position
                result.setImage(t, 0, image);
            }
        }
        finally
        {
            // end sequence modification
            result.endUpdate();
        }

        // display result
        addSequence(result);
    }

    private IcyBufferedImage getMaxZProjection(Sequence sequence, int t)
    {
        // create a new empty image with the same dimension and data type than input sequence
        IcyBufferedImage result = new IcyBufferedImage(sequence.getSizeX(), sequence.getSizeY(), sequence.getSizeC(),
                sequence.getDataType_());

        // for each channel
        for (int c = 0; c < sequence.getSizeC(); c++)
        {
            // transform data array to double data array for easy processing
            double[] doubleArray = Array1DUtil.arrayToDoubleArray(result.getDataXY(c), result.isSignedDataType());

            // for each Z slice
            for (int z = 0; z < sequence.getSizeZ(); z++)
                // compute the maximum between the Z slice data and the current result
                projectMax(sequence, t, z, c, doubleArray);

            // convert back double array to original data type
            Array1DUtil.doubleArrayToArray(doubleArray, result.getDataXY(c));
        }

        // inform the image that we have modified its data
        result.dataChanged();

        return result;
    }

    private void projectMax(Sequence sequence, int t, int z, int c, double[] result)
    {
        // get XY plan image data at specified T, Z and C position
        Object dataArray = sequence.getDataXY(t, z, c);

        // transform data array to double data array for easy processing
        double[] imgDoubleArray = Array1DUtil.arrayToDoubleArray(dataArray, sequence.isSignedDataType());

        // compute the maximum
        ArrayMath.max(result, imgDoubleArray, result);
    }
}
