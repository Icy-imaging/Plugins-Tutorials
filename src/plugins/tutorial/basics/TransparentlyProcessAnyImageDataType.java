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
import icy.plugin.abstract_.PluginActionable;
import icy.sequence.Sequence;
import icy.type.collection.array.Array1DUtil;

/**
 * This tutorial details how to create a simple image processing section without specific
 * knowledge of the original data type. The image will be convert to a 1D array of double,
 * then the process can be performed and stored back to the original image.
 * 
 * @author Fabrice de Chaumont
 * @author Stephane Dallongeville
 */
public class TransparentlyProcessAnyImageDataType extends PluginActionable
{
    // This method is called as the plugin is launched
    @Override
    public void run()
    {
        // Display what this tutorial perform.
        new AnnounceFrame(
                "This tutorial fades to black the band 0 of the image, on the upper part of the image, regardless of the image dataType.");

        // Get the current sequence having focus.
        Sequence sequence = getActiveSequence();

        // Check if sequence exists.
        if (sequence == null)
        {
            MessageDialog.showDialog("Please open a sequence to use this plugin.", MessageDialog.WARNING_MESSAGE);
            return;
        }

        // Get the image at t=0 and z=0
        IcyBufferedImage image = sequence.getImage(0, 0);

        // Check if the image exists
        if (image == null)
        {
            MessageDialog.showDialog("No image is present at t=0 and z=0.", MessageDialog.WARNING_MESSAGE);
            return;
        }

        // Get the data of the image for channel 0 as a linear buffer, regardless of the type.
        Object imageData = image.getDataXY(0);

        // Get a copy of the data in double.
        double[] dataBuffer = Array1DUtil.arrayToDoubleArray(imageData, image.isSignedDataType());

        // Fade the first half of the pixels.
        for (int i = 0; i < dataBuffer.length / 2; i++)
            dataBuffer[i] /= 2;

        // Put the data back to the original image.
        // Convert the double data automatically to the data type of the image.
        Array1DUtil.doubleArrayToArray(dataBuffer, imageData);

        // just to let the image know the data has changed (internal updates and view refresh) and also to update cache for volatile image
        image.setDataXY(0, imageData);
    }
}
