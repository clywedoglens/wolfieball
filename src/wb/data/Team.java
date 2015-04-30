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
    List<Pitcher> pitchers;
    List<Hitter> hitters;
    int draftNumber;
    
    public Team(){
        pitchers = new ArrayList<Pitcher>();
        hitters = new ArrayList<Hitter>();
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
    
    public void addPitcher(Pitcher pitcher){
        pitchers.add(pitcher);
    }
    public List<Pitcher> getPitchers(){
        return pitchers;
    }
    
    public void addHitter(Hitter hitter){
        hitters.add(hitter);
    }
    public List<Hitter> getHitters(){
        return hitters;
    }
    
    @Override
    public String toString(){
        return teamName;
    }
}
