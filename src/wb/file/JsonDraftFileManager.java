/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wb.file;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import static wb.WB_StartupConstants.PATH_DRAFTS;
import wb.data.Draft;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import javax.json.JsonValue;
import static wb.WB_StartupConstants.PATH_DATA;
import wb.data.Hitter;
import wb.data.Pitcher;
import wb.data.Player;
import wb.data.Team;

/**
 *
 * @author George
 */
public class JsonDraftFileManager implements DraftFileManager{
    //JSON FILE READING AND WRITING CONSTANTS
    String JSON_NAME = "NAME";
    String JSON_PICK = "PICK";
    String JSON_DRAFT = "DRAFT_ORDER";
    String JSON_TEAMS = "teams";
    String JSON_PLAYERS = "Players";
    String JSON_HITTERS = "Hitters";
    String JSON_PITCHERS = "Pitchers";
    String JSON_TEAM = "TEAM";
    String JSON_OWNER = "OWNER";
    String JSON_TAXI = "TAXI_SQUAD";
    String JSON_LNAME = "LAST_NAME";
    String JSON_FNAME = "FIRST_NAME";
    String JSON_CONTRACT = "CONTRACT";
    String JSON_SALARY = "SALARY";
    String JSON_IP = "IP";
    String JSON_ER = "ER";
    String JSON_W = "W";
    String JSON_SV = "SV";
    String JSON_H = "H";
    String JSON_BB = "BB";
    String JSON_K = "K";
    String JSON_NOTES = "NOTES";
    String JSON_YEAR = "YEAR_OF_BIRTH";
    String JSON_NATION = "NATION_OF_BIRTH";
    String JSON_QP = "QP";
    String JSON_AB = "AB";
    String JSON_R = "R";
    String JSON_HR = "HR";
    String JSON_RBI = "RBI";
    String JSON_SB = "SB";
    String JSON_EXT = ".json";
    String SLASH = "/";
  
   @Override
   public void saveDraft(Draft draftToSave) throws IOException {
       //BUILD FILE PATH
	   String draftName = draftToSave.getName();
	   if(draftName == null)
		   draftName = "default" + ((int) 100 * Math.random());
       String draftListing = "" + draftName;
       String jsonFilePath = PATH_DRAFTS + draftListing + JSON_EXT;
       
       //INITIALIZE WRITER
       OutputStream os = new FileOutputStream(jsonFilePath);
       JsonWriter jsonWriter = Json.createWriter(os);
      
       
       //MAKE A JSON ARRAY FOR THE TEAMS ARRAY
       List<Team> teams = draftToSave.getTeams().subList(0, draftToSave.getTeams().size());
       JsonArray fantasyTeamsJsonArray = makeFantasyTeamsJsonArray(teams);
       List<Player> draftOrder = draftToSave.getDraftOrder();
       JsonArray fantasyDraftJsonArray = makeDraftOrderJsonArray(draftOrder);
       
       
       //BUILD THE DRAFT
       JsonObject draftJsonObject = Json.createObjectBuilder()
                                   .add(JSON_NAME, draftName)
                                   .add(JSON_TEAMS, fantasyTeamsJsonArray)
                                   .add(JSON_DRAFT, fantasyDraftJsonArray)
               .build();
       //SAVE IT TO FILE
       jsonWriter.writeObject(draftJsonObject);
   }
   
   @Override
   public void loadDraft(Draft draftToLoad, String jsonFilePath) throws IOException {
       //LOAD JSON FILE WITH DATA
       JsonObject json = loadJSONFile(jsonFilePath);
       
       //LOAD COURSE INFO
       draftToLoad.setName(json.getString(JSON_NAME));
      
       JsonArray teams = json.getJsonArray(JSON_TEAMS);
       
       for(int i = 0; i < teams.size(); i++){
           Team team = new Team();
           draftToLoad.addTeam(loadTeam(team, teams.getJsonObject(i)));
       }
       
       loadAllPlayers(draftToLoad);
       
   }

   @Override
   public Team loadTeam(Team teamToLoad, JsonObject teamJsonObject) throws IOException {
       
       
       //LOAD THE TEAM
       teamToLoad.setName(teamJsonObject.getString(JSON_TEAM));
       teamToLoad.setOwnerName(teamJsonObject.getString(JSON_OWNER));
       
       //GET PLAYERS
       JsonArray jsonHittersArray = teamJsonObject.getJsonArray(JSON_HITTERS);
       for(int i = 0; i < jsonHittersArray.size(); i++){
           JsonArray positionArray = jsonHittersArray.getJsonArray(i);
           for(int j = 0; j < positionArray.size(); j++){
               Hitter newHitter = new Hitter();
               JsonObject h = positionArray.getJsonObject(j);
               teamToLoad.addHitter(loadDraftedHitter(newHitter, h), i);
           } 
       }
       
       JsonArray jsonPitchersArray = teamJsonObject.getJsonArray(JSON_PITCHERS);
       for(int i = 0; i < jsonPitchersArray.size(); i++){
           Pitcher newPitcher = new Pitcher();
           JsonObject p = jsonPitchersArray.getJsonObject(i);
           teamToLoad.addPitcher((Pitcher) loadDraftedPitcher(newPitcher, p));
       }
       
       JsonArray taxiSquadArray = teamJsonObject.getJsonArray(JSON_TAXI);
       for(int i = 0; i <taxiSquadArray.size(); i++){
           Player player = new Player();
           JsonObject p = taxiSquadArray.getJsonObject(i);
           teamToLoad.addToTaxiSquad(loadDraftedPitcher(player, p));
       }
       return teamToLoad;
       
   }
   
   @Override
   public void loadAllPlayers(Draft draft) throws IOException{
       String pitchersFilePath = PATH_DATA + SLASH + "Pitchers" + JSON_EXT;
       String hittersFilePath = PATH_DATA + SLASH + "Hitters" + JSON_EXT;
       
       JsonObject pitchers = loadJSONFile(pitchersFilePath);
       JsonObject hitters = loadJSONFile(hittersFilePath);
       
       JsonArray allPitchers = pitchers.getJsonArray(JSON_PITCHERS);
       for(int i = 0; i < allPitchers.size(); i++){
           Player pitcher = new Player();
           JsonObject p = allPitchers.getJsonObject(i);
           draft.addPlayer(loadPitcher(pitcher, p));
       }
       JsonArray allHitters = hitters.getJsonArray(JSON_HITTERS);
       for(int i = 0; i < allHitters.size(); i++){
           Player hitter = new Player();
           JsonObject h = allHitters.getJsonObject(i);
           draft.addPlayer(loadHitter(hitter, h));
       }
       
       
   }
   public Player loadDraftedHitter(Player playerToLoad, JsonObject hitterObject) throws IOException {
       
       playerToLoad.setMLBTeam(hitterObject.getString(JSON_TEAM));
       playerToLoad.setLastName(hitterObject.getString(JSON_LNAME));
       playerToLoad.setFirstName(hitterObject.getString(JSON_FNAME));
       playerToLoad.setPosition(hitterObject.getString(JSON_QP));
       playerToLoad.setIPAB(hitterObject.getString(JSON_AB));
       playerToLoad.setERR(hitterObject.getString(JSON_R));
       playerToLoad.setWH(hitterObject.getString(JSON_H));
       playerToLoad.setSVHR(hitterObject.getString(JSON_HR));
       playerToLoad.setHRBI(hitterObject.getString(JSON_RBI));
       playerToLoad.setBBSB(hitterObject.getString(JSON_SB));
       playerToLoad.setK("N/A");
       playerToLoad.setNotes(hitterObject.getString(JSON_NOTES));
       playerToLoad.setContract(hitterObject.getString(JSON_CONTRACT));
       playerToLoad.setSalary(hitterObject.getInt(JSON_SALARY));
       playerToLoad.setYearOfBirth(hitterObject.getString(JSON_YEAR));
       playerToLoad.setNationOfBirth(hitterObject.getString(JSON_NATION));
       
       return playerToLoad;
       
   }
   public Player loadDraftedPitcher(Player playerToLoad, JsonObject pitcherObject) throws IOException{
       
       playerToLoad.setMLBTeam(pitcherObject.getString(JSON_TEAM));
       playerToLoad.setLastName(pitcherObject.getString(JSON_LNAME));
       playerToLoad.setFirstName(pitcherObject.getString(JSON_FNAME));
       playerToLoad.setPosition("P");
       playerToLoad.setIPAB(pitcherObject.getString(JSON_IP));
       playerToLoad.setERR(pitcherObject.getString(JSON_ER));
       playerToLoad.setWH(pitcherObject.getString(JSON_W));
       playerToLoad.setSVHR(pitcherObject.getString(JSON_SV));
       playerToLoad.setHRBI(pitcherObject.getString(JSON_H));
       playerToLoad.setBBSB(pitcherObject.getString(JSON_BB));
       playerToLoad.setK(pitcherObject.getString(JSON_K));
       playerToLoad.setNotes(pitcherObject.getString(JSON_NOTES));
       playerToLoad.setContract(pitcherObject.getString(JSON_CONTRACT));
       playerToLoad.setSalary(pitcherObject.getInt(JSON_SALARY));
       playerToLoad.setYearOfBirth(pitcherObject.getString(JSON_YEAR));
       playerToLoad.setNationOfBirth(pitcherObject.getString(JSON_NATION));
       
       return playerToLoad;
   }
   public Player loadHitter(Player playerToLoad, JsonObject hitterObject) throws IOException {
       
       playerToLoad.setMLBTeam(hitterObject.getString(JSON_TEAM));
       playerToLoad.setLastName(hitterObject.getString(JSON_LNAME));
       playerToLoad.setFirstName(hitterObject.getString(JSON_FNAME));
       playerToLoad.setPosition(hitterObject.getString(JSON_QP));
       playerToLoad.setIPAB(hitterObject.getString(JSON_AB));
       playerToLoad.setERR(hitterObject.getString(JSON_R));
       playerToLoad.setWH(hitterObject.getString(JSON_H));
       playerToLoad.setSVHR(hitterObject.getString(JSON_HR));
       playerToLoad.setHRBI(hitterObject.getString(JSON_RBI));
       playerToLoad.setBBSB(hitterObject.getString(JSON_SB));
       playerToLoad.setK("N/A");
       playerToLoad.setNotes(hitterObject.getString(JSON_NOTES));
       playerToLoad.setYearOfBirth(hitterObject.getString(JSON_YEAR));
       playerToLoad.setNationOfBirth(hitterObject.getString(JSON_NATION));
       
       return playerToLoad;
       
   }
   
   public Player loadPitcher(Player playerToLoad, JsonObject pitcherObject) throws IOException{
       
       playerToLoad.setMLBTeam(pitcherObject.getString(JSON_TEAM));
       playerToLoad.setLastName(pitcherObject.getString(JSON_LNAME));
       playerToLoad.setFirstName(pitcherObject.getString(JSON_FNAME));
       playerToLoad.setPosition("P");
       playerToLoad.setIPAB(pitcherObject.getString(JSON_IP));
       playerToLoad.setERR(pitcherObject.getString(JSON_ER));
       playerToLoad.setWH(pitcherObject.getString(JSON_W));
       playerToLoad.setSVHR(pitcherObject.getString(JSON_SV));
       playerToLoad.setHRBI(pitcherObject.getString(JSON_H));
       playerToLoad.setBBSB(pitcherObject.getString(JSON_BB));
       playerToLoad.setK(pitcherObject.getString(JSON_K));
       playerToLoad.setNotes(pitcherObject.getString(JSON_NOTES));
       playerToLoad.setYearOfBirth(pitcherObject.getString(JSON_YEAR));
       playerToLoad.setNationOfBirth(pitcherObject.getString(JSON_NATION));
       
       return playerToLoad;
   }
   
   // LOADS A JSON FILE AS A SINGLE OBJECT AND RETURNS IT
    private JsonObject loadJSONFile(String jsonFilePath) throws IOException {
        InputStream is = new FileInputStream(jsonFilePath);
        JsonReader jsonReader = Json.createReader(is);
        JsonObject json = jsonReader.readObject();
        jsonReader.close();
        is.close();
        return json;
    } 
       
     // LOADS AN ARRAY OF A SPECIFIC NAME FROM A JSON FILE AND
    // RETURNS IT AS AN ArrayList FULL OF THE DATA FOUND
    private ArrayList<String> loadArrayFromJSONFile(String jsonFilePath, String arrayName) throws IOException {
        JsonObject json = loadJSONFile(jsonFilePath);
        ArrayList<String> items = new ArrayList();
        JsonArray jsonArray = json.getJsonArray(arrayName);
        for (JsonValue jsV : jsonArray) {
            items.add(jsV.toString());
        }
        return items;
    }
    
    // BUILDS AND RETURNS A JsonArray CONTAINING THE PROVIDED DATA
    public JsonArray buildJsonArray(List<Object> data) {
        JsonArrayBuilder jsb = Json.createArrayBuilder();
        for (Object d : data) {
           jsb.add(d.toString());
        }
        JsonArray jA = jsb.build();
        return jA;
    }
    
    private JsonObject makeDraftNameJsonObject(String draftName) {
        JsonObject jso = Json.createObjectBuilder().add(JSON_NAME, draftName)
                                                    .build();
        return jso;
    }

    private JsonArray makeDraftOrderJsonArray(List<Player> draftOrder) {
        JsonArrayBuilder jsb = Json.createArrayBuilder();
        for(Player p: draftOrder) {
            JsonObject draftJsonObject = Json.createObjectBuilder()
                                         .add(JSON_FNAME, p.getFirstName())
                                         .add(JSON_LNAME, p.getLastName())
                                         .add(JSON_PICK, p.getPickNumber())
                                         .add(JSON_CONTRACT, p.getContract())
                                         .add(JSON_SALARY, p.getSalary())
                    .build();
            jsb.add(draftJsonObject);
        }
        JsonArray jA = jsb.build();
        return jA;
    }
    private JsonArray makeFantasyTeamsJsonArray(List<Team> teams) {
        JsonArrayBuilder jsb = Json.createArrayBuilder();
        for(Team t: teams) {
            JsonArray pitchersJsonArray = makePitchersJsonArray(t.getPitchers());
            JsonArray hittersJsonArray = makeHittersJsonArray(t.getHitters());
            JsonArray taxiSquadJsonArray = makePlayersJsonArray(t.getTaxiSquad());
                       
            //NOW MAKE THE TEAM JSON OBJECT AND BUILD IT
            JsonObject teamJsonObject = Json.createObjectBuilder()
                                       .add(JSON_TEAM, t.getName())
                                       .add(JSON_OWNER, t.getOwnerName())
                                       .add(JSON_PITCHERS, pitchersJsonArray)
                                       .add(JSON_HITTERS, hittersJsonArray)
                                       .add(JSON_TAXI, taxiSquadJsonArray)
                    .build();
            jsb.add(teamJsonObject);
        }
        JsonArray jA = jsb.build();
        return jA;
    }

    private JsonArray makePlayersJsonArray(List<Player> players) {
        JsonArrayBuilder jsb = Json.createArrayBuilder();
        for(Player p: players){
            JsonObjectBuilder jso = Json.createObjectBuilder().add(JSON_TEAM, p.getMLBTeam())
                                                       .add(JSON_LNAME, p.getLastName())
                                                       .add(JSON_FNAME, p.getFirstName())
                                                       .add(JSON_IP, p.getIPAB())
                                                       .add(JSON_ER, p.getERR())
                                                       .add(JSON_W, p.getWH())
                                                       .add(JSON_SV, p.getSVHR())
                                                       .add(JSON_H, p.getHRBI())
                                                       .add(JSON_BB, p.getBBSB())
                                                       .add(JSON_K, p.getK())
                                                       .add(JSON_NOTES, p.getNotes())
                                                       .add(JSON_CONTRACT, p.getContract())
                                                       .add(JSON_SALARY, p.getSalary())
                                                       .add(JSON_YEAR, p.getYearOfBirth())
                                                       .add(JSON_NATION, p.getNationOfBirth());
            jsb.add(jso);
        }
        JsonArray jA = jsb.build();
        return jA;
    }
    
    private JsonArray makeHittersJsonArray(List<ArrayList<Player>> positions) {
        JsonArrayBuilder jsb = Json.createArrayBuilder();
        for(ArrayList<Player> a : positions) {
            JsonArrayBuilder jsa = Json.createArrayBuilder();
            for(Player h :a){
                JsonObjectBuilder jso = Json.createObjectBuilder().add(JSON_TEAM, h.getMLBTeam())
                                                       .add(JSON_LNAME, h.getLastName())
                                                       .add(JSON_FNAME, h.getFirstName())
                                                       .add(JSON_QP, h.getPosition())
                                                       .add(JSON_AB, h.getIPAB())
                                                       .add(JSON_R, h.getERR())
                                                       .add(JSON_H, h.getWH())
                                                       .add(JSON_HR, h.getSVHR())
                                                       .add(JSON_RBI, h.getHRBI())
                                                       .add(JSON_SB, h.getBBSB())
                                                       .add(JSON_NOTES, h.getNotes())
                                                       .add(JSON_CONTRACT, h.getContract())
                                                       .add(JSON_SALARY, h.getSalary())
                                                       .add(JSON_YEAR, h.getYearOfBirth())
                                                       .add(JSON_NATION, h.getNationOfBirth());
                                                       
            jsa.add(jso);                                    
            }
            jsb.add(jsa);
        }
        JsonArray jA = jsb.build();
        return jA;
    }

    private JsonArray makePitchersJsonArray(List<Player> pitchers) {
        JsonArrayBuilder jsb = Json.createArrayBuilder();
        for(Player p: pitchers){
            JsonObjectBuilder jso = Json.createObjectBuilder().add(JSON_TEAM, p.getMLBTeam())
                                                       .add(JSON_LNAME, p.getLastName())
                                                       .add(JSON_FNAME, p.getFirstName())
                                                       .add(JSON_IP, p.getIPAB())
                                                       .add(JSON_ER, p.getERR())
                                                       .add(JSON_W, p.getWH())
                                                       .add(JSON_SV, p.getSVHR())
                                                       .add(JSON_H, p.getHRBI())
                                                       .add(JSON_BB, p.getBBSB())
                                                       .add(JSON_K, p.getK())
                                                       .add(JSON_NOTES, p.getNotes())
                                                       .add(JSON_CONTRACT, p.getContract())
                                                       .add(JSON_SALARY, p.getSalary())
                                                       .add(JSON_YEAR, p.getYearOfBirth())
                                                       .add(JSON_NATION, p.getNationOfBirth());
            jsb.add(jso);
        }
        JsonArray jA = jsb.build();
        return jA;
    }
       
       
    
}
