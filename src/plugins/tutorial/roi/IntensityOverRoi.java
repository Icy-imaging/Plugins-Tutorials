/*
 * Copyright 2010, 2011 Institut Pasteur.
 * 
 * This file is part of ICY.
 * 
 * ICY is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * ICY is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with ICY. If not, see <http://www.gnu.org/licenses/>.
 */
package plugins.tutorial.roi;

import icy.gui.dialog.MessageDialog;
import icy.gui.frame.progress.AnnounceFrame;
import icy.plugin.abstract_.PluginActionable;
import icy.roi.ROI2DPolyLine;
import icy.sequence.Sequence;

import java.awt.Polygon;

/**
 * This tutorial details how to create an intensity ruler.
 * Look at IntensityRulerPainter for the most interesting part.
 * 
 * @author Fabrice de Chaumont & Stephane Dallongeville
 */
public class IntensityOverRoi extends PluginActionable
{
    @Override
    public void run()
    {
        Sequence sequence = getActiveSequence();

        if (sequence == null)
        {
            MessageDialog.showDialog("Please open an image first.", MessageDialog.ERROR_MESSAGE);
            return;
        }

        sequence.addOverlay(new IntensityOverRoiPainter());

        new AnnounceFrame("This tutorial displays an intensity profile over compatible ROIs");

        // creates a ROI2DPolyLine if no ROI exists
        if (!sequence.hasROI())
        {
            ROI2DPolyLine roi = new ROI2DPolyLine();
            int[] x = {3 * sequence.getWidth() / 4, 2 * sequence.getWidth() / 4, sequence.getWidth() / 4};
            int[] y = {sequence.getHeight() / 2, sequence.getHeight() / 4, sequence.getHeight() / 2};
            Polygon polygon = new Polygon(x, y, x.length);
            roi.setPolygon(polygon);
            sequence.addROI(roi);
        }
    }
}
