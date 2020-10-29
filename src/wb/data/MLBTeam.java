/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wb.data;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author George
 */
public class MLBTeam {
    private String name;
    ObservableList<Player> players;
    
    public MLBTeam(String name){
        this.name = name;
        players = FXCollections.observableArrayList();
    }
    
    public String getName(){
        return name;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public void addPlayer(Player player){
        players.add(player);
    }
    public ObservableList<Player> getPlayers(){
        return players;
    }
    
}
