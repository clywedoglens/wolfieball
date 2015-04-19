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
import wb.data.Hitter;
import wb.data.Pitcher;
import wb.data.Team;

/**
 *
 * @author George
 */
public class JsonDraftFileManager implements DraftFileManager{
    //JSON FILE READING AND WRITING CONSTANTS
    String JSON_NUMBER = "number";
    String JSON_TEAMS = "teams";
    String JSON_PLAYERS = "players";
    String JSON_HITTERS = "Hitters";
    String JSON_PITCHERS = "Pitchers";
    String JSON_TEAM = "TEAM";
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
       String draftListing = "" + "Draft #" + draftToSave.getNumber();
       String jsonFilePath = PATH_DRAFTS + SLASH + draftListing + JSON_EXT;
       
       //INITIALIZE WRITER
       OutputStream os = new FileOutputStream(jsonFilePath);
       JsonWriter jsonWriter = Json.createWriter(os);
       
       //MAKE AN OBJECT FOR THE DRAFT NUMBER
       JsonObject numberJsonObject = makeNumberJsonObject(draftToSave.getNumber());
       
       //MAKE A JSON ARRAY FOR THE TEAMS ARRAY
       JsonArray teamsJsonArray = makeTeamsJsonArray(draftToSave.getTeams());
       
   }
   
   @Override
   public void loadDraft(Draft draftToLoad, String jsonFilePath) throws IOException {
       //LOAD JSON FILE WITH DATA
       JsonObject json = loadJSONFile(jsonFilePath);
       
       //LOAD COURSE INFO
       draftToLoad.setNumber(json.getInt(JSON_NUMBER));
       
       JsonArray teamNames = json.getJsonArray(JSON_TEAMS);
       
       for(int i = 0; i < teamNames.size(); i++){
           Team team = new Team();
           draftToLoad.addTeam(loadTeam(team, teamNames.getString(i)).getName());
       }
       
   }
   
   @Override
   public void saveTeams(List<Object> teams, String filePath) throws IOException{
       Iterator it = teams.iterator();
       while(it.hasNext()){
           Team teamToSave = (Team) it.next();
           String teamName = teamToSave.getName();
           String teamFileListing = "" + teamName;
           String teamFilePath = PATH_DRAFTS + SLASH + teamFileListing + JSON_EXT;
           
           OutputStream os = new FileOutputStream(teamFilePath);
           JsonWriter jsonWriter = Json.createWriter(os);
           
           //THIS OBJECT WILL KEEP TRACK OF WHICH DRAFT THE TEAM WAS CREATED
           JsonObject numberJsonObject = makeNumberJsonObject(teamToSave.getDraftNumber());
                   
           //MAKE AN OBJECT FOR THE TEAM NAME;
           JsonObject teamNameJsonObject = makeTeamNameJsonObject(teamName);
           
           //MAKE A JSON ARRAY FOR THE PLAYERS THE TEAM HAS
           JsonArray hittersJsonArray = makeHittersJsonArray(teamToSave.getHitters());
           JsonArray pitchersJsonArray = makePitchersJsonArray(teamToSave.getPitchers());
           
           //BUILD THE TEAM WITH ALL THE DATA
       JsonObject teamJsonObject = Json.createObjectBuilder()
                                  .add(JSON_NUMBER, numberJsonObject)
                                  .add(JSON_TEAM, teamNameJsonObject)
                                  .add(JSON_HITTERS, hittersJsonArray)
                                  .add(JSON_PITCHERS, pitchersJsonArray)
               .build();
       
       //SAVE
       jsonWriter.writeObject(teamJsonObject);   
       }
   }

   
   public Team loadTeam(Team teamToLoad, String teamName) throws IOException {
       //LOAD JSON OBJECT
       String teamFilePath = PATH_DRAFTS + teamName + JSON_EXT;
       JsonObject json = loadJSONFile(teamFilePath);
       
       //LOAD THE TEAM
       
       teamToLoad.setDraftNumber(json.getInt(JSON_NUMBER));
       teamToLoad.setName(json.getString(JSON_TEAM));
       
       //GET PLAYERS
       JsonArray jsonHittersArray = json.getJsonArray(JSON_HITTERS);
       for(int i = 0; i < jsonHittersArray.size(); i++){
           Hitter newHitter = new Hitter();
           teamToLoad.addHitter(loadHitter(newHitter, jsonHittersArray.getInt(i)));
       }
       
       JsonArray jsonPitchersArray = json.getJsonArray(JSON_PITCHERS);
       for(int i = 0; i < jsonPitchersArray.size(); i++){
           Pitcher newPitcher = new Pitcher();
           teamToLoad.addPitcher(loadPitcher(newPitcher, jsonPitchersArray.getInt(i)));
       }
       return teamToLoad;
       
   }
   
   public Hitter loadHitter(Hitter playerToLoad, int index) throws IOException {
      //LOAD THE HITTERS AND PITCHERS OBJECTS AND ARRAYS
       String hittersFilePath = PATH_DRAFTS + SLASH + "Hitters" + JSON_EXT;
       
       
       JsonObject hittersObject = loadJSONFile(hittersFilePath);
       JsonArray hitters = hittersObject.getJsonArray(JSON_HITTERS);
       JsonObject hitterObject = hitters.getJsonObject(index);
       
       playerToLoad.setMLBTeam(hitterObject.getString(JSON_TEAM));
       playerToLoad.setLastName(hitterObject.getString(JSON_LNAME));
       playerToLoad.setFirstName(hitterObject.getString(JSON_FNAME));
       playerToLoad.setPosition(hitterObject.getString(JSON_QP));
       playerToLoad.setAtBats(hitterObject.getInt(JSON_AB));
       playerToLoad.setRuns(hitterObject.getInt(JSON_R));
       playerToLoad.setHits(hitterObject.getInt(JSON_H));
       playerToLoad.setHomeRuns(hitterObject.getInt(JSON_HR));
       playerToLoad.setRBI(hitterObject.getInt(JSON_RBI));
       playerToLoad.setStolenBases(hitterObject.getInt(JSON_SB));
       playerToLoad.setNotes(hitterObject.getString(JSON_NOTES));
       playerToLoad.setYearOfBirth(hitterObject.getInt(JSON_YEAR));
       playerToLoad.setNationOfBirth(hitterObject.getString(JSON_NATION));
       
       return playerToLoad;
       
   }
   
   public Pitcher loadPitcher(Pitcher playerToLoad, int index) throws IOException{
       String pitchersFilePath = PATH_DRAFTS + SLASH + "Pitchers" + JSON_EXT;
       
       JsonObject pitchersObject = loadJSONFile(pitchersFilePath);
       JsonArray pitchers = pitchersObject.getJsonArray(JSON_PITCHERS);
       JsonObject pitcherObject = pitchers.getJsonObject(index);
       
       playerToLoad.setMLBTeam(pitcherObject.getString(JSON_TEAM));
       playerToLoad.setLastName(pitcherObject.getString(JSON_LNAME));
       playerToLoad.setFirstName(pitcherObject.getString(JSON_FNAME));
       playerToLoad.setIP(pitcherObject.getInt(JSON_IP));
       playerToLoad.setEarnedRuns(pitcherObject.getInt(JSON_ER));
       playerToLoad.setWins(pitcherObject.getInt(JSON_W));
       playerToLoad.setSaves(pitcherObject.getInt(JSON_SV));
       playerToLoad.setHits(pitcherObject.getInt(JSON_H));
       playerToLoad.setWalks(pitcherObject.getInt(JSON_BB));
       playerToLoad.setStrikeouts(pitcherObject.getInt(JSON_K));
       playerToLoad.setNotes(pitcherObject.getString(JSON_NOTES));
       playerToLoad.setYearOfBirth(pitcherObject.getInt(JSON_YEAR));
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
    
    // BUILDS AND RETURNS A JsonObject CONTAINING A JsonArray
    // THAT CONTAINS THE PROVIDED DATA
    public JsonObject buildJsonArrayObject(List<Object> data) {
        JsonArray jA = buildJsonArray(data);
        JsonObject arrayObject = Json.createObjectBuilder().add(JSON_SUBJECTS, jA).build();
        return arrayObject;
    }

    private JsonObject makeNumberJsonObject(int number) {
        JsonObject jso = Json.createObjectBuilder().add(JSON_NUMBER, number)
                                                    .build();
        return jso;
    }

    private JsonArray makeTeamsJsonArray(List<String> teams) {
        JsonArrayBuilder jsb = Json.createArrayBuilder();
        for(String s: teams) {
            jsb.add(s);
        }
        JsonArray jA = jsb.build();
        return jA;
    }

    private JsonObject makeTeamNameJsonObject(String teamName) {
        JsonObject jso = Json.createObjectBuilder().add(JSON_TEAM, teamName)
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
