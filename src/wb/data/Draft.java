/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wb.data;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author George
 */
public class Draft {

    int number;
    static int counter;
    List<String> teams;
    ObservableList<Player> allPlayers;
    public Draft(){
        teams = new ArrayList();
        allPlayers = FXCollections.observableArrayList();
    }
    
    public void addPlayer(Player p){
        allPlayers.add(p);
        
    }
    public ObservableList<Player> getAllPlayers(){
        return allPlayers;
    }
    
    public void setAllPlayers(ObservableList<Player> allPlayers){
        this.allPlayers = allPlayers;   
    }
    public void setNumber(int number) {
        this.number = number;
    }
    public int getNumber() {
        return number;
    }
    
    public List<String> getTeams(){
        return teams;
    }
    
    public void addTeam(String teamName){
        teams.add(teamName);
    }
    
    public void clearTeams(){
        teams.clear();
    }
    public static int getCounter(){
        return counter;
    }
    
}
