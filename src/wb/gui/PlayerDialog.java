/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wb.gui;

import java.util.ArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import static wb.WB_StartupConstants.PATH_FLAGS;
import static wb.WB_StartupConstants.PATH_PLAYERS;
import wb.data.Draft;
import wb.data.MLBTeam;
import wb.data.Player;
import wb.data.Team;
import static wb.gui.WB_GUI.CLASS_HEADING_LABEL;
import static wb.gui.WB_GUI.CLASS_PROMPT_LABEL;
import static wb.gui.WB_GUI.CLASS_SUBHEADING_LABEL;
import static wb.gui.WB_GUI.PRIMARY_STYLE_SHEET;

/**
 *
 * @author George
 */
public class PlayerDialog extends Stage {
    
    Player player;
    Draft draft;
    
    GridPane addPane;
    Scene addDialogScene;
    Label headingLabel;
    Label firstNameLabel;
    TextField firstNameTextField;
    Label lastNameLabel;
    TextField lastNameTextField;
    Label proTeamLabel;
    ComboBox proTeamsComboBox;
    ArrayList<CheckBox> positionList;
    CheckBox catcherCheckBox;
    CheckBox pitcherCheckBox;
    CheckBox firstBaseCheckBox;
    CheckBox secondBaseCheckBox;
    CheckBox thirdBaseCheckBox;
    CheckBox shortstopCheckBox;
    CheckBox outfielderCheckBox;
    Button completeButton;
    Button cancelButton;
    
    GridPane editPane;
    Scene editDialogScene;
    Image playerPortraitImage;
    ImageView playerPicture;
    Image playerFlagImage;
    ImageView flagPicture;
    Label playerName;
    Label playerPosition;
    Label playerTeam;
    ComboBox<Team> fantasyTeams;
    Label positionLabel;
    ComboBox<String> positions;
    Label contractLabel;
    ComboBox contracts;
    
    //KEEPS TRACK WHICH BUTTON USER PRESSED
    String selection;
    
    //UI CONSTANTS
    public static final String PLAYER_HEADING = "Player Details";
    //FIRST, FOR THE ADD PLAYER DIALOG
    public static final String COMPLETE = "Complete";
    public static final String CANCEL = "Cancel";
    public static final String FIRST_NAME_PROMPT = "First Name: ";
    public static final String LAST_NAME_PROMPT = "Last Name: ";
    public static final String PRO_TEAM_PROMPT = "Pro Yeam: ";
    public static final String ADD_PLAYER_TITLE = "Add New Player";
    
    //NOW FOR THE EDIT PLAYER DIALOG
    public static final String FANTASY_TEAM_PROMPT = "Fantasy Team: ";
    public static final String POSITION_PROMPT = "Position: ";
    public static final String CONTRACT_PROMPT = "Contract: ";
    public static final String EDIT_PLAYER_TITLE = "Edit Player";
    public static final String TEMP_PLAYER_PICTURE = "file:" + PATH_PLAYERS + "AAA_PhotoMissing.jpg";
    public static final String TEMP_FLAG_PICTURE = "file:" + PATH_FLAGS + "USA.png";
    
    public PlayerDialog(Stage primaryStage, Draft draft) {
        //MAKE A NEW PLAYER OBJECT
        player = new Player();
        
        //INITIALIZE THE DRAFT OBJECT
        this.draft = draft;
        
        //MAKE DIALOG MODAL
        initModality(Modality.WINDOW_MODAL);
        initOwner(primaryStage);
        
       addPane = new GridPane();
       addPane.setPadding(new Insets(10, 20, 20, 20));
       addPane.setHgap(10);
       addPane.setVgap(10);
       
       headingLabel = new Label(PLAYER_HEADING);
       headingLabel.getStyleClass().add(CLASS_HEADING_LABEL);
       
       //FIRST NAME
       firstNameLabel = new Label(FIRST_NAME_PROMPT);
       firstNameLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
       firstNameTextField = new TextField();
       firstNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
           player.setFirstName(newValue);
       });
       
       //LAST NAME
       lastNameLabel = new Label(LAST_NAME_PROMPT);
       lastNameLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
       lastNameTextField = new TextField();
       lastNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
           player.setLastName(newValue);
       });
       
       //PRO TEAM
       proTeamLabel = new Label(PRO_TEAM_PROMPT);
       proTeamLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
       proTeamsComboBox = new ComboBox();
       loadProTeamsComboBox(draft.getMLBTeams());
       proTeamsComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
          @Override
          public void changed(ObservableValue observable, Object oldValue, Object newValue) {
              player.setMLBTeam(newValue.toString());
          }
       });
       positionList = new ArrayList<CheckBox>();
       //NOW ALL THE CHECKBOXES
       //CATCHER
       catcherCheckBox = new CheckBox("C");
       catcherCheckBox.setUserData("C");
       positionList.add(catcherCheckBox);
       //PITCHER
       pitcherCheckBox = new CheckBox("P");
       pitcherCheckBox.setUserData("P");
       positionList.add(pitcherCheckBox);
       //FIRSTBASE
       firstBaseCheckBox = new CheckBox("1B");
       firstBaseCheckBox.setUserData("1B");
       positionList.add(firstBaseCheckBox);
       //SECONDBASE
       secondBaseCheckBox = new CheckBox("2B");
       secondBaseCheckBox.setUserData("2B");
       positionList.add(secondBaseCheckBox);
       //THIRDBASE
       thirdBaseCheckBox = new CheckBox("3B");
       thirdBaseCheckBox.setUserData("3B");
       positionList.add(thirdBaseCheckBox);
       //SHORTSTOP
       shortstopCheckBox = new CheckBox("SS");
       shortstopCheckBox.setUserData("SS");
       positionList.add(shortstopCheckBox);
       //OUTFIELDER
       outfielderCheckBox = new CheckBox("OF");
       outfielderCheckBox.setUserData("OF");
       positionList.add(outfielderCheckBox);
       //THE BUTTONS
       completeButton = new Button(COMPLETE);
       cancelButton = new Button(CANCEL);
       
       EventHandler completeCancelHandler = (EventHandler<ActionEvent>) (ActionEvent ae) -> {
           Button sourceButton = (Button) ae.getSource();
           PlayerDialog.this.selection = sourceButton.getText();
           PlayerDialog.this.hide();
       };
       completeButton.setOnAction(completeCancelHandler);
       cancelButton.setOnAction(completeCancelHandler);
       
       //ADD EVERYTHING TO THE ADD GRIDPANE
       addPane.add(headingLabel, 0, 0, 2, 1);
       addPane.add(firstNameLabel, 0, 1, 1, 1);
       addPane.add(firstNameTextField, 1, 1, 1, 1);
       addPane.add(lastNameLabel, 0, 2, 1, 1);
       addPane.add(lastNameTextField, 1, 2, 1, 1);
       addPane.add(proTeamLabel, 0, 3, 1, 1);
       addPane.add(proTeamsComboBox, 1, 3, 1, 1);
       int i = 0;
       for(CheckBox c: positionList){
        addPane.add(c , i , 4, 1, 1);
        i++;
       }
       addDialogScene = new Scene(addPane);
       addDialogScene.getStylesheets().add(PRIMARY_STYLE_SHEET);
       this.setScene(addDialogScene);
       
       //NOW FOR THE EDIT PANE
       editPane = new GridPane();
       editPane.setPadding(new Insets(10, 20, 20, 20));
       editPane.setHgap(10);
       editPane.setVgap(10);
       
       playerPortraitImage = new Image(TEMP_PLAYER_PICTURE);
       playerPicture = new ImageView(playerPortraitImage);
       playerFlagImage = new Image(TEMP_FLAG_PICTURE);
       flagPicture = new ImageView(playerFlagImage);
       playerName = new Label();
       playerName.getStyleClass().add(CLASS_SUBHEADING_LABEL);
       playerPosition = new Label();
       playerPosition.getStyleClass().add(CLASS_SUBHEADING_LABEL);
       playerTeam = new Label(FANTASY_TEAM_PROMPT);
       playerTeam.getStyleClass().add(CLASS_PROMPT_LABEL);
       fantasyTeams = new ComboBox();
       fantasyTeams.setCellFactory(new Callback<ListView<Team>, ListCell<Team>>() {
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
        
        fantasyTeams.setButtonCell(new ListCell<Team>(){
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
       ObservableList<Team> teams = draft.getTeams();
       //ADD A FREE AGENCY TEAM
       Team freeAgency = new Team();
       teams.add(freeAgency);
       fantasyTeams.setItems(teams);
       fantasyTeams.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
           @Override
           public void changed(ObservableValue observable, Object oldValue, Object newValue) {
               
           }
       });
       positionLabel = new Label(POSITION_PROMPT);
       positionLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
       positions = new ComboBox();
       positions.getItems().addAll("C", "P", "1B", "2B", "3B", "SS", "OF");
       positions.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
           @Override
           public void changed(ObservableValue observable, Object oldValue, Object newValue) {
               
           }
       });
       contractLabel = new Label(CONTRACT_PROMPT);
       contractLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
       contracts = new ComboBox();
       contracts.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
           @Override
           public void changed(ObservableValue observable, Object oldValue, Object newValue) {
               
           }
       });
       editPane.add(headingLabel, 0, 0, 2, 1);
       editPane.add(playerPicture, 0, 1, 1, 4);
       editPane.add(flagPicture, 1, 1, 1, 1);
       editPane.add(playerName, 1, 2, 1, 1);
       editPane.add(playerPosition, 1, 4, 1, 1);
       editPane.add(playerTeam, 0, 6, 1, 1);
       editPane.add(fantasyTeams, 1, 6, 1, 1);
       editPane.add(positionLabel, 0, 7, 1, 1);
       editPane.add(positions, 1, 7, 1, 1);
       editPane.add(contractLabel, 0, 8, 1, 1);
       editPane.add(contracts, 1, 8, 1, 1);
       editPane.add(completeButton, 1, 9, 1, 1);
       editPane.add(cancelButton, 2, 9, 1, 1);
      
       editDialogScene = new Scene(editPane);
       editDialogScene.getStylesheets().add(PRIMARY_STYLE_SHEET);
       
    }
    
    public String getSelection() {
        return selection;
    }
    
    public Player getPlayer() {
        return player;
    }
    
    public Player showAddPlayerDialog() {
        setTitle(ADD_PLAYER_TITLE);
        //MAKE SURE THE SCENE IS CORRECT
        this.setScene(addDialogScene);
        player = new Player();
        
        firstNameTextField.setText(player.getFirstName());
        lastNameTextField.setText(player.getLastName());
        proTeamsComboBox.getSelectionModel().select(0);
        
        this.showAndWait();
        
        return player;
    }
    
    public void loadGUIData() {
        playerName.setText(player.getFirstName() + " " + player.getLastName());
        playerPosition.setText(player.getPosition());
        
         //ADD THE IMAGES TO THE EDIT PANE
        playerPortraitImage = new Image("file:" + PATH_PLAYERS + player.getImageFilePath());
        playerPicture.setImage(playerPortraitImage);
        playerFlagImage = new Image("file:" + PATH_FLAGS + player.getNationOfBirth() + ".png");
        flagPicture.setImage(playerFlagImage);
        
        String[] playerPositions = player.getPosition().split("_");
        positions.getSelectionModel().select(playerPositions[0]);
    }
    
    public boolean wasCompleteSelected() {
        return selection.equals(COMPLETE);
    }
    
    public Team getSelectedTeam(){
        return fantasyTeams.getSelectionModel().getSelectedItem();
    }
    
    public String getSelectedPosition(){
        return positions.getSelectionModel().getSelectedItem();
    }
    
    public void showEditLectureDialog(Player playerToEdit){
        setTitle(EDIT_PLAYER_TITLE);
        
        
        
        //LOAD THE PLAYER INTO OUR LOCAL OBJECT
        player.setFirstName(playerToEdit.getFirstName());
        player.setLastName(playerToEdit.getLastName());
        player.setYearOfBirth(playerToEdit.getYearOfBirth());
        player.setPosition(playerToEdit.getPosition());
        player.setMLBTeam(playerToEdit.getMLBTeam());
        player.setIPAB(playerToEdit.getIPAB());
        player.setERR(playerToEdit.getERR());
        player.setWH(playerToEdit.getWH());
        player.setSVHR(playerToEdit.getSVHR());
        player.setHRBI(playerToEdit.getHRBI());
        player.setBBSB(playerToEdit.getBBSB());
        player.setK(playerToEdit.getK());
        player.setNationOfBirth(playerToEdit.getNationOfBirth());
        player.setNotes(playerToEdit.getNotes());
        
        
        //NOW LOAD THE DATA INTO THE GUI
        loadGUIData();
        this.setScene(editDialogScene);
        
        this.showAndWait();
    }
    private void loadProTeamsComboBox(ArrayList<MLBTeam> mlbTeams){
        for(MLBTeam m: mlbTeams){
            proTeamsComboBox.getItems().add(m.getName());
        }
    }
    
}
    
    
