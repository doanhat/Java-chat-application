package IHMMain;

import app.MainWindowController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ConnectionController implements Initializable{
    @FXML
    private Button chooseFileButton;
    @FXML
    private TextField chooseFileTextField;
    @FXML
    private Label loginLabel1;
    @FXML
    private Label passwordLabel1;

    private MainWindowController mainWindowController;

    public void setMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Mettez ici le code qui s'execute avant l'apparition de la vue
    }

    @FXML
    public void onSeConnecterButtonClick(){

        mainWindowController.loadIHMMainWindow();
    }

    @FXML
    public void onSinscrireButtonClick() {

        mainWindowController.loadIHMMainWindow();
    }

    @FXML
    public void onChooseFileButtonClick(ActionEvent actionEvent) {
        Stage thisStage = (Stage) chooseFileButton.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg", "*.png")
        );
        File selectedFile = fileChooser.showOpenDialog(thisStage);
        chooseFileTextField.setText(selectedFile.getName());
        System.out.println(selectedFile);
    }
}
