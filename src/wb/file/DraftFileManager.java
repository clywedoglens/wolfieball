/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wb.file;

import java.io.IOException;
import java.util.List;
import javafx.collections.ObservableList;
import javax.json.JsonObject;
import wb.data.Draft;
import wb.data.Player;
import wb.data.Team;

/**
 *
 * @author George
 */
public interface DraftFileManager {
    public void                 saveDraft(Draft draftToSave) throws IOException;
    public void                 loadDraft(Draft draftToLoad, String coursePath) throws IOException;
    public Team                 loadTeam(Team teamToLoad, JsonObject teamJsonObject) throws IOException;
    public void                 loadAllPlayers(Draft draft) throws IOException;
    
    
}
