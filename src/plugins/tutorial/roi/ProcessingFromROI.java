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

import icy.canvas.IcyCanvas;
import icy.gui.dialog.MessageDialog;
import icy.gui.frame.progress.AnnounceFrame;
import icy.gui.viewer.Viewer;
import icy.gui.viewer.ViewerEvent;
import icy.gui.viewer.ViewerEvent.ViewerEventType;
import icy.gui.viewer.ViewerListener;
import icy.image.IcyBufferedImage;
import icy.painter.AbstractPainter;
import icy.plugin.abstract_.Plugin;
import icy.plugin.interface_.PluginImageAnalysis;
import icy.roi.BooleanMask2D;
import icy.roi.ROI2D;
import icy.sequence.Sequence;
import icy.sequence.SequenceEvent;
import icy.sequence.SequenceListener;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

/**
 *           This class show how we can use ROI to do localized operation on image
 *           
 * @author Stephane
 * 
 *\page tuto12 Tutorial: Processing data from ROI: fast build of the mask.
 *
 *\code
 *
 * public class ProcessingFromROI extends Plugin implements PluginImageAnalysis, SequenceListener, ViewerListener
{
    private Viewer viewer;
    private Sequence sequence;
    private AbstractPainter painter;
    BufferedImage img;
    private int[] imgData;

    @Override
    public void compute()
    {
        viewer = getFocusedViewer();

        // no viewer has been found ?
        if (viewer == null)
        {
            // display an information message as we need an opened sequence
            MessageDialog.showDialog("This example needs a sequence to start. Please load an image file.",
                    MessageDialog.INFORMATION_MESSAGE);
            return;
        }

        // we should avoid direct sequence reference but it really help there
        sequence = viewer.getSequence();

        // display an announcement with Plugin description
        new AnnounceFrame("This example show how do localized operation on image from ROI");
        // no ROI2D in sequence
        if (!sequence.hasROI(ROI2D.class))
            new AnnounceFrame("Add a ROI to the sequence to see plugin action");

        // define a painter to just draw the image over the sequence
        painter = new AbstractPainter()
        {
            @Override
            public void paint(Graphics2D g, Sequence sequence, IcyCanvas canvas)
            {
                // just draw the image over the sequence
                g.drawImage(img, null, 0, 0);
            }
        };

        // add the painter to the sequence
        sequence.addPainter(painter);

        // build an ARGB image with same dimension than sequence
        img = new BufferedImage(sequence.getSizeX(), sequence.getSizeY(), BufferedImage.TYPE_INT_ARGB);
        // get internal image data reference
        imgData = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();

        // listen viewer changes so we know when current displayed image change
        viewer.addListener(this);
        // listen sequence changes so we know when sequence rois are modified
        sequence.addListener(this);

        refresh();
    }

    // we call this method when we want to terminate plugin execution
    private void close()
    {
        // remove viewer listener
        viewer.removeListener(this);
        // remove sequence listener
        sequence.removeListener(this);
        // remove the painter from the sequence (same as painter.detachFromAll())
        sequence.removePainter(painter);

        // free sequence reference
        viewer = null;
        sequence = null;
    }

    private void refresh()
    {
        // clear image
        Arrays.fill(imgData, 0);

        final IcyBufferedImage currentImage = getFocusedImage();

        if (currentImage != null)
        {
            // get image bounds (rectangle defining height and width of image)
            final Rectangle imageBounds = currentImage.getBounds();

            BooleanMask2D globalMask = null;

            // compute global boolean mask of all ROI2D contained in the sequence
            for (ROI2D roi : sequence.getROI2Ds())
            {
                // get intersection between image and roi bounds
                final Rectangle intersect = roi.getBounds().intersection(imageBounds);
                // get the boolean mask of roi (optimized from intersection bounds)
                final boolean[] mask = roi.getAsBooleanMask(intersect);

                // update global mask
                if (globalMask == null)
                    globalMask = new BooleanMask2D(intersect, mask);
                else
                    globalMask.union(intersect, mask);
            }

            // process only if global mask is not empty
            if ((globalMask != null) && (!globalMask.bounds.isEmpty()))
            {
                final Rectangle bounds = globalMask.bounds;
                final boolean[] mask = globalMask.mask;

                // calculate offset
                int offMsk = 0;
                int offImg = (bounds.y * imageBounds.width) + bounds.x;

                // do process only on data contained in ROI
                for (int y = 0; y < bounds.height; y++)
                {
                    // override all contained pixels with half transparent green
                    for (int x = 0; x < bounds.width; x++)
                        if (mask[offMsk + x])
                            imgData[offImg + x] = 0x8000FF00;

                    offMsk += bounds.width;
                    offImg += imageBounds.width;
                }
            }
        }

        // notify that our painter has changed (image data modified)
        // this method is a facility of AbstractPainter
        // this is equivalent to sequence.painterChanged(painter);
        painter.changed();
    }

    // called when sequence has changed
    @Override
    public void sequenceChanged(SequenceEvent sequenceEvent)
    {
        // we want to know about sequence roi changes
        switch (sequenceEvent.getSourceType())
        {
            case SEQUENCE_ROI:
                // roi change --> refresh
                refresh();
                break;
        }
    }

    // called when sequence is closed (last viewer containing sequence has changed
    @Override
    public void sequenceClosed(Sequence sequence)
    {
    }

    @Override
    public void viewerChanged(ViewerEvent event)
    {
        // we want to know about navigation change (current displayed image change)
        if (event.getType() == ViewerEventType.POSITION_CHANGED)
            // refresh
            refresh();
    }

    @Override
    public void viewerClosed(Viewer viewer)
    {
        // end plugin execution
        close();
    }
}
 * \endcode
 *           
 * @formatter:off
 * 
 */
public class ProcessingFromROI extends Plugin implements PluginImageAnalysis, SequenceListener, ViewerListener
{
    private Viewer viewer;
    private Sequence sequence;
    private AbstractPainter painter;
    BufferedImage img;
    private int[] imgData;

    @Override
    public void compute()
    {
        viewer = getFocusedViewer();

        // no viewer has been found ?
        if (viewer == null)
        {
            // display an information message as we need an opened sequence
            MessageDialog.showDialog("This example needs a sequence to start. Please load an image file.",
                    MessageDialog.INFORMATION_MESSAGE);
            return;
        }

        // we should avoid direct sequence reference but it really help there
        sequence = viewer.getSequence();

        // display an announcement with Plugin description
        new AnnounceFrame("This example show how do localized operation on image from ROI");
        // no ROI2D in sequence
        if (!sequence.hasROI(ROI2D.class))
            new AnnounceFrame("Add a ROI to the sequence to see plugin action");

        // define a painter to just draw the image over the sequence
        painter = new AbstractPainter()
        {
            @Override
            public void paint(Graphics2D g, Sequence sequence, IcyCanvas canvas)
            {
                // just draw the image over the sequence
                g.drawImage(img, null, 0, 0);
            }
        };

        // add the painter to the sequence
        sequence.addPainter(painter);

        // build an ARGB image with same dimension than sequence
        img = new BufferedImage(sequence.getSizeX(), sequence.getSizeY(), BufferedImage.TYPE_INT_ARGB);
        // get internal image data reference
        imgData = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();

        // listen viewer changes so we know when current displayed image change
        viewer.addListener(this);
        // listen sequence changes so we know when sequence rois are modified
        sequence.addListener(this);

        refresh();
    }

    // we call this method when we want to terminate plugin execution
    private void close()
    {
        // remove viewer listener
        viewer.removeListener(this);
        // remove sequence listener
        sequence.removeListener(this);
        // remove the painter from the sequence (same as painter.detachFromAll())
        sequence.removePainter(painter);

        // free sequence reference
        viewer = null;
        sequence = null;
    }

    private void refresh()
    {
        // clear image
        Arrays.fill(imgData, 0);

        final IcyBufferedImage currentImage = getFocusedImage();

        if (currentImage != null)
        {
            // get image bounds (rectangle defining height and width of image)
            final Rectangle imageBounds = currentImage.getBounds();

            BooleanMask2D globalMask = null;

            // compute global boolean mask of all ROI2D contained in the sequence
            for (ROI2D roi : sequence.getROI2Ds())
            {
                // get intersection between image and roi bounds
                final Rectangle intersect = roi.getBounds().intersection(imageBounds);
                // get the boolean mask of roi (optimized from intersection bounds)
                final boolean[] mask = roi.getAsBooleanMask(intersect);

                // update global mask
                if (globalMask == null)
                    globalMask = new BooleanMask2D(intersect, mask);
                else
                    globalMask.union(intersect, mask);
            }

            // process only if global mask is not empty
            if ((globalMask != null) && (!globalMask.bounds.isEmpty()))
            {
                final Rectangle bounds = globalMask.bounds;
                final boolean[] mask = globalMask.mask;

                // calculate offset
                int offMsk = 0;
                int offImg = (bounds.y * imageBounds.width) + bounds.x;

                // do process only on data contained in ROI
                for (int y = 0; y < bounds.height; y++)
                {
                    // override all contained pixels with half transparent green
                    for (int x = 0; x < bounds.width; x++)
                        if (mask[offMsk + x])
                            imgData[offImg + x] = 0x8000FF00;

                    offMsk += bounds.width;
                    offImg += imageBounds.width;
                }
            }
        }

        // notify that our painter has changed (image data modified)
        // this method is a facility of AbstractPainter
        // this is equivalent to sequence.painterChanged(painter);
        painter.changed();
    }

    // called when sequence has changed
    @Override
    public void sequenceChanged(SequenceEvent sequenceEvent)
    {
        // we want to know about sequence roi changes
        switch (sequenceEvent.getSourceType())
        {
            case SEQUENCE_ROI:
                // roi change --> refresh
                refresh();
                break;
        }
    }

    // called when sequence is closed (last viewer containing sequence has changed
    @Override
    public void sequenceClosed(Sequence sequence)
    {
    }

    @Override
    public void viewerChanged(ViewerEvent event)
    {
        // we want to know about navigation change (current displayed image change)
        if (event.getType() == ViewerEventType.POSITION_CHANGED)
            // refresh
            refresh();
    }

    @Override
    public void viewerClosed(Viewer viewer)
    {
        // end plugin execution
        close();
    }
}
