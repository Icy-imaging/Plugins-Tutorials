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

import icy.image.IcyBufferedImage;
import icy.plugin.abstract_.Plugin;
import icy.plugin.interface_.PluginImageAnalysis;
import icy.sequence.Sequence;
import icy.type.TypeUtil;

/**
 * This plugin generates image of all types supported by ICY.
 *         
 * @author Stephane Dallongeville / Fabrice de Chaumont
 *
 * \page tuto2 Tutorial: How to Generate images of all type available.
 * 
 * \code
 * 
 * 
public class GenerateImageOfDifferentType extends Plugin implements PluginImageAnalysis
{
    @Override
    public void compute()
    {
        // generate a FLOAT image ( 1 band )
        {
            // create the image object
            IcyBufferedImage image = new IcyBufferedImage(256, // width
                    256, // height
                    1, // number of channels (note that channel is same as color components or color
                       // band)
                    TypeUtil.TYPE_FLOAT);

            float[] dataBuffer = image.getDataXYAsFloat(0); // get a direct reference to first
                                                            // component data

            // fill data
            for (int x = 0; x < 256; x++)
                for (int y = 0; y < 256; y++)
                    dataBuffer[x + y * 256] = (float) (Math.random() - 0.5); // directly assign to
                                                                             // buffer

            // notify to icy that data has changed to refresh internal state and display
            image.dataChanged();

            // create a sequence
            Sequence sequence = new Sequence("Float Image", image);

            // Create a viewer to watch the sequence.
            addSequence(sequence);
        }

        // generate a BYTE image ( 1 band )
        {
            // create the image object
            IcyBufferedImage image = new IcyBufferedImage(256, // width
                    256, // height
                    1, // number of channels (note that channel is same as color components or color
                       // band)
                    TypeUtil.TYPE_BYTE);

            byte[] dataBuffer = image.getDataXYAsByte(0); // get a direct reference to first
                                                          // component data

            // fill data
            for (int x = 0; x < 256; x++)
                for (int y = 0; y < 256; y++)
                    dataBuffer[x + y * 256] = (byte) (x * y); // directly assign to buffer

            // notify to icy that data has changed to refresh internal state and display
            image.dataChanged();

            // create a sequence
            Sequence sequence = new Sequence("Byte Image", image);

            // Create a viewer to watch the sequence.
            addSequence(sequence);
        }

        // generate a INT image ( 1 band )
        {
            // create the image object
            IcyBufferedImage image = new IcyBufferedImage(256, // width
                    256, // height
                    1, // number of channels (note that channel is same as color components or color
                       // band)
                    TypeUtil.TYPE_INT);

            int[] dataBuffer = image.getDataXYAsInt(0); // get a direct reference to first component
                                                        // data

            // fill data
            for (int x = 0; x < 256; x++)
                for (int y = 0; y < 256; y++)
                    dataBuffer[x + y * 256] = (int) (x * y); // directly assign to buffer

            // notify to icy that data has changed to refresh internal state and display
            image.dataChanged();

            // create a sequence
            Sequence sequence = new Sequence("Int Image", image);

            // Create a viewer to watch the sequence.
            addSequence(sequence);
        }

        // generate a USHORT image ( 1 band )
        {
            // create the image object
            IcyBufferedImage image = new IcyBufferedImage(256, // width
                    256, // height
                    1, // number of channels (note that channel is same as color components or color
                       // band)
                    TypeUtil.TYPE_SHORT, false // set as USHORT ( unsigned )
            );

            short[] dataBuffer = image.getDataXYAsShort(0); // get a direct reference to first
                                                            // component data

            // fill data
            for (int x = 0; x < 256; x++)
                for (int y = 0; y < 256; y++)
                    dataBuffer[x + y * 256] = (short) (x * y); // directly assign to buffer

            // notify to icy that data has changed to refresh internal state and display
            image.dataChanged();

            // create a sequence
            Sequence sequence = new Sequence("Unsigned Short Image", image);

            // Create a viewer to watch the sequence.
            addSequence(sequence);
        }

        // generate a SHORT image ( 1 band )
        {
            // create the image object
            IcyBufferedImage image = new IcyBufferedImage(256, // width
                    256, // height
                    1, // number of channels (note that channel is same as color components or color
                       // band)
                    TypeUtil.TYPE_SHORT, true // set as SHORT ( signed )
            );

            short[] dataBuffer = image.getDataXYAsShort(0); // get a direct reference to first
                                                            // component data

            // fill data
            for (int x = 0; x < 256; x++)
                for (int y = 0; y < 256; y++)
                    dataBuffer[x + y * 256] = (short) (x * y); // directly assign to buffer

            // notify to icy that data has changed to refresh internal state and display
            image.dataChanged();

            // create a sequence
            Sequence sequence = new Sequence("Short Image", image);

            // Create a viewer to watch the sequence.
            addSequence(sequence);
        }

        // generate a DOUBLE image ( 1 band )
        {
            // create the image object
            IcyBufferedImage image = new IcyBufferedImage(256, // width
                    256, // height
                    1, // number of channels (note that channel is same as color components or color
                       // band)
                    TypeUtil.TYPE_DOUBLE);

            double[] dataBuffer = image.getDataXYAsDouble(0); // get a direct reference to first
                                                              // component data

            // fill data
            for (int x = 0; x < 256; x++)
                for (int y = 0; y < 256; y++)
                    dataBuffer[x + y * 256] = (double) (Math.random() - 0.5); // directly assign to
                                                                              // buffer

            // notify to icy that data has changed to refresh internal state and display
            image.dataChanged();

            // create a sequence
            Sequence sequence = new Sequence("Double Image", image);

            // Create a viewer to watch the sequence.
            addSequence(sequence);
        }
    }

}
 * 
 * \endcode
 * 
 * @formatter:off
 * 
 */

public class GenerateImageOfDifferentType extends Plugin implements PluginImageAnalysis
{
    /**
     * @formatter:on
     */
    @Override
    public void compute()
    {
        // generate a FLOAT image ( 1 band )
        {
            // create the image object
            IcyBufferedImage image = new IcyBufferedImage(256, // width
                    256, // height
                    1, // number of channels (note that channel is same as color components or color
                       // band)
                    TypeUtil.TYPE_FLOAT);

            float[] dataBuffer = image.getDataXYAsFloat(0); // get a direct reference to first
                                                            // component data

            // fill data
            for (int x = 0; x < 256; x++)
                for (int y = 0; y < 256; y++)
                    dataBuffer[x + y * 256] = (float) (Math.random() - 0.5); // directly assign to
                                                                             // buffer

            // notify to icy that data has changed to refresh internal state and display
            image.dataChanged();

            // create a sequence
            Sequence sequence = new Sequence("Float Image", image);

            // Create a viewer to watch the sequence.
            addSequence(sequence);
        }

        // generate a BYTE image ( 1 band )
        {
            // create the image object
            IcyBufferedImage image = new IcyBufferedImage(256, // width
                    256, // height
                    1, // number of channels (note that channel is same as color components or color
                       // band)
                    TypeUtil.TYPE_BYTE);

            byte[] dataBuffer = image.getDataXYAsByte(0); // get a direct reference to first
                                                          // component data

            // fill data
            for (int x = 0; x < 256; x++)
                for (int y = 0; y < 256; y++)
                    dataBuffer[x + y * 256] = (byte) (x * y); // directly assign to buffer

            // notify to icy that data has changed to refresh internal state and display
            image.dataChanged();

            // create a sequence
            Sequence sequence = new Sequence("Byte Image", image);

            // Create a viewer to watch the sequence.
            addSequence(sequence);
        }

        // generate a INT image ( 1 band )
        {
            // create the image object
            IcyBufferedImage image = new IcyBufferedImage(256, // width
                    256, // height
                    1, // number of channels (note that channel is same as color components or color
                       // band)
                    TypeUtil.TYPE_INT);

            int[] dataBuffer = image.getDataXYAsInt(0); // get a direct reference to first component
                                                        // data

            // fill data
            for (int x = 0; x < 256; x++)
                for (int y = 0; y < 256; y++)
                    dataBuffer[x + y * 256] = (int) (x * y); // directly assign to buffer

            // notify to icy that data has changed to refresh internal state and display
            image.dataChanged();

            // create a sequence
            Sequence sequence = new Sequence("Int Image", image);

            // Create a viewer to watch the sequence.
            addSequence(sequence);
        }

        // generate a USHORT image ( 1 band )
        {
            // create the image object
            IcyBufferedImage image = new IcyBufferedImage(256, // width
                    256, // height
                    1, // number of channels (note that channel is same as color components or color
                       // band)
                    TypeUtil.TYPE_SHORT, false // set as USHORT ( unsigned )
            );

            short[] dataBuffer = image.getDataXYAsShort(0); // get a direct reference to first
                                                            // component data

            // fill data
            for (int x = 0; x < 256; x++)
                for (int y = 0; y < 256; y++)
                    dataBuffer[x + y * 256] = (short) (x * y); // directly assign to buffer

            // notify to icy that data has changed to refresh internal state and display
            image.dataChanged();

            // create a sequence
            Sequence sequence = new Sequence("Unsigned Short Image", image);

            // Create a viewer to watch the sequence.
            addSequence(sequence);
        }

        // generate a SHORT image ( 1 band )
        {
            // create the image object
            IcyBufferedImage image = new IcyBufferedImage(256, // width
                    256, // height
                    1, // number of channels (note that channel is same as color components or color
                       // band)
                    TypeUtil.TYPE_SHORT, true // set as SHORT ( signed )
            );

            short[] dataBuffer = image.getDataXYAsShort(0); // get a direct reference to first
                                                            // component data

            // fill data
            for (int x = 0; x < 256; x++)
                for (int y = 0; y < 256; y++)
                    dataBuffer[x + y * 256] = (short) (x * y); // directly assign to buffer

            // notify to icy that data has changed to refresh internal state and display
            image.dataChanged();

            // create a sequence
            Sequence sequence = new Sequence("Short Image", image);

            // Create a viewer to watch the sequence.
            addSequence(sequence);
        }

        // generate a DOUBLE image ( 1 band )
        {
            // create the image object
            IcyBufferedImage image = new IcyBufferedImage(256, // width
                    256, // height
                    1, // number of channels (note that channel is same as color components or color
                       // band)
                    TypeUtil.TYPE_DOUBLE);

            double[] dataBuffer = image.getDataXYAsDouble(0); // get a direct reference to first
                                                              // component data

            // fill data
            for (int x = 0; x < 256; x++)
                for (int y = 0; y < 256; y++)
                    dataBuffer[x + y * 256] = (double) (Math.random() - 0.5); // directly assign to
                                                                              // buffer

            // notify to icy that data has changed to refresh internal state and display
            image.dataChanged();

            // create a sequence
            Sequence sequence = new Sequence("Double Image", image);

            // Create a viewer to watch the sequence.
            addSequence(sequence);
        }
    }

}
