/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wb.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;
import wb.WB_PropertyType;
import wb.data.Draft;
import wb.data.DraftDataManager;
import wb.data.Player;
import wb.data.Team;
import wb.error.ErrorHandler;
import wb.gui.FantasyTeamDialog;
import wb.gui.MessageDialog;
import wb.gui.PlayerDialog;
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
    PlayerDialog pd;
    ErrorHandler errorHandler;
    
    public TeamsController(Stage initStage, Draft draft, MessageDialog initMessageDialog)       {
        ftd = new FantasyTeamDialog(initStage, draft, initMessageDialog);
        pd = new PlayerDialog(initStage, draft);
        errorHandler = ErrorHandler.getErrorHandler();
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
            //WE MUST MOVE ALL THE PLAYERS TO THE FREE AGENCY TEAM
            //FIRST THE PITCHERS
            for(Player p: teamToRemove.getPitchers()){
                gui.getDataManager().getDraft().getAllPlayers().add(p);
            }
            //NOW THE HITTERS
            for(int i = 0; i < teamToRemove.getHitters().size(); i++){
                for(Player p: teamToRemove.getHitters().get(i)){
                    gui.getDataManager().getDraft().getAllPlayers().add(p);
                }
            }
            //NOW REMOVE THE TEAM FROM THE TEAMS LIST
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
    
        public void handleEditPlayerRequest(WB_GUI gui, Player playerToEdit){
        dataManager = gui.getDataManager();
        Draft draft = dataManager.getDraft();
        //MAKE FREE AGENCY IS AN OPTION
        pd.addFreeAgencyOption();
        pd.showEditPlayerDialog(playerToEdit);
        
        //DID USER CONFIRM
        if(pd.wasCompleteSelected()) {
            //UPDATE THE PLAYER
            Player p = pd.getPlayer();
            Team origTeam = p.getTeam();
            origTeam = draft.getTeams().get(draft.getTeams().indexOf(p.getTeam()));
            Team newTeam = pd.getSelectedTeam();
            if(newTeam.getName().equals("Free Agency")){
                draft.addPlayer(p);
                p.setTeam(null);
                if(p.getPosition().equalsIgnoreCase("P")){
                    Iterator itr = origTeam.getPitchers().iterator();
                    while(itr.hasNext()){
                        Player pitcher = (Player) itr.next();
                        if(pitcher.getFirstName().equals(p.getFirstName()) && pitcher.getLastName().equals(p.getLastName()) && pitcher.getMLBTeam().equals(p.getMLBTeam())){
                            itr.remove();
                            break;
                        }
                    }
            }
            else
                origTeam.removeHitter(p, pd.getSelectedPosition());
            }
            else{
            p.setTeam(newTeam);
            //NOW REMOVE THE PLAYER FROM ITS ORIGINAL TEAM
            if(p.getPosition().equalsIgnoreCase("P"))
                newTeam.addPitcher(p);
            else
                if(newTeam.addHitter(p, pd.getSelectedPosition()))
                    ;
                else
                    errorHandler.handlePositionFullError();
                    
            if(p.getPosition().equalsIgnoreCase("P")){
                Iterator itr = origTeam.getPitchers().iterator();
                    while(itr.hasNext()){
                        Player pitcher = (Player) itr.next();
                        if(pitcher.getFirstName().equals(p.getFirstName()) && pitcher.getLastName().equals(p.getFirstName()) && pitcher.getMLBTeam().equals(p.getMLBTeam())){
                            itr.remove();
                            break;
                        }
                    }
            }
            else
                origTeam.removeHitter(p, pd.getSelectedPosition());
              
            }
            gui.getFileController().markAsEdited(gui);
        }
    }
       
}
