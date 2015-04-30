/**
 * 
 */
package plugins.tutorial.training;

import icy.gui.dialog.MessageDialog;
import icy.image.IcyBufferedImage;
import icy.math.ArrayMath;
import icy.plugin.abstract_.PluginActionable;
import icy.sequence.Sequence;
import icy.type.collection.array.Array1DUtil;

/**
 * Basic image processing plugin example: apply a maximum intensity projection along Z axis on the
 * active sequence
 * 
 * @author Stephane
 */
public class MaximumZProjectionPlugin extends PluginActionable
{

    @Override
    public void run()
    {
        Sequence sequence = getActiveSequence();

        // check if a sequence is opened
        if (sequence == null)
        {
            MessageDialog.showDialog("Please open a sequence to use this plugin.", MessageDialog.WARNING_MESSAGE);
            return;
        }

        // create a new empty sequence by using the original sequence name
        Sequence result = new Sequence(sequence.getName() + " - Z projection");

        // start sequence modification
        result.beginUpdate();
        try
        {
            // for each frame of the sequence
            for (int t = 0; t < sequence.getSizeT(); t++)
            {
                // compute the Z projection for the specified frame of sequence
                IcyBufferedImage image = getMaxZProjection(sequence, t);
                // set the image result at frame position t in the result Sequence
                result.setImage(t, 0, image);
            }
        }
        finally
        {
            // we are done with sequence modification
            result.endUpdate();
        }

        // make the sequence visible (add it to the GUI)
        addSequence(result);
    }

    IcyBufferedImage getMaxZProjection(Sequence sequence, int t)
    {
        // create a new image with same size and data type as the original Sequence
        IcyBufferedImage result = new IcyBufferedImage(sequence.getSizeX(), sequence.getSizeY(), sequence.getSizeC(),
                sequence.getDataType_());

        // for each channel of the input Sequence
        for (int c = 0; c < sequence.getSizeC(); c++)
        {
            // convert the result image data to double type for calculations
            double[] doubleArray = Array1DUtil.arrayToDoubleArray(result.getDataXY(c), result.isSignedDataType());

            // for each Z slice of the input Sequence
            for (int z = 0; z < sequence.getSizeZ(); z++)
                // compute the maximum between the specified slice data and the result array
                projectMax(sequence, t, z, c, doubleArray);

            // convert back the double array to original image data type
            Array1DUtil.doubleArrayToArray(doubleArray, result.getDataXY(c));
        }

        // Inform that image data has changed
        result.dataChanged();

        return result;
    }

    void projectMax(Sequence sequence, int t, int z, int c, double[] result)
    {
        // get XY planar image data array for the specified T, Z and C position
        Object dataArray = sequence.getDataXY(t, z, c);

        // convert to double data array
        double[] imgDoubleArray = Array1DUtil.arrayToDoubleArray(dataArray, sequence.isSignedDataType());

        // get the maximum from the 2 input arrays and save the result
        // into the result array by using the ArrayMath.max(..) method
        ArrayMath.max(result, imgDoubleArray, result);
    }
}
