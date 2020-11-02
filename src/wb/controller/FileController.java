/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wb.controller;

import java.io.File;
import java.io.IOException;
import javafx.stage.FileChooser;
import properties_manager.PropertiesManager;
import static wb.WB_PropertyType.DRAFT_SAVED_MESSAGE;
import static wb.WB_PropertyType.NEW_DRAFT_CREATED_MESSAGE;
import static wb.WB_PropertyType.SAVE_UNSAVED_WORK_MESSAGE;
import static wb.WB_PropertyType.DRAFT_LOADED_MESSAGE;
import static wb.WB_StartupConstants.PATH_DRAFTS;
import wb.data.Draft;
import wb.data.DraftDataManager;
import wb.error.ErrorHandler;
import wb.file.DraftFileManager;
import wb.file.DraftSiteExporter;
import wb.gui.MessageDialog;
import wb.gui.WB_GUI;
import wb.gui.YesNoCancelDialog;

/**
 *
 * @author George
 */
public class FileController {
    // WE WANT TO KEEP TRACK OF WHEN SOMETHING HAS NOT BEEN SAVED
    private boolean saved;

    // THIS GUY KNOWS HOW TO READ AND WRITE COURSE DATA
    private DraftFileManager draftIO;

    // THIS GUY KNOWS HOW TO EXPORT COURSE SCHEDULE PAGES
    private DraftSiteExporter exporter;

    // THIS WILL PROVIDE FEEDBACK TO THE USER WHEN SOMETHING GOES WRONG
    ErrorHandler errorHandler;
    
    // THIS WILL PROVIDE FEEDBACK TO THE USER AFTER
    // WORK BY THIS CLASS HAS COMPLETED
    MessageDialog messageDialog;
    
    // AND WE'LL USE THIS TO ASK YES/NO/CANCEL QUESTIONS
    YesNoCancelDialog yesNoCancelDialog;
    
    // WE'LL USE THIS TO GET OUR VERIFICATION FEEDBACK
    PropertiesManager properties;
    
     /**
     * This default constructor starts the program without a course file being
     * edited.
     *
     * @param primaryStage The primary window for this application, which we
     * need to set as the owner for our dialogs.
     * @param initFtsgyIO The object that will be reading and writing draft
     * data.
     * @param initExporter The object that will be exporting drafts to Web
     * sites.
     */
    public FileController(
            MessageDialog initMessageDialog,
            YesNoCancelDialog initYesNoCancelDialog,
            DraftFileManager initDraftIO,
            DraftSiteExporter initExporter) {
        // NOTHING YET
        saved = true;
        
        // KEEP THESE GUYS FOR LATER
        draftIO = initDraftIO;
        exporter = initExporter;
        
        // BE READY FOR ERRORS
        errorHandler = ErrorHandler.getErrorHandler();
        
        // AND GET READY TO PROVIDE FEEDBACK
        messageDialog = initMessageDialog;
        yesNoCancelDialog = initYesNoCancelDialog;
        properties = PropertiesManager.getPropertiesManager();
    }
    
    public void markAsEdited(WB_GUI gui) {
        // THE Course OBJECT IS NOW DIRTY
        saved = false;
        
        // LET THE UI KNOW
        gui.updateToolbarControls(saved);
    }
    
    public void handleNewDraftRequest(WB_GUI gui) { 
        try {
            boolean continueToMakeNew = true;
            if(!saved) {
                //THE USER CAN OPT OUT WITH CANCEL
                continueToMakeNew = promptToSave(gui);
            }
            
            //IF THE USERS DECIDES TO CONTINUE
            if (continueToMakeNew) {
                
                DraftDataManager dataManager = gui.getDataManager();
                dataManager.reset();
                saved = false;
                draftIO.loadAllPlayers(dataManager.getDraft());
                
                gui.updateToolbarControls(saved);
                
                messageDialog.show(properties.getProperty(NEW_DRAFT_CREATED_MESSAGE));
            }
        } catch (IOException ioe) {
            //WILL DO LATER
        }     
    }
    
    public void handleLoadDraftRequest(WB_GUI gui) {
            try{
                boolean continueToOpen = true;
                if(!saved) {
                    //THE USER CAN OPT OUT
                    continueToOpen = promptToSave(gui);
                }
                
                if(continueToOpen) {
                    
                    promptToOpen(gui);
                    
                    messageDialog.show(properties.getProperty(DRAFT_LOADED_MESSAGE));
                }
            } catch (IOException ioe) {
                
                //WILL DO LATER
            }
        }
    
    public void handleSaveDraftRequest(WB_GUI gui, Draft draftToSave) {
        try {
            //SAVE TO FILE
            draftIO.saveDraft(draftToSave);
            
            saved = true;
            
            messageDialog.show(properties.getProperty(DRAFT_SAVED_MESSAGE));
            
            //REFRESH THE GUI
            gui.updateToolbarControls(saved);    
        } catch (IOException ioe) {
            //WILL DO LATER
        }
    }
    
    public void handleExportDraftRequest(WB_GUI gui) {
        //WILL FILL OUT LATER
    }
    
    public void handleExitRequest(WB_GUI gui) {
        try {
            boolean continueToExit = true;
            if (!saved) {
                continueToExit = promptToSave(gui);
            }
            
            if(continueToExit) {
                System.exit(0);
            }
        } catch (IOException ioe) {
            
        }
    }
    
    private boolean promptToSave(WB_GUI gui) throws IOException {
        // PROMPT THE USER TO SAVE UNSAVED WORK
        yesNoCancelDialog.show(properties.getProperty(SAVE_UNSAVED_WORK_MESSAGE));
        
        // AND NOW GET THE USER'S SELECTION
        String selection = yesNoCancelDialog.getSelection();

        // IF THE USER SAID YES, THEN SAVE BEFORE MOVING ON
        if (selection.equals(YesNoCancelDialog.YES)) {
            //SAVE DRAFT
            DraftDataManager dataManager = gui.getDataManager();
            draftIO.saveDraft(dataManager.getDraft());
            saved = true;
        }
        
        else if(selection.equals(YesNoCancelDialog.CANCEL)) {
            return false;
        }
        
        return true;
    }
    
    private void promptToOpen(WB_GUI gui) {
        //ASK USER WHICH DRAFT TO OPEN
        FileChooser draftFileChooser = new FileChooser();
        draftFileChooser.setInitialDirectory(new File(PATH_DRAFTS));
        File selectedFile = draftFileChooser.showOpenDialog(gui.getWindow());
        
        //ONLY OPEN A NEW FILE IF THE USER SAYS OK
        if (selectedFile != null) {
            try {
                Draft draftToLoad = gui.getDataManager().getDraft();
                draftIO.loadDraft(draftToLoad, selectedFile.getAbsolutePath());
                saved = true;
                gui.updateToolbarControls(saved);
                gui.reloadDraft(draftToLoad);
                
            } catch (Exception e) {
                //WILL DO LATER
            }
        }
    }
    
    public void markFileAsNotSaved() {
        saved = false;
    }
    
    public boolean isSaved() {
        return saved;
    }
}
