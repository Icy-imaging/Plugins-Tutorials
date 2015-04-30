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
import icy.image.IcyBufferedImage;
import icy.plugin.abstract_.PluginActionable;
import icy.sequence.Sequence;
import icy.type.collection.array.Array1DUtil;
import plugins.kernel.roi.roi2d.ROI2DArea;

/**
 * This tutorial creates an Area ROI containing pixel of values greater than the mean of the band 0
 * of the image (t=0, z=0). It creates a ROI2DArea
 * 
 * @author Fabrice de Chaumont
 * @author Stephane Dallongeville
 */
public class CreateAreaROI extends PluginActionable
{

    @Override
    public void run()
    {
        // Display what this tutorial perform.
        new AnnounceFrame(
                "This tutorial creates an Area ROI containing pixel of values greater than the mean of the band 0 of the image (t=0, z=0).");

        // Get the current active sequence
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

        // Get the data of the image for band 0 as a linear buffer, regardless of the type.
        Object imageData = image.getDataXY(0);

        // Get a copy of the data in double.
        double[] dataBuffer = Array1DUtil.arrayToDoubleArray(imageData, image.isSignedDataType());

        // Compute mean
        double total = 0;
        for (int i = 0; i < dataBuffer.length; i++)
            total += dataBuffer[i];
        double mean = total / dataBuffer.length;

        // Compute mask
        boolean[] mask = new boolean[dataBuffer.length];
        for (int i = 0; i < dataBuffer.length; i++)
            // equivalent to if ( dataBuffer[i] > mean ) mask[i]=true; else mask[i]=false;
            mask[i] = (dataBuffer[i] > mean);

        // create ROI
        ROI2DArea roi = new ROI2DArea();

        // fill the roi
        roi.setAsBooleanMask(0, 0, sequence.getWidth(), sequence.getHeight(), mask);

        // add the roi to the sequence
        sequence.addROI(roi);
    }

}
