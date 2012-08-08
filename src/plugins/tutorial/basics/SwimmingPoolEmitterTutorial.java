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
package plugins.tutorial.basics;

import icy.gui.frame.progress.AnnounceFrame;
import icy.main.Icy;
import icy.plugin.abstract_.PluginActionable;
import icy.swimmingPool.SwimmingObject;

import java.awt.geom.Point2D;

/**
 *\page tuto11 Tutorial: Plugin Communication : SwimmingPool : How to send data to the swimming pool   
 *
 *\code
 
public class SwimmingPoolEmitterTutorial extends PluginActionable
{
    @Override
    public void run() {     
        // Create my data, of any type I wish. I chose Point2D
        Point2D myData = new Point2D.Double( Math.random() , Math.random() );

        // Put my object in a Swimming Object
        SwimmingObject swimmingObject = new SwimmingObject( myData );
        
        // add the object in the swimming pool
        Icy.getMainInterface().getSwimmingPool().add( swimmingObject );         
        
        new AnnounceFrame("Swimming pool emitter: I Just put an object, use a swimming pool listener to watch result." );               

        // done !       
    }   
}

 *\endcode
 *
 * @formatter:off
 *   
 * This is a swimming pool emitter tutorial. Use it with SwimmingPoolListenerTutorial.
 *
 * The swimming pool is a place where any plugin can access, listen, create, delete or transform objects.
 * This is the ultimate flexibility to create communication between plugins.
 * 
 * This tutorial explain how to send an object in the swimming pool.
 * 
 * @author Fabrice de Chaumont and Stephane Dallongeville

 */
public class SwimmingPoolEmitterTutorial extends PluginActionable
{
	@Override
	public void run() {		
		// Create my data, of any type I wish. I chose Point2D
		Point2D myData = new Point2D.Double( Math.random() , Math.random() );

		// Put my object in a Swimming Object
		SwimmingObject swimmingObject = new SwimmingObject( myData );
		
		// add the object in the swimming pool
		Icy.getMainInterface().getSwimmingPool().add( swimmingObject );			
		
		new AnnounceFrame("Swimming pool emitter: I Just put an object, use a swimming pool listener to watch result." );        		

		// done !		
	}	
}
