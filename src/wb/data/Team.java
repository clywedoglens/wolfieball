/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wb.data;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
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
        for(int i = 0; i < 9; i++)
            hitters.add(new ArrayList<Player>());
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
        if(pitchers.size() < 10){
            pitcher.setTeamPosition("P");
            pitchers.add(pitcher);
        }
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
                            hitter.setTeamPosition("C");
                            hitters.get(0).add(hitter);
                        }
                        else{
                            return false;
                        }
                       break;
            case "1B" : if(hitters.get(1).size() < 2){
                            hitter.setTeamPosition("1B");
                            hitters.get(1).add(hitter);
                        }
                        else{
                            return false;
                        }
                        break;
            case "CI" : if(hitters.get(2).size() < 2){
                            hitter.setTeamPosition("CI");
                            hitters.get(2).add(hitter);
                        }
                        else {
                           return false;
                        }
                        break;
            case "3B" : if(hitters.get(3).size() < 2){
                            hitter.setTeamPosition("3B");
                            hitters.get(3).add(hitter);
                        }
                        else {
                            return false;
                        }
                        break;
            case "2B" : if(hitters.get(4).size() < 2){
                            hitter.setTeamPosition("2B");
                            hitters.get(4).add(hitter);
                        }
                        else {
                            return false;
                        }
                        break;
            case "MI" : if(hitters.get(5).size() < 2){
                            hitter.setTeamPosition("MI");
                            hitters.get(5).add(hitter);
                        }
                        else {
                            return false;
                        }
                        break;
            case "SS" : if(hitters.get(6).size() < 2){
                            hitter.setTeamPosition("SS");
                            hitters.get(6).add(hitter);
                        }
                        else {
                            return false;
                        }
                        break;
            case "OF" : if(hitters.get(7).size() < 6){
                            hitter.setTeamPosition("OF");
                            hitters.get(7).add(hitter);
                        }
                        else {
                            return false;
                        }
                        break;
            case "U"  : if(hitters.get(8).size() < 10){
                            hitter.setTeamPosition("U");
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
        Iterator itr;
        switch(position.toUpperCase()){
            case "C" : itr = hitters.get(0).iterator();
                       while(itr.hasNext()){
                           Player hitter = (Player) itr.next();
                           if(hitter.getFirstName().equals(p.getFirstName()) && hitter.getLastName().equals(p.getLastName()) && hitter.getMLBTeam().equals(p.getMLBTeam())){
                               itr.remove();
                               break;
                           }
                       }
                        break;
            case "1B" : itr = hitters.get(1).iterator();
                       while(itr.hasNext()){
                           Player hitter = (Player) itr.next();
                           if(hitter.getFirstName().equals(p.getFirstName()) && hitter.getLastName().equals(p.getLastName()) && hitter.getMLBTeam().equals(p.getMLBTeam())){
                               itr.remove();
                               break;
                           }
                       }
                        break;
            case "CI" : itr = hitters.get(2).iterator();
                       while(itr.hasNext()){
                           Player hitter = (Player) itr.next();
                           if(hitter.getFirstName().equals(p.getFirstName()) && hitter.getLastName().equals(p.getLastName()) && hitter.getMLBTeam().equals(p.getMLBTeam())){
                               itr.remove();
                               break;
                           }
                       }
                        break;
            case "3B" : itr = hitters.get(3).iterator();
                       while(itr.hasNext()){
                           Player hitter = (Player) itr.next();
                           if(hitter.getFirstName().equals(p.getFirstName()) && hitter.getLastName().equals(p.getLastName()) && hitter.getMLBTeam().equals(p.getMLBTeam())){
                               itr.remove();
                               break;
                           }
                       }
                        break;
            case "2B" : itr = hitters.get(4).iterator();
                       while(itr.hasNext()){
                           Player hitter = (Player) itr.next();
                           if(hitter.getFirstName().equals(p.getFirstName()) && hitter.getLastName().equals(p.getLastName()) && hitter.getMLBTeam().equals(p.getMLBTeam())){
                               itr.remove();
                               break;
                           }
                       }
                        break;
            case "MI" : itr = hitters.get(5).iterator();
                       while(itr.hasNext()){
                           Player hitter = (Player) itr.next();
                           if(hitter.getFirstName().equals(p.getFirstName()) && hitter.getLastName().equals(p.getLastName()) && hitter.getMLBTeam().equals(p.getMLBTeam())){
                               itr.remove();
                               break;
                           }
                       }
                        break;
            case "SS" : itr = hitters.get(6).iterator();
                       while(itr.hasNext()){
                           Player hitter = (Player) itr.next();
                           if(hitter.getFirstName().equals(p.getFirstName()) && hitter.getLastName().equals(p.getLastName()) && hitter.getMLBTeam().equals(p.getMLBTeam())){
                               itr.remove();
                               break;
                           }
                       }
                        break;
            case "OF" : itr = hitters.get(7).iterator();
                       while(itr.hasNext()){
                           Player hitter = (Player) itr.next();
                           if(hitter.getFirstName().equals(p.getFirstName()) && hitter.getLastName().equals(p.getLastName()) && hitter.getMLBTeam().equals(p.getMLBTeam())){
                               itr.remove();
                               break;
                           }
                       }
                        break;
            case "U"  : itr = hitters.get(8).iterator();
                       while(itr.hasNext()){
                           Player hitter = (Player) itr.next();
                           if(hitter.getFirstName().equals(p.getFirstName()) && hitter.getLastName().equals(p.getLastName()) && hitter.getMLBTeam().equals(p.getMLBTeam())){
                               itr.remove();
                               break;
                           }
                       }
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
    
    //ALL THE FOLLOWING METHODS WILL BE NEEDED FOR THE TABLE ON THE STANDINGS SCREEN
    
    public Integer getPlayersNeeded(){
        
        int numOfPitchers = pitchers.size();
        int numOfHitters = 0;
        for(ArrayList<Player> a: hitters){
                numOfHitters += a.size();
            }
        
        return 23 - (numOfPitchers + numOfHitters);
    }
    
    public Integer getMoneyLeft(){
        
        int moneySpent = 0;
        for(Player p: pitchers)
            moneySpent += p.getSalary();
        for(ArrayList<Player> a: hitters){
            for(Player p: a)
                moneySpent += p.getSalary();
        }
        
        return 260 - moneySpent;
    }
    
    public Integer getMoneyPP(){
        
        return getMoneyLeft()/getPlayersNeeded();
    } 
    public Integer getHR(){
        int hr = 0;
        
        for(ArrayList<Player> a: hitters){
            for(Player p: a)
                hr += Integer.parseInt(p.getSVHR());
        }
        return hr;
    }
    
    public Integer getRBI(){
        int rbi = 0;
        
        for(ArrayList<Player> a: hitters){
            for(Player p: a)
                rbi += Integer.parseInt(p.getHRBI());
        }
        return rbi;
    }
    
    public Integer getSB(){
        int sb = 0;
        
        for(ArrayList<Player> a: hitters){
            for(Player p: a)
                sb += Integer.parseInt(p.getBBSB());
        }
        return sb;
    }
    public Double getBA(){
        
        int numOfHitters = 0;
        double average = 0;
        
        for(ArrayList<Player> a: hitters){
            for(Player p: a){
                numOfHitters++;
                average += p.getBA();
            }
                
        }
        return average/numOfHitters;
        
    }
    public Integer getWins(){
        
        int wins = 0;
        for(Player p: pitchers)
            wins += Integer.parseInt(p.getWH());
        
        return wins;
    }
    
    public Integer getSaves(){
        
        int saves = 0;
        for(Player p: pitchers)
            saves += Integer.parseInt(p.getSVHR());
        
        return saves;
    }
    
    public Integer getK(){
        
        int k = 0;
        for(Player p: pitchers)
            k += Integer.parseInt(p.getK());
        
        return k;
    }
    
    public Double getERA(){
        
        int numOfPitchers = pitchers.size();
        double era = 0;
        if(numOfPitchers == 0)
            return 0.0;
        
        for(Player p: pitchers)
            era += p.getERA();
        
        return Double.parseDouble(new DecimalFormat("##.##").format(era/numOfPitchers));
    }
    
    public Double getWHIP(){
        int numOfPitchers = pitchers.size();
        double whip = 0;
        if(numOfPitchers == 0)
            return 0.0;
        for(Player p: pitchers)
            whip += p.getWHIP();
        
        return Double.parseDouble(new DecimalFormat("##.##").format(whip/numOfPitchers));
    }
}
