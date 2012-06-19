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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import vtk.vtkActor;
import vtk.vtkActor2D;
import vtk.vtkEarthSource;
import vtk.vtkPolyDataMapper;

/**
 * @author stephane
 */
public class Painter3DExampleAnimatedEarth extends PluginActionable
{
    private static class AnimatedEarth3DPainter extends AbstractPainter implements ActionListener, VtkPainter
    {
        // vtk object
        private vtkActor earthActor;

        private double posX, posY, posZ;

        public AnimatedEarth3DPainter(Sequence sequence)
        {
            super();

            init();
            posX = posY = posZ = 0;

            // attach to sequence only when init is done
            attachTo(sequence);

            // start update timer
            new Timer(20, this).start();
        }

        // init vtk objects
        private void init()
        {
            final vtkEarthSource earth = new vtkEarthSource();
            earth.SetOnRatio(earth.GetOnRatioMaxValue()); // ( 1 to 16 )
            earth.OutlineOn();
            earth.SetRadius(150);

            final vtkPolyDataMapper earthMapper = new vtkPolyDataMapper();
            earthMapper.SetInput(earth.GetOutput());

            earthActor = new vtkActor();
            earthActor.SetMapper(earthMapper);
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            // update position
            earthActor.SetOrientation(++posX, posY, posZ);

            // refresh render & painters
            for (Sequence seq : getSequences())
                seq.painterChanged(this);
        }

        @Override
        public vtkActor[] getActors()
        {
            return new vtkActor[] {earthActor};
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
        new AnimatedEarth3DPainter(getFocusedSequence());
    }
}
