/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wb.file;


import wb.data.Draft;

/**
 *
 * @author George
 */
public class JsonDraftFileManager implements DraftFileManager{
    //JSON FILE READING AND WRITING CONSTANTS
    String JSON_NUMBER = "number";
    String JSON_TEAMS = "teams";
    String JSON_PLAYERS = "players";
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
  
   @Override
   public void saveDraft(Draft draftToSave) throws IOException {
       //BUILD FILE PATH
       String draftListing = "" + "Draft #" + draftToSave.getNumber();
       String jsonFilPath = PATH_DRAFTS + SLASH + draftListing + JSON_EXT;
   }
    
}
