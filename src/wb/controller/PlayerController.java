/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wb.controller;

import java.util.ArrayList;
import java.util.Iterator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
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
import wb.error.ErrorHandler;
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
    ErrorHandler eH;
    PlayerDialog pd;
    
    public PlayerController(PlayerDialog initPlayerDialog) {
        enabled = true;
        pd = initPlayerDialog;
        eH = ErrorHandler.getErrorHandler();
    }
    
    public void enable(boolean enableSetting) {
        enabled = enableSetting;
    }
    
    public void handleAddPlayerRequest(WB_GUI gui) {
        dataManager = gui.getDataManager();
        Draft draft = dataManager.getDraft();
        pd.showAddPlayerDialog();
        
        if(pd.wasCompleteSelected()) {
            ArrayList<CheckBox> positionList = pd.getCheckBoxes();
            String eligiblePositions = new String();
            for(CheckBox c: positionList){
                if(c.isSelected()){
                    if(c.getUserData().equals("P")){
                        eligiblePositions = "P_";
                        break;
                    }
                    eligiblePositions += c.getUserData() + "_";
                }
            }
            eligiblePositions = eligiblePositions.substring(0, eligiblePositions.length() - 1);
            Player p = pd.getPlayer();
            Player newPlayer = new Player();
            newPlayer.setMLBTeam(p.getMLBTeam());
            newPlayer.setFirstName(p.getFirstName());
            newPlayer.setPosition(eligiblePositions);              
            
        }
    }
    
    public void handleRemovePlayerRequest(WB_GUI gui) {
        //WILL DO LATER
    }
    
    public void handleEditPlayerRequest(WB_GUI gui, Player playerToEdit){
        dataManager = gui.getDataManager();
        Draft draft = dataManager.getDraft();
        //MAKE SURE FREE AGENCY IS NOT AN OPTION, SINCE THIS PLAYER IS ALREADY A FREE AGENT
        pd.removeFreeAgencyOption();
        pd.showEditPlayerDialog(playerToEdit);
        
        //DID USER CONFIRM
        if(pd.wasCompleteSelected()) {
            //UPDATE THE PLAYER
            Player p = pd.getPlayer();
            Team newTeam = pd.getSelectedTeam();
            //DEAL WITH THE CONTRACT
            if(pd.getSelectedContract().equals("X")){
                int numOfPlayers;
                numOfPlayers = newTeam.getPitchers().size();
                for(ArrayList<Player> a: newTeam.getHitters())
                    numOfPlayers += a.size();
                if(numOfPlayers == 23){
                    p.setContract("X");
                    newTeam.addToTaxiSquad(p);
                }
                else{
                    eH.handleTaxiSquadError();
                    return;
                }
            }
            else{
                p.setContract(pd.getSelectedContract());
                if(p.getPosition().equalsIgnoreCase("P"))
                    newTeam.addPitcher(p);
                else{
                    if(newTeam.addHitter(p, pd.getSelectedPosition()))
                        ;
                    else
                        eH.handlePositionFullError();
                }   
            }
            gui.getFileController().markAsEdited(gui);
            Iterator itr = draft.getAllPlayers().iterator();
            while(itr.hasNext()){
                Player player = (Player) itr.next();
                if(player.getFirstName().equals(p.getFirstName()) && player.getLastName().equals(p.getLastName()) && player.getMLBTeam().equals(p.getMLBTeam())){
                    itr.remove();
                    break;
               }
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
