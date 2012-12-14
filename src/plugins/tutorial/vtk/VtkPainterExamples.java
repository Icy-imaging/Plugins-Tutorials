/**
 * 
 */
package plugins.tutorial.vtk;

import icy.plugin.abstract_.PluginActionable;
import icy.sequence.Sequence;
import icy.util.Random;

/**
 * This plugin shows how to render different VTK objects type through the Painter.
 * 
 * @author Stephane
 */
public class VtkPainterExamples extends PluginActionable
{
    @Override
    public void run()
    {
        final Sequence sequence = getFocusedSequence();

        if (sequence != null)
        {
            switch (Random.nextInt() % 8)
            {
                default:
                    new VtkAnimatedEarthPainter(sequence);
                    break;
                case 1:
                    new VtkAxesPainter(sequence);
                    break;
                case 2:
                    new VtkComplexeSplinePainter(sequence);
                    break;
                case 3:
                    new VtkCubePainter(sequence);
                    break;
                case 4:
                    new VtkLabelPainter(sequence);
                    break;
                case 5:
                    new VtkSpherePainter(sequence);
                    break;
                case 6:
                    new VtkText2DPainter(sequence);
                    break;
                case 7:
                    new VtkText3DPainter(sequence);
                    break;
            }
        }
    }
}
