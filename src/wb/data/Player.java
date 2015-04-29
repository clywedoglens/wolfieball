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

/**
 *
 * @author George
 */
 public class Player {
    
    public StringProperty lastName;
    public StringProperty firstName;
    public StringProperty mlbTeam;
    public StringProperty position;
    public StringProperty ipab;
    public StringProperty err;
    public StringProperty wh;
    public StringProperty svhr;
    public StringProperty hrbi;
    public StringProperty bbsb;
    public StringProperty k;
    public StringProperty notes;
    public StringProperty yearOfBirth;
    public StringProperty nationOfBirth;
    public static final String DEFAULT_NAME = "DEFAULT NAME";
    public String imageFilePath;
    public String fantasyTeam;
    
    
    public Player() {
        lastName = new SimpleStringProperty("");
        firstName = new SimpleStringProperty("");
        mlbTeam = new SimpleStringProperty("");
        position = new SimpleStringProperty("");
        ipab = new SimpleStringProperty(DEFAULT_NAME);
        err = new SimpleStringProperty(DEFAULT_NAME);
        wh = new SimpleStringProperty(DEFAULT_NAME);
        svhr = new SimpleStringProperty(DEFAULT_NAME);
        hrbi = new SimpleStringProperty(DEFAULT_NAME);
        bbsb = new SimpleStringProperty(DEFAULT_NAME);
        k = new SimpleStringProperty();
        notes = new SimpleStringProperty("");
        yearOfBirth = new SimpleStringProperty(DEFAULT_NAME);
        nationOfBirth = new SimpleStringProperty("");
        imageFilePath = firstName.get() + lastName.get() + ".jpg";
    }
    
    public Player(String lName, String fName, String Team, String year, String nation) {
        lastName = new SimpleStringProperty(lName);
        firstName = new SimpleStringProperty(fName);
        mlbTeam = new SimpleStringProperty(Team);
        position = new SimpleStringProperty(Team);
        ipab = new SimpleStringProperty(DEFAULT_NAME);
        err = new SimpleStringProperty(DEFAULT_NAME);
        wh = new SimpleStringProperty(DEFAULT_NAME);
        svhr = new SimpleStringProperty(DEFAULT_NAME);
        hrbi = new SimpleStringProperty(DEFAULT_NAME);
        bbsb = new SimpleStringProperty(DEFAULT_NAME);
        k = new SimpleStringProperty();
        notes = new SimpleStringProperty("");
        yearOfBirth = new SimpleStringProperty(year);
        nationOfBirth = new SimpleStringProperty(nation);
    }
    
    public String getLastName(){
        return lastName.get();
    }
    
    public void setLastName(String initName){
        lastName.set(initName);
    }
    
    public String getFirstName(){
        return firstName.get();
    }
    
    public void setFirstName(String initName){
        firstName.set(initName);
    }
    
    public String getMLBTeam(){
        return mlbTeam.get();
    }
    
    public void setMLBTeam(String initTeam){
        mlbTeam.set(initTeam);
    }
    
    public void setPosition(String initPos){
        position.set(initPos);
    }
    
    public String getPosition(){
        return position.get();
    }
    
    public String getIPAB(){
        return ipab.get();
    }
    
    public void setIPAB(String newValue){
        ipab.set(newValue);
    }
    
    public String getERR(){
        return err.get();
    }
    
    public void setERR(String newValue){
        err.set(newValue);
    }
    
    public String getWH(){
        return wh.get();
    }
    
    public void setWH(String newValue){
        wh.set(newValue);
    }
    
    public String getSVHR(){
        return svhr.get();
    }
    
    public void setSVHR(String newValue){
        svhr.set(newValue);
    }
    
    public String getHRBI(){
        return hrbi.get();
    }
    
    public void setHRBI(String newValue){
        hrbi.set(newValue);
    }
    
    public String getBBSB(){
        return bbsb.get();
    }
    
    public void setBBSB(String newValue){
        bbsb.set(newValue);
    }
    
    public String getK(){
        return k.get();
    }
    
    public void setK(String newValue) {
        k.set(newValue);
    }
    public String getNotes(){
        return notes.get();
    }
    public void setNotes(String initNotes){
        notes.set(initNotes);
    }
    
    public String getYearOfBirth(){
        return yearOfBirth.get();
    }
    
    public void setYearOfBirth(String initYear){
        yearOfBirth.set(initYear);
    }
    
    public String getNationOfBirth(){
        return nationOfBirth.get();
    }
    
    public void setNationOfBirth(String initNation){
       nationOfBirth.set(initNation);
    }
    
    public String getImageFilePath(){
        return imageFilePath;
    }
    
    public void setFantasyTeam(String fantasyTeam){
        this.fantasyTeam = fantasyTeam;
    }
    
    public String getFantasyTeam(){
        return fantasyTeam;
    }
}
