/**
 * 
 */
package plugins.tutorial.undo;

import icy.math.ArrayMath;
import icy.plugin.abstract_.PluginActionable;
import icy.sequence.Sequence;
import icy.sequence.edit.AbstractSequenceEdit;
import icy.type.collection.array.Array1DUtil;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

/**
 * This plugin demonstrate how to use the Undo framework with 'redo' operation support.
 * 
 * @author Stephane
 */
public class AdvancedUndo extends PluginActionable
{
    /**
     * Create the Undoable operation class
     * 
     * @author Stephane
     */
    class DivideOperation extends AbstractSequenceEdit
    {
        final double divisor;

        public DivideOperation(Sequence sequence, double divisor)
        {
            super(sequence, "Divide intensity");

            this.divisor = divisor;
            // consider the operation as "not done" by default
            hasBeenDone = false;
        }

        @Override
        public void undo() throws CannotUndoException
        {
            super.undo();

            final Sequence seq = getSequence();
            final Object data = seq.getDataXY(0, 0, 0);

            // convert to double
            final double[] doubleArray = Array1DUtil.arrayToDoubleArray(data, seq.isSignedDataType());
            // multiply intensity by 2
            ArrayMath.multiply(doubleArray, 2d, doubleArray);
            // copy data back to image
            Array1DUtil.doubleArrayToArray(doubleArray, data);
            // notify sequence we changed its data
            seq.dataChanged();
        }

        @Override
        public void redo() throws CannotRedoException
        {
            super.redo();

            final Sequence seq = getSequence();
            final Object data = seq.getDataXY(0, 0, 0);

            // convert to double
            final double[] doubleArray = Array1DUtil.arrayToDoubleArray(data, seq.isSignedDataType());
            // divide intensity by 2
            ArrayMath.divide(doubleArray, 2d, doubleArray);
            // copy data back to image, note that here we can loss information (double --> whatever
            // type conversion) so the redo operation won't correctly restore original data
            Array1DUtil.doubleArrayToArray(doubleArray, data);
            // notify sequence we changed its data
            seq.dataChanged();
        }
    }

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
                final DivideOperation op = new DivideOperation(seq, 2d);

                // execute operation
                op.redo();
                // add as undoable edit
                seq.addUndoableEdit(op);
            }
        }
    }
}
