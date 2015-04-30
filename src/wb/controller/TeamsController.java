/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wb.controller;

import properties_manager.PropertiesManager;
import wb.WB_PropertyType;
import wb.data.Draft;
import wb.data.DraftDataManager;
import wb.data.Team;
import wb.error.ErrorHandler;
import wb.gui.FantasyTeamDialog;
import wb.gui.MessageDialog;
import wb.gui.WB_GUI;
import wb.gui.YesNoCancelDialog;

/**
 *
 * @author George
 */
public class TeamsController {
    private DraftDataManager dataManager;
    private boolean enabled;
    FantasyTeamDialog ftd;
    MessageDialog messageDialog;
    YesNoCancelDialog yesNoCancelDialog;
    
    public TeamsController(){
        enabled = true;
    }
    
    public void enable(boolean enableSetting){
        enabled = enableSetting;
    }
    
    public void handleAddTeamRequest(WB_GUI gui){
        dataManager = gui.getDataManager();
        Draft draft = dataManager.getDraft();
        ftd.showAddFantasyTeamDialog();
        
        if(ftd.wasCompleteSelected()) {
            Team t = ftd.getFantasyTeam();
            
            //ADD IT TO THE LIST OF TEAMS IN THE DRAFT
            draft.addTeam(t);
            
            gui.getFileController().markAsEdited(gui);
        }
        else {
            //USER PRESSED CANCEL
            //DO NOTHING
        }
        
    }
    
    public void handleRemoveTeamRequest(WB_GUI gui, Team teamToRemove){
        //PROMPT USER TO SAVE UNSAVED WORK
        yesNoCancelDialog.show(PropertiesManager.getPropertiesManager().getProperty(WB_PropertyType.REMOVE_ITEM_MESSAGE));
        
        String selection = yesNoCancelDialog.getSelection();
        
        //IF THE USER SAID YES, WE MUST REMOVE THE TEAM
        if(selection.equals(YesNoCancelDialog.YES)) {
            gui.getDataManager().getDraft().removeTeam(teamToRemove);
            
            gui.getFileController().markAsEdited(gui);
        }
    }
    
    public void handleEditTeamRequest(WB_GUI gui, Team teamToEdit){
        dataManager = gui.getDataManager();
        Draft draft = dataManager.getDraft();
        if(draft.getTeams().contains(teamToEdit)){
            int index = draft.getTeams().indexOf(teamToEdit);
            ftd.showEditFantasyTeamDialog(teamToEdit);
            
            if(ftd.wasCompleteSelected()){
                draft.getTeams().set(index, ftd.getFantasyTeam());
                
                gui.getFileController().markAsEdited(gui);
            }
        }
    }
    
    public void handleDraftChangeRequest(WB_GUI gui){
        if(enabled) {
            try {
                gui.updateDraftInfo(gui.getDataManager().getDraft());
                gui.getFileController().markAsEdited(gui);
            } catch (Exception e) {
                //SOMETHING WENT WRONG
                ErrorHandler eH = ErrorHandler.getErrorHandler();
            }
        }
        
    }
}
