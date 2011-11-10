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

import icy.canvas.Canvas3D;
import icy.canvas.IcyCanvas;
import icy.painter.AbstractPainter;
import icy.plugin.abstract_.Plugin;
import icy.plugin.interface_.PluginImageAnalysis;
import icy.sequence.Sequence;
import icy.vtk.VtkUtil;

import java.awt.Graphics2D;

import vtk.vtkActor;
import vtk.vtkAxes;
import vtk.vtkPolyDataMapper;

/**
 * @author stephane
 */
public class Painter3DExampleAxes extends Plugin implements PluginImageAnalysis
{
    private static class Axes3DPainter extends AbstractPainter
    {
        // vtk object
        private vtkActor axesActor;

        public Axes3DPainter(Sequence sequence)
        {
            super();

            init();

            // attach to sequence only when init is done
            attachTo(sequence);
        }

        // init vtk objects
        private void init()
        {
            // 3D axes
            final vtkAxes axes = new vtkAxes();
            axes.SetOrigin(0, 0, 0);
            axes.SetScaleFactor(100);

            final vtkPolyDataMapper axesMapper = new vtkPolyDataMapper();
            axesMapper.SetInput(axes.GetOutput());

            axesActor = new vtkActor();
            axesActor.SetMapper(axesMapper);
        }

        @Override
        public void paint(Graphics2D g, Sequence sequence, IcyCanvas canvas)
        {
            if (canvas instanceof Canvas3D)
            {
                // add actor to the renderer if not already exist
                VtkUtil.addActor(((Canvas3D) canvas).getRenderer(), axesActor);
            }
        }
    }

    @Override
    public void compute()
    {
        // create painter
        new Axes3DPainter(getFocusedSequence());
    }
}
