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
import icy.plugin.abstract_.Plugin;
import icy.plugin.interface_.PluginImageAnalysis;
import icy.type.collection.array.ArrayUtil;

/**
 * \page tuto7 Tutorial: How to use some math operation over full array, regardless of the type.
 * \code
 * public class SimpleIntensify extends Plugin implements PluginImageAnalysis
 * {
 * 
 * @Override
 *           public void compute()
 *           {
 *           // get focused image
 *           IcyBufferedImage image = getFocusedImage();
 *           // check if the image exists
 *           if (image == null)
 *           {
 *           MessageDialog.showDialog("This plugin need a valid opened image.",
 *           MessageDialog.WARNING_MESSAGE);
 *           return;
 *           }
 *           // display what this tutorial perform.
 *           new AnnounceFrame(
 *           "This tutorial multiply image intensity by a factor of 2, regardless the image data type."
 *           );
 *           // get first component image data as double[] whatever is the base data type
 *           double[] data = ArrayUtil.arrayToDoubleArray1D(image.getDataXY(0),
 *           image.isSignedDataType());
 *           // multiply by a factor of 2
 *           MathUtil.mul(data, 2d);
 *           // write back data by taking care of destination type limitation
 *           ArrayUtil.doubleArrayToSafeArray1D(data, image.getDataXY(0), image.isSignedDataType());
 *           // notify data changed
 *           image.dataChanged();
 *           }
 *           }
 *           \endcode
 *           This plugin do a simple intensify operation on current opened image
 * @author Stephane & Fabrice
 */
public class SimpleIntensify extends Plugin implements PluginImageAnalysis
{
    @Override
    public void compute()
    {
        // get focused image
        IcyBufferedImage image = getFocusedImage();

        // check if the image exists
        if (image == null)
        {
            MessageDialog.showDialog("This plugin need a valid opened image.", MessageDialog.WARNING_MESSAGE);
            return;
        }

        // display what this tutorial perform.
        new AnnounceFrame("This tutorial multiply image intensity by a factor of 2, regardless the image data type.");

        // get first component image data as double[] whatever is the base data type
        double[] data = ArrayUtil.arrayToDoubleArray1D(image.getDataXY(0), image.isSignedDataType());

        // multiply by a factor of 2
        MathUtil.mul(data, 2d);

        // write back data by taking care of destination type limitation
        ArrayUtil.doubleArrayToSafeArray1D(data, image.getDataXY(0), image.isSignedDataType());

        // notify data changed
        image.dataChanged();
    }
}
