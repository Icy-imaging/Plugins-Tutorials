package plugins.tutorial.basics;

import icy.gui.frame.progress.AnnounceFrame;
import icy.plugin.abstract_.Plugin;
import icy.plugin.interface_.PluginImageAnalysis;
import icy.plugin.interface_.PluginStartAsThread;

/**
 * 
 * This tutorial just create a bug to watch the bug report window in action
 * To make it harder to catch by ICY, we add the PluginStartAsThread interface, which starts the plugin as a thread.
 * 
 * @author Fabrice de Chaumont and Stephane Dallongeville
 *
 */
public class GenerateABugTutorial extends Plugin implements PluginImageAnalysis , PluginStartAsThread {


	@Override
	public void compute()
	{
		
		new AnnounceFrame("This plugin will crash in 3 seconds to show the automatic bug report feature.");
		new AnnounceFrame("To make it difficult to catch, the error is within a thread.");
		
		try
		{
			Thread.sleep(3000);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		// generate an exception
		throw new NullPointerException();
	}

	
}
