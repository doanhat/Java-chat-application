package IHMMain.controllers;

import IHMMain.IHMMainController;
import app.MainWindowController;
import common.IHMTools.IHMTools;
import common.shared_data.Channel;
import common.shared_data.ChannelType;
import common.shared_data.UserLite;
import common.shared_data.Visibility;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class IHMMainWindowController implements Initializable{

    private IHMMainController ihmMainController;

    private MainWindowController mainWindowController;

    private UserLite userL;
    
    // Is true if it's home page currently display, false otherwise
    private boolean isHomePage = true;

    private boolean isViewChangeSelectedChannel = false;

    private Region ihmChannelNode;

    @FXML
    private ListView<Channel> privateChannels;

    @FXML
    private ListView<Channel> publicChannels;

    @FXML
    private StackPane stackMenu;

    @FXML
    private StackPane mainArea;

    @FXML
    private ImageView profileImage;

    @FXML
    private ImageView avatarUser;

    @FXML
    private Text nickname;

    @FXML
    private Button homePageButton;

    @FXML
    private Button disconnectButton;

    @FXML
    private TextField channelSearchTextField;

    public MainWindowController getMainWindowController() {
        return mainWindowController;
    }

    public void setMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Mettez ici le code qui s'execute avant l'apparition de la vue
        loadHomePage();
        userL = ihmMainController.getIHMMainToData().getUser().getUserLite();
        updateProfileImage();
        nickname.setText(userL.getNickName());

        // Gestion de l'affichage du bouton de redirection vers la page d'accueil
        ImageView goToHomePageButton = new ImageView("IHMMain/icons/home.png");
        goToHomePageButton.setFitHeight(18);
        goToHomePageButton.setFitWidth(18);
        homePageButton.setGraphic(goToHomePageButton);

        // Gestion de l'affichage du bouton de déconnexion
        ImageView exitButtonConnection = new ImageView("IHMMain/icons/exit_to_app.png");
        exitButtonConnection.setFitHeight(18);
        exitButtonConnection.setFitWidth(18);
        disconnectButton.setGraphic(exitButtonConnection);

        Stage primaryStage = mainWindowController.getPrimaryStage();
        Platform.setImplicitExit(false);
        primaryStage.setOnCloseRequest(event -> {
            // Save file
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Fermer l'application");
            alert.setHeaderText("Voulez-vous fermer l'application?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                // ... user chose OK
                try {
                    try {
                        ihmMainController.getIIHMMainToCommunication().disconnect();
                        Platform.exit();
                    } catch(Exception e){
                        throw new Exception("Impossible de se déconnecter du serveur lors de la fermeture de l'application", e);
                    }
                } catch(Exception e){
                    e.printStackTrace();
                    Platform.exit();
                }
            } else {
                // ... user chose CANCEL or closed the dialog
                event.consume();
            }
        });

        initChannelsListView();
    }

    private void initChannelsListView() {
        ObservableList<Channel> visibleChannelsObservableList ;

        visibleChannelsObservableList = mainWindowController.getIhmMainController().getVisibleChannels();
        FilteredList<Channel> filteredChannels = new FilteredList<>(visibleChannelsObservableList, b-> true);
        channelSearchTextField.textProperty().addListener((observable,oldValue,newValue) -> {
            filteredChannels.setPredicate(channel -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return (channel.getName().toLowerCase().indexOf(lowerCaseFilter) != -1
                        || channel.getDescription().toLowerCase().indexOf(lowerCaseFilter) != -1);
            });
            privateChannels.setItems(filteredChannels.filtered(channel -> channel.getVisibility() == Visibility.PRIVATE));
            publicChannels.setItems(filteredChannels.filtered(channel -> channel.getVisibility() == Visibility.PUBLIC));
        });

        /**
         * Bind the ListView with the list of private channels.
         * And use the ChannelListViewCellController to display each item.
         */
        privateChannels.setItems(filteredChannels.filtered(channel -> channel.getVisibility() == Visibility.PRIVATE));
        privateChannels.setCellFactory(privateChannelsListView -> new ChannelListViewCellController(ihmMainController));

        /**
         * When a channel is selected, we display is channel view
         * and deselect the previous channel on the list if it exist.
         * We only deselect from the opposite list, because on the same list, it's automatic
         */
        privateChannels.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null && !isViewChangeSelectedChannel) {
                        viewChannel(newValue);
                        clearSelectedChannel(publicChannels);
                    }
                }
        );

        /**
         * Bind the ListView with the list of public channels.
         * And use the ChannelListViewCellController to display each item.
         */
        publicChannels.setItems(filteredChannels.filtered(channel -> channel.getVisibility() == Visibility.PUBLIC));
        publicChannels.setCellFactory(privateChannelsListView -> new ChannelListViewCellController(ihmMainController));

        /**
         * When a channel is selected, we display is channel view
         * and deselect the previous channel on the list if it exist.
         * We only deselect from the opposite list, because on the same list, it's automatic
         */
        publicChannels.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null && !isViewChangeSelectedChannel) {
                        viewChannel(newValue);
                        clearSelectedChannel(privateChannels);
                    }
                }
        );
    }

    public void updateChannelListView(){
        ObservableList<Channel> visibleChannelsObservableList ;
        visibleChannelsObservableList = mainWindowController.getIhmMainController().getVisibleChannels();
        FilteredList<Channel> filteredChannels = new FilteredList<>(visibleChannelsObservableList, b-> true);
        privateChannels.setItems(filteredChannels.filtered(channel -> channel.getVisibility() == Visibility.PRIVATE));
        publicChannels.setItems(filteredChannels.filtered(channel -> channel.getVisibility() == Visibility.PUBLIC));
    }

    /**
     * Unselect the selected item from the ListView if it exist
     * @param listChannels ListView to unselect
     */
    private void clearSelectedChannel(ListView<Channel> listChannels) {
        MultipleSelectionModel<Channel> multipleSelectionModel = listChannels.getSelectionModel();
        if (multipleSelectionModel.selectedItemProperty().getValue() != null) {
            multipleSelectionModel.clearSelection();
        }
    }

    /**
     * Use to show the thread of this channel
     * @param channel Channel to set a in the current view
     */
    public void viewChannel(Channel channel) {
        if (ihmChannelNode == null) {
            ihmChannelNode = ihmMainController.getIHMMainToIHMChannel().initIHMChannelWindow();
        }
        if (this.isHomePage) {
            loadIHMChannelWindow();
        }
        this.ihmMainController.getIHMMainToIHMChannel().viewChannel(channel);
    }

    /**
     * Change the visual state of the current selected channel.
     * Use when there a changed tab inside IHM-Channel view to maintain visual consistency
     * @param channel Channel to view as selected
     */
    public void setViewChannelSelected(Channel channel) {
        if (ihmMainController.getVisibleChannels().contains(channel)) {
            clearSelectedChannel(privateChannels);
            clearSelectedChannel(publicChannels);
            isViewChangeSelectedChannel = true;
            if (channel.getVisibility() == Visibility.PUBLIC) {
                publicChannels.scrollTo(channel);
                publicChannels.getSelectionModel().select(channel);
            } else {
                privateChannels.scrollTo(channel);
                privateChannels.getSelectionModel().select(channel);
            }
            isViewChangeSelectedChannel = false;
        }
    }

    public void loadIHMChannelWindow(){
        Platform.runLater(() -> {
            mainArea.getChildren().clear(); //On efface les noeuds fils
            isHomePage = false;
            //On charge la vue d'IHM-Channel
            mainArea.getChildren().addAll(ihmChannelNode); //On ajoute le noeud parent (fxml) au noeud racine de cette vue
            IHMTools.fitSizeToParent((Region)mainArea,ihmChannelNode);
        });
    }

    @FXML
    public void createPrivateChannel() {
        try {
            loadCreationChannelPopup(Visibility.PRIVATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void createPublicChannel() {
        try {
            loadCreationChannelPopup(Visibility.PUBLIC);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    private void updateProfileImage(){
        if(!userL.getAvatar().equals("")){
            /**
             * Voir avec Data comment sont stockées les images sur le serveur,
             * faire en sorte que getAvatar renvoie une image afin de ne pas stocker trop
             * d'images en local
             */

            //String avatarPath = parentController.getIhmMainController().getIIHMMainToCommunication().getAvatarPath(u.getUserLite());
            String avatarPath = "IHMMain/icons/willsmith.png";
            Image image = new Image(avatarPath);
            profileImage.setImage(image);
        }
    }

    private void loadCreationChannelPopup(Visibility type) throws IOException {
        Parent root;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/CreationChannelPopup.fxml"));
            root = fxmlLoader.load();

            CreationChannelPopupController creationController = fxmlLoader.getController();
            creationController.setParentController(this);
            creationController.setVisibility(type);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.setTitle("Nouveau channel");
            stage.setScene(new Scene(root, 600, 400));
            stage.setResizable(false);

            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createChannel(Channel newChannel) throws IOException {
        if (newChannel.getType() == ChannelType.SHARED) {
            ihmMainController.getIIHMMainToCommunication().createChannel(newChannel, newChannel.getType() == ChannelType.SHARED, newChannel.getVisibility() == Visibility.PUBLIC, newChannel.getCreator());
        } else {
            ihmMainController.getIHMMainToData().createChannel(newChannel.getName(), newChannel.getDescription(), newChannel.getType() == ChannelType.SHARED, newChannel.getVisibility() == Visibility.PUBLIC, newChannel.getCreator() );
        }
    }

    @FXML
    public void loadHomePage() {
        this.mainArea.getChildren().clear(); //On efface les noeuds fils
        this.isHomePage = true;

        // Unselect both ListView
        clearSelectedChannel(privateChannels);
        clearSelectedChannel(publicChannels);

        //On charge la vue UserListView
        try {
            HomePageController homePageController;

            FXMLLoader fxmlLoader = new
                    FXMLLoader(getClass().getResource("../views/HomePage.fxml"));
            Parent parent = fxmlLoader.load(); //On recupère le noeud racine du fxml chargé
            homePageController = fxmlLoader.getController(); //On récupère la classe controller liée au fxml
            homePageController.setIhmMainController(ihmMainController);
            homePageController.setMainWindowController(this.mainWindowController); //On donne au controller fils une référence de son controller grand-parent
            this.mainArea.getChildren().addAll(parent); //On ajoute le noeud parent (fxml) au noeud racine de cette vue
            IHMTools.fitSizeToParent((Region)this.mainArea,(Region)parent);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    private void loadUserInfosPopup() throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/UserInfos.fxml"));
            Parent root = fxmlLoader.load();

            UserInfosController userInfosController = fxmlLoader.getController();
            try {
                userInfosController.setParentController(this);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.setTitle("Informations Utilisateur");
            stage.setScene(new Scene(root, 600, 400));
            stage.setResizable(false);

            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    public StackPane getMainArea() {
        return this.mainArea;
    }

    public IHMMainController getIhmMainController() {
        return ihmMainController;
    }

    public void setIhmMainController(IHMMainController ihmMainController) {
        this.ihmMainController = ihmMainController;
    }

    public ListView<Channel> getPrivateChannels() {
        return privateChannels;
    }

    public ListView<Channel> getPublicChannels() {
        return publicChannels;
    }
}
