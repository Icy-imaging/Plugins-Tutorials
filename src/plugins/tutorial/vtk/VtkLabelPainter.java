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

import icy.canvas.IcyCanvas;
import icy.painter.Overlay;
import icy.painter.VtkPainter;
import icy.sequence.Sequence;

import java.awt.Graphics2D;

import plugins.kernel.canvas.VtkCanvas;
import vtk.vtkActor2D;
import vtk.vtkCubeSource;
import vtk.vtkLabeledDataMapper;
import vtk.vtkProp;
import vtk.vtkRenderer;
import vtk.vtkSelectVisiblePoints;

/**
 * @author stephane
 */
public class VtkLabelPainter extends Overlay implements VtkPainter
{
    private vtkActor2D pointLabels;
    private vtkSelectVisiblePoints visPts;

    public VtkLabelPainter()
    {
        super("VTK label");

        init();
    }

    // init vtk objects
    private void init()
    {
        // labellers to display coordinates
        final vtkCubeSource cubeSource = new vtkCubeSource();
        cubeSource.SetBounds(0, 50, 100, 150, 300, 200);

        // Create labels for points
        visPts = new vtkSelectVisiblePoints();
        visPts.SetInputConnection(cubeSource.GetOutputPort());

        // Create the mapper to display the point ids. Specify the format to
        // use for the labels. Also create the associated actor.
        final vtkLabeledDataMapper ldm = new vtkLabeledDataMapper();

        ldm.SetInputConnection(visPts.GetOutputPort());
        ldm.SetLabelFormat("%g");
        ldm.SetLabelModeToLabelFieldData();

        pointLabels = new vtkActor2D();
        pointLabels.SetMapper(ldm);
    }

    @Override
    public void paint(Graphics2D g, Sequence sequence, IcyCanvas canvas)
    {
        if (canvas instanceof VtkCanvas)
        {
            final vtkRenderer renderer = ((VtkCanvas) canvas).getRenderer();

            if (visPts.GetRenderer() == null)
                visPts.SetRenderer(renderer);
        }
    }

    @Override
    public vtkProp[] getProps()
    {
        return new vtkProp[] {pointLabels};
    }

}
