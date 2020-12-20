package IHMChannel.controllers;

import common.shared_data.Channel;
import common.shared_data.UserLite;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.time.LocalDate;


public class KickPopUpController {

    @FXML
    Text informationText;

    @FXML
    TextField explanationText;

    @FXML
    DatePicker datePick;

    @FXML
    Boolean isPermanent;

    @FXML
    Button confirmBtn;

    @FXML
    Button cancelBtn;

    /**
     * Appelée automatiquement par le FXML Loader
     * C'est la méthode dans laquelle on initialise les affichages.
     */
    public void initialize() {
        isPermanent = false;
    }

    public  String getExplanationMessage(){
        return explanationText.getText();
    }

    public  LocalDate getDatePick(){
        return datePick.getValue();
    }

    public  Boolean getIsPermanent() { return isPermanent;}

    public  Button getCancelBtn() { return cancelBtn;    }

    public  Button getConfirmBtn() {        return confirmBtn;
    }

    public void setPopupText(String userToKick, String channelName){
        informationText.setText("Voulez vous kicker "+userToKick+" du channel "+channelName+" ? \n");
    }

    public void permanentBoxHandler() {
        if(isPermanent) {
            isPermanent=false;
            datePick.setDisable(false);
        }else{
            isPermanent=true;
            datePick.setDisable(true);
        }
    }

}
