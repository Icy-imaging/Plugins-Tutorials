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
import icy.plugin.PluginDescriptor;
import icy.plugin.PluginLoader;
import icy.plugin.abstract_.Plugin;
import icy.plugin.interface_.PluginImageAnalysis;

/**
 * \page tuto3 Tutorial: Getting the list of registered plugin.
 * 
 * \code
 * 
 * public class ListOfRegisteredPlugins extends Plugin implements PluginImageAnalysis {

	@Override
	public void compute() {

		new AnnounceFrame("This plugin display infos about presents plugins and display it in the output tab (or console).");
		
		// Loop over all registered plugins ( recognized at startup )
		
		for ( PluginDescriptor pluginDescriptor : PluginLoader.getPlugins() )
		{
			// output the name and the author in the console ( visible in the output tab of the inspector )
			System.out.println( "Name: " + pluginDescriptor.getName() );
			
			// pluginDescriptor contains a lot of infos about plugins, you can check for update, look for dependencies, get
			// the beta version, full changelog, icons, description , author email, url, web link (...).
			
		}
		
		
	}
	
}
 *  \endcode
 * 
 * This tutorial displays the registered plugins ( installed and recognized plugins in ICY ).
 * It uses the PluginLoader and the PluginDescriptor classes.
 * The display is in the stdio.
 *
 * @author Fabrice de Chaumont and Stephane Dallongeville
 */
public class ListOfRegisteredPlugins extends Plugin implements PluginImageAnalysis {

	@Override
	public void compute() {

		new AnnounceFrame("This plugin display infos about presents plugins and display it in the output tab (or console).");
		
		// Loop over all registered plugins ( recognized at startup )
		
		for ( PluginDescriptor pluginDescriptor : PluginLoader.getPlugins() )
		{
			// output the name and the author in the console ( visible in the output tab of the inspector )
			System.out.println( "Name: " + pluginDescriptor.getName() );
			
			// pluginDescriptor contains a lot of infos about plugins, you can check for update, look for dependencies, get
			// the beta version, full changelog, icons, description , author email, url, web link (...).
			
		}
		
		
	}
	
}
