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
import icy.painter.VtkPainter;
import icy.plugin.abstract_.PluginActionable;
import icy.sequence.Sequence;

import java.awt.Graphics2D;

import vtk.vtkActor;
import vtk.vtkActor2D;
import vtk.vtkCubeSource;
import vtk.vtkLabeledDataMapper;
import vtk.vtkRenderer;
import vtk.vtkSelectVisiblePoints;

/**
 * @author stephane
 */
public class Painter3DExampleLabel extends PluginActionable
{
    private static class Label3DPainter extends AbstractPainter implements VtkPainter
    {
        private vtkActor2D pointLabels;
        private vtkSelectVisiblePoints visPts;

        public Label3DPainter(Sequence sequence)
        {
            super();

            init();

            // attach to sequence only when init is done
            attachTo(sequence);
        }

        // init vtk objects
        private void init()
        {
            // labellers to display coordinates
            final vtkCubeSource cubeSource = new vtkCubeSource();
            cubeSource.SetBounds(0, 50, 100, 150, 300, 200);

            // Create labels for points
            visPts = new vtkSelectVisiblePoints();
            visPts.SetInput(cubeSource.GetOutput());

            // Create the mapper to display the point ids. Specify the format to
            // use for the labels. Also create the associated actor.
            final vtkLabeledDataMapper ldm = new vtkLabeledDataMapper();

            ldm.SetInput(visPts.GetOutput());
            ldm.SetLabelFormat("%g");
            ldm.SetLabelModeToLabelFieldData();

            pointLabels = new vtkActor2D();
            pointLabels.SetMapper(ldm);
        }

        @Override
        public void paint(Graphics2D g, Sequence sequence, IcyCanvas canvas)
        {
            if (canvas instanceof Canvas3D)
            {
                final vtkRenderer renderer = ((Canvas3D) canvas).getRenderer();

                if (visPts.GetRenderer() == null)
                    visPts.SetRenderer(renderer);
            }
        }

        @Override
        public vtkActor[] getActors()
        {
            return new vtkActor[] {};
        }

        @Override
        public vtkActor2D[] getActors2D()
        {
            return new vtkActor2D[] {pointLabels};
        }
    }

    @Override
    public void run()
    {
        // create painter
        new Label3DPainter(getFocusedSequence());
    }
}
