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

import icy.plugin.abstract_.Plugin;
import icy.plugin.interface_.PluginImageAnalysis;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import vtk.vtkActor;
import vtk.vtkConeSource;
import vtk.vtkPanel;
import vtk.vtkPolyDataMapper;

/**
 * A very basic application that simple displays a cone using vtkPanel. Fabrice de Chaumont
 */
public class SimpleVtk3DExample extends Plugin implements PluginImageAnalysis
{
    private final vtkPanel renWin;

    public SimpleVtk3DExample()
    {
        System.out.println("This example displays a cone in its own window.");

        // Setup VTK rendering panel
        renWin = new vtkPanel();

        final JFrame frame = new JFrame("SimpleCone");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.getContentPane().add("Center", renWin);
        frame.pack();
        frame.setVisible(true);

        // Setup cone rendering pipeline
        final vtkConeSource cone = new vtkConeSource();
        cone.SetResolution(8);

        final vtkPolyDataMapper coneMapper = new vtkPolyDataMapper();
        coneMapper.SetInput(cone.GetOutput());

        final vtkActor coneActor = new vtkActor();
        coneActor.SetMapper(coneMapper);

        renWin.GetRenderer().AddActor(coneActor);

    }

    public vtkPanel getRenWin()
    {
        return renWin;
    }

    @Override
    public void compute()
    {

    }

}
