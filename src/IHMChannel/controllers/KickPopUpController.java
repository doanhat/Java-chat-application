package IHMChannel.controllers;

import common.shared_data.Channel;
import common.shared_data.UserLite;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    DatePicker datePick
            ;
    @FXML
    Button confirmBtn;
    @FXML
    Button cancelBtn;

    //"Voulez vous kicker cet utilisateur ?"

    /**
     * Appelée automatiquement par le FXML Loader
     * C'est la méthode dans laquelle on initialise les affichages.
     */
    public void initialize() {
    }

    public String getExplanationMessage(){
        return explanationText.getText();
    }

    public LocalDate datePick(){
        return datePick.getValue();
    }

    public Button getCancelBtn() { return cancelBtn;    }

    public Button getConfirmBtn() {
        return confirmBtn;
    }

    public void setText(UserLite userToKick, Channel channel){
        informationText.setText("Voulez vous kicker "+userToKick.getNickName()+" du channel "+channel.getName()+" ? \n");
    }
}
