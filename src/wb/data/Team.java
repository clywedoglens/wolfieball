/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wb.data;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    List<Player> taxiSquad;
    int draftNumber;
    int totalPoints;
    public Team(){
        pitchers = new ArrayList<Player>();
        //EACH INDEX WILL SERVE AS A SEPARATE LIST FOR EACH POSITION, LISTED IN THE SPECIFIED ORDER 
        hitters = new ArrayList<ArrayList<Player>>();
        for(int i = 0; i < 9; i++)
            hitters.add(new ArrayList<Player>());
        taxiSquad = new ArrayList<Player>();
        totalPoints = 0;
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
    
    public List<Player> getTaxiSquad(){
        return taxiSquad;
    }
    
    public void setTaxiSquad(ArrayList<Player> taxiSquad){
        this.taxiSquad = taxiSquad;
        
    }
    public void addToTaxiSquad(Player player){
        if(taxiSquad.size() < 9)
            taxiSquad.add(player);
    }
    public boolean addPitcher(Player pitcher){
        if(pitchers.size() < 9){
            pitcher.setTeamPosition("P");
            pitchers.add(pitcher);
            return true;
        }
        return false;
    }
    public List<Player> getPitchers(){
        return pitchers;
    }
    
    public void addHitter(Player hitter, int positionArrayIndex){
        hitters.get(positionArrayIndex).add(hitter);
    }
    public boolean addHitter(Player hitter, String position){
        switch(position.toUpperCase()) {
            case "C" :  if(hitters.get(0).size() < 2){
                            hitter.setTeamPosition("C");
                            hitters.get(0).add(hitter);
                        }
                        else{
                            return false;
                        }
                       break;
            case "1B" : if(hitters.get(1).size() < 1){
                            hitter.setTeamPosition("1B");
                            hitters.get(1).add(hitter);
                        }
                        else{
                            return false;
                        }
                        break;
            case "CI" : if(hitters.get(2).size() < 1){
                            hitter.setTeamPosition("CI");
                            hitters.get(2).add(hitter);
                        }
                        else {
                           return false;
                        }
                        break;
            case "3B" : if(hitters.get(3).size() < 1){
                            hitter.setTeamPosition("3B");
                            hitters.get(3).add(hitter);
                        }
                        else {
                            return false;
                        }
                        break;
            case "2B" : if(hitters.get(4).size() < 1){
                            hitter.setTeamPosition("2B");
                            hitters.get(4).add(hitter);
                        }
                        else {
                            return false;
                        }
                        break;
            case "MI" : if(hitters.get(5).size() < 1){
                            hitter.setTeamPosition("MI");
                            hitters.get(5).add(hitter);
                        }
                        else {
                            return false;
                        }
                        break;
            case "SS" : if(hitters.get(6).size() < 1){
                            hitter.setTeamPosition("SS");
                            hitters.get(6).add(hitter);
                        }
                        else {
                            return false;
                        }
                        break;
            case "OF" : if(hitters.get(7).size() < 5){
                            hitter.setTeamPosition("OF");
                            hitters.get(7).add(hitter);
                        }
                        else {
                            return false;
                        }
                        break;
            case "U"  : if(hitters.get(8).size() < 1){
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
        if(getPlayersNeeded() == 0)
            return 0;
        return getMoneyLeft()/getPlayersNeeded();
    } 
    
    public Integer getRuns(){
        int runs = 0;
        for(ArrayList<Player> a: hitters){
            for(Player p: a)
                runs += Integer.parseInt(p.getERR());
        }
        return runs;
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
        return Double.parseDouble(new DecimalFormat("##.##").format(average/numOfHitters));
        
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
    
    public void addTotalPoints(int totalPoints){
        this.totalPoints += totalPoints;
    }
    
    public Integer getTotalPoints(){
        return totalPoints;
    }
    
    public static enum Order implements Comparator {
        ByRuns(){
            public int compare(Team team1, Team team2){
                return team1.getRuns().compareTo(team2.getRuns());
            }

            @Override
            public int compare(Object o1, Object o2) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        },
        ByHR(){
            public int compare(Team team1, Team team2){
                return team1.getHR().compareTo(team2.getHR());
            }

            @Override
            public int compare(Object o1, Object o2) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        },
        ByRBI(){
            @Override
            public int compare(Team team1, Team team2){
                return team1.getRBI().compareTo(team2.getRBI());
            }

            @Override
            public int compare(Object o1, Object o2) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        },
        BySB(){
            public int compare(Team team1, Team team2){
                return team1.getSB().compareTo(team2.getSB());
            }

            @Override
            public int compare(Object o1, Object o2) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        },
        ByBA(){
            public int compare(Team team1, Team team2){
                return team1.getBA().compareTo(team2.getBA());
            }

            @Override
            public int compare(Object o1, Object o2) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        },
        ByW(){
            public int compare(Team team1, Team team2){
                return team1.getWins().compareTo(team2.getWins());
            }

            @Override
            public int compare(Object o1, Object o2) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        },
        BySV(){
            public int compare(Team team1, Team team2){
                return team1.getSaves().compareTo(team2.getSaves());
            }

            @Override
            public int compare(Object o1, Object o2) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        },
        ByK(){
            public int compare(Team team1, Team team2){
                return team1.getK().compareTo(team2.getK());
            }

            @Override
            public int compare(Object o1, Object o2) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        },
        ByERA(){
            public int compare(Team team1, Team team2){
                return team1.getERA().compareTo(team2.getERA());
            }

            @Override
            public int compare(Object o1, Object o2) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        },
        ByWHIP(){
            public int compare(Team team1, Team team2){
                return team1.getWHIP().compareTo(team2.getWHIP());
            }

            @Override
            public int compare(Object o1, Object o2) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        
        public abstract int compare(Team team1, Team team2);
        
        public Comparator ascending(){
            return this;
        }
        
        public Comparator descending(){
            return Collections.reverseOrder(this);
        }
    }
}
