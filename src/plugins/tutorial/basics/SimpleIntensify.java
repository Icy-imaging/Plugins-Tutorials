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
package plugins.tutorial.basics;

import icy.gui.dialog.MessageDialog;
import icy.gui.frame.progress.AnnounceFrame;
import icy.image.IcyBufferedImage;
import icy.math.MathUtil;
import icy.plugin.abstract_.PluginActionable;
import icy.type.collection.array.Array1DUtil;

/**
 * This plugin do a simple intensify operation on current opened image
 * 
 * @author Stephane
 */
public class SimpleIntensify extends PluginActionable
{
    @Override
    public void run()
    {
        // get focused image
        IcyBufferedImage image = getActiveImage();

        // check if the image exists
        if (image == null)
        {
            MessageDialog.showDialog("This plugin need a valid opened image.", MessageDialog.WARNING_MESSAGE);
            return;
        }

        // display what this tutorial perform.
        new AnnounceFrame("This tutorial multiply image intensity by a factor of 2, regardless the image data type.");

        // get first channel image data array regardless of the data type
        Object dataArray = image.getDataXY(0);

        // transform data array to double data array for easy processing
        double[] doubleDataArray = Array1DUtil.arrayToDoubleArray(dataArray, image.isSignedDataType());

        // multiply by a factor of 2
        MathUtil.mul(doubleDataArray, 2d);

        // transform data back to original data type.
        // 'Safe' methods take care of overflow with the destination data type.
        Array1DUtil.doubleArrayToSafeArray(doubleDataArray, image.getDataXY(0), image.isSignedDataType());

        // notify the data has changed (internal updates and view refresh)
        image.dataChanged();
    }
}
