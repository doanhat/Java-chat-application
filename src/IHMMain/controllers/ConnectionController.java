package IHMMain.controllers;

import IHMMain.IHMMainController;
import app.MainWindowController;
import common.shared_data.ConnectionStatus;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.net.URL;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class ConnectionController implements Initializable{
    static final String ALERT = "Alerte";
    static final String IMPOSSIBLE_CONNECTION = "Connexion impossible";

    @FXML
    private Button chooseFileButton;
    @FXML
    private TextField chooseFileTextField;
    @FXML
    private TextField userConnectionIDTextField;
    @FXML
    private PasswordField userConnectionPasswordPasswordField;
    @FXML
    private TextField userConnectionPasswordTextField;
    @FXML
    private TextField userSubscriptionPasswordTextField;
    @FXML
    private TextField userSubscriptionIDTextField;
    @FXML
    private PasswordField userSubscriptionPasswordPasswordField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField surnameTextField;
    @FXML
    private DatePicker birthDatePicker;
    @FXML
    private Button showSubscriptionPasswordButton;
    @FXML
    private Button showConnectionPasswordButton;

    // Variables pour gérer l'affichage du mot de passe (TextField ou PasswordField)
    private boolean isPasswordFieldSubscription = true;
    private boolean isPasswordFieldConnection = true;

    private MainWindowController mainWindowController;

    public void setMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }

    private IHMMainController ihmMainController;
    public void setIhmMainController(IHMMainController ihmMainController) {
        this.ihmMainController = ihmMainController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Gestion de l'affichage du bouton d'affichage/masquage du mot de passe lors de l'inscription
        ImageView eyeButtonSubscription = new ImageView("IHMMain/icons/eye.png");
        eyeButtonSubscription.setFitHeight(18);
        eyeButtonSubscription.setFitWidth(18);
        showSubscriptionPasswordButton.setGraphic(eyeButtonSubscription);

        // Gestion de l'affichage du bouton d'affichage/masquage du mot de passe lors de la connexion
        ImageView eyeButtonConnection = new ImageView("IHMMain/icons/eye.png");
        eyeButtonConnection.setFitHeight(18);
        eyeButtonConnection.setFitWidth(18);
        showConnectionPasswordButton.setGraphic(eyeButtonConnection);

        // Gestion de l'affichage du bouton d'import d'un fichier pour la photo de profil
        ImageView uploadFileButton = new ImageView("IHMMain/icons/uploadFile.png");
        uploadFileButton.setFitHeight(18);
        uploadFileButton.setFitWidth(18);
        chooseFileButton.setGraphic(uploadFileButton);

        // Set event when Enter key is press on connection password field, onSeConnecterButtonClick is call
        userConnectionPasswordPasswordField.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                onSeConnecterButtonClick();
            }
        });
    }

    @FXML
    public void onSeConnecterButtonClick(){
        StringBuilder errors = new StringBuilder();
        String password = isPasswordFieldConnection ? userConnectionPasswordPasswordField.getText().trim() : userConnectionPasswordTextField.getText().trim();
        if (userConnectionIDTextField.getText().trim().isEmpty()) {
            errors.append("Veuillez saisir un identifiant\n");
        }
        if (password.isEmpty()) {
            errors.append("Veuillez saisir un mot de passe\n");
        }
        if (checkMandatoryFields(errors) && !ihmMainController.getIHMMainToData().localAuthentification(userConnectionIDTextField.getText().trim(), password)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle(ALERT);
                alert.setHeaderText(IMPOSSIBLE_CONNECTION);
                alert.setContentText("Identifiant ou mot de passe incorrect");
                alert.showAndWait();
        }
    }

    @FXML
    public void onSinscrireButtonClick() {
        StringBuilder errors = new StringBuilder();
        String password = isPasswordFieldSubscription ? userSubscriptionPasswordPasswordField.getText().trim() : userSubscriptionPasswordTextField.getText().trim();

        // Conversion de la date de naissance depuis un LocalDate vers un Date
        Date dateOfBirth = null;
        if (birthDatePicker.getValue() != null) {
            dateOfBirth = Date.from(birthDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        }

        if (userSubscriptionIDTextField.getText().trim().isEmpty()) {
            errors.append("Veuillez saisir un identifiant\n");
        }
        if (password.isEmpty()) {
            errors.append("Veuillez saisir un mot de passe\n");
        }
        if (checkMandatoryFields(errors)) {
            if (!ihmMainController.getIHMMainToData().createAccount(
                    userSubscriptionIDTextField.getText().trim(),
                    chooseFileTextField.getText(),
                    password,
                    surnameTextField.getText(),
                    nameTextField.getText(),
                    dateOfBirth)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle(ALERT);
                alert.setHeaderText("Inscription impossible");
                alert.showAndWait();
            } else {
                if (!ihmMainController.getIHMMainToData().localAuthentification(
                        userSubscriptionIDTextField.getText().trim(),
                        password)) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle(ALERT);
                    alert.setHeaderText(IMPOSSIBLE_CONNECTION);
                    alert.setContentText("Identifiant ou mot de passe incorrect");
                    alert.showAndWait();
                }
            }
        }
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
    }

    @FXML
    public void onShowHidePasswordSubscriptionButtonClick(ActionEvent actionEvent) {
        isPasswordFieldSubscription = toggleVisiblePassword(isPasswordFieldSubscription, userSubscriptionPasswordPasswordField, userSubscriptionPasswordTextField);
    }

    @FXML
    public void onShowHidePasswordConnectionButtonClick(ActionEvent actionEvent) {
        isPasswordFieldConnection = toggleVisiblePassword(isPasswordFieldConnection, userConnectionPasswordPasswordField, userConnectionPasswordTextField);
    }

    /**
     * Permet de passer d'un PasswordField à un TextField et vice-versa
     * @param isPasswordField   Le champ affiché actuellement est un PasswordField (true) ou un TextField (false)
     * @param passwordField     Id du PasswordField
     * @param textField         Id du TextField
     * @return Renvoie la nouvelle valeur de isPasswordField
     */
    private boolean toggleVisiblePassword(boolean isPasswordField, PasswordField passwordField, TextField textField) {
        if (isPasswordField) {
            textField.setText(passwordField.getText());
            passwordField.setVisible(false);
            textField.setVisible(true);
            return !isPasswordField;
        }
        passwordField.setText(textField.getText());
        passwordField.setVisible(true);
        textField.setVisible(false);
        return !isPasswordField;
    }

    /**
     * Vérifie si des champs obligatoires ne sont pas renseignés
     * @param   errors  StringBuilder contenant les messages à afficher si des erreurs ont été relevées
     * @return  Renvoie false si des champs obligatoires ne sont pas remplis, true sinon.
     */
    private boolean checkMandatoryFields(StringBuilder errors) {
        if (errors.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(ALERT);
            alert.setHeaderText("Champs obligatoires non renseignés !");
            alert.setContentText(errors.toString());
            alert.showAndWait();
            return false;
        }
        return true;
    }

    public void loadIHMMainWindow(ConnectionStatus status) {
        if (status != ConnectionStatus.CONNECTED) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(ALERT);
            alert.setHeaderText(IMPOSSIBLE_CONNECTION);
            alert.setContentText("Erreur de connexion au serveur");
            alert.showAndWait();
        } else {
            mainWindowController.loadIHMMainWindow();
        }
    }
}
