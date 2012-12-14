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
import vtk.vtkProp;
import vtk.vtkTextActor;
import vtk.vtkTextProperty;

/**
 * This plugin shows how to use VTK to render a simple 2D text as 3D painter
 * 
 * @author Stephane
 */
public class Painter3DExample2DText extends PluginActionable
{
    private static class Text2DPainter extends AbstractPainter implements VtkPainter
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
        public vtkProp[] getProps()
        {
            return new vtkProp[] {textActor};
        }
    }

    @Override
    public void run()
    {
        // create painter
        new Text2DPainter(getFocusedSequence());
    }
}
