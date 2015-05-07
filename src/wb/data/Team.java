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
    
    public void addHitter(Player hitter, int positionArrayIndex){
        hitters.get(positionArrayIndex).add(hitter);
    }
    public boolean addHitter(Player hitter, String position){
        switch(position.toUpperCase()) {
            case "C" :  if(hitters.get(0).size() < 3){
                            hitters.get(0).add(hitter);
                        }
                        else{
                            return false;
                        }
                       break;
            case "1B" : if(hitters.get(1).size() < 2){
                            hitters.get(1).add(hitter);
                        }
                        else{
                            return false;
                        }
                        break;
            case "CI" : if(hitters.get(2).size() < 2){
                            hitters.get(2).add(hitter);
                        }
                        else {
                           return false;
                        }
                        break;
            case "3B" : if(hitters.get(3).size() < 2){
                            hitters.get(3).add(hitter);
                        }
                        else {
                            return false;
                        }
                        break;
            case "2B" : if(hitters.get(4).size() < 2){
                            hitters.get(4).add(hitter);
                        }
                        else {
                            return false;
                        }
                        break;
            case "MI" : if(hitters.get(5).size() < 2){
                            hitters.get(5).add(hitter);
                        }
                        else {
                            return false;
                        }
                        break;
            case "SS" : if(hitters.get(6).size() < 2){
                            hitters.get(6).add(hitter);
                        }
                        else {
                            return false;
                        }
                        break;
            case "OF" : if(hitters.get(7).size() < 6){
                            hitters.get(7).add(hitter);
                        }
                        else {
                            return false;
                        }
                        break;
            case "U"  : if(hitters.get(8).size() < 10){
                            hitters.get(8).add(hitter);
                        }
                        else {
                            return false;
                        }
                        break;
                
        }
        return true;
    }
    
    public void removeHitter(Player p, String position){
        switch(position.toUpperCase()){
            case "C" : if(hitters.get(0).contains(p))
                            hitters.get(0).remove(p);
                        break;
            case "1B" : if(hitters.get(1).contains(p))
                            hitters.get(1).remove(p);
                        break;
            case "CI" : if(hitters.get(2).contains(p))
                            hitters.get(2).remove(p);
                        break;
            case "3B" : if(hitters.get(3).contains(p))
                            hitters.get(3).remove(p);
                        break;
            case "2B" : if(hitters.get(4).contains(p))
                            hitters.get(4).remove(p);
                        break;
            case "MI" : if(hitters.get(5).contains(p))
                            hitters.get(5).remove(p);
                        break;
            case "SS" : if(hitters.get(6).contains(p))
                            hitters.get(6).remove(p);
                        break;
            case "OF" : if(hitters.get(7).contains(p))
                            hitters.get(7).remove(p);
                        break;
            case "U"  : if(hitters.get(8).contains(p))
                            hitters.get(8).remove(p);
                
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
