/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wb.gui;

import java.io.IOException;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import wb.controller.FileController;
import wb.controller.PlayerController;
import wb.data.Draft;
import wb.data.DraftDataManager;
import wb.data.DraftDataView;
import wb.data.Player;
import wb.file.DraftFileManager;
import wb.file.DraftSiteExporter;

/**
 *
 * @author George
 */
public class WB_GUI implements DraftDataView{
    
    //MANAGES THE APPLICATION'S DATA
    DraftDataManager dataManager;
    
    //MANAGES COURSE FILE I/O
    DraftFileManager draftFileManager;
    
    //MANAGES EXPORTING THE DRAFT
    DraftSiteExporter draftExporter;
    
    //FILE MANAGER
    FileController fileController;
    
    //THE FOLLOWING HANDLES ALL THE USER INTERACTIONS WITH ALL THE CONTROLS
    PlayerController playerController;
    
    //APPLICATION WINDOW
    Stage primaryStage;
    
    //STAGE'S SCENE GRAPH
    Scene primaryScene;
    
    //PANE THAT ORGANIZES THE BIG PICTURE CONTAINERS
    BorderPane wbPane;
    
    //FILE TOOLBAR
    FlowPane fileToolBarPane;
    Button newDraftButton;
    Button loadDraftButton;
    Button saveDraftButton;
    Button exportDraftButton;
    Button exitButton;
    
    //SCREEN SELECTION TOOLBAR
    FlowPane screenToolBarPane;
    Button fantasyTeamsButton;
    Button playersButton;
    Button teamsStandingsButton;
    Button draftSummaryButton;
    Button mlbTeamsButton;
    
    //ORGANIZE WORKSPACE COMPONENTS USING A BORDER PANE
    BorderPane workspacePane;
    boolean workspaceActivated;
    
    ScrollPane workspaceScrollPane;
    
    //THIS WILL BE THE WORKSPACE FOR THE PLAYERS SCREEN
    //WHICH SHOULD CONTAIN A GRID PANE WITH A LABEL, RADIO BUTTON CONTROLS,
    //SEARCH LABEL, SEARCH BAR AND A TABLE
    VBox playersWorkspacePane;
    GridPane playersTablePane;
    Label playersHeadingLabel;
    Button addPlayerButton;
    Button removePlayerButton;
    Label playerSearchLabel;
    TextField playerSearchBar;
    HBox radioButtonsPane;
    ToggleGroup playerPositionsGroup;
    RadioButton allButton;
    RadioButton cButton;
    RadioButton firstBaseButton;
    RadioButton secondBaseButton;
    RadioButton thirdBaseButton;
    RadioButton ciButton;
    RadioButton miButton;
    RadioButton ssButton;
    RadioButton ofButton;
    RadioButton uButton;
    RadioButton pButton;
    TableView<Player> playersTable;
    TableColumn playerFirstNameColumn;
    TableColumn playerLastNameColumn;
    TableColumn playerProTeamColumn;
    TableColumn playerPositionsColumn;
    TableColumn playerYearOfBirthColumn;
    TableColumn playerRWColumn;
    TableColumn playerHRSVColumn;
    TableColumn playerRBIKColumn;
    TableColumn playerSBERAColumn;
    TableColumn playerBAWHIPColumn;
    TableColumn playerEstValueColumn;
    TableColumn playerNotesColumn;
    
    
    //FANTASY TEAMS SCREEN WORKSPACE
    Label fantasyTeamsLabel;
    
    
    //FANTASY STANDINGS SCREEN WORKSPACE
    Label fantasyStandingsLabel;
    
    
    //DRAFT SUMMARY SCREEN WORKSPACE
    Label draftSummaryLabel;
    
    
    //MLB TEAMS SCREEN WORKSPACE
    Label mlbTeamsLabel;
    
    //TABLE COLUMNS
    static final String COL_FIRSTNAME = "First";
    static final String COL_LASTNAME = "Last";
    static final String COL_PROTEAM = "Pro Team";
    static final String COL_POSITIONS = "Positions";
    static final String COL_YEAROFBIRTH = "Year of Birth";
    static final String COL_RUNSORWINS = "R/W";
    static final String COL_RUNS = "R";
    static final String COL_WINS = "W";
    static final String COL_HRORSAVES = "HR/SV";
    static final String COL_HOMERUNS = "HR";
    static final String COL_SAVES = "SV";
    static final String COL_RBIORK = "RBI/K";
    static final String COL_RBI = "RBI";
    static final String COL_K = "K";
    static final String COL_SBORERA = "SB/ERA";
    static final String COL_SB = "SB";
    static final String COL_ERA = "ERA";
    static final String COL_BAORWHIP = "BA/WHIP";
    static final String COL_BA = "BA";
    static final String COL_WHIP = "WHIP";
    static final String COL_ESTVAL = "Estimated Value";
    static final String COL_NOTES = "Notes";
    
    //DIALOGS
    MessageDialog messageDialog;
    YesNoCancelDialog yesNoCancelDialog;
    
    public WB_GUI(Stage initPrimaryStage) {
        primaryStage = initPrimaryStage;
    }
    
    public DraftDataManager getDataManager() {
        return dataManager;
    }
    
    public FileController getFileController() {
        return fileController;
    }
    
    public DraftFileManager getDraftFileManager() {
        return draftFileManager;
    }
    
    public DraftSiteExporter getDraftExporter() {
        return draftExporter;
    }
    
    public Stage getWindow() {
        return primaryStage;
    }
    
    public MessageDialog getMessageDialog() {
        return messageDialog;
    }
    
    public YesNoCancelDialog getYesNoCancelDialog() {
        return yesNoCancelDialog;
    }
    
    public void setDataManager(DraftDataManager initDataManager) {
        dataManager = initDataManager;
    }
    
    public void setDraftFileManager(DraftFileManager initDraftFileManager) {
        draftFileManager = initDraftFileManager;
    }

    public void setDraftExporter(DraftSiteExporter initDraftExporter) {
        draftExporter = initDraftExporter;
    }

    public void initGUI(String windowTitle) throws IOException {
        // INIT THE DIALOGS
        initDialogs();
        
        // INIT THE TOOLBAR
        initFileToolbar();

        // INIT THE CENTER WORKSPACE CONTROLS BUT DON'T ADD THEM
        // TO THE WINDOW YET
        initWorkspace();

        // NOW SETUP THE EVENT HANDLERS
        initEventHandlers();

        // AND FINALLY START UP THE WINDOW (WITHOUT THE WORKSPACE)
        initWindow(windowTitle);
    }
    
    @Override
    public void reloadDraft(Draft draftToReload) {
        
    }
    
}
