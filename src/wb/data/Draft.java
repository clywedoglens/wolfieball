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
    String name;
    int number;
    static int counter;
    ArrayList<Team> teams;
    ObservableList<Player> allPlayers;
    ArrayList<MLBTeam> mlbTeams;
    public Draft(){
        teams = new ArrayList();
        mlbTeams = new ArrayList();
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
    
    public void setName(String name){
        this.name = name;
    }
    
    public String getName(){
        return name;
    }
    public void setNumber(int number) {
        this.number = number;
    }
    public int getNumber() {
        return number;
    }
    
    public ArrayList<Team> getTeams(){
        return teams;
    }
    
    public ArrayList<MLBTeam> getMLBTeams(){
        return mlbTeams;
    }
    public void addTeam(Team team){
        teams.add(team);
    }
    
    public void removeTeam(Team team){
        teams.remove(team);
    }
    public void clearTeams(){
        teams.clear();
    }
    public static int getCounter(){
        return counter;
    }
    
}
