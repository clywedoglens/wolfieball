/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wb;

import java.util.Locale;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import wb.error.ErrorHandler;
import wb.gui.WB_GUI;

/**
 *
 * @author George
 */
public class WolfieBall extends Application {
        //THIS IS THE FULL USER INTERFACE, WHICH 
        //WILL BE INITIALIZED AFTER THE PROPERTIES FILE IS LOAD
        WB_GUI gui;
    @Override
    public void start(Stage primaryStage) {
        //GIVE THE PRIMARY STAGE TO OUR ERROR HANDLER
        ErrorHandler eH = ErrorHander.getErrorHandler();
        eH.initMessageDialog(primaryStage);
        
        //LOAD APP SETTINGS
        boolean success = loadProperties();
        if(success) {
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            String appTitle = props.getProperty(PROP_APP_TITLE);
            try {
                               
                
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        launch(args);
    }
    
}
