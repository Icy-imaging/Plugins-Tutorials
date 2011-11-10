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

import icy.canvas.Canvas2D;
import icy.canvas.Canvas3D;
import icy.canvas.IcyCanvas;
import icy.painter.AbstractPainter;
import icy.plugin.abstract_.Plugin;
import icy.plugin.interface_.PluginImageAnalysis;
import icy.sequence.Sequence;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import javax.swing.Timer;

import vtk.vtkActor;
import vtk.vtkActorCollection;
import vtk.vtkEarthSource;
import vtk.vtkPolyDataMapper;
import vtk.vtkRenderer;

/**
 * @author stephane
 */
public class Painter3DExampleAnimatedEarth extends Plugin implements PluginImageAnalysis
{
    private static class AnimatedEarth3DPainter extends AbstractPainter implements ActionListener
    {
        final Sequence seq;

        // vtk objects
        private vtkEarthSource earth;
        private vtkPolyDataMapper earthMapper;
        private vtkActor earthActor;
        vtkRenderer renderer;

        private boolean initialized;

        private double posX, posY, posZ;

        public AnimatedEarth3DPainter(Sequence sequence)
        {
            initialized = false;

            seq = sequence;

            // add painter to the sequence
            if (seq != null)
                seq.addPainter(this);

            final Timer t = new Timer(20, this);
            t.start();
        }

        // init vtk objects
        private void init()
        {
            earth = new vtkEarthSource();
            earth.SetOnRatio(earth.GetOnRatioMaxValue()); // ( 1 to 16 )
            earth.OutlineOn();
            earth.SetRadius(150);

            earthMapper = new vtkPolyDataMapper();
            earthMapper.SetInput(earth.GetOutput());

            earthActor = new vtkActor();
            earthActor.SetMapper(earthMapper);

            posX = posY = posZ = 0;
        }

        // deinit vtk objects
        private void deinit()
        {
            earthActor.Delete();
            earthMapper.Delete();
            earth.Delete();
        }

        private vtkActor findActor(vtkRenderer renderer, vtkActor actor)
        {
            final vtkActorCollection actors = renderer.GetActors();

            // search if actor already present in render
            actors.InitTraversal();
            for (int i = 0; i < actors.GetNumberOfItems(); i++)
            {
                final vtkActor curactor = actors.GetNextActor();

                // already present --> exit
                if (curactor == actor)
                    return curactor;

                // curactor.InitPathTraversal();
                // for (int j = 0; j < curactor.GetNumberOfPaths(); j++)
                // {
                // final vtkAssemblyPath curpath = curactor.GetNextPath();
                //
                // // already present --> exit
                // if (curpath == actor)
                // return curpart;
                // }
            }

            return null;
        }

        @Override
        public void paint(Graphics2D g, Sequence sequence, IcyCanvas canvas)
        {
            if (canvas instanceof Canvas3D)
            {
                // 3D canvas
                final Canvas3D canvas3d = (Canvas3D) canvas;
                renderer = canvas3d.getRenderer();

                if (!initialized)
                {
                    init();
                    initialized = true;
                }

                // try to get earth actor from renderer
                vtkActor actor = findActor(renderer, earthActor);
                // actor not found ?
                if (actor == null)
                {
                    // add actor to renderer
                    renderer.AddActor(earthActor);
                    actor = earthActor;
                }

                // update position
                actor.SetOrientation(posX, posY, posZ);
            }
            else if (canvas instanceof Canvas2D)
            {
                // 2D canvas
                final Canvas2D canvas2d = (Canvas2D) canvas;

                if (initialized)
                {
                    deinit();
                    initialized = false;
                }
            }
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            posX += 1;
            // refresh painter (this will called 'paint' at some point)
            seq.painterChanged(this);
            // earthActor.SetOrientation(posX, posY, posZ);
            // earth.Update();
        }

        @Override
        public void keyPressed(KeyEvent e, Point2D imagePoint, IcyCanvas canvas)
        {
            // TODO Auto-generated method stub

        }

        @Override
        public void mouseClick(MouseEvent e, Point2D p, IcyCanvas canvas)
        {
            // TODO Auto-generated method stub

        }

        @Override
        public void mouseDrag(MouseEvent e, Point2D p, IcyCanvas canvas)
        {
            // TODO Auto-generated method stub

        }

        @Override
        public void mouseMove(MouseEvent e, Point2D p, IcyCanvas canvas)
        {
            // TODO Auto-generated method stub

        }

        @Override
        public void keyReleased(KeyEvent e, Point2D imagePoint, IcyCanvas canvas)
        {
            // TODO Auto-generated method stub

        }

        @Override
        public void mousePressed(MouseEvent e, Point2D imagePoint, IcyCanvas canvas)
        {
            // TODO Auto-generated method stub

        }

        @Override
        public void mouseReleased(MouseEvent e, Point2D imagePoint, IcyCanvas canvas)
        {
            // TODO Auto-generated method stub

        }
    }

    @Override
    public void compute()
    {
        // create painter
        new AnimatedEarth3DPainter(getFocusedSequence());
    }

}
