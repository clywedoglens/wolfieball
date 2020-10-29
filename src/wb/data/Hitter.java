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
public class Hitter extends Player{
    
    public StringProperty position;
    public StringProperty ipab;
    public StringProperty err;
    public StringProperty wh;
    public StringProperty svhr;
    public StringProperty hrbi;
    public StringProperty bbsb;
    
    public Hitter(){
        super();
        position = new SimpleStringProperty(DEFAULT_NAME);
        ipab = new SimpleStringProperty(DEFAULT_NAME);
        err = new SimpleStringProperty(DEFAULT_NAME);
        wh = new SimpleStringProperty(DEFAULT_NAME);
        svhr = new SimpleStringProperty(DEFAULT_NAME);
        hrbi = new SimpleStringProperty(DEFAULT_NAME);
        bbsb = new SimpleStringProperty(DEFAULT_NAME);
        super.k.set("N/A");}
    
    public Hitter(String pos, String ab, String initRuns, String initHits, String initHR, String initRBI, String initSB, String lastName, String firstName, String mlbTeam, String yearOfBirth, String nationOfBirth){
        super(lastName, firstName, mlbTeam, yearOfBirth, nationOfBirth);
        position = new SimpleStringProperty(pos);
        ipab = new SimpleStringProperty(ab);
        err = new SimpleStringProperty(initRuns);
        wh = new SimpleStringProperty(initHits);
        svhr = new SimpleStringProperty(initHR);
        hrbi = new SimpleStringProperty(initRBI);
        bbsb = new SimpleStringProperty(initSB);
    }
    
    public String getPosition(){
        return position.get();
    }
    
    public void setPosition(String newPos){
        super.position.set(newPos);
        position.set(newPos);
    }
    
    public String getAtBats(){
        return ipab.get();
    }
    
    public void setAtBats(String newAB){
        super.ipab.set(newAB);
        ipab.set(newAB);
    }
    
    public String getRuns(){
        return err.get(); 
    }
    
    public void setRuns(String newRuns){
        super.err.set(newRuns);
        err.set(newRuns);
    }
    
    public String getHits(){
        return wh.get();
    }
    
    public void setHits(String newHits){
        super.wh.set(newHits);
        wh.set(newHits);
    }
    
    public String getHomeRuns(){
        return svhr.get();
    }
    
    public void setHomeRuns(String newHR){
        super.svhr.set(newHR);
        svhr.set(newHR);
    }
    
    public String getRBI(){
        return hrbi.get();
    }
    
    public void setRBI(String newRBI){
        super.hrbi.set(newRBI);
        hrbi.set(newRBI);
    }
    
    public String getStolenBases(){
        return bbsb.get();
    }
    
    public void setStolenBases(String newSB){
        super.bbsb.set(newSB);
        bbsb.set(newSB);
    }
}
