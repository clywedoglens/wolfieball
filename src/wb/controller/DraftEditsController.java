/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wb.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
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
    private int totalDraftPicks;
    private int plusTaxiPicks;
    
    public DraftEditsController(){
        
        enabled = true;
        nextIndex = 0;
    }
    
    public void handleAddPlayerRequest(WB_GUI gui){
        
        dataManager = gui.getDataManager();
        Draft draft = dataManager.getDraft();
        totalDraftPicks = draft.getTeams().size() * 23;
        plusTaxiPicks = totalDraftPicks + (draft.getTeams().size() * 8);
        //GET THE LIST OF AVAILABLE PLAYERS TO DRAFT
        ObservableList<Player> availablePlayers = draft.getAllPlayers();
        //GET THE TEAM DRAFTING
        Team draftingTeam = draft.getTeams().get(nextIndex);
        if(draft.getDraftOrder().size() < totalDraftPicks){
            boolean wasNotAdded = true;
            while(wasNotAdded){
                wasNotAdded = addPlayer(draftingTeam, availablePlayers);
            }
        }
        else if(draft.getDraftOrder().size() < plusTaxiPicks){
            Player draftedPlayer = availablePlayers.get(new Random().nextInt(availablePlayers.size()));
            draftedPlayer.setSalary(1);
            draftedPlayer.setContract("X");
            draftedPlayer.setTeam(draftingTeam);
            if(draftedPlayer.getPosition().equals("P")){
                draftedPlayer.setTeamPosition("P");
            }
            else{
                String[] eligiblePositions = draftedPlayer.getPosition().split("_");
                String teamPosition = eligiblePositions[new Random().nextInt(eligiblePositions.length)];
                draftedPlayer.setTeamPosition(teamPosition);
            }
            draftingTeam.addToTaxiSquad(draftedPlayer);
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
        }
        //NOW INCREMENT NEXTINDEX, BUT ONCE IT GOES PAST THE AMOUNT OF TEAMS, IT MUST RESET TO THE FIRST TEAM
        nextIndex++;
        if(nextIndex > draft.getTeams().size() - 1)
            nextIndex = 0;
        gui.getFileController().markAsEdited(gui);
        gui.updateDraftInfo(draft);
        gui.updatePlayersTable(draft);
        dataManager.getDraft().updateTotalPoints();
    }
    
    public void handleAutoDraftRequest(WB_GUI gui, TableView table){
        this.gui = gui;
        dataManager = gui.getDataManager();
        Draft draft = dataManager.getDraft();
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
    
    private boolean addPlayer(Team draftingTeam, ObservableList<Player> availablePlayers){
    
        Player draftedPlayer = availablePlayers.get(new Random().nextInt(availablePlayers.size()));
        if(draftedPlayer.getPosition().equals("P")){
            if(draftingTeam.addPitcher(draftedPlayer)){
                draftedPlayer.setTeam(draftingTeam);
                draftedPlayer.setContract("S2");
                draftedPlayer.setSalary(1);
                dataManager.getDraft().getDraftOrder().add(draftedPlayer);
                draftedPlayer.setPickNumber(dataManager.getDraft().getDraftOrder().size());
                //WE MUST NOW REMOVE THE PLAYER FROM THE FREE AGENTS TABLE
                Iterator itr = dataManager.getDraft().getAllPlayers().iterator();
                while(itr.hasNext()){
                    Player player = (Player) itr.next();
                    if(player.getFirstName().equals(draftedPlayer.getFirstName()) && player.getLastName().equals(draftedPlayer.getLastName()) && player.getMLBTeam().equals(draftedPlayer.getMLBTeam())){
                        itr.remove();
                        break;
                    }
                }
                return false;
            }
            return true;
        }
        else{
            String[] eligiblePositions = draftedPlayer.getPosition().split("_");
                List<String> playerPositions = new ArrayList<>(Arrays.asList(eligiblePositions));
                if(playerPositions.contains("1B") && playerPositions.contains("3B"))
                    playerPositions.add("CI");
                else if(playerPositions.contains("2B") && playerPositions.contains("SS"))
                    playerPositions.add("MI");   
                if(playerPositions.size() > 1)
                    playerPositions.add("U");
                String teamPosition = playerPositions.get(new Random().nextInt(playerPositions.size()));
                if(draftingTeam.addHitter(draftedPlayer, teamPosition)){
                    draftedPlayer.setTeam(draftingTeam);
                    draftedPlayer.setContract("S2");
                    draftedPlayer.setSalary(1);
                    dataManager.getDraft().getDraftOrder().add(draftedPlayer);
                    draftedPlayer.setPickNumber(dataManager.getDraft().getDraftOrder().size());
                    Iterator itr = dataManager.getDraft().getAllPlayers().iterator();
                    while(itr.hasNext()){
                        Player player = (Player) itr.next();
                        if(player.getFirstName().equals(draftedPlayer.getFirstName()) && player.getLastName().equals(draftedPlayer.getLastName()) && player.getMLBTeam().equals(draftedPlayer.getMLBTeam())){
                            itr.remove();
                            break;
                        }
                    }
                    return false;
                }
                return true;
        }
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
            while(dataManager.getDraft().getDraftOrder().size() <= plusTaxiPicks){
                if (isCancelled()) break;
                Platform.runLater(new Runnable() {
                    @Override public void run(){
                        handleAddPlayerRequest(gui);
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
