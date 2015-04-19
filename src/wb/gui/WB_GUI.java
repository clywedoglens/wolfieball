/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wb.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;
import wb.WB_PropertyType;
import static wb.WB_StartupConstants.CLOSE_BUTTON_LABEL;
import static wb.WB_StartupConstants.PATH_CSS;
import static wb.WB_StartupConstants.PATH_IMAGES;
import wb.controller.FileController;
import wb.controller.PlayerController;
import wb.controller.ScreenController;
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
    
    static final String PRIMARY_STYLE_SHEET = PATH_CSS + "wb_style.css";
    static final String CLASS_BORDERED_PANE = "bordered_pane";
    static final String CLASS_SUBJECT_PANE = "subject_pane";
    static final String CLASS_HEADING_LABEL = "heading_label";
    static final String CLASS_SUBHEADING_LABEL = "subheading_label";
    static final String CLASS_PROMPT_LABEL = "prompt_label";
    static final String EMPTY_TEXT = "";
    static final int LARGE_TEXT_FIELD_LENGTH = 20;
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
    
    //THIS HANDLES THE BOTTOM TOOLBAR TO SWITCH SCREENS
    ScreenController screenController;
    
    //APPLICATION WINDOW
    Stage primaryStage;
    
    //OUR MAIN WINDOW
    Scene primaryScene;
    
    //PANE THAT ORGANIZES THE BIG PICTURE CONTAINERS
    BorderPane wbPane;
    
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
    
    HashMap<String, Pane> workspacePanes;
    
    //THIS WILL BE THE WORKSPACE FOR THE PLAYERS SCREEN
    //WHICH SHOULD CONTAIN A GRID PANE WITH A LABEL, RADIO BUTTON CONTROLS,
    //SEARCH LABEL, SEARCH BAR AND A TABLE
    VBox playersTopPane;
    BorderPane playersWorkspacePane;
    SplitPane playersWorkspaceSplitPane;
    GridPane playerSearchPane;
    GridPane positionSelectionPane;
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
    VBox teamsTopPane;
    Label fantasyTeamsLabel;
    BorderPane teamsWorkspacePane;
    
    
    //FANTASY STANDINGS SCREEN WORKSPACE
    VBox standingsTopPane;
    Label fantasyStandingsLabel;
    BorderPane standingsWorkspacePane;
    
    //DRAFT SUMMARY SCREEN WORKSPACE
    VBox draftTopPane;
    Label draftSummaryLabel;
    BorderPane draftWorkspacesPane;
    
    
    //MLB TEAMS SCREEN WORKSPACE
    VBox mlbTopPane;
    Label mlbTeamsLabel;
    BorderPane mlbWorkspacePane;
    
    //PLAYER POSITIONS
    static final String ALL = "All";
    static final String CATCHER = "C";
    static final String FIRST_BASE = "1B";
    static final String CI = "CI";
    static final String SECOND_BASE = "2B";
    static final String THIRD_BASE = "3B";
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
            wbPane.setCenter(teamsWorkspacePane);
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
    }
    
    public void updateDraftInfo(Draft draft) {
        
    }
    
    /****************************************************************************/
    /* BELOW ARE ALL THE PRIVATE HELPER METHODS WE USE FOR INITIALIZING OUR GUI */
    /****************************************************************************/
    
    private void initDialogs() {
        messageDialog = new MessageDialog(primaryStage, CLOSE_BUTTON_LABEL);
        yesNoCancelDialog = new YesNoCancelDialog(primaryStage);
    }
    
    private void initToolbars() {
        fileToolbarPane = new FlowPane();
        
        newDraftButton = initChildButton(fileToolbarPane, WB_PropertyType.NEW_DRAFT_ICON, WB_PropertyType.NEW_DRAFT_TOOLTIP, false);
        loadDraftButton = initChildButton(fileToolbarPane, WB_PropertyType.LOAD_DRAFT_ICON, WB_PropertyType.LOAD_DRAFT_TOOLTIP, false);
        saveDraftButton = initChildButton(fileToolbarPane, WB_PropertyType.SAVE_DRAFT_ICON, WB_PropertyType.SAVE_DRAFT_TOOLTIP, true);
        exportDraftButton = initChildButton(fileToolbarPane, WB_PropertyType.EXPORT_DRAFT_ICON, WB_PropertyType.EXPORT_DRAFT_TOOLTIP, true);
        exitButton = initChildButton(fileToolbarPane, WB_PropertyType.EXIT_ICON, WB_PropertyType.EXIT_TOOLTIP, true);
        
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
        
        workspacePanes = new HashMap<String, Pane>();
        workspacePanes.put(TEAMS_SCREEN, teamsWorkspacePane);
        workspacePanes.put(PLAYER_SCREEN, playersWorkspacePane);
        workspacePanes.put(STANDINGS_SCREEN, standingsWorkspacePane);
        workspacePanes.put(MLB_SCREEN, mlbWorkspacePane);
        
        initTopWorkspaces();
        
        initPlayersScreenControls();
        
        //initFantasyTeamsScreenControls();
        
        //initStandingsScreenControls();
        
        //initDraftScreenControls();
        
        //initMLBTeamsScreenControls();
        
        
    }
    
    private void initTopWorkspaces(){
        
        playersWorkspaceSplitPane = new SplitPane();
        playersWorkspaceSplitPane.getItems().add(playerSearchPane);
        playersWorkspaceSplitPane.getItems().add(positionSelectionPane);
        
     
        playersTopPane = new VBox();
        playersTopPane.getStyleClass().add(CLASS_BORDERED_PANE);
        
        playersHeadingLabel = initChildLabel(playersTopPane, WB_PropertyType.PLAYER_AVAILABLE_LABEL, CLASS_HEADING_LABEL);
        
        playersTopPane.getChildren().add(playersWorkspaceSplitPane);
        
        teamsTopPane = new VBox();
        teamsTopPane.getStyleClass().add(CLASS_BORDERED_PANE);
        
        fantasyTeamsLabel = initChildLabel(teamsTopPane, WB_PropertyType.FANTASY_TEAMS_LABEL, CLASS_HEADING_LABEL);
        
        standingsTopPane = new VBox();
        standingsTopPane.getStyleClass().add(CLASS_BORDERED_PANE);
        
        fantasyStandingsLabel = initChildLabel(standingsTopPane, WB_PropertyType.FANTASY_STANDINGS_LABEL, CLASS_HEADING_LABEL);
        
        draftTopPane = new VBox();
        draftTopPane.getStyleClass().add(CLASS_BORDERED_PANE);
        
        draftSummaryLabel = initChildLabel(draftTopPane, WB_PropertyType.DRAFT_SUMMARY_LABEL, CLASS_HEADING_LABEL);
        
        mlbTopPane = new VBox();
        mlbTopPane.getStyleClass().add(CLASS_BORDERED_PANE);
        
        mlbTeamsLabel = initChildLabel(mlbTopPane, WB_PropertyType.MLB_TEAMS_LABEL, CLASS_HEADING_LABEL);
     
    }
    private void initPlayersScreenControls() {
       playerSearchPane = new GridPane();
        
       addPlayerButton = initGridButton(playerSearchPane, WB_PropertyType.ADD_PLAYER_ICON, WB_PropertyType.ADD_PLAYER_TOOLTIP, 0, 0, 1, 1, false);
       removePlayerButton = initGridButton(playerSearchPane, WB_PropertyType.REMOVE_PLAYER_ICON, WB_PropertyType.REMOVE_PLAYER_TOOLTIP, 1, 0, 1, 1, false);
       playerSearchLabel = initGridLabel(playerSearchPane, WB_PropertyType.PLAYER_SEARCH_LABEL, CLASS_SUBHEADING_LABEL, 0, 1, 1, 1);
       playerSearchBar = initGridTextField(playerSearchPane, SMALL_TEXT_FIELD_LENGTH, EMPTY_TEXT, true, 1, 1, 1, 1);
       
       positionSelectionPane = new GridPane();
       playerPositionsGroup = new ToggleGroup();
       allButton = initGridRadioButton(positionSelectionPane, ALL, playerPositionsGroup, 0, 0, 1, 1);//new RadioButton("All");
       cButton = initGridRadioButton(positionSelectionPane, CATCHER, playerPositionsGroup, 1, 0, 1, 1);//new RadioButton("C");
       firstBaseButton = initGridRadioButton(positionSelectionPane, FIRST_BASE, playerPositionsGroup, 2, 0, 1, 1); //new RadioButton("1B");
       ciButton = initGridRadioButton(positionSelectionPane, CI, playerPositionsGroup, 3, 0, 1, 1);//new RadioButton("CI");
       secondBaseButton = initGridRadioButton(positionSelectionPane, SECOND_BASE, playerPositionsGroup, 4, 0, 1, 1); //new RadioButton("2B");
       thirdBaseButton = initGridRadioButton(positionSelectionPane, THIRD_BASE, playerPositionsGroup, 5, 0, 1, 1); //new RadioButton("3B");
       miButton = initGridRadioButton(positionSelectionPane, MI, playerPositionsGroup, 6, 0, 1, 1);//new RadioButton("MI");
       ssButton = initGridRadioButton(positionSelectionPane, SHORTSTOP, playerPositionsGroup, 0, 1, 1, 1);//new RadioButton("SS");
       ofButton = initGridRadioButton(positionSelectionPane, OUTFIELDER, playerPositionsGroup, 1, 1, 1, 1);//new RadioButton("OF");
       uButton = initGridRadioButton(positionSelectionPane, U, playerPositionsGroup, 1, 2, 1, 1);//new RadioButton("U");
       pButton = initGridRadioButton(positionSelectionPane, PITCHER, playerPositionsGroup,1, 3, 1, 1);//new RadioButton("P");
       
      
       
       
       
    }
    
    private void changeScreen(String screen) {
        
        BorderPane newPane = (BorderPane) workspacePanes.get(screen);
        primaryStage.setScene(new Scene(newPane));
        
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
        teamsStandingsButton.setOnAction(null);
        draftSummaryButton.setOnAction(null);
        mlbTeamsButton.setOnAction(null);
        //NOW THE PLAYERS SCREEN
        playerController = new PlayerController();
        addPlayerButton.setOnAction(e -> {
            playerController.handleAddPlayerRequest(this);
        });
        removePlayerButton.setOnAction(e -> {
            playerController.handleRemovePlayerRequest(this);
        });
        
        registerSearchBarController(playerSearchBar);
    }
    
    private void registerSearchBarController(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            playerController.handlePlayerSearchRequest(this, newValue);
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
