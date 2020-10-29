/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wb.data;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import static wb.data.Player.DEFAULT_NAME;

/**
 *
 * @author George
 */
public class Pitcher extends Player{
    public StringProperty ip;
    public StringProperty earnedRuns;
    public StringProperty wins;
    public StringProperty saves;
    public StringProperty hits;
    public StringProperty walks;
    public StringProperty strikeouts;
    
    public Pitcher() {
        super();
        ip = new SimpleStringProperty(DEFAULT_NAME);
        earnedRuns = new SimpleStringProperty(DEFAULT_NAME);
        wins = new SimpleStringProperty(DEFAULT_NAME);
        saves = new SimpleStringProperty(DEFAULT_NAME);
        hits = new SimpleStringProperty(DEFAULT_NAME);
        walks = new SimpleStringProperty(DEFAULT_NAME);
        strikeouts = new SimpleStringProperty(DEFAULT_NAME);
        super.position.set("P");
    }
    
    public Pitcher(String initIP, String initER, String initWins, String initSaves, String initHits, String initWalks, String initK, String lastName, String firstName, String mlbTeam, String yearOfBirth, String nationOfBirth){
        super(lastName, firstName, mlbTeam, yearOfBirth, nationOfBirth);
        ip = new SimpleStringProperty(initIP);
        earnedRuns = new SimpleStringProperty(initER);
        wins = new SimpleStringProperty(initWins);
        saves = new SimpleStringProperty(initSaves);
        hits = new SimpleStringProperty(initHits);
        walks = new SimpleStringProperty(initWalks);
        strikeouts = new SimpleStringProperty(initK);
    }
    
    public String getIP(){
        return ip.get();
    }
    
    public void setIP(String newIP){
        super.ipab.set(newIP);
        ip.set(newIP);
    }
    
    public String getEarnedRuns(){
        return earnedRuns.get();
    }
    
    public void setEarnedRuns(String newER){
        super.err.set(newER);
        earnedRuns.set(newER);
    }
    
    public String getWins(){
        return wins.get(); 
    }
    
    public void setWins(String newWins){
        super.wh.set(newWins);
        wins.set(newWins);
    }
    
    public String getSaves(){
        return saves.get();
    }
    
    public void setSaves(String newSaves){
        super.svhr.set(newSaves);
        saves.set(newSaves);
    }
    
    public String getHits(){
        return hits.get();
    }
    
    public void setHits(String newHits){
        super.hrbi.set(newHits);
        hits.set(newHits);
    }
    
    public String getWalks(){
        return walks.get();
    }
    
    public void setWalks(String newWalks){
        super.wh.set(newWalks);
        walks.set(newWalks);
    }
    
    public String getStrikeouts(){
        return strikeouts.get();
    }
    
    public void setStrikeouts(String newK){
        super.k.set(newK);
        strikeouts.set(newK);
    }
}
