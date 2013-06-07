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

import icy.painter.Overlay;
import icy.painter.VtkPainter;
import icy.vtk.VtkUtil;
import vtk.vtkActor;
import vtk.vtkCellArray;
import vtk.vtkPoints;
import vtk.vtkPolyData;
import vtk.vtkPolyDataMapper;
import vtk.vtkProp;

/**
 * This plugin shows how use VTK to render a simple cube mesh as a 3D painter
 * 
 * @author Stephane
 */
public class VtkCubePainter extends Overlay implements VtkPainter
{
    private static final double[][] cube_vertex = new double[][] { {-10, -10, -10}, {-10, 10, -10}, {10, 10, -10},
            {10, -10, -10}, {-10, -10, 10}, {-10, 10, 10}, {10, 10, 10}, {10, -10, 10}};
    private static final int[][] cube_poly = new int[][] { {0, 1, 2}, {0, 2, 3}, {4, 5, 1}, {4, 1, 0}, {3, 2, 6},
            {3, 6, 7}, {1, 5, 6}, {1, 6, 2}, {4, 0, 3}, {4, 3, 7}, {7, 6, 5}, {7, 5, 4}};

    private vtkActor cubeActor;

    public VtkCubePainter()
    {
        super("VTK cube");

        init();
    }

    private void init()
    {
        // vertex data
        final vtkPoints points;
        // polygon data
        final vtkCellArray cells;

        // fast java data conversion for vertexes
        points = VtkUtil.getPoints(cube_vertex);
        // fast java data conversion for cells (polygons)
        cells = VtkUtil.getCells(12, VtkUtil.prepareCells(cube_poly));

        final vtkPolyData polyData = new vtkPolyData();

        // set polygon
        polyData.SetPolys(cells);
        // set vertex
        polyData.SetPoints(points);

        // add actor to the renderer
        final vtkPolyDataMapper polyMapper = new vtkPolyDataMapper();
        polyMapper.SetInput(polyData);

        cubeActor = new vtkActor();
        cubeActor.SetMapper(polyMapper);
    }

    @Override
    public vtkProp[] getProps()
    {
        return new vtkProp[] {cubeActor};
    }
}
