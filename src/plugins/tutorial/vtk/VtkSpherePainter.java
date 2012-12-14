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
import icy.sequence.Sequence;
import vtk.vtkActor;
import vtk.vtkPolyDataMapper;
import vtk.vtkProp;
import vtk.vtkSphereSource;

/**
 * @author stephane
 */
public class VtkSpherePainter extends AbstractPainter implements VtkPainter
{
    private vtkActor aSphere;

    public VtkSpherePainter(Sequence sequence)
    {
        super();

        init();

        // attach to sequence only when init is done
        attachTo(sequence);
    }

    // init vtk objects
    private void init()
    {
        // source
        final vtkSphereSource sphere = new vtkSphereSource();
        sphere.SetRadius(100);
        sphere.SetThetaResolution(18);
        sphere.SetPhiResolution(18);

        // mapper
        final vtkPolyDataMapper map = new vtkPolyDataMapper();
        map.SetInput(sphere.GetOutput());

        // actor
        aSphere = new vtkActor();
        aSphere.SetMapper(map);
        aSphere.GetProperty().SetColor(0, 0, 1); // color blue
    }

    @Override
    public vtkProp[] getProps()
    {
        return new vtkProp[] {aSphere};
    }
}
