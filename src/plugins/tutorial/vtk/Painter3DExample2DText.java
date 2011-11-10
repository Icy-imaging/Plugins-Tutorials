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

import vtk.vtkTextActor;
import vtk.vtkTextProperty;

/**
 * This plugin shows how to use VTK to render a simple 2D text as 3D painter
 * 
 * @author Stephane
 */
public class Painter3DExample2DText extends Plugin implements PluginImageAnalysis
{
    private static class Text2DPainter extends AbstractPainter
    {
        // vtk object
        private vtkTextActor textActor;

        public Text2DPainter(Sequence sequence)
        {
            super();

            init();

            // attach to sequence only when init is done
            attachTo(sequence);
        }

        // init vtk objects
        private void init()
        {
            // VTK 2D text object
            textActor = new vtkTextActor();
            textActor.SetInput("VTK text test");

            final vtkTextProperty textProperty = textActor.GetTextProperty();

            // change text properties
            textProperty.SetFontFamilyToArial();
            textProperty.BoldOn();
            textProperty.ShadowOn();
            textProperty.SetFontSize(30);
            textProperty.SetColor(0.5, 0.5, 0.5);
            textProperty.SetShadowOffset(2, 2);

            // set text position
            textActor.SetPosition(100, 300);
        }

        @Override
        public void paint(Graphics2D g, Sequence sequence, IcyCanvas canvas)
        {
            if (canvas instanceof Canvas3D)
            {
                // add actor to the renderer if not already exist
                VtkUtil.addActor2D(((Canvas3D) canvas).getRenderer(), textActor);
            }
        }
    }

    @Override
    public void compute()
    {
        // create painter
        new Text2DPainter(getFocusedSequence());
    }
}
