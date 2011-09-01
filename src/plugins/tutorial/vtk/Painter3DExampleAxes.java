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
package plugins.tutorial.vtk;

import icy.canvas.Canvas2D;
import icy.canvas.Canvas3D;
import icy.canvas.IcyCanvas;
import icy.painter.Painter;
import icy.plugin.abstract_.Plugin;
import icy.sequence.Sequence;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import vtk.vtkActor;
import vtk.vtkAxes;
import vtk.vtkPolyDataMapper;
import vtk.vtkRenderer;

/**
 * @author stephane
 */
public class Painter3DExampleAxes extends Plugin implements Painter
{
    final Sequence seq;

    private boolean initialized;

    public Painter3DExampleAxes()
    {
        initialized = false;

        seq = getFocusedSequence();

        // add painter to the sequence
        if (seq != null)
            seq.addPainter(this);
    }

    // init vtk objects
    private void init(vtkRenderer renderer)
    {
        // 3D axes
        final vtkAxes axes = new vtkAxes();
        axes.SetOrigin(0, 0, 0);
        axes.SetScaleFactor(100);
        final vtkPolyDataMapper axesMapper = new vtkPolyDataMapper();
        axesMapper.SetInput(axes.GetOutput());
        final vtkActor axesActor = new vtkActor();
        axesActor.SetMapper(axesMapper);
        renderer.AddActor(axesActor);
    }

    /*
     * (non-Javadoc)
     * 
     * @see icy.gui.painter.Painter#paint(icy.sequence.Sequence, java.awt.Graphics,
     * icy.gui.canvas.IcyCanvas)
     */
    @Override
    public void paint(Graphics2D g, Sequence sequence, IcyCanvas canvas)
    {
        if (canvas instanceof Canvas3D)
        {
            // 3D canvas
            final Canvas3D canvas3d = (Canvas3D) canvas;

            if (!initialized)
            {
                init(canvas3d.getRenderer());
                initialized = true;
            }
        }
        else if (canvas instanceof Canvas2D)
        {
            // 2D canvas
            final Canvas2D canvas2d = (Canvas2D) canvas;

        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see icy.gui.painter.Painter#keyPressed(java.awt.Point, java.awt.event.KeyEvent,
     * icy.gui.canvas.IcyCanvas)
     */
    @Override
    public void keyPressed(KeyEvent e, Point2D imagePoint, IcyCanvas canvas)
    {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see icy.gui.painter.Painter#mouseClick(java.awt.Point, java.awt.event.MouseEvent,
     * icy.gui.canvas.IcyCanvas)
     */
    @Override
    public void mouseClick(MouseEvent e, Point2D p, IcyCanvas canvas)
    {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see icy.gui.painter.Painter#mouseDrag(java.awt.Point, java.awt.event.MouseEvent,
     * icy.gui.canvas.IcyCanvas)
     */
    @Override
    public void mouseDrag(MouseEvent e, Point2D p, IcyCanvas canvas)
    {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see icy.gui.painter.Painter#mouseMove(java.awt.Point, java.awt.event.MouseEvent,
     * icy.gui.canvas.IcyCanvas)
     */
    @Override
    public void mouseMove(MouseEvent e, Point2D p, IcyCanvas canvas)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyReleased(KeyEvent e, Point2D imagePoint, IcyCanvas canvas)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent e, Point2D imagePoint, IcyCanvas canvas)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent e, Point2D imagePoint, IcyCanvas canvas)
    {
        // TODO Auto-generated method stub

    }

}
