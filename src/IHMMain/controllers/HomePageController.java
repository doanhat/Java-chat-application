package IHMMain.controllers;

import IHMMain.IHMMainController;
import app.MainWindowController;
import common.shared_data.Channel;
import common.shared_data.User;
import common.shared_data.UserLite;
import common.shared_data.Visibility;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {

    private IHMMainController ihmMainController;

    private MainWindowController mainWindowController;

    private User connectedUser;

    @FXML
    private ListView<UserLite> connectedUsersListView;
    @FXML
    private TextField filteredInput;
    @FXML
    private CheckBox filtrerChannelCheckBox;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private DatePicker birthDateField;
    @FXML
    private ImageView avatarUser;
    @FXML
    private Button changeAvatarButton;
    @FXML
    private Label userLoginLabel;

    public void setIhmMainController(IHMMainController ihmMainController) {
        this.ihmMainController = ihmMainController;
    }

    public void setMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;

        // Gestion des champs de modification du profil
        connectedUser = ihmMainController.getIHMMainToData().getUser();
        // TODO fix this, due to change in the interface
//        String avatarPath = (ihmMainController.getIIHMMainToCommunication().getAvatarPath(connectedUser.getUserLite()) != null
//                ? ihmMainController.getIIHMMainToCommunication().getAvatarPath(connectedUser.getUserLite())
//                : "IHMMain/icons/willsmith.png");
        String avatarPath = "IHMMain/icons/willsmith.png";
        Image image = new Image(avatarPath);
        avatarUser.setImage(image);

        userLoginLabel.setText(connectedUser.getNickName());

        firstNameField.setPromptText(connectedUser.getFirstName());
        firstNameField.setText(connectedUser.getFirstName());
        lastNameField.setPromptText(connectedUser.getLastName());
        lastNameField.setText(connectedUser.getLastName());
        if (connectedUser.getBirthDate() != null) {
            birthDateField.setPromptText(connectedUser.getBirthDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)));
            birthDateField.setValue(connectedUser.getBirthDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        }

        ObservableList<UserLite> connectedUsersObservableList ;
        ObservableList<Channel> visibleChannelsObservableList ;

        connectedUsersObservableList= mainWindowController.getIhmMainController().getConnectedUsers();
        FilteredList<UserLite> filteredData = new FilteredList<>(connectedUsersObservableList, b-> true);
        visibleChannelsObservableList = mainWindowController.getIhmMainController().getVisibleChannels();
        FilteredList<Channel> filteredChannels = new FilteredList<>(visibleChannelsObservableList, b-> true);

        filteredInput.textProperty().addListener((observable,oldValue,newValue) -> {
            filteredData.setPredicate(userLite -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return (userLite.getNickName().toLowerCase().indexOf(lowerCaseFilter) != -1);
            });
            //Added for channel filter
            if(filtrerChannelCheckBox.isSelected()) {
                filteredChannels.setPredicate(channel -> {
                    if (newValue == null || newValue.isEmpty()) {
                        for(Channel c: mainWindowController.getIhmMainController().getVisibleChannels()){
                            mainWindowController.getIhmMainController().getIIHMMainToCommunication().getConnectedUsers(c.getId());
                        }
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    try {
                        mainWindowController.getIhmMainController().getIIHMMainToCommunication().getConnectedUsers(channel.getId());
                        for (UserLite userLite : mainWindowController.getIhmMainController().getConnectedUserByChannels().get(channel.getId())) {
                            if (userLite.getNickName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                                return true;
                            }
                        }
                    }catch(Exception e){
                        return false;
                    }
                    return false;
                });
                mainWindowController.getIHMMainWindowController().getPrivateChannels().setItems(filteredChannels.filtered(channel -> channel.getVisibility() == Visibility.PRIVATE));
                mainWindowController.getIHMMainWindowController().getPublicChannels().setItems(filteredChannels.filtered(channel -> channel.getVisibility() == Visibility.PUBLIC));
            }
        });
        connectedUsersListView.setItems(filteredData.sorted());

        //Reset la liste des channels si déselectionne la checkbox
        filtrerChannelCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(Boolean.FALSE.equals(newValue)) {
                mainWindowController.getIHMMainWindowController().getPrivateChannels().setItems(visibleChannelsObservableList.filtered(channel -> channel.getVisibility() == Visibility.PRIVATE));
                mainWindowController.getIHMMainWindowController().getPublicChannels().setItems(visibleChannelsObservableList.filtered(channel -> channel.getVisibility() == Visibility.PUBLIC));
                for(Channel c: mainWindowController.getIhmMainController().getVisibleChannels()){
                    mainWindowController.getIhmMainController().getIIHMMainToCommunication().getConnectedUsers(c.getId());
                }
                filteredInput.setText("");
            } else{
                filteredInput.setText("");
            }
        });
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Mettez ici le code qui s'execute avant l'apparition de la vue

        // Bouton de changement d'avatar
        ImageView changeAvatarImage = new ImageView("IHMMain/icons/exchange.png");
        changeAvatarImage.setFitHeight(18);
        changeAvatarImage.setFitWidth(18);
        changeAvatarButton.setGraphic(changeAvatarImage);
    }

    @FXML
    public void onSeDeconnecterButtonClick(){
        try {
            Stage primaryStage = mainWindowController.getPrimaryStage();
            Platform.setImplicitExit(false);
            primaryStage.setOnCloseRequest(event -> {});
            ihmMainController.getIIHMMainToCommunication().disconnect();
            ihmMainController.getIHMMainToData().disconnect();
            ihmMainController.getMainWindowController().loadConnectionWindow();
            ihmMainController.reset();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onEnregistrerButtonClick() {
        try {
            connectedUser = ihmMainController.getIHMMainToData().getUser();
            String firstName = (firstNameField.getText().trim().isEmpty()
                    ? connectedUser.getFirstName()
                    : firstNameField.getText().trim());
            String lastName = (lastNameField.getText().trim().isEmpty()
                    ? connectedUser.getLastName()
                    : lastNameField.getText().trim());
            Date birthDate = (birthDateField.getValue() == null
                    ? connectedUser.getBirthDate()
                    : Date.from(birthDateField.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            ihmMainController.getIHMMainToData().editProfile(
                    connectedUser.getNickName(),
                    connectedUser.getAvatar(),
                    connectedUser.getPassword(),
                    lastName,
                    firstName,
                    birthDate,
                    connectedUser);
            ihmMainController.getMainWindowController().getIHMMainWindowController().loadHomePage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onChangeAvatarButtonClick() {
        connectedUser = ihmMainController.getIHMMainToData().getUser();
        Stage thisStage = (Stage) changeAvatarButton.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg", "*.png")
        );
        File selectedFile = fileChooser.showOpenDialog(thisStage);
        if (selectedFile != null) {
            ihmMainController.getIHMMainToData().editProfile(
                    connectedUser.getNickName(),
                    selectedFile.getName(),
                    connectedUser.getPassword(),
                    connectedUser.getLastName(),
                    connectedUser.getFirstName(),
                    connectedUser.getBirthDate(),
                    connectedUser);
            ihmMainController.getMainWindowController().getIHMMainWindowController().loadHomePage();
        }
    }
}
