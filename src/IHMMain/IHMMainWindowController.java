package IHMMain;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Communication.IHMMainToCommunication;
import IHMChannel.IHMMainToIHMChannel;
import app.MainWindowController;
import common.IHMTools.*;
import common.interfaces.client.IIHMMainToCommunication;
import common.sharedData.Channel;
import common.sharedData.SharedChannel;
import common.sharedData.Visibility;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;

public class IHMMainWindowController implements Initializable{

    private IHMMainController ihmMainController;

    private MainWindowController mainWindowController;

    private IHMMainToIHMChannel ihmMainToIHMChannel;

    private IHMMainToCommunication ihmMainToCommunication;

    private CommunicationToIHMMain communicationToIHMMain;

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
        ihmMainToIHMChannel = new IHMMainToIHMChannel();
        ihmMainToCommunication = new IHMMainToCommunication();
        communicationToIHMMain = new CommunicationToIHMMain();
        communicationToIHMMain.setIhmMainWindowController(this);
        loadUserListView();
    }

    @FXML
    public void loadIHMChannelWindow(){
        this.mainArea.getChildren().clear(); //On efface les noeuds fils
        //On charge la vue IHMMainWindow
        Region ihmChannelNode = ihmMainToIHMChannel.getIHMChannelWindow();
        this.mainArea.getChildren().addAll(ihmChannelNode); //On ajoute le noeud parent (fxml) au noeud racine de cette vue
        IHMTools.fitSizeToParent((Region)this.mainArea,ihmChannelNode);
    }

    @FXML
    public void loadCreationChannelPopup() throws IOException {
        Parent root;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../IHMMain/CreationChannelPopup.fxml"));
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

    public void createChannel(Channel newChannel){
        /* --------- Debug -----------
        System.out.println(newChannel.getId());
        System.out.println(newChannel.getName());
        System.out.println(newChannel.getDescription());
        System.out.println(newChannel.getCreator().getNickName());
        */

        ihmMainToCommunication.createChannel(newChannel, newChannel instanceof SharedChannel, newChannel.getVisibility() == Visibility.PUBLIC, newChannel.getCreator());
    }

    @FXML
    public void loadUserListView(){
        this.mainArea.getChildren().clear(); //On efface les noeuds fils
        //On charge la vue UserListView
        try {
            FXMLLoader fxmlLoader = new
                    FXMLLoader(getClass().getResource("UserListView.fxml"));
            Parent parent = fxmlLoader.load(); //On recupère le noeud racine du fxml chargé
            UserListViewController userListViewController = fxmlLoader.getController(); //On récupère la classe controller liée au fxml
            userListViewController.setMainWindowController(this.mainWindowController); //On donne au controller fils une référence de son controller grand-parent
            this.mainArea.getChildren().addAll(parent); //On ajoute le noeud parent (fxml) au noeud racine de cette vue
            IHMTools.fitSizeToParent((Region)this.mainArea,(Region)parent);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
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
