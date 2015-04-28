/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wb.gui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import wb.data.Player;

/**
 *
 * @author George
 */
public class PlayerDialog extends Stage {
    
    Player player;
    
    GridPane addPane;
    Scene addDialogScene;
    Label detailsLabel;
    Label firstNameLabel;
    TextField firstNameTextField;
    Label lastNameLabel;
    TextField lastNameTextField;
    Label proTeamLabel;
    ComboBox proTeamsComboBox;
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
    Image playerFlagImage;
    Label playerName;
    Label playerPosition;
    Label playerTeam;
    ComboBox fantasyTeams;
    Label positionLabel;
    ComboBox positions;
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
    
    public PlayerDialog(Stage primaryStage) {
        //MAKE A NEW PLAYER OBJECT
        player = new Player();
        
        //MAKE DIALOG MODAL
        initModality(Modality.WINDOW_MODAL);
        initOwner(primaryStage);
        
        //WILL COMPLETE LATER 
    }
}
