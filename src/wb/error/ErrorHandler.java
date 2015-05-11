/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wb.error;

import javafx.stage.Stage;
import properties_manager.PropertiesManager;
import static wb.WB_PropertyType.POSITION_FULL_ERROR_MESSAGE;
import static wb.WB_PropertyType.STARTING_LINEUP_NOT_FULL_ERROR_MESSAGE;
import static wb.WB_StartupConstants.CLOSE_BUTTON_LABEL;
import static wb.WB_StartupConstants.PROPERTIES_FILE_ERROR_MESSAGE;
import wb.gui.MessageDialog;
import wb.data.Draft;

/**
 *
 * @author George
 */
public class ErrorHandler {
    
    static ErrorHandler singleton;
    
    MessageDialog messageDialog;
    
    PropertiesManager properties;
    
    private ErrorHandler() {
        
        singleton = null;
        
        properties = PropertiesManager.getPropertiesManager();
    }
    
    public void initMessageDialog(Stage owner) {
        
        messageDialog = new MessageDialog(owner, CLOSE_BUTTON_LABEL);
    }
    
    public static ErrorHandler getErrorHandler() {
        
        if(singleton == null)
            singleton = new ErrorHandler();
        
        return singleton;
    }
    
    public void handlePositionFullError() {
        messageDialog.show(properties.getProperty(POSITION_FULL_ERROR_MESSAGE));
    }
    public void handleTaxiSquadError(){
        messageDialog.show(properties.getProperty(STARTING_LINEUP_NOT_FULL_ERROR_MESSAGE));
    }
    public void handlePropertiesFileError() {
        messageDialog.show(properties.getProperty(PROPERTIES_FILE_ERROR_MESSAGE));
    }
}
