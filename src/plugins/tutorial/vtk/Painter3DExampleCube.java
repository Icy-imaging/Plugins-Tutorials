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
import vtk.vtkCellArray;
import vtk.vtkPoints;
import vtk.vtkPolyData;
import vtk.vtkPolyDataMapper;

/**
 * This plugin shows how use VTK to render a simple cube mesh as a 3D painter
 * 
 * @author Stephane
 */
public class Painter3DExampleCube extends Plugin implements PluginImageAnalysis
{
    private static class Cube3DPainter extends AbstractPainter
    {
        private static final double[][] cube_vertex = new double[][] { {-10, -10, -10}, {-10, 10, -10}, {10, 10, -10},
                {10, -10, -10}, {-10, -10, 10}, {-10, 10, 10}, {10, 10, 10}, {10, -10, 10}};
        private static final int[][] cube_poly = new int[][] { {0, 1, 2}, {0, 2, 3}, {4, 5, 1}, {4, 1, 0}, {3, 2, 6},
                {3, 6, 7}, {1, 5, 6}, {1, 6, 2}, {4, 0, 3}, {4, 3, 7}, {7, 6, 5}, {7, 5, 4}};

        private vtkActor cubeActor;

        public Cube3DPainter(Sequence sequence)
        {
            super();

            init();

            // attach to sequence only when init is done
            attachTo(sequence);
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
        public void paint(Graphics2D g, Sequence sequence, IcyCanvas canvas)
        {
            if (canvas instanceof Canvas3D)
            {
                // add actor to the renderer if not already exist
                VtkUtil.addActor(((Canvas3D) canvas).getRenderer(), cubeActor);
            }
        }
    }

    @Override
    public void compute()
    {
        // create painter
        new Cube3DPainter(getFocusedSequence());
    }
}
