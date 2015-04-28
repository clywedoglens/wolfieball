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
import wb.data.Player;
import wb.gui.WB_GUI;

/**
 *
 * @author George
 */
public class PlayerController {
    
    private DraftDataManager dataManager;
    private boolean enabled;
    ObservableList<Player> tableItems;
    
    public PlayerController() {
        enabled = true;
    }
    
    public void enable(boolean enableSetting) {
        enabled = enableSetting;
    }
    
    public void handleAddPlayerRequest(WB_GUI gui) {
        //WILL DO LATER
    }
    
    public void handleRemovePlayerRequest(WB_GUI gui) {
        //WILL DO LATER
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
        else{
            for (Player p : allPlayers) {
                if(p.getPosition().contains(position))
                    tableItems.add(p);
            }
        }    
        table.setItems(tableItems);
    }
    
}
