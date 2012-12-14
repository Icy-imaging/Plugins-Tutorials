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
import vtk.vtkCardinalSpline;
import vtk.vtkCellArray;
import vtk.vtkGlyph3D;
import vtk.vtkMath;
import vtk.vtkPoints;
import vtk.vtkPolyData;
import vtk.vtkPolyDataMapper;
import vtk.vtkProp;
import vtk.vtkSphereSource;
import vtk.vtkTubeFilter;

/**
 * @author stephane
 */
public class Painter3DExampleComplexeSpline extends PluginActionable
{
    private static class ComplexeSpline3DPainter extends AbstractPainter implements VtkPainter
    {
        // vtk object
        private vtkActor glyph;
        private vtkActor profile;

        public ComplexeSpline3DPainter(Sequence sequence)
        {
            super();

            init();

            // attach to sequence only when init is done
            attachTo(sequence);
        }

        // init vtk objects
        private void init()
        {
            // This will be used later to get random numbers.
            final vtkMath math = new vtkMath();
            // Total number of points.
            final int numberOfInputPoints = 20;

            // One spline for each direction.
            final vtkCardinalSpline aSplineX = new vtkCardinalSpline();
            final vtkCardinalSpline aSplineY = new vtkCardinalSpline();
            final vtkCardinalSpline aSplineZ = new vtkCardinalSpline();

            // Generate random (pivot); points and add the corresponding
            // coordinates to the splines.
            // aSplineX will interpolate the x values of the points
            // aSplineY will interpolate the y values of the points
            // aSplineZ will interpolate the z values of the points
            final vtkPoints inputPoints = new vtkPoints();
            for (int i = 0; i < numberOfInputPoints; i++)
            {
                final double x = 200 * math.Random(0, 1);
                final double y = 100 * math.Random(0, 1);
                final double z = 50 * math.Random(0, 1);
                aSplineX.AddPoint(i, x);
                aSplineY.AddPoint(i, y);
                aSplineZ.AddPoint(i, z);
                inputPoints.InsertPoint(i, x, y, z);
            }

            // The following section will create glyphs for the pivot points
            // in order to make the effect of the spline more clear.

            // Create a polydata to be glyphed.
            final vtkPolyData inputData = new vtkPolyData();
            inputData.SetPoints(inputPoints);

            // Use sphere as glyph source.
            final vtkSphereSource balls = new vtkSphereSource();
            // balls.SetRadius(.02);
            balls.SetRadius(5);
            balls.SetPhiResolution(10);
            balls.SetThetaResolution(10);

            final vtkGlyph3D glyphPoints = new vtkGlyph3D();
            glyphPoints.SetInput(inputData);
            glyphPoints.SetSource(balls.GetOutput());

            final vtkPolyDataMapper glyphMapper = new vtkPolyDataMapper();
            glyphMapper.SetInput(glyphPoints.GetOutput());

            glyph = new vtkActor();
            glyph.SetMapper(glyphMapper);
            glyph.GetProperty().SetDiffuseColor(1, 1, 0);
            glyph.GetProperty().SetSpecular(.3);
            glyph.GetProperty().SetSpecularPower(30);

            // Generate the polyline for the spline.
            final vtkPoints points = new vtkPoints();
            final vtkPolyData profileData = new vtkPolyData();

            // Number of points on the spline
            final int numberOfOutputPoints = 4000;

            // Interpolate x, y and z by using the three spline filters and
            // create new points
            for (int i = 0; i < numberOfOutputPoints; i++)
            {
                final double t = (numberOfInputPoints - 1.0) / (numberOfOutputPoints - 1.0) * i;
                points.InsertPoint(i, aSplineX.Evaluate(t), aSplineY.Evaluate(t), aSplineZ.Evaluate(t));
            }

            // Create the polyline.
            final vtkCellArray lines = new vtkCellArray();
            lines.InsertNextCell(numberOfOutputPoints);
            for (int i = 0; i < numberOfOutputPoints; i++)
                lines.InsertCellPoint(i);

            profileData.SetPoints(points);
            profileData.SetLines(lines);

            // Add thickness to the resulting line.
            final vtkTubeFilter profileTubes = new vtkTubeFilter();
            profileTubes.SetNumberOfSides(8);
            profileTubes.SetInput(profileData);
            // profileTubes.SetRadius(.01);
            profileTubes.SetRadius(1);

            final vtkPolyDataMapper profileMapper = new vtkPolyDataMapper();
            profileMapper.SetInput(profileTubes.GetOutput());

            profile = new vtkActor();
            profile.SetMapper(profileMapper);

            profile.GetProperty().SetOpacity(1);
            profile.GetProperty().SetDiffuseColor(1, 0, 1);
            profile.GetProperty().SetSpecular(.3);
            profile.GetProperty().SetSpecularPower(30);
        }

        @Override
        public vtkProp[] getProps()
        {
            return new vtkProp[] {glyph, profile};
        }
    }

    @Override
    public void run()
    {
        // create painter
        new ComplexeSpline3DPainter(getFocusedSequence());
    }
}
