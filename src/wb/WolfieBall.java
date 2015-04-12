/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wb;

import java.io.IOException;
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
import wb.file.JsonPlayerFileManager;
import wb.gui.WB_GUI;
import properties_manager.PropertiesManager;
import static wb.WB_StartupConstants.PATH_BASE;
import static wb.WB_StartupConstants.PATH_DATA;
import static wb.WB_StartupConstants.PATH_SITES;
import static wb.WB_StartupConstants.PROPERTIES_FILE_NAME;
import static wb.WB_StartupConstants.PROPERTIES_SCHEMA_FILE_NAME;
import wb.file.DraftSiteExporter;
import xml_utilities.InvalidXMLFileFormatException;

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
        ErrorHandler eH = ErrorHandler.getErrorHandler();
        eH.initMessageDialog(primaryStage);
        
        //LOAD APP SETTINGS
        boolean success = loadProperties();
        if(success) {
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            String appTitle = props.getProperty(PROP_APP_TITLE);
            try {
                 
                //WE WILL READ AND SAVE OUR PLAYER DATA USING THE JSON FILE
                //FORMATER SO THIS OBJECT WILL HANDLE THAT
                JsonPlayerFileManager jsonFileManager = new JsonPlayerFileManager();
                
                //THIS EXPORTS THE DRAFT RESULTS INTO A WEB PAGE
                DraftSiteExporter exporter = new DraftSiteExporter(PATH_BASE, PATH_SITES);
                
                //THIS WILL LOAD THE DATA FROM JSON FILES
                
                //INTILIAZE GUI STUFF
                gui = new WB_GUI(primaryStage);
                gui.getPlayerFileManager(jsonFileManager);
                gui.setSiteExporter(exporter);
                
                //CONSTRUCT THE DATA MANAGER AND GIVE IT TO THE GUI
                PlayerDataManager dataManager = new PlayerDataManager(gui);
                gui.setDataManager(dataManager);
                
                //START THE WINDOW THAT THE USER SEES
                gui.initGUI(appTitle,);
            }
            catch(IOException ioe) {
                eH = ErrorHandler.getErrorHandler();
                eH.handlePropertiesFileError();
            }
               
            }
        }
    
    public boolean loadProperties() {
        try {
            //LOAD SETTINGS FOR STARTING THE APP
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            props.addProperty(PropertiesManager.DATA_PATH_PROPERTY, PATH_DATA);
            props.loadProperties(PROPERTIES_FILE_NAME, PROPERTIES_SCHEMA_FILE_NAME);
            return true;
        } catch(InvalidXMLFileFormatException ixmlffe) {
            //SOMETHING WENT WRONG INITIALIZING THE XML FILE
            ErrorHandler eH = ErrorHandler.getErrorHandler();
            eH.handlePropertiesFileError();
            return false;
        }
    }
    /**
     * This is where the program execution begins.
     */
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        launch(args);
    }
    
}
