/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wb.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import wb.data.Draft;
import wb.data.DraftDataManager;
import wb.data.Hitter;
import wb.data.Pitcher;
import wb.data.Player;
import wb.data.Team;
import wb.gui.PlayerDialog;
import wb.gui.WB_GUI;

/**
 *
 * @author George
 */
public class PlayerController {
    
    private DraftDataManager dataManager;
    private boolean enabled;
    ObservableList<Player> tableItems;
    PlayerDialog pd;
    
    public PlayerController(PlayerDialog initPlayerDialog) {
        enabled = true;
        pd = initPlayerDialog;
    }
    
    public void enable(boolean enableSetting) {
        enabled = enableSetting;
    }
    
    public void handleAddPlayerRequest(WB_GUI gui) {
        dataManager = gui.getDataManager();
        Draft draft = dataManager.getDraft();
        pd.showAddPlayerDialog();
        
        if(pd.wasCompleteSelected()) {
            Player p = pd.getPlayer();
            
            if(p.getPosition().equals("P")){
                Pitcher pitcher = new Pitcher();
                Team teamToAdd = p.getTeam();
                pitcher.setTeam(p.getTeam());
            }
        }
    }
    
    public void handleRemovePlayerRequest(WB_GUI gui) {
        //WILL DO LATER
    }
    
    public void handleEditPlayerRequest(WB_GUI gui, Player playerToEdit){
        dataManager = gui.getDataManager();
        Draft draft = dataManager.getDraft();
        pd.showEditLectureDialog(playerToEdit);
        
        //DID USER CONFIRM
        if(pd.wasCompleteSelected()) {
            //UPDATE THE PLAYER
            Player p = pd.getPlayer();
            //FIND THE ORIGINAL TEAM THE PLAYER WAS IN 
            Team origTeam = new Team();
            if(p.getTeam() != null)
                origTeam = draft.getTeams().get(draft.getTeams().indexOf(p));
            Team newTeam = pd.getSelectedTeam();
            p.setTeam(newTeam);
            //NOW REMOVE THE PLAYER FROM ITS ORIGINAL TEAM
            if(p.getPosition().equalsIgnoreCase("P"))
                newTeam.getPitchers().add(p);
            else
                newTeam.getHitters().add(p);
            if(origTeam != null){
                if(p.getPosition().equalsIgnoreCase("P")){
                    origTeam.getPitchers().remove(p);
                }
                else{
                    origTeam.getHitters().remove(p);
                }
                
                gui.getFileController().markAsEdited(gui);
            }
        }
       
        
    }
    public void handlePlayerSearchRequest(WB_GUI gui, TableView table, String player){
        dataManager = gui.getDataManager();
        Draft draft = dataManager.getDraft();
        ObservableList<Player> data = table.getItems();
        tableItems = FXCollections.observableArrayList();
        int dataSize = data.size();
        //IF THE SEARCH BAR IS EMPTY, RETURN THE ORIGINAL TABLE WITH ALL PLAYERS
        if(player.isEmpty()){
            table.setItems(draft.getAllPlayers());
            return;
        }
        //GET ALL THE COLUMNS FROM THE TABLE
        ObservableList<TableColumn<Player, String>> cols = table.getColumns();
        for(int i = 0; i < dataSize; i++){
            for(int j = 0; j < 2; j++){
                TableColumn col = cols.get(j);
                String cellValue = col.getCellData(data.get(i)).toString();
                
                cellValue = cellValue.toLowerCase();
                if(cellValue.startsWith(player.toLowerCase())){
                    tableItems.add(data.get(i));
                    break;
                }
            }
        }
        table.setItems(tableItems);
        
        
    }
    
    public void handlePositionSelectRequest(WB_GUI gui, TableView table, Toggle toggle){
        dataManager = gui.getDataManager();
        Draft draft = dataManager.getDraft();
        tableItems = FXCollections.observableArrayList();
        ObservableList<Player> data = table.getItems();
        ObservableList<Player> allPlayers = draft.getAllPlayers();
        String position = toggle.getUserData().toString();
        
        
        if(position.equalsIgnoreCase("ALL")){
            table.setItems(allPlayers);
            return;
        }
        else if(position.equalsIgnoreCase("U")){
            for (Player p : allPlayers) {
                if(!p.getPosition().equalsIgnoreCase("P"))
                    tableItems.add(p);
            }
        }
        else if(position.equalsIgnoreCase("P")){
            for (Player p : allPlayers) {
                if(p.getPosition().equalsIgnoreCase("P"))
                    tableItems.add(p);
            }
        }
        else if(position.equalsIgnoreCase("CI")){
            for(Player p : allPlayers) {
                if((p.getPosition().contains("1B") || p.getPosition().contains("3B")) && !p.getPosition().contains("2B") && !p.getPosition().contains("SS") && !p.getPosition().contains("C") && !p.getPosition().contains("OF")){
                    tableItems.add(p);
                }
            }
        }
        else if(position.equalsIgnoreCase("MI")){
            for(Player p : allPlayers) {
                if((p.getPosition().contains("2B") || p.getPosition().contains("SS")) && !p.getPosition().contains("1B") && !p.getPosition().contains("3B") &&  !p.getPosition().contains("C") && !p.getPosition().contains("OF")){
                    tableItems.add(p);
                }
            }
        }
        else{
            for (Player p : allPlayers) {
                if(p.getPosition().contains(position))
                    tableItems.add(p);
            }
        }    
        table.setItems(tableItems);
    }
    
}
