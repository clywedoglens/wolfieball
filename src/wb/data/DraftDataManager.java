/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wb.data;

import wb.file.DraftFileManager;

/**
 *
 * @author George
 */
public class DraftDataManager {
    
    Draft draft;
    DraftDataView view;
    
    DraftFileManager fileManager;
    
    static int  DEFAULT_NUM = 0;
    
    
    public DraftDataManager(DraftDataView initView)
    {
        view = initView;
        draft = new Draft();
    }
    
    public Draft getDraft(){
        return draft;
    }
    
    public DraftFileManager getFileManager() {
        return fileManager;
    }
    
    public void reset() {
        draft.setNumber(DEFAULT_NUM);
        draft.clearTeams();
        Team freeAgency = new Team();
        freeAgency.setName("Free Agency");
        draft.addTeam(freeAgency);
        
        view.reloadDraft(draft);
    }
}
