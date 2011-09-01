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
import icy.plugin.abstract_.Plugin;
import icy.plugin.interface_.PluginImageAnalysis;
import icy.swimmingPool.SwimmingObject;
import icy.swimmingPool.SwimmingPoolEvent;
import icy.swimmingPool.SwimmingPoolEventType;
import icy.swimmingPool.SwimmingPoolListener;

import java.awt.geom.Point2D;

/**
 *\page tuto12 Tutorial: Plugin Communication : SwimmingPool : How to read and watch/listen data event of the swimming pool 
 *
 *\code
 *
 *
 *public class SwimmingPoolListenerTutorial extends Plugin implements PluginImageAnalysis , SwimmingPoolListener {

	@Override
	public void compute() {

		// We register this class as a listener of the swimmingPool
		Icy.getMainInterface().getSwimmingPool().addListener( this );

		new AnnounceFrame( "Swimming pool listener example: I am listening/watching the swimmingpool." );
		
		// watch if objects are already in the swimming pool:
		for ( SwimmingObject swimmingObject : Icy.getMainInterface().getSwimmingPool().getObjects() )
		{
			if ( swimmingObject.getObject() instanceof Point2D )
			{
				Point2D point = (Point2D) swimmingObject.getObject();
				new AnnounceFrame("Swimming pool listener example: There is already an object in the swimming pool ! Its coordinates are "+ point.getX() + "," + point.getY() );
			}
		}
		
	}

	@Override
	public void swimmingPoolChangeEvent(SwimmingPoolEvent event) {

		// an object has been added in the swimming pool !
		if ( event.getType() == SwimmingPoolEventType.ELEMENT_ADDED )
		{
			// Can we manage this type ?
			if ( event.getResult().getObject() instanceof Point2D )
			{
				// yes, we know it, let's process it.
				Point2D point = (Point2D) event.getResult().getObject();
				new AnnounceFrame("Swimming pool listener example: A point has been dropped ! Its coordinates are "+ point.getX() + "," + point.getY() );
			}else
			{
				// no, we don't know what we should do with it.
				new AnnounceFrame( "Swimming pool listener example: Something has been dropped, but I don't know how to handle it." );		
			}
		}

	}

	
	
}
 *
 *\endcode
 *
 * This is a swimming pool listener tutorial. Use it with SwimmingPoolEmitterTutorial.
 *
 * The swimming pool is a place where any plugin can access, listen, create, delete or transform objects.
 * This is the ultimate flexibility to create communication between plugins.
 * 
 * This tutorial explain how to register a listener, and listen to a certain type of object.
 *
 * @author Fabrice de Chaumont and Stephane Dallongeville
 */
public class SwimmingPoolListenerTutorial extends Plugin implements PluginImageAnalysis , SwimmingPoolListener {

	@Override
	public void compute() {

		// We register this class as a listener of the swimmingPool
		Icy.getMainInterface().getSwimmingPool().addListener( this );

		new AnnounceFrame( "Swimming pool listener example: I am listening/watching the swimmingpool." );
		
		// watch if objects are already in the swimming pool:
		for ( SwimmingObject swimmingObject : Icy.getMainInterface().getSwimmingPool().getObjects() )
		{
			if ( swimmingObject.getObject() instanceof Point2D )
			{
				Point2D point = (Point2D) swimmingObject.getObject();
				new AnnounceFrame("Swimming pool listener example: There is already an object in the swimming pool ! Its coordinates are "+ point.getX() + "," + point.getY() );
			}
		}
		
	}

	@Override
	public void swimmingPoolChangeEvent(SwimmingPoolEvent event) {

		// an object has been added in the swimming pool !
		if ( event.getType() == SwimmingPoolEventType.ELEMENT_ADDED )
		{
			// Can we manage this type ?
			if ( event.getResult().getObject() instanceof Point2D )
			{
				// yes, we know it, let's process it.
				Point2D point = (Point2D) event.getResult().getObject();
				new AnnounceFrame("Swimming pool listener example: A point has been dropped ! Its coordinates are "+ point.getX() + "," + point.getY() );
			}else
			{
				// no, we don't know what we should do with it.
				new AnnounceFrame( "Swimming pool listener example: Something has been dropped, but I don't know how to handle it." );		
			}
		}

	}

	
	
}
