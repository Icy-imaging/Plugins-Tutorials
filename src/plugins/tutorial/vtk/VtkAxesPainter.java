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
import vtk.vtkAxisActor;
import vtk.vtkCamera;
import vtk.vtkCubeAxesActor;
import vtk.vtkOrientationMarkerWidget;
import vtk.vtkProp;
import vtk.vtkRenderWindowInteractor;

/**
 * @author stephane
 */
public class VtkAxesPainter extends Overlay implements VtkPainter
{
    // vtk objects
    private vtkAxisActor axesActor;
    private vtkCubeAxesActor cubeAxesActor;
    private vtkRenderWindowInteractor renderWindowInteractor;
    private vtkOrientationMarkerWidget widget;

    public VtkAxesPainter()
    {
        super("VTK Axes");

        init();
    }

    // init vtk objects
    private void init()
    {
        // 3D axes
        axesActor = new vtkAxisActor();
        axesActor.SetOrigin(0, 0, 0);
        axesActor.SetPosition(0, 0, 0);

        renderWindowInteractor = new vtkRenderWindowInteractor();
        widget = new vtkOrientationMarkerWidget();
        widget.SetOutlineColor(0.9300, 0.5700, 0.1300);
        widget.SetOrientationMarker(axesActor);
        widget.SetInteractor(renderWindowInteractor);
        widget.SetViewport(0.0, 0.0, 0.4, 0.4);

        cubeAxesActor = new vtkCubeAxesActor();
        cubeAxesActor.SetOrigin(0, 0, 0);
    }

    @Override
    public void paint(Graphics2D g, Sequence sequence, IcyCanvas canvas)
    {
        // super.paint(g, sequence, canvas);

        if (canvas instanceof VtkCanvas)
        {
            final VtkCanvas c3d = (VtkCanvas) canvas;
            final vtkCamera camera = c3d.getRenderer().GetActiveCamera();

            if (cubeAxesActor.GetCamera() != camera)
            {
                renderWindowInteractor.SetRenderWindow(c3d.getRenderer().GetRenderWindow());
                widget.SetEnabled(1);
                widget.InteractiveOn();

                cubeAxesActor.SetBounds(0, c3d.getImageSizeX(), 0, c3d.getImageSizeY(), 0, c3d.getImageSizeZ());
                cubeAxesActor.SetCamera(camera);
            }
        }
    }

    @Override
    public vtkProp[] getProps()
    {
        return new vtkProp[] {axesActor, cubeAxesActor};
    }
}
