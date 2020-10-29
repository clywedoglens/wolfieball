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
public class DraftSiteExporter {
    
    String baseDir;
    String sitesDir;
    
    public DraftSiteExporter (String initBaseDir, String initSitesDir) {
        baseDir = initBaseDir;
        sitesDir = initSitesDir;
    }
    
    public void exportDraftSite(Draft draftToExport) throws Exception {
        
    }
}
