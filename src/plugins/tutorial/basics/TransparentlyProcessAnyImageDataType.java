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
import icy.plugin.abstract_.Plugin;
import icy.plugin.interface_.PluginImageAnalysis;
import icy.sequence.Sequence;
import icy.type.collection.array.ArrayUtil;

/**
 * \page tuto6 Tutorial: How to process transparently any image data as input, work with it in double and then put them back to the original image, in the original format.
 *
 *\code
 *
public class TransparentlyProcessAnyImageDataType extends Plugin implements PluginImageAnalysis {

	@Override
	public void compute() {
		
		// Display what this tutorial perform.
		new AnnounceFrame("This tutorial fades to black the band 0 of the image, on the upper part of the image, regardless of the image dataType.");
		
		// Get the current sequence having focus.
		Sequence sequence = getFocusedSequence();
		
		// Check if sequence exists.
		if ( sequence == null )
		{
			MessageDialog.showDialog("Please open a sequence to use this plugin.", MessageDialog.WARNING_MESSAGE );
			return;
		}
		
		// Get the image at t=0 and z=0
		IcyBufferedImage image = sequence.getImage( 0 , 0 );

		// Check if the image exists
		if ( image == null )
		{
			MessageDialog.showDialog("No image is present at t=0 and z=0.", MessageDialog.WARNING_MESSAGE );			
			return;
		}
		
		// Get the data of the image for band 0 as a linear buffer, regardless of the type.
		Object imageData = image.getDataXY( 0 );
		
		// Get a copy of the data in double.
		double[] dataBuffer = ArrayUtil.arrayToDoubleArray1D( imageData , image.isSignedDataType() );
		
		// Fade the first half of the pixels.
		
		for ( int i = 0 ; i < dataBuffer.length/2 ; i++ )
		{
			dataBuffer[i]/=2;
		}
		
		// Put the data back to the original image
		// Convert the double data automatically to the data type of the image. image.getDataXY(0) return a reference on the internal data of the image.
		ArrayUtil.doubleArrayToArray1D( dataBuffer , image.getDataXY( 0 ) ) ;
		
		// notify ICY the data has changed.
		image.dataChanged();
		
	}	
	
}
 *
 *\endcode
 *
 * This tutorial details how to create a simple image processing section without specific 
 * knowledge of the original data type. The image will be convert to a 1D array of double,
 * then the process can be performed and stored back to the original image.
 * 
 * @author Fabrice de Chaumont & Stephane Dallongeville
 */
public class TransparentlyProcessAnyImageDataType extends Plugin implements PluginImageAnalysis {

	/**
	 * This method is called as the plugin is launched
	 */
	@Override
	public void compute() {
		
		// Display what this tutorial perform.
		new AnnounceFrame("This tutorial fades to black the band 0 of the image, on the upper part of the image, regardless of the image dataType.");
		
		// Get the current sequence having focus.
		Sequence sequence = getFocusedSequence();
		
		// Check if sequence exists.
		if ( sequence == null )
		{
			MessageDialog.showDialog("Please open a sequence to use this plugin.", MessageDialog.WARNING_MESSAGE );
			return;
		}
		
		// Get the image at t=0 and z=0
		IcyBufferedImage image = sequence.getImage( 0 , 0 );

		// Check if the image exists
		if ( image == null )
		{
			MessageDialog.showDialog("No image is present at t=0 and z=0.", MessageDialog.WARNING_MESSAGE );			
			return;
		}
		
		// Get the data of the image for band 0 as a linear buffer, regardless of the type.
		Object imageData = image.getDataXY( 0 );
		
		// Get a copy of the data in double.
		double[] dataBuffer = ArrayUtil.arrayToDoubleArray1D( imageData , image.isSignedDataType() );
		
		// Fade the first half of the pixels.
		
		for ( int i = 0 ; i < dataBuffer.length/2 ; i++ )
		{
			dataBuffer[i]/=2;
		}
		
		// Put the data back to the original image
		// Convert the double data automatically to the data type of the image. image.getDataXY(0) return a reference on the internal data of the image.
		ArrayUtil.doubleArrayToArray1D( dataBuffer , image.getDataXY( 0 ) ) ;
		
		// notify ICY the data has changed.
		image.dataChanged();
		
	}	
	
}
