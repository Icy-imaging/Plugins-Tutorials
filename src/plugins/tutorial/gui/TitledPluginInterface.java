package plugins.tutorial.gui;

import icy.gui.frame.ActionFrame;
import icy.plugin.abstract_.PluginActionable;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This tutorial shows how to create a very simple frame dedicated to do a specific action.<br>
 * You have a main panel to display basic GUI and a OK / Cancel button couple.
 * 
 * @author Fabrice de Chaumont & Stephane Dallongeville
 */
public class TitledPluginInterface extends PluginActionable
{
    @Override
    public void run()
    {
        // create a simple Action frame !
        ActionFrame frame = new ActionFrame("Tutorial", true, true);

        // get main panel to add component if desired...
        JPanel mainPanel = frame.getMainPanel();

        mainPanel.add(new JLabel("Very basic ActionFrame example"));

        // specify the action for OK button click
        frame.setOkAction(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("Action done !");
            }
        });

        frame.addToMainDesktopPane();
        frame.setVisible(true);
        frame.center();
    }
}
