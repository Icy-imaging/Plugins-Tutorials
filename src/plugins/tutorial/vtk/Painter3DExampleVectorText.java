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
import vtk.vtkActor2D;
import vtk.vtkFollower;
import vtk.vtkPolyDataMapper;
import vtk.vtkVectorText;

/**
 * @author stephane
 */
public class Painter3DExampleVectorText extends PluginActionable
{
    private static class VectorText3DPainter extends AbstractPainter implements VtkPainter
    {
        private vtkFollower textActor;

        public VectorText3DPainter(Sequence sequence)
        {
            super();

            init();

            // attach to sequence only when init is done
            attachTo(sequence);
        }

        // init vtk objects
        private void init()
        {
            // Create the 3D text and the associated mapper and follower (a type of actor)
            // Position the text so it is displayed over the origin of the axes.
            final vtkVectorText atext = new vtkVectorText();
            atext.SetText("Origin");

            final vtkPolyDataMapper textMapper = new vtkPolyDataMapper();
            textMapper.SetInput(atext.GetOutput());

            textActor = new vtkFollower();
            textActor.SetMapper(textMapper);
            textActor.SetScale(100, 100, 100);
            // textActor.AddPosition(0, -0.1, 0);
        }

        @Override
        public vtkActor[] getActors()
        {
            return new vtkActor[] {textActor};
        }

        @Override
        public vtkActor2D[] getActors2D()
        {
            return new vtkActor2D[] {};
        }
    }

    @Override
    public void run()
    {
        // create painter
        new VectorText3DPainter(getFocusedSequence());
    }
}
