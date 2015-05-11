/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wb.data;

import java.text.DecimalFormat;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
    public StringProperty eligiblePosition;
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
    public StringProperty contract;
    public IntegerProperty salary;
    public IntegerProperty pick;
    public static final String DEFAULT_NAME = "DEFAULT NAME";
    public Team team;
    
    public Player() {
        lastName = new SimpleStringProperty("");
        firstName = new SimpleStringProperty("");
        mlbTeam = new SimpleStringProperty("");
        eligiblePosition = new SimpleStringProperty("");
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
        contract = new SimpleStringProperty("");
        salary = new SimpleIntegerProperty(0);
        position = new SimpleStringProperty("");
        pick = new SimpleIntegerProperty(0);
        team = null;
    }
    
    public Player(String lName, String fName, String Team, String year, String nation) {
        lastName = new SimpleStringProperty(lName);
        firstName = new SimpleStringProperty(fName);
        mlbTeam = new SimpleStringProperty(Team);
        eligiblePosition = new SimpleStringProperty(Team);
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
        eligiblePosition.set(initPos);
    }
    
    public String getPosition(){
        return eligiblePosition.get();
    }
    
    public String getTeamPosition(){
        return position.get();
    }
    
    public void setTeamPosition(String initPos){
        position.set(initPos);
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
        return lastName.get() + firstName.get() + ".jpg";
    }
    
    public void setTeam(Team team){
        this.team = team;
    }
    
    public Team getTeam(){
        return team;
    }
    public String getTeamName(){
        return team.getName();
    }
    public void setContract(String initContract){
        contract.set(initContract);
    }
    
    public String getContract(){
        return contract.get();
    }
    
    public void setSalary(int initSalary){
        salary.set(initSalary);
    }
    
    public Integer getSalary(){
        return salary.get();
    }
    
    public void setPickNumber(int initPick){
        this.pick.set(initPick);
    }
    
    public Integer getPickNumber(){
        return pick.get();
    }
    
    public Integer getRW(){
        if(position.get().equals("P"))
            return Integer.parseInt(wh.get());
        else
            return Integer.parseInt(err.get());
    }
    
    public Integer getHRSV(){
        return Integer.parseInt(svhr.get());
    }
    
    public Integer getRBIK(){
        if(position.get().equals("P"))
            return Integer.parseInt(k.get());
        else
            return Integer.parseInt(hrbi.get());
    }
    
    public Double getSBERA(){
        if(position.get().equals("P"))
           return getERA();
        else
            return Double.parseDouble(new DecimalFormat("##.##").format(Double.parseDouble(bbsb.get())));
    }
    
    public Double getBAWHIP(){
        if(position.get().equals("P"))
            return getWHIP();
        else
            return getBA();
    }
    public Double getBA(){
        if(ipab.get().equals("0"))
            return 0.0;
        return Double.parseDouble(new DecimalFormat("##.##").format(Double.parseDouble(wh.get())/Double.parseDouble(ipab.get())));
    }
    
    public Double getERA(){
        if(ipab.get().equals("0"))
            return 0.0;
        return Double.parseDouble(new DecimalFormat("##.##").format((Double.parseDouble(err.get()) * 9)/Double.parseDouble(ipab.get())));
    }
    
    public Double getWHIP(){
        if(ipab.get().equals("0"))
            return 0.0;
        return Double.parseDouble(new DecimalFormat("##.##").format((Double.parseDouble(bbsb.get()) + Double.parseDouble(hrbi.get()))/Double.parseDouble(ipab.get())));
    }
}
