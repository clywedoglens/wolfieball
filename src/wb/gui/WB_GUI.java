    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wb.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import properties_manager.PropertiesManager;
import wb.WB_PropertyType;
import static wb.WB_StartupConstants.CLOSE_BUTTON_LABEL;
import static wb.WB_StartupConstants.PATH_CSS;
import static wb.WB_StartupConstants.PATH_IMAGES;
import wb.controller.FileController;
import wb.controller.PlayerController;
import wb.controller.ScreenController;
import wb.controller.TeamsController;
import wb.data.Draft;
import wb.data.DraftDataManager;
import wb.data.DraftDataView;
import wb.data.Player;
import wb.data.Team;
import wb.file.DraftFileManager;
import wb.file.DraftSiteExporter;

/**
 *
 * @author George
 */
public class WB_GUI implements DraftDataView{
    
    static final String PRIMARY_STYLE_SHEET = PATH_CSS + "wb_style.css";
    static final String CLASS_GRAY_PANE = "gray_pane";
    static final String CLASS_BORDERED_PANE = "bordered_pane";
    static final String CLASS_SUBJECT_PANE = "subject_pane";
    static final String CLASS_HEADING_LABEL = "heading_label";
    static final String CLASS_SUBHEADING_LABEL = "subheading_label";
    static final String CLASS_PROMPT_LABEL = "prompt_label";
    static final String EMPTY_TEXT = "";
    static final int LARGE_TEXT_FIELD_LENGTH = 20;
    static final int MEDIUM_TEXT_FIELD_LENGTH = 12;
    static final int SMALL_TEXT_FIELD_LENGTH = 5;
    
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
    
    //THIS HANDLES ALL THE USER INTERACTIONS WITH THE TEAMS SCREEN
    TeamsController teamsController;
    
    //THIS HANDLES THE BOTTOM TOOLBAR TO SWITCH SCREENS
    ScreenController screenController;
    
    //APPLICATION WINDOW
    Stage primaryStage;
    
    //OUR MAIN WINDOW
    Scene primaryScene;
    
    //PANE THAT ORGANIZES THE BIG PICTURE CONTAINERS
    BorderPane wbPane;
    
    ScrollPane workspaceScrollPane;
    
    //FILE TOOLBAR
    FlowPane fileToolbarPane;
    Button newDraftButton;
    Button loadDraftButton;
    Button saveDraftButton;
    Button exportDraftButton;
    Button exitButton;
    
    //SCREEN SELECTION TOOLBAR
    FlowPane screenToolbarPane;
    Button fantasyTeamsButton;
    Button playersButton;
    Button teamsStandingsButton;
    Button draftSummaryButton;
    Button mlbTeamsButton;
    
    //SCREEN STRINGS
    static String TEAMS_SCREEN = "teams screen";
    static String PLAYER_SCREEN = "player screen";
    static String STANDINGS_SCREEN = "standings screen";
    static String DRAFT_SCREEN = "draft screen";
    static String MLB_SCREEN = "mlb screen";
    //ORGANIZE WORKSPACE COMPONENTS USING A BORDER PANE
    boolean workspaceActivated;
    
    HashMap<String,Pane> workspacePanes;
    
    //THIS WILL BE THE WORKSPACE FOR THE PLAYERS SCREEN
    //WHICH SHOULD CONTAIN A GRID PANE WITH A LABEL, RADIO BUTTON CONTROLS,
    //SEARCH LABEL, SEARCH BAR AND A TABLE
    VBox playersTopPane;
    BorderPane playersCenterPane;
    BorderPane playersWorkspacePane;
    VBox playersControlBox;
    VBox playersBox;
    SplitPane playersWorkspaceSplitPane;
    GridPane playerSearchPane;
    GridPane positionSelectionPane;
    Label playersHeadingLabel;
    GridPane topPlayersLabelPane;
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
    TableColumn playerIPABColumn;
    TableColumn playerERRColumn;
    TableColumn playerWHColumn;
    TableColumn playerSVHRColumn;
    TableColumn playerHRBIColumn;
    TableColumn playerBBSBColumn;
    TableColumn playerKColumn;
    TableColumn playerNotesColumn;
    TableColumn playerNationOfBirthColumn;
    
    //FANTASY TEAMS SCREEN WORKSPACE
    VBox teamsTopPane;
    VBox teamsCenterPane;
    Label fantasyTeamsLabel;
    GridPane topTeamsLabelPane;
    BorderPane teamsWorkspacePane;
    GridPane lineupPane;
    HBox lineupButtonsPane;
    Label draftNameLabel;
    TextField draftNameTextField;
    Button addTeamButton;
    Button removeTeamButton;
    Button editTeamButton;
    Label selectTeamLabel;
    ComboBox<Team> selectTeamComboBox;
    VBox fantasyTableBox;
    Label startingLineupLabel;
    TableView<Player> fantasyTable;
    TableColumn fantasyFirstNameColumn;
    TableColumn fantasyLastNameColumn;
    TableColumn fantasyProTeamColumn;
    TableColumn fantasyPositionsColumn;
    TableColumn fantasyYearOfBirthColumn;
    TableColumn fantasyIPABColumn;
    TableColumn fantasyERRColumn;
    TableColumn fantasyWHColumn;
    TableColumn fantasySVHRColumn;
    TableColumn fantasyHRBIColumn;
    TableColumn fantasyBBSBColumn;
    TableColumn fantasyKColumn;
    
    //FANTASY STANDINGS SCREEN WORKSPACE
    VBox standingsTopPane;
    VBox standingsCenterPane;
    Label fantasyStandingsLabel;
    GridPane standingsLabelPane;
    BorderPane standingsWorkspacePane;
    
    //DRAFT SUMMARY SCREEN WORKSPACE
    VBox draftTopPane;
    VBox draftCenterPane;
    Label draftSummaryLabel;
    GridPane draftLabelPane;
    BorderPane draftWorkspacePane;
    
    
    //MLB TEAMS SCREEN WORKSPACE
    VBox mlbTopPane;
    VBox mlbCenterPane;
    Label mlbTeamsLabel;
    GridPane mlbLabelPane;
    BorderPane mlbWorkspacePane;
    
    //PLAYER POSITIONS
    static final String ALL = "All";
    static final String CATCHER = "C";
    static final String FIRST_BASE = "1B";
    static final String SECOND_BASE = "2B";
    static final String THIRD_BASE = "3B";
    static final String CI = "CI";
    static final String MI = "MI";
    static final String SHORTSTOP = "SS";
    static final String OUTFIELDER = "OF";
    static final String U = "U";
    static final String PITCHER = "P";
    
    //TABLE COLUMNS
    static final String COL_FIRSTNAME = "First";
    static final String COL_LASTNAME = "Last";
    static final String COL_PROTEAM = "Pro Team";
    static final String COL_POSITIONS = "Positions";
    static final String COL_YEAROFBIRTH = "Year of Birth";
    static final String COL_IPAB = "IP/AB";
    static final String COL_ERR = "ER/R";
    static final String COL_WH = "W/H";
    static final String COL_SVHR = "SV/HR";
    static final String COL_HRBI = "H/RBI";
    static final String COL_BBSB = "BBSB";
    static final String COL_K = "K";
    static final String COL_NOTES = "Notes";
    static final String COL_NATIONOFBIRTH = "Nation of Birth";
    
    //DIALOGS
    MessageDialog messageDialog;
    YesNoCancelDialog yesNoCancelDialog;
    PlayerDialog playerDialog;
    
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
        initToolbars();

        // INIT THE CENTER WORKSPACE CONTROLS BUT DON'T ADD THEM
        // TO THE WINDOW YET
        initWorkspace();

        // NOW SETUP THE EVENT HANDLERS
        initEventHandlers();

        // AND FINALLY START UP THE WINDOW (WITHOUT THE WORKSPACE)
        initWindow(windowTitle);
    }
    
    public void activateWorkspace() {
        if(!workspaceActivated) {
            //PUT THE MAIN WORKSPACE IN THE GUI WHEN A NEW DRAFT STARTS
            wbPane.setCenter(workspaceScrollPane);
            workspaceActivated = true;
        }
    }
    @Override
    public void reloadDraft(Draft draftToReload) {
        if( !workspaceActivated) {
            activateWorkspace();
        }
        
        
    }
    
    public void updateToolbarControls(boolean saved) {
        //THIS TOGGLES WITH WHETHER THE CURRENT DRAFT
        // HAS BEEN SAVED OR NOT
        saveDraftButton.setDisable(saved);
        
        //ALL THE OTHER BUTTONS ARE ALWAYS ENABLED
        //ONCE EDITING THAT FIRST DRAFT BEGINS
        loadDraftButton.setDisable(false);
        exportDraftButton.setDisable(false);
        fantasyTeamsButton.setDisable(false);
        playersButton.setDisable(false);
        teamsStandingsButton.setDisable(false);
        draftSummaryButton.setDisable(false);
        mlbTeamsButton.setDisable(false);
        
        
    }
    
    public void changeScreen(String screen) {
        
        workspaceActivated = false;
        BorderPane newPane = (BorderPane) workspacePanes.get(screen);
        workspaceScrollPane.setContent(newPane);
        workspaceActivated = true;
    }
    
    public void updateDraftInfo(Draft draft) {
        draft.setName(draftNameTextField.getText());
        Team teamToShow = selectTeamComboBox.getSelectionModel().getSelectedItem();
        ObservableList<Player> fantasyTeamPlayers = FXCollections.observableArrayList();
        fantasyTeamPlayers.addAll(teamToShow.getPitchers());
        //fantasyTeamPlayers.addAll(teamToShow.getHitters());
        fantasyTable.setItems(fantasyTeamPlayers);
    }
    
    /****************************************************************************/
    /* BELOW ARE ALL THE PRIVATE HELPER METHODS WE USE FOR INITIALIZING OUR GUI */
    /****************************************************************************/
    
    private void initDialogs() {
        messageDialog = new MessageDialog(primaryStage, CLOSE_BUTTON_LABEL);
        yesNoCancelDialog = new YesNoCancelDialog(primaryStage);
        playerDialog = new PlayerDialog(primaryStage, dataManager.getDraft());
    }
    
    private void initToolbars() {
        fileToolbarPane = new FlowPane();
        
        newDraftButton = initChildButton(fileToolbarPane, WB_PropertyType.NEW_DRAFT_ICON, WB_PropertyType.NEW_DRAFT_TOOLTIP, false);
        loadDraftButton = initChildButton(fileToolbarPane, WB_PropertyType.LOAD_DRAFT_ICON, WB_PropertyType.LOAD_DRAFT_TOOLTIP, false);
        saveDraftButton = initChildButton(fileToolbarPane, WB_PropertyType.SAVE_DRAFT_ICON, WB_PropertyType.SAVE_DRAFT_TOOLTIP, true);
        exportDraftButton = initChildButton(fileToolbarPane, WB_PropertyType.EXPORT_DRAFT_ICON, WB_PropertyType.EXPORT_DRAFT_TOOLTIP, true);
        exitButton = initChildButton(fileToolbarPane, WB_PropertyType.EXIT_ICON, WB_PropertyType.EXIT_TOOLTIP, false);
        
        screenToolbarPane = new FlowPane();
        
        fantasyTeamsButton = initChildButton(screenToolbarPane, WB_PropertyType.TEAMS_SCREEN_ICON, WB_PropertyType.TEAMS_SCREEN_TOOLTIP, true);
        playersButton = initChildButton(screenToolbarPane, WB_PropertyType.PLAYERS_SCREEN_ICON, WB_PropertyType.PLAYERS_SCREEN_TOOLTIP, true);
        teamsStandingsButton = initChildButton(screenToolbarPane, WB_PropertyType.STANDINGS_SCREEN_ICON, WB_PropertyType.STANDINGS_SCREEN_TOOLTIP, true);
        draftSummaryButton = initChildButton(screenToolbarPane, WB_PropertyType.DRAFT_SCREEN_ICON, WB_PropertyType.DRAFT_SCREEN_TOOLTIP, true);
        mlbTeamsButton = initChildButton(screenToolbarPane, WB_PropertyType.MLBTEAMS_SCREEN_ICON, WB_PropertyType.MLBTEAMS_SCREEN_TOOLTIP, true);
        
    }
    
    private void initWindow(String windowTitle) {
        //WINDOW TITLE
        primaryStage.setTitle(windowTitle);
        
        //SIZE OF SCREEN
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        
        
        // AND USE IT TO SIZE THE WINDOW
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());
        
        wbPane = new BorderPane();
        wbPane.setTop(fileToolbarPane);
        wbPane.setBottom(screenToolbarPane);
        primaryScene = new Scene(wbPane);
        
        // NOW TIE THE SCENE TO THE WINDOW, SELECT THE STYLESHEET
        // WE'LL USE TO STYLIZE OUR GUI CONTROLS, AND OPEN THE WINDOW
        primaryScene.getStylesheets().add(PRIMARY_STYLE_SHEET);
        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }
    
    private void initWorkspace() throws IOException {
        
        
        
        initTopWorkspaces();
        
        initPlayersScreenControls();
        
        initFantasyTeamsScreenControls();
        
        initStandingsScreenControls();
        
        initDraftScreenControls();
        
        initMLBTeamsScreenControls();
        
        teamsWorkspacePane = new BorderPane();
        playersWorkspacePane = new BorderPane();
        standingsWorkspacePane = new BorderPane();
        draftWorkspacePane = new BorderPane();
        mlbWorkspacePane = new BorderPane();
        
        workspacePanes = new HashMap<String, Pane>();
        workspacePanes.put(TEAMS_SCREEN, teamsWorkspacePane);
        workspacePanes.put(PLAYER_SCREEN, playersWorkspacePane);
        workspacePanes.put(STANDINGS_SCREEN, standingsWorkspacePane);
        workspacePanes.put(DRAFT_SCREEN, draftWorkspacePane);
        workspacePanes.put(MLB_SCREEN, mlbWorkspacePane);
        
        teamsWorkspacePane.setTop(teamsTopPane);
        playersWorkspacePane.setTop(playersTopPane);
        standingsWorkspacePane.setTop(standingsTopPane);
        draftWorkspacePane.setTop(draftTopPane);
        mlbWorkspacePane.setTop(mlbTopPane);
        
        teamsWorkspacePane.setCenter(teamsCenterPane);
        teamsWorkspacePane.getStyleClass().add(CLASS_BORDERED_PANE);
        
        playersWorkspacePane.setCenter(playersCenterPane);
        playersWorkspacePane.getStyleClass().add(CLASS_BORDERED_PANE);
        
        standingsWorkspacePane.setCenter(standingsCenterPane);
        standingsWorkspacePane.getStyleClass().add(CLASS_BORDERED_PANE);
        
        draftWorkspacePane.setCenter(draftCenterPane);
        draftWorkspacePane.getStyleClass().add(CLASS_BORDERED_PANE);
        
        mlbWorkspacePane.setCenter(mlbCenterPane);
        mlbWorkspacePane.getStyleClass().add(CLASS_BORDERED_PANE);
        
        workspaceScrollPane = new ScrollPane();
        workspaceScrollPane.setContent(teamsWorkspacePane);
        workspaceScrollPane.setFitToWidth(true);
        workspaceScrollPane.setFitToHeight(true);
        workspaceActivated = false;
        
        
    }
    
    private void initTopWorkspaces(){
        
        
     
        playersTopPane = new VBox();
        
        playersTopPane.getStyleClass().add(CLASS_GRAY_PANE);
        topPlayersLabelPane = new GridPane();
        playersHeadingLabel = initGridLabel(topPlayersLabelPane, WB_PropertyType.PLAYER_AVAILABLE_LABEL, CLASS_HEADING_LABEL, 0, 0, 4, 1);
        
        playersTopPane.getChildren().add(topPlayersLabelPane);
        
        teamsTopPane = new VBox();
        topTeamsLabelPane = new GridPane();
        teamsTopPane.getStyleClass().add(CLASS_GRAY_PANE);
        fantasyTeamsLabel = initGridLabel(topTeamsLabelPane, WB_PropertyType.FANTASY_TEAMS_LABEL, CLASS_HEADING_LABEL, 0, 0, 4, 1);
        teamsTopPane.getChildren().add(topTeamsLabelPane);
        
        standingsTopPane = new VBox();
        standingsLabelPane = new GridPane();
        fantasyStandingsLabel = initGridLabel(standingsLabelPane, WB_PropertyType.FANTASY_STANDINGS_LABEL, CLASS_HEADING_LABEL, 0, 0, 4, 1);
        standingsTopPane.getStyleClass().add(CLASS_GRAY_PANE);
        standingsTopPane.getChildren().add(standingsLabelPane); 
        
              
        draftTopPane = new VBox();
        draftLabelPane = new GridPane();
        draftSummaryLabel = initGridLabel(draftLabelPane, WB_PropertyType.DRAFT_SUMMARY_LABEL, CLASS_HEADING_LABEL, 0, 0, 4, 1);
        draftTopPane.getStyleClass().add(CLASS_GRAY_PANE);
        draftTopPane.getChildren().add(draftLabelPane);
        
        
        
        mlbTopPane = new VBox();
        mlbLabelPane = new GridPane();
        mlbTeamsLabel = initGridLabel(mlbLabelPane, WB_PropertyType.MLB_TEAMS_LABEL, CLASS_HEADING_LABEL, 0, 0, 4, 1);
        mlbTopPane.getStyleClass().add(CLASS_GRAY_PANE);
        mlbTopPane.getChildren().add(mlbLabelPane);
        
        
     
    }
    
 
    private void initPlayersScreenControls() {
       playerSearchPane = new GridPane();
        
       addPlayerButton = initGridButton(playerSearchPane, WB_PropertyType.ADD_PLAYER_ICON, WB_PropertyType.ADD_PLAYER_TOOLTIP, 0, 0, 1, 1, false);
       removePlayerButton = initGridButton(playerSearchPane, WB_PropertyType.REMOVE_PLAYER_ICON, WB_PropertyType.REMOVE_PLAYER_TOOLTIP, 1, 0, 1, 1, false);
       playerSearchLabel = initGridLabel(playerSearchPane, WB_PropertyType.PLAYER_SEARCH_LABEL, CLASS_SUBHEADING_LABEL, 0, 1, 1, 1);
       playerSearchBar = initGridTextField(playerSearchPane, MEDIUM_TEXT_FIELD_LENGTH, EMPTY_TEXT, true, 1, 1, 1, 1);
       
       positionSelectionPane = new GridPane();
       playerPositionsGroup = new ToggleGroup();
       allButton = initGridRadioButton(positionSelectionPane, ALL, playerPositionsGroup, 0, 0, 1, 1);//new RadioButton("All");
       cButton = initGridRadioButton(positionSelectionPane, CATCHER, playerPositionsGroup, 3, 0, 1, 1);//new RadioButton("C");
       firstBaseButton = initGridRadioButton(positionSelectionPane, FIRST_BASE, playerPositionsGroup, 6, 0, 1, 1);
       secondBaseButton = initGridRadioButton(positionSelectionPane, SECOND_BASE, playerPositionsGroup, 9, 0, 1, 1); //new RadioButton("2B");
       thirdBaseButton = initGridRadioButton(positionSelectionPane, THIRD_BASE, playerPositionsGroup, 12, 0, 1, 1);
       ssButton = initGridRadioButton(positionSelectionPane, SHORTSTOP, playerPositionsGroup, 15, 0, 1, 1);//new RadioButton("SS");
       ciButton = initGridRadioButton(positionSelectionPane, CI, playerPositionsGroup, 18, 0, 1, 1);
       miButton = initGridRadioButton(positionSelectionPane, MI, playerPositionsGroup, 21, 0, 1, 1);
       ofButton = initGridRadioButton(positionSelectionPane, OUTFIELDER, playerPositionsGroup, 24, 0, 1, 1);//new RadioButton("OF");
       uButton = initGridRadioButton(positionSelectionPane, U, playerPositionsGroup, 27, 0, 1, 1);//new RadioButton("U");
       pButton = initGridRadioButton(positionSelectionPane, PITCHER, playerPositionsGroup, 30, 0, 1, 1);//new RadioButton("P");
       allButton.setSelected(true);
       
       playersWorkspaceSplitPane = new SplitPane();
       playersWorkspaceSplitPane.getItems().add(playerSearchPane);
       playersWorkspaceSplitPane.getItems().add(positionSelectionPane);
       playersWorkspaceSplitPane.getStyleClass().add(CLASS_BORDERED_PANE);
       
       playersControlBox = new VBox();
       playersControlBox.getStyleClass().add(CLASS_BORDERED_PANE);
       playersControlBox.getChildren().add(playersWorkspaceSplitPane);
       
       playersCenterPane = new BorderPane();
       playersCenterPane.setTop(playersControlBox);
       playersCenterPane.getStyleClass().add(CLASS_GRAY_PANE);
       
       playersBox = new VBox();
       playersTable = new TableView();
       playersBox.getChildren().add(playersTable);
       playersBox.getStyleClass().add(CLASS_GRAY_PANE);
       
       //SETUP TABLE COLUMNS
       playerFirstNameColumn = new TableColumn(COL_FIRSTNAME);
       playerLastNameColumn = new TableColumn(COL_LASTNAME);
       playerProTeamColumn = new TableColumn(COL_PROTEAM);
       playerPositionsColumn = new TableColumn(COL_POSITIONS);
       playerYearOfBirthColumn = new TableColumn(COL_YEAROFBIRTH);
       playerIPABColumn = new TableColumn(COL_IPAB);
       playerERRColumn = new TableColumn(COL_ERR);
       playerWHColumn = new TableColumn(COL_WH);
       playerSVHRColumn = new TableColumn(COL_SVHR);
       playerHRBIColumn = new TableColumn(COL_HRBI);
       playerBBSBColumn = new TableColumn(COL_BBSB);
       playerKColumn = new TableColumn(COL_K);
       playerNotesColumn = new TableColumn(COL_NOTES);
       playerNationOfBirthColumn = new TableColumn(COL_NATIONOFBIRTH);
       
       //LINK COLUMNS TO DATA
       playerFirstNameColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("firstName"));
       playerLastNameColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("lastName"));
       playerProTeamColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("MLBTeam"));
       playerPositionsColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("position"));
       playerYearOfBirthColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("yearOfBirth"));
       playerIPABColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("IPAB"));
       playerERRColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("ERR"));
       playerWHColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("WH"));
       playerSVHRColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("SVHR"));
       playerHRBIColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("HRBI"));
       playerBBSBColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("BBSB"));
       playerKColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("K"));
       playerNotesColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("notes"));
       playerNationOfBirthColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("nationOfBirth"));
       
       //MAKE THE NOTES CELLS EDITABLE BY THE USER
       playersTable.setEditable(true);
       playerNotesColumn.setCellFactory(TextFieldTableCell.forTableColumn());
       playerNotesColumn.setOnEditCommit(
            new EventHandler<CellEditEvent<Player,String>>(){
                @Override
                public void handle(CellEditEvent<Player, String> t) {
                    ((Player) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())
                    ).setNotes(t.getNewValue());
                }
            });
       playersTable.getColumns().add(playerFirstNameColumn);
       playersTable.getColumns().add(playerLastNameColumn);
       playersTable.getColumns().add(playerProTeamColumn);
       playersTable.getColumns().add(playerPositionsColumn);
       playersTable.getColumns().add(playerYearOfBirthColumn);
       playersTable.getColumns().add(playerIPABColumn);
       playersTable.getColumns().add(playerERRColumn);
       playersTable.getColumns().add(playerWHColumn);
       playersTable.getColumns().add(playerSVHRColumn);
       playersTable.getColumns().add(playerHRBIColumn);
       playersTable.getColumns().add(playerBBSBColumn);
       playersTable.getColumns().add(playerNotesColumn);
       playersTable.getColumns().add(playerNationOfBirthColumn);
       playersTable.setItems(dataManager.getDraft().getAllPlayers());
       
       //ASSEMBLE EVERYTHING
       playersCenterPane.setCenter(playersBox);
       
    }
    
    private void initFantasyTeamsScreenControls() throws IOException {
        teamsCenterPane = new VBox();
        teamsCenterPane.getStyleClass().add(CLASS_GRAY_PANE);
        
        lineupPane = new GridPane();
        draftNameLabel = initGridLabel(lineupPane, WB_PropertyType.DRAFT_NAME_LABEL, CLASS_SUBHEADING_LABEL, 0, 0, 1, 1);
        draftNameTextField = initGridTextField(lineupPane, MEDIUM_TEXT_FIELD_LENGTH, EMPTY_TEXT, true, 1, 0, 1, 1 );
        
        lineupButtonsPane = new HBox();
        addTeamButton = initChildButton(lineupButtonsPane, WB_PropertyType.ADD_PLAYER_ICON, WB_PropertyType.ADD_TEAM_TOOLTIP, false);
        removeTeamButton = initChildButton(lineupButtonsPane, WB_PropertyType.REMOVE_PLAYER_ICON, WB_PropertyType.REMOVE_TEAM_TOOLTIP, true);
        editTeamButton = initChildButton(lineupButtonsPane, WB_PropertyType.EDIT_TEAM_ICON, WB_PropertyType.EDIT_TEAM_TOOLTIP, true);
        fantasyTeamsLabel = initChildLabel(lineupButtonsPane, WB_PropertyType.SELECT_TEAM_LABEL, CLASS_SUBHEADING_LABEL);
        
        selectTeamComboBox = new ComboBox();
        selectTeamComboBox.setCellFactory(new Callback<ListView<Team>, ListCell<Team>>() {
            @Override
            public ListCell<Team> call(ListView<Team> t) {
               ListCell cell = new ListCell<Team>() {
                   @Override
                   protected void updateItem(Team item, boolean empty){
                       super.updateItem(item, empty);
                       if(empty) {
                           setText("");
                       } else {
                           setText(item.getName());
                       }
                   }
               };return cell;
            }
        });
        
        selectTeamComboBox.setButtonCell(new ListCell<Team>(){
        @Override
        protected void updateItem(Team t, boolean bln) {
            super.updateItem(t, bln);
            if(bln){
                setText("");
            } else {
                setText(t.getName());
            }
        }
    });
        selectTeamComboBox.setItems(dataManager.getDraft().getTeams());
        
        lineupButtonsPane.getChildren().add(selectTeamComboBox);
        
        fantasyTableBox = new VBox();
        startingLineupLabel = initChildLabel(fantasyTableBox, WB_PropertyType.STARTING_LINEUP_LABEL, CLASS_SUBHEADING_LABEL);
        fantasyTable = new TableView();
        fantasyTableBox.getChildren().add(fantasyTable);
        fantasyTableBox.getStyleClass().add(CLASS_BORDERED_PANE);
        
        //SETUP TABLE COLUMNS
       fantasyFirstNameColumn = new TableColumn(COL_FIRSTNAME);
       fantasyLastNameColumn = new TableColumn(COL_LASTNAME);
       fantasyProTeamColumn = new TableColumn(COL_PROTEAM);
       fantasyPositionsColumn = new TableColumn(COL_POSITIONS);
       fantasyYearOfBirthColumn = new TableColumn(COL_YEAROFBIRTH);
       fantasyIPABColumn = new TableColumn(COL_IPAB);
       fantasyERRColumn = new TableColumn(COL_ERR);
       fantasyWHColumn = new TableColumn(COL_WH);
       fantasySVHRColumn = new TableColumn(COL_SVHR);
       fantasyHRBIColumn = new TableColumn(COL_HRBI);
       fantasyBBSBColumn = new TableColumn(COL_BBSB);
       fantasyKColumn = new TableColumn(COL_K);
       
        //LINK COLUMNS TO DATA
       fantasyFirstNameColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("firstName"));
       fantasyLastNameColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("lastName"));
       fantasyProTeamColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("MLBTeam"));
       fantasyPositionsColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("position"));
       fantasyYearOfBirthColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("yearOfBirth"));
       fantasyIPABColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("IPAB"));
       fantasyERRColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("ERR"));
       fantasyWHColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("WH"));
       fantasySVHRColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("SVHR"));
       fantasyHRBIColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("HRBI"));
       fantasyBBSBColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("BBSB"));
       fantasyKColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("K"));
       
       
       fantasyTable.getColumns().add(fantasyFirstNameColumn);
       fantasyTable.getColumns().add(fantasyLastNameColumn);
       fantasyTable.getColumns().add(fantasyProTeamColumn);
       fantasyTable.getColumns().add(fantasyPositionsColumn);
       fantasyTable.getColumns().add(fantasyYearOfBirthColumn);
       fantasyTable.getColumns().add(fantasyIPABColumn);
       fantasyTable.getColumns().add(fantasyERRColumn);
       fantasyTable.getColumns().add(fantasyWHColumn);
       fantasyTable.getColumns().add(fantasySVHRColumn);
       fantasyTable.getColumns().add(fantasyHRBIColumn);
       fantasyTable.getColumns().add(fantasyBBSBColumn);
       fantasyTable.getColumns().add(fantasyKColumn);
        
        teamsCenterPane.getChildren().add(lineupButtonsPane);
        teamsCenterPane.getChildren().add(lineupPane);
        teamsCenterPane.getChildren().add(fantasyTableBox);
    }
    
    private void initStandingsScreenControls(){
        standingsCenterPane = new VBox();
        standingsCenterPane.getStyleClass().add(CLASS_GRAY_PANE);
    }
    
    private void initDraftScreenControls(){
        draftCenterPane = new VBox();
        draftCenterPane.getStyleClass().add(CLASS_GRAY_PANE);
    }
    
    private void initMLBTeamsScreenControls(){
         mlbCenterPane = new VBox(); 
         mlbCenterPane.getStyleClass().add(CLASS_GRAY_PANE);
    }

    private void initEventHandlers() throws IOException {
        // FIRST THE FILE CONTROLS
        fileController = new FileController(messageDialog, yesNoCancelDialog, draftFileManager, draftExporter);
        newDraftButton.setOnAction(e -> {
            fileController.handleNewDraftRequest(this);
        });
        loadDraftButton.setOnAction(e -> {
            fileController.handleLoadDraftRequest(this);
        });
        saveDraftButton.setOnAction(e -> {
            fileController.handleSaveDraftRequest(this, dataManager.getDraft());
        });
        exportDraftButton.setOnAction(e -> {
            fileController.handleExportDraftRequest(this);
        });
        exitButton.setOnAction(e -> {
            fileController.handleExitRequest(this);
        });
        
        //NOW THE BOTTOM TOOLBAR CONTROLS
        screenController = new ScreenController();
        fantasyTeamsButton.setOnAction(e -> {
            screenController.handleScreenChangeRequest(this, TEAMS_SCREEN);
        });
        playersButton.setOnAction(e -> {
            screenController.handleScreenChangeRequest(this, PLAYER_SCREEN);
        });
        teamsStandingsButton.setOnAction(e -> {
            screenController.handleScreenChangeRequest(this, STANDINGS_SCREEN);
        });
        draftSummaryButton.setOnAction(e -> {
            screenController.handleScreenChangeRequest(this, DRAFT_SCREEN);
        });
        mlbTeamsButton.setOnAction(e -> {
            screenController.handleScreenChangeRequest(this, MLB_SCREEN);
        });
        //NOW THE PLAYERS SCREEN
        playerController = new PlayerController(playerDialog);
        addPlayerButton.setOnAction(e -> {
            playerController.handleAddPlayerRequest(this);
        });
        removePlayerButton.setOnAction(e -> {
            playerController.handleRemovePlayerRequest(this);
        });
        
        //THE PLAYERS TABLE
        playersTable.setOnMouseClicked(e -> {
            if(e.getClickCount() == 2) {
                //OPEN UP THE PLAYER EDITOR
                Player p = playersTable.getSelectionModel().getSelectedItem();
                playerController.handleEditPlayerRequest(this, p);
            }
        });
        registerToggleGroup(playerPositionsGroup);
        registerSearchBarController(playerSearchBar);
        
        //NOW THE FANTASY TEAMS SCREEN
        //TEXT FIELDS WORK DIFFERENTLY
        teamsController = new TeamsController(primaryStage, dataManager.getDraft(), messageDialog);
        registerTextFieldController(draftNameTextField);
        addTeamButton.setOnAction(e -> {
            teamsController.handleAddTeamRequest(this);
        });
        
        removeTeamButton.setOnAction(e -> {
            teamsController.handleRemoveTeamRequest(this, selectTeamComboBox.getSelectionModel().getSelectedItem());
        });
        editTeamButton.setOnAction(e -> {
            teamsController.handleEditTeamRequest(this, selectTeamComboBox.getSelectionModel().getSelectedItem());    
        });
        selectTeamComboBox.setOnAction(e -> {
            teamsController.handleDraftChangeRequest(this);
        });
        
        
    }
    
    private void registerTextFieldController(TextField textField){
        textField.textProperty().addListener((observable, oldValue, newValue) ->{
            teamsController.handleDraftChangeRequest(this);
        });
    }
    private void registerToggleGroup(ToggleGroup group){
        group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            playerController.handlePositionSelectRequest(this, playersTable, newValue);
        });
    }
    private void registerSearchBarController(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            playerController.handlePlayerSearchRequest(this, playersTable, newValue);
        });
    }
    
    private Button initChildButton(Pane toolbar, WB_PropertyType icon, WB_PropertyType tooltip, boolean disabled) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String imagePath = "file:" + PATH_IMAGES + props.getProperty(icon.toString());
        Image buttonImage = new Image(imagePath);
        Button button = new Button();
        button.setDisable(disabled);
        button.setGraphic(new ImageView(buttonImage));
        Tooltip buttonTooltip = new Tooltip(props.getProperty(tooltip.toString()));
        button.setTooltip(buttonTooltip);
        toolbar.getChildren().add(button);
        return button;
    }
    
    private Label initLabel(WB_PropertyType labelProperty, String styleClass) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String labelText = props.getProperty(labelProperty);
        Label label = new Label(labelText);
        label.getStyleClass().add(styleClass);
        return label;
    }
    
    private Label initGridLabel(GridPane container, WB_PropertyType labelProperty, String styleClass, int col, int row, int colSpan, int rowSpan) {
        Label label = initLabel(labelProperty, styleClass);
        container.add(label, col, row, colSpan, rowSpan);
        return label;
    }
    
    // INIT A LABEL AND PUT IT IN A TOOLBAR
    private Label initChildLabel(Pane container, WB_PropertyType labelProperty, String styleClass) {
        Label label = initLabel(labelProperty, styleClass);
        container.getChildren().add(label);
        return label;
    }
    
    private ComboBox initGridComboBox(GridPane container, int col, int row, int colSpan, int rowSpan) throws IOException {
        ComboBox comboBox = new ComboBox();
        container.add(comboBox, col, row, colSpan, rowSpan);
        return comboBox;
    }
    
    private Button initGridButton(GridPane container, WB_PropertyType icon, WB_PropertyType tooltip, int col, int row, int colSpan, int rowSpan, boolean disabled) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String imagePath = "file:" + PATH_IMAGES + props.getProperty(icon.toString());
        Image buttonImage = new Image(imagePath);
        Button button = new Button();
        button.setDisable(disabled);
        button.setGraphic(new ImageView(buttonImage));
        Tooltip buttonTooltip = new Tooltip(props.getProperty(tooltip.toString()));
        button.setTooltip(buttonTooltip);
        container.add(button, col, row, colSpan, rowSpan);
        return button;
    }
    
    private RadioButton initGridRadioButton(GridPane container, String position, ToggleGroup group, int col, int row, int colSpan, int rowSpan) {
        RadioButton rButton = new RadioButton(position);
        rButton.setUserData(position);
        rButton.setToggleGroup(group);
        container.add(rButton, col, row, colSpan, rowSpan);
        return rButton;
    }
    // INIT A TEXT FIELD AND PUT IT IN A GridPane
    private TextField initGridTextField(GridPane container, int size, String initText, boolean editable, int col, int row, int colSpan, int rowSpan) {
        TextField tf = new TextField();
        tf.setPrefColumnCount(size);
        tf.setText(initText);
        tf.setEditable(editable);
        container.add(tf, col, row, colSpan, rowSpan);
        return tf;
    }
    
    
}
