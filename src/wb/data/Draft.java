/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wb.data;

import java.util.ArrayList;
import java.util.HashMap;
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
    ObservableList<Team> teams;
    ObservableList<Player> allPlayers;
    Team freeAgency;
    ObservableList<MLBTeam> mlbTeams;
    HashMap<String, MLBTeam> mlbMap;
    public Draft(){
        teams = FXCollections.observableArrayList();
        mlbTeams = FXCollections.observableArrayList();
        freeAgency = new Team();
        freeAgency.setName("Free Agency");
        mlbMap = new HashMap<>();
        loadMLBTeams();
        allPlayers = FXCollections.observableArrayList();
    }
    
    public void addPlayer(Player p){
       
        if(p.getMLBTeam().equalsIgnoreCase("TOR")){
            //DONT ADD IT TO THE LIST
        }
        else{
            allPlayers.add(p);
            MLBTeam mlb = mlbMap.get(p.getMLBTeam());
            if(mlb == null)
                p.setFirstName(p.getFirstName());
            mlb.addPlayer(p);
        }
        
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
    
    public ObservableList<Team> getTeams(){
        return teams;
    }
    
    public ObservableList<MLBTeam> getMLBTeams(){
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
    
    public Team getFreeAgency(){
        return freeAgency;
    }
    private void loadMLBTeams(){
        ArrayList<String> teamNames= new ArrayList<String>();
        teamNames.add("ATL");
        teamNames.add("AZ");
        teamNames.add("CHC");
        teamNames.add("CIN");
        teamNames.add("COL");
        teamNames.add("LAD");
        teamNames.add("MIA");
        teamNames.add("MIL");
        teamNames.add("NYM");
        teamNames.add("PHI");
        teamNames.add("PIT");
        teamNames.add("SD");
        teamNames.add("SF");
        teamNames.add("STL");
        teamNames.add("WSH");
        for(String s: teamNames){
            MLBTeam mlb = new MLBTeam(s);
            mlbTeams.add(mlb);
            mlbMap.put(s, mlb);
        }
    }
}
