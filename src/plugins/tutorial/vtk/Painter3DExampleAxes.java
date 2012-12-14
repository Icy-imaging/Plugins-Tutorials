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

import icy.painter.AbstractPainter;
import icy.painter.VtkPainter;
import icy.plugin.abstract_.PluginActionable;
import icy.sequence.Sequence;
import vtk.vtkActor;
import vtk.vtkAxes;
import vtk.vtkPolyDataMapper;
import vtk.vtkProp;

/**
 * @author stephane
 */
public class Painter3DExampleAxes extends PluginActionable
{
    private static class Axes3DPainter extends AbstractPainter implements VtkPainter
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
        public vtkProp[] getProps()
        {
            return new vtkProp[] {axesActor};
        }
    }

    @Override
    public void run()
    {
        // create painter
        new Axes3DPainter(getFocusedSequence());
    }
}
