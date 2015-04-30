/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wb.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import wb.data.Draft;
import wb.data.Team;
import static wb.gui.WB_GUI.CLASS_HEADING_LABEL;
import static wb.gui.WB_GUI.CLASS_PROMPT_LABEL;
import static wb.gui.WB_GUI.PRIMARY_STYLE_SHEET;

/**
 *
 * @author George
 */
public class FantasyTeamDialog extends Stage{
    Team fantasyTeam;
    
    GridPane gridPane;
    Scene dialogScene;
    Label headingLabel;
    Label nameLabel;
    TextField nameTextField;
    Label ownerLabel;
    TextField ownerTextField;
    Button completeButton;
    Button cancelButton;
    
    //KEEPS TRACK WHICH BUTTON THE USER PRESSED
    String selection;
    
    //CONSTANTS
    public static final String COMPLETE = "Complete";
    public static final String CANCEL = "Cancel";
    public static final String NAME_PROMPT = "Name:";
    public static final String OWNER_PROMPT = "Owner:";
    public static final String FANTASY_TEAM_HEADING = "Fantasy Team Details";
    public static final String ADD_FANTASY_TEAM_TITLE = "Add New Fantasy Team";
    public static final String EDIT_FANTASY_TEAM_TITLE = "Edit Fantasy Team";
    
    public FantasyTeamDialog(Stage primaryStage, Draft draft, MessageDialog messageDialog) {
       //MAKE DIALOG MODAL
        initModality(Modality.WINDOW_MODAL);
        initOwner(primaryStage);
        
        //NOW CONTAINER
        gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 20, 20, 20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        
        headingLabel = new Label(FANTASY_TEAM_HEADING);
        headingLabel.getStyleClass().add(CLASS_HEADING_LABEL);
        
        //TEAM NAME
        nameLabel = new Label(NAME_PROMPT);
        nameLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        nameTextField = new TextField();
        nameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            fantasyTeam.setName(newValue);
        });
        
        //NOW THE OWNER NAME
        ownerLabel = new Label(OWNER_PROMPT);
        ownerLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        ownerTextField = new TextField();
        ownerTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            fantasyTeam.setOwnerName(newValue);
        });
        
        //FINALLY THE BUTTONS
        completeButton = new Button(COMPLETE);
        cancelButton = new Button(CANCEL);
        
        // REGISTER EVENT HANDLERS FOR OUR BUTTONS
        EventHandler completeCancelHandler = (EventHandler<ActionEvent>) (ActionEvent ae) -> {
            Button sourceButton = (Button)ae.getSource();
            FantasyTeamDialog.this.selection = sourceButton.getText();
            FantasyTeamDialog.this.hide();
        };
        completeButton.setOnAction(completeCancelHandler);
        cancelButton.setOnAction(completeCancelHandler);
        
        //ARRANGE EVERYTHING INSIDE THE PANE
        gridPane.add(headingLabel, 0, 0, 2, 1);
        gridPane.add(nameLabel, 0, 1, 1, 1);
        gridPane.add(nameTextField, 1, 1, 1, 1);
        gridPane.add(ownerLabel, 0, 2, 1, 1);
        gridPane.add(ownerTextField, 1, 2, 1, 1);
        gridPane.add(completeButton, 0, 3, 1, 1);
        gridPane.add(cancelButton, 1, 3, 1, 1);
        
        //PUT THE GRID PANE IN THE WINDOW
        dialogScene = new Scene(gridPane);
        dialogScene.getStylesheets().add(PRIMARY_STYLE_SHEET);
        this.setScene(dialogScene);
    }
    
    public String getSelection() {
        return selection;
    }
    
    public Team getFantasyTeam() {
        return fantasyTeam;
    }
            
    public Team showAddFantasyTeamDialog(){
        setTitle(ADD_FANTASY_TEAM_TITLE);
        
        //RESET THE TEAM OBJECT
        fantasyTeam = new Team();
        
        //LOAD THE UI 
        nameTextField.setText(fantasyTeam.getName());
        ownerTextField.setText(fantasyTeam.getOwnerName());
        
        //OPEN THE DIALOG
        this.showAndWait();
        
        return fantasyTeam;
    }
    public void loadGUIData() {
        nameTextField.setText(fantasyTeam.getName());
        ownerTextField.setText(fantasyTeam.getOwnerName());
        
    }
    
    public boolean wasCompleteSelected() {
        return selection.equals(COMPLETE);
    }
    public void showEditFantasyTeamDialog(Team teamToEdit){
        //TITLE
        setTitle(EDIT_FANTASY_TEAM_TITLE);
        
        //LOAD THE ITEM INTO OUR LOCAL TEAM OBJECT
        fantasyTeam = new Team();
        fantasyTeam.setName(teamToEdit.getName());
        fantasyTeam.setOwnerName(teamToEdit.getOwnerName());
        
        //LOAD GUI
        loadGUIData();
        
        //OPEN IT
        this.showAndWait();
        
    }
    
    
}
