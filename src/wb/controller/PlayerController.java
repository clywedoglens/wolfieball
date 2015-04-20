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
public class PlayerController {
    
    private DraftDataManager dataManager;
    private boolean enabled;
    
    public PlayerController() {
        enabled = true;
    }
    
    public void enable(boolean enableSetting) {
        enabled = enableSetting;
    }
    
    public void handleAddPlayerRequest(WB_GUI gui) {
        //WILL DO LATER
    }
    
    public void handleRemovePlayerRequest(WB_GUI gui) {
        //WILL DO LATER
    }
    
    public void handlePlayerSearchRequest(WB_GUI gui, String player){
        
        Draft draft = dataManager.getDraft();
        
    }
    
}
