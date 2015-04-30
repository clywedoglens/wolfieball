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
    String JSON_TEAMS = "teams";
    String JSON_PLAYERS = "Players";
    String JSON_HITTERS = "Hitters";
    String JSON_PITCHERS = "Pitchers";
    String JSON_TEAM = "TEAM";
    String JSON_OWNER = "OWNER";
    String JSON_LNAME = "LAST_NAME";
    String JSON_FNAME = "FIRST_NAME";
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
       String draftListing = "" + "" + draftToSave.getName();
       String jsonFilePath = PATH_DRAFTS + SLASH + draftListing + JSON_EXT;
       
       //INITIALIZE WRITER
       OutputStream os = new FileOutputStream(jsonFilePath);
       JsonWriter jsonWriter = Json.createWriter(os);
       
       //MAKE AN OBJECT FOR THE DRAFT NUMBER
      
       
       //MAKE A JSON ARRAY FOR THE TEAMS ARRAY
       List<Team> teams = draftToSave.getTeams();
       JsonArray fantasyTeamsJsonArray = makeFantasyTeamsJsonArray(teams);
   
       
       //BUILD THE DRAFT
       JsonObject draftJsonObject = Json.createObjectBuilder()
                                   .add(JSON_NAME, draftToSave.getName())
                                   .add(JSON_TEAMS, fantasyTeamsJsonArray)
               .build();
   
   }
   
   @Override
   public void loadDraft(Draft draftToLoad, String jsonFilePath) throws IOException {
       //LOAD JSON FILE WITH DATA
       JsonObject json = loadJSONFile(jsonFilePath);
       
       //LOAD COURSE INFO
       draftToLoad.setName(json.getString(JSON_NAME));
       
       JsonArray teamNames = json.getJsonArray(JSON_TEAMS);
       
       for(int i = 0; i < teamNames.size(); i++){
           Team team = new Team();
           draftToLoad.addTeam(loadTeam(team, teamNames.getString(i)));
       }
       
       loadAllPlayers(draftToLoad);
       
   }

   @Override
   public Team loadTeam(Team teamToLoad, String teamName) throws IOException {
       //LOAD JSON OBJECT
       String teamFilePath = PATH_DRAFTS + teamName + JSON_EXT;
       JsonObject json = loadJSONFile(teamFilePath);
       
       //LOAD THE TEAM
       
       teamToLoad.setDraftNumber(json.getInt(JSON_NUMBER));
       teamToLoad.setName(json.getString(JSON_TEAM));
       teamToLoad.setOwnerName(json.getString(JSON_OWNER));
       
       //GET PLAYERS
       JsonArray jsonHittersArray = json.getJsonArray(JSON_HITTERS);
       for(int i = 0; i < jsonHittersArray.size(); i++){
           Hitter newHitter = new Hitter();
           JsonObject h = jsonHittersArray.getJsonObject(i);
           teamToLoad.addHitter((Hitter) loadHitter(newHitter, h));
       }
       
       JsonArray jsonPitchersArray = json.getJsonArray(JSON_PITCHERS);
       for(int i = 0; i < jsonPitchersArray.size(); i++){
           Pitcher newPitcher = new Pitcher();
           JsonObject p = jsonPitchersArray.getJsonObject(i);
           teamToLoad.addPitcher((Pitcher) loadPitcher(newPitcher, p));
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

    private JsonArray makeFantasyTeamsJsonArray(List<Team> teams) {
        JsonArrayBuilder jsb = Json.createArrayBuilder();
        for(Team t: teams) {
            JsonArray pitchersJsonArray = makePitchersJsonArray(t.getPitchers());
            JsonArray hittersJsonArray = makeHittersJsonArray(t.getHitters());
                       
            //NOW MAKE THE TEAM JSON OBJECT AND BUILD IT
            JsonObject teamJsonObject = Json.createObjectBuilder()
                                       .add(JSON_TEAM, t.getName())
                                       .add(JSON_OWNER, t.getOwnerName())
                                       .add(JSON_PITCHERS, pitchersJsonArray)
                                       .add(JSON_HITTERS, hittersJsonArray)
                    .build();
            jsb.add(teamJsonObject);
        }
        JsonArray jA = jsb.build();
        return jA;
    }

    private JsonObject makeTeamNameJsonObject(String teamName) {
        JsonObject jso = Json.createObjectBuilder().add(JSON_TEAM, teamName)
                                                    .build();
        return jso;
    }

    private JsonObject makeOwnerNameJsonObject(String ownerName) {
        JsonObject jso = Json.createObjectBuilder().add(JSON_OWNER, ownerName)
                                                    .build();
        return jso;
    }
    private JsonArray makeHittersJsonArray(List<Hitter> hitters) {
        JsonArrayBuilder jsb = Json.createArrayBuilder();
        for(Hitter h : hitters) {
            JsonObjectBuilder jso = Json.createObjectBuilder().add(JSON_TEAM, h.getMLBTeam())
                                                       .add(JSON_LNAME, h.getLastName())
                                                       .add(JSON_FNAME, h.getFirstName())
                                                       .add(JSON_QP, h.getPosition())
                                                       .add(JSON_AB, h.getAtBats())
                                                       .add(JSON_R, h.getRuns())
                                                       .add(JSON_H, h.getHits())
                                                       .add(JSON_HR, h.getHomeRuns())
                                                       .add(JSON_RBI, h.getRBI())
                                                       .add(JSON_SB, h.getStolenBases())
                                                       .add(JSON_NOTES, h.getNotes())
                                                       .add(JSON_YEAR, h.getYearOfBirth())
                                                       .add(JSON_NATION, h.getNationOfBirth());
                                                       
            jsb.add(jso);                                    
        }
        JsonArray jA = jsb.build();
        return jA;
    }

    private JsonArray makePitchersJsonArray(List<Pitcher> pitchers) {
        JsonArrayBuilder jsb = Json.createArrayBuilder();
        for(Pitcher p: pitchers){
            JsonObjectBuilder jso = Json.createObjectBuilder().add(JSON_TEAM, p.getMLBTeam())
                                                       .add(JSON_LNAME, p.getLastName())
                                                       .add(JSON_FNAME, p.getFirstName())
                                                       .add(JSON_IP, p.getIP())
                                                       .add(JSON_ER, p.getEarnedRuns())
                                                       .add(JSON_W, p.getWins())
                                                       .add(JSON_SV, p.getHits())
                                                       .add(JSON_H, p.getHits())
                                                       .add(JSON_BB, p.getWalks())
                                                       .add(JSON_K, p.getStrikeouts())
                                                       .add(JSON_NOTES, p.getNotes())
                                                       .add(JSON_YEAR, p.getYearOfBirth())
                                                       .add(JSON_NATION, p.getNationOfBirth());
            jsb.add(jso);
        }
        JsonArray jA = jsb.build();
        return jA;
    }
       
       
    
}
