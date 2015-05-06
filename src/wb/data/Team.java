/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wb.data;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author George
 */
public class Team {
    String teamName;
    String ownerName;
    List<Player> pitchers;
    List<ArrayList<Player>> hitters;
    int draftNumber;
    
    public Team(){
        pitchers = new ArrayList<Player>();
        //EACH INDEX WILL SERVE AS A SEPARATE LIST FOR EACH POSITION, LISTED IN THE SPECIFIED ORDER 
        hitters = new ArrayList<ArrayList<Player>>();
    }

    public Team(String teamName, String ownerName) {
        this.teamName = teamName;
        this.ownerName = ownerName;
    }
    
    public String getName(){
        return teamName;
    }
    
    public void setName(String teamName){
        this.teamName = teamName;
    }
    
    public String getOwnerName(){
        return ownerName;
    }
    
    public void setOwnerName(String ownerName){
        this.ownerName = ownerName;
    }
    public int getDraftNumber(){
        return draftNumber;
    }
    
    public void setDraftNumber(int draftNumber){
        this.draftNumber = draftNumber;
    }
    
    public void addPitcher(Player pitcher){
        pitchers.add(pitcher);
    }
    public List<Player> getPitchers(){
        return pitchers;
    }
    
    public void addHitter(Player hitter, String position){
        switch(position.toUpperCase()) {
            case "C" : hitters.get(0).add(hitter);
                       break;
            case "1B" : hitters.get(1).add(hitter);
                        break;
            case "3B" : hitters.get(2).add(hitter);
                        break;
            case "2B" : hitters.get(3).add(hitter);
                        break;
            case "SS" : hitters.get(4).add(hitter);
                        break;
            case "OF" : hitters.get(5).add(hitter);
                        break;
                
        }
    }
    public List<ArrayList<Player>> getHitters(){
        return hitters;
    }
    
    @Override
    public String toString(){
        return teamName;
    }
}
