package IHMMain.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import IHMChannel.IHMMainToIHMChannel;
import IHMMain.implementations.CommunicationToIHMMain;
import app.MainWindowController;
import common.IHMTools.*;
import common.sharedData.Channel;
import common.sharedData.SharedChannel;
import common.sharedData.UserLite;
import common.sharedData.Visibility;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class IHMMainWindowController implements Initializable{

    private IHMMainController ihmMainController;

    private MainWindowController mainWindowController;

    private ObservableList<Channel> channelsObservableList = FXCollections.observableArrayList();

    @FXML
    private ListView<Channel> privateChannels;

    @FXML
    private ListView<Channel> publicChannels;

    @FXML
    private StackPane stackMenu;

    @FXML
    private StackPane mainArea;

    public MainWindowController getMainWindowController() {
        return mainWindowController;
    }

    public void setMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Mettez ici le code qui s'execute avant l'apparition de la vue
        loadUserListView();

        // TODO get by interface
        UserLite testUser = new UserLite("Jean Valjean", "");
        channelsObservableList.setAll(
                new SharedChannel("chan0", testUser, "channel 0", Visibility.PRIVATE),
                new SharedChannel("chan1", testUser, "channel 1", Visibility.PRIVATE),
                new SharedChannel("chan2", testUser, "channel 3", Visibility.PUBLIC),
                new SharedChannel("chan3", testUser, "channel 3", Visibility.PUBLIC)
        );

        /**
         * Bind the ListView with the list of private channels.
         * And use the ChannelListViewCellController to display each item.
         */
        privateChannels.setItems(channelsObservableList.filtered(channel -> channel.getVisibility() == Visibility.PRIVATE));
        privateChannels.setCellFactory(privateChannelsListView -> new ChannelListViewCellController());

        /**
         * When a channel is selected, we display is channel view
         * and deselect the previous channel on the list if it exist.
         * We only deselect from the opposite list, because on the same list, it's automatic
         */
        privateChannels.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Channel>() {
                    @Override
                    public void changed(ObservableValue observable, Channel oldValue, Channel newValue) {
                        if (newValue != null) {
                            loadIHMChannelWindow(newValue);
                            clearSelectedChannel(publicChannels);
                        }
                    }
                }
        );

        /**
         * Bind the ListView with the list of public channels.
         * And use the ChannelListViewCellController to display each item.
         */
        publicChannels.setItems(channelsObservableList.filtered(channel -> channel.getVisibility() == Visibility.PUBLIC));
        publicChannels.setCellFactory(privateChannelsListView -> new ChannelListViewCellController());

        /**
         * When a channel is selected, we display is channel view
         * and deselect the previous channel on the list if it exist.
         * We only deselect from the opposite list, because on the same list, it's automatic
         */
        publicChannels.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Channel>() {
                    @Override
                    public void changed(ObservableValue observable, Channel oldValue, Channel newValue) {
                        if (newValue != null) {
                            loadIHMChannelWindow(newValue);
                            clearSelectedChannel(privateChannels);
                        }
                    }
                }
        );
    }

    /**
     * Unselect the selected item from the ListView if it exist
     * @param listChannels ListView to unselect
     */
    private void clearSelectedChannel(ListView<Channel> listChannels) {
        MultipleSelectionModel multipleSelectionModel = listChannels.getSelectionModel();
        if (multipleSelectionModel.selectedItemProperty().getValue() != null) {
            multipleSelectionModel.clearSelection();
        }
    }

    @FXML
    public void loadIHMChannelWindow(Channel channel){
        this.mainArea.getChildren().clear(); //On efface les noeuds fils
        //On charge la vue IHMMainWindow
        Region ihmChannelNode = ihmMainController.getIHMMainToIHMChannel().getIHMChannelWindow();
        // Region ihmChannelNode = ihmMainToIHMChannel.getIHMChannelWindow(channel); // TODO lors du merge avec IHM-Channel, utiliser cette ligne plutot que celle au dessus
        this.mainArea.getChildren().addAll(ihmChannelNode); //On ajoute le noeud parent (fxml) au noeud racine de cette vue
        IHMTools.fitSizeToParent((Region)this.mainArea,ihmChannelNode);
    }

    @FXML
    public void loadCreationChannelPopup() throws IOException {
        Parent root;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/CreationChannelPopup.fxml"));
            root = fxmlLoader.load();

            CreationChannelPopupController creationController = fxmlLoader.getController();
            creationController.setParentController(this);

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
        /* --------- Debug -----------
        System.out.println(newChannel.getId());
        System.out.println(newChannel.getName());
        System.out.println(newChannel.getDescription());
        System.out.println(newChannel.getCreator().getNickName());
        */
        ihmMainController.getIIHMMainToCommunication().createChannel(newChannel, newChannel instanceof SharedChannel, newChannel.getVisibility() == Visibility.PUBLIC, newChannel.getCreator());
        channelsObservableList.add(newChannel);
    }

    @FXML
    public void loadUserListView(){
        this.mainArea.getChildren().clear(); //On efface les noeuds fils

        // Unselect both ListView
        clearSelectedChannel(privateChannels);
        clearSelectedChannel(publicChannels);

        //On charge la vue UserListView
        try {
            FXMLLoader fxmlLoader = new
                    FXMLLoader(getClass().getResource("../views/UserListView.fxml"));
            Parent parent = fxmlLoader.load(); //On recupère le noeud racine du fxml chargé
            UserListViewController userListViewController = fxmlLoader.getController(); //On récupère la classe controller liée au fxml
            userListViewController.setMainWindowController(this.mainWindowController); //On donne au controller fils une référence de son controller grand-parent
            this.mainArea.getChildren().addAll(parent); //On ajoute le noeud parent (fxml) au noeud racine de cette vue
            IHMTools.fitSizeToParent((Region)this.mainArea,(Region)parent);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    public void createPrivateChannel() {
        int nb = channelsObservableList.size();
        channelsObservableList.add(
                new SharedChannel("chan"+String.valueOf(nb), new UserLite(), "channel "+String.valueOf(nb), Visibility.PRIVATE)
        );
    }

    @FXML
    public void createPublicChannel() {
        int nb = channelsObservableList.size();
        channelsObservableList.add(
                new SharedChannel("chan"+String.valueOf(nb), new UserLite(), "channel "+String.valueOf(nb), Visibility.PUBLIC)
        );
    }

    public StackPane getMainArea() {
        return this.mainArea;
    }

    public void addChannel(Channel newChannel) {
        //TODO : Ajouter le channel à la liste des channels
    }


    public IHMMainController getIhmMainController() {
        return ihmMainController;
    }

    public void setIhmMainController(IHMMainController ihmMainController) {
        this.ihmMainController = ihmMainController;
    }


}
