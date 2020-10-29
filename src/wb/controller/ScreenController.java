/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wb.controller;

import wb.gui.WB_GUI;

/**
 *
 * @author George
 */
public class ScreenController {
    private boolean enabled;
    
    public ScreenController(){
        enabled = true;
    }
    
    public void handleScreenChangeRequest(WB_GUI gui, String screen){
        if(enabled){
            if(screen.equalsIgnoreCase("teams screen"))
                gui.updateDraftInfo(gui.getDataManager().getDraft());
            gui.changeScreen(screen);
        }
        
    }
}
