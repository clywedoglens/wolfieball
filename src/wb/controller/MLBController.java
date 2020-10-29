/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wb.controller;

import wb.data.Draft;
import wb.data.DraftDataManager;
import wb.gui.WB_GUI;

/**
 *
 * @author George
 */
public class MLBController {
    private DraftDataManager dataManager;
    private boolean enabled;
    
    public MLBController(){
        enabled = true;
    }
    
    public void enable(boolean enableSetting){
        enabled = enableSetting;
    }
    
    public void handleTeamSelectRequest(WB_GUI gui){
        dataManager = gui.getDataManager();
        Draft draft = dataManager.getDraft();
        gui.updateMLBTable(draft);
        
        
    }
}
