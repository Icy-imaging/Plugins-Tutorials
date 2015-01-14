/**
 * 
 */
package plugins.tutorial.undo;

import icy.gui.dialog.ConfirmDialog;
import icy.math.ArrayMath;
import icy.plugin.abstract_.PluginActionable;
import icy.sequence.Sequence;
import icy.type.collection.array.Array1DUtil;

/**
 * This plugin demonstrate how to use the Undo framework in a very lazy / simple way
 * 
 * @author Stephane
 */
public class SimpleUndo extends PluginActionable
{
    @Override
    public void run()
    {
        final Sequence seq = getActiveSequence();

        if (seq != null)
        {
            final Object data = seq.getDataXY(0, 0, 0);

            // have some data here ?
            if (data != null)
            {
                // create simple undo point
                if (!seq.createUndoDataPoint("Half intensity"))
                {
                    // failed to create undo point (not enough memory)
                    if (!ConfirmDialog.confirm("Cannot undo operation, are you sure you want to continue ?"))
                        return;
                }

                // convert to double
                final double[] doubleArray = Array1DUtil.arrayToDoubleArray(data, seq.isSignedDataType());
                // divide intensity by 2
                ArrayMath.divide(doubleArray, 2d, doubleArray);
                // copy data back to image
                Array1DUtil.doubleArrayToArray(doubleArray, data);
                // notify sequence we changed its data
                seq.dataChanged();
            }
        }
    }
}
