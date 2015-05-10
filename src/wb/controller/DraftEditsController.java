/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wb.controller;

import java.util.Iterator;
import java.util.Random;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableView;
import wb.data.Draft;
import wb.data.DraftDataManager;
import wb.data.Player;
import wb.data.Team;
import wb.gui.WB_GUI;

/**
 *
 * @author George
 */
public class DraftEditsController {
    
    private DraftDataManager dataManager;
    private boolean enabled;
    private Service<ObservableList<Player>> autoDraftThread;
    private int nextIndex;                  //THIS IS THE INDEX FOR THE NEXT TEAM TO PICK IN THE DRAFT
    WB_GUI gui;
    public DraftEditsController(){
        enabled = true;
        nextIndex = 0;
    }
    
    public void handleAddPlayerRequest(WB_GUI gui){
        dataManager = gui.getDataManager();
        Draft draft = dataManager.getDraft();
        
        //GET THE TEAM DRAFTING
        Team draftingTeam = draft.getTeams().get(nextIndex);
        //GET THE LIST OF AVAILABLE PLAYERS TO DRAFT
        ObservableList<Player> availablePlayers = draft.getAllPlayers();
        
        if(!availablePlayers.isEmpty()){
        Player draftedPlayer = availablePlayers.get(new Random().nextInt(availablePlayers.size()));
        draftedPlayer.setSalary(1);
        if(draftedPlayer.getPosition().equals("P")){
            draftedPlayer.setTeamPosition("P");
            draftingTeam.addPitcher(draftedPlayer);
            }
        else{
            String[] eligiblePositions = draftedPlayer.getPosition().split("_");
            if(eligiblePositions.length > 1)
                eligiblePositions.toString();
            draftedPlayer.setTeamPosition(eligiblePositions[new Random().nextInt(eligiblePositions.length)]);
            
            //NOW ADD THE PLAYER TO THE TEAM
            draftedPlayer.setTeam(draftingTeam);
            draftingTeam.addHitter(draftedPlayer, draftedPlayer.getTeamPosition());
        }
            draft.getDraftOrder().add(draftedPlayer);
            draftedPlayer.setPickNumber(draft.getDraftOrder().size());
        //WE MUST NOW REMOVE THE PLAYER FROM THE FREE AGENTS TABLE
        Iterator itr = draft.getAllPlayers().iterator();
           while(itr.hasNext()){
               Player player = (Player) itr.next();
               if(player.getFirstName().equals(draftedPlayer.getFirstName()) && player.getLastName().equals(draftedPlayer.getLastName()) && player.getMLBTeam().equals(draftedPlayer.getMLBTeam())){
                   itr.remove();
                   break;
               }
           }
        //NOW INCREMENT NEXTINDEX, BUT ONCE IT GOES PAST THE AMOUNT OF TEAMS, IT MUST RESET TO THE FIRST TEAM
        nextIndex++;
        if(nextIndex > draft.getTeams().size() - 1)
            nextIndex = 0;
        gui.getFileController().markAsEdited(gui);
        }
    }
    
    public void handleAutoDraftRequest(WB_GUI gui, TableView table){
        this.gui = gui;
        dataManager = gui.getDataManager();
        Draft draft = dataManager.getDraft();
        
        //THE DRAFT IS RUNNING, UPDATE CERTAIN BUTTONS
        gui.enableDraftMode();
        
        autoDraftThread = new Service<ObservableList<Player>>() {
            
            @Override
            protected Task<ObservableList<Player>> createTask(){
                return new PartialResultsTask();
            }
        };
        
        autoDraftThread.setOnSucceeded(new EventHandler<WorkerStateEvent>(){
            
            @Override
            public void handle(WorkerStateEvent event) {
                table.itemsProperty().unbind();
            }
        });
        table.itemsProperty().bind(autoDraftThread.valueProperty());
        autoDraftThread.restart();
        
    }
    
    public void handlePauseAutoDraftRequest(WB_GUI gui){
        autoDraftThread.cancel();
        gui.disableDraftMode();
    }
    private Player getDraftedPlayer(WB_GUI gui, Draft draft){
        
        ObservableList<Player> autoDraft = draft.getDraftOrder();
        Team draftingTeam = draft.getTeams().get(nextIndex);
        //GET THE LIST OF AVAILABLE PLAYERS TO DRAFT
        ObservableList<Player> availablePlayers = draft.getAllPlayers();
        
        Player draftedPlayer = availablePlayers.get(new Random().nextInt(availablePlayers.size()));
        draftedPlayer.setSalary(1);
        if(draftedPlayer.getPosition().equals("P")){
            draftedPlayer.setTeamPosition("P");
            draftingTeam.addPitcher(draftedPlayer);
            }
        else{
            String[] eligiblePositions = draftedPlayer.getPosition().split("_");
            draftedPlayer.setTeamPosition(eligiblePositions[new Random().nextInt(eligiblePositions.length)]);
            
            //NOW ADD THE PLAYER TO THE TEAM
            draftedPlayer.setTeam(draftingTeam);
            draftingTeam.addHitter(draftedPlayer, draftedPlayer.getTeamPosition());
        }
            
        //WE MUST NOW REMOVE THE PLAYER FROM THE FREE AGENTS TABLE
        Iterator itr = draft.getAllPlayers().iterator();
           while(itr.hasNext()){
               Player player = (Player) itr.next();
               if(player.getFirstName().equals(draftedPlayer.getFirstName()) && player.getLastName().equals(draftedPlayer.getLastName()) && player.getMLBTeam().equals(draftedPlayer.getMLBTeam())){
                   itr.remove();
                   break;
               }
           }
        //NOW INCREMENT NEXTINDEX, BUT ONCE IT GOES PAST THE AMOUNT OF TEAMS, IT MUST RESET TO THE FIRST TEAM
        nextIndex++;
        if(nextIndex > draft.getTeams().size() - 1)
            nextIndex = 0;
        gui.getFileController().markAsEdited(gui);
        
        return draftedPlayer;
        
    }
    
    class PartialResultsTask extends Task<ObservableList<Player>>{
        
        private final ReadOnlyObjectWrapper partialResults = new ReadOnlyObjectWrapper<>(this, "partialResults",
                dataManager.getDraft().getDraftOrder());
        
        public final ObservableList getPartialResults() {return (ObservableList) partialResults.get();}
        public final ReadOnlyObjectProperty partialResultsProperty() {
            return partialResults.getReadOnlyProperty();
        }
        
        @Override
        protected ObservableList call() throws Exception {
            int playersSize = dataManager.getDraft().getAllPlayers().size();
            while(!dataManager.getDraft().getAllPlayers().isEmpty()){
                if (isCancelled()) break;
                
                Player draftedPlayer = getDraftedPlayer(gui, dataManager.getDraft());
                Platform.runLater(new Runnable() {
                    @Override public void run(){
                        getPartialResults().add(draftedPlayer);
                        draftedPlayer.setPickNumber(getPartialResults().size());
                    }
                });
                updateValue(dataManager.getDraft().getDraftOrder());
                try{
                    Thread.sleep(1000);
                }
                catch(InterruptedException e){
                    break;
                }
                
            }
            return getPartialResults();
        }
    }
}
