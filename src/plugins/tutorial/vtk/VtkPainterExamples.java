/**
 * 
 */
package plugins.tutorial.vtk;

import icy.painter.Overlay;
import icy.plugin.abstract_.PluginActionable;
import icy.sequence.Sequence;

/**
 * This plugin shows how to render different VTK objects type through the Painter.
 * 
 * @author Stephane
 */
public class VtkPainterExamples extends PluginActionable
{
    private static int index = 0;

    @Override
    public void run()
    {
        final Sequence sequence = getActiveSequence();

        if (sequence != null)
        {
            final Overlay overlay;
            
            switch (index % 8)
            {
                default:
                    overlay = new VtkAnimatedEarthPainter();
                    break;
                case 1:
                    overlay = new VtkAxesPainter();
                    break;
                case 2:
                    overlay = new VtkComplexeSplinePainter();
                    break;
                case 3:
                    overlay = new VtkCubePainter();
                    break;
                case 4:
                    overlay = new VtkLabelPainter();
                    break;
                case 5:
                    overlay = new VtkSpherePainter();
                    break;
                case 6:
                    overlay = new VtkText2DPainter();
                    break;
                case 7:
                    overlay = new VtkText3DPainter();
                    break;
            }

            // add the created VTK overlay to the sequence
            sequence.addOverlay(overlay);

            index++;
        }
    }
}
