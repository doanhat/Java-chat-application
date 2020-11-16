package IHMChannel;

import IHMMain.UserListViewController;
import common.IHMTools.IHMTools;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import IHMMain.IHMMainWindowController;
import app.MainWindowController;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import IHMMain.IHMMainPage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class IHMChannelPageController implements Initializable {
    @FXML
    private StackPane StackMenu;

    @FXML
    private Button backBtn;

    @FXML
    private Button controlMemberBtn;

    @FXML
    private Button addMemberBtn;

    @FXML
    private Button quitChannelBtn;

    private ChannelMembersDisplay channelMembersDisplay;
    private ChannelMessagesDisplay channelMessagesDisplay;
    private MainWindowController mainWindowController;
    private IHMMainWindowController ihmMainWindowController;
    private boolean seeMember = false;

    public void setMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }

    public void setIhmMainWindowController(IHMMainWindowController ihmMainWindowController) {
        this.ihmMainWindowController = ihmMainWindowController;
    }
    public IHMMainWindowController getIHMMainWindowController(){
        return this.ihmMainWindowController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Mettez ici le code qui s'execute avant l'apparition de la vue
        this.init();

    }

    @FXML
    void loadUserListView() {
        this.StackMenu.getChildren().clear(); //On efface les noeuds fils
        //On charge la vue UserListView
        try {
            FXMLLoader fxmlLoader = new
                    FXMLLoader(getClass().getResource("../IHMMain/IHMMainPage.fxml"));
            Parent parent = fxmlLoader.load(); //On recupère le noeud racine du fxml chargé
            IHMMainPage ihmMainPage = fxmlLoader.getController(); //On récupère la classe controller liée au fxml
            ihmMainPage.setMainWindowController(this.mainWindowController); //On donne au controller fils une référence de son controller grand-parent
            this.StackMenu.getChildren().addAll(parent); //On ajoute le noeud parent (fxml) au noeud racine de cette vue
            IHMTools.fitSizeToParent((Region)this.StackMenu,(Region)parent);

            FXMLLoader fxmlLoader_userList = new
                    FXMLLoader(getClass().getResource("../IHMMain/UserListView.fxml"));
            Parent parent_userList = fxmlLoader_userList.load();
            UserListViewController userListViewController = fxmlLoader_userList.getController(); //On récupère la classe controller liée au fxml
            userListViewController.setMainWindowController(this.mainWindowController); //On donne au controller fils une référence de son controller grand-parent
            this.ihmMainWindowController.getMainArea().getChildren().clear();
            this.ihmMainWindowController.getMainArea().getChildren().addAll(parent_userList); //On ajoute le noeud parent (fxml) au noeud racine de cette vue
            IHMTools.fitSizeToParent((Region)this.ihmMainWindowController.getMainArea(),(Region)parent_userList);

        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    void addMember() {
        System.out.println("add member");
    }

    @FXML
    void quitChannel() {
        this.StackMenu.getChildren().clear(); //On efface les noeuds fils
        //On charge la vue UserListView
        try {
            FXMLLoader fxmlLoader = new
                    FXMLLoader(getClass().getResource("../IHMMain/IHMMainPage.fxml"));
            Parent parent = fxmlLoader.load(); //On recupère le noeud racine du fxml chargé
            IHMMainPage ihmMainPage = fxmlLoader.getController(); //On récupère la classe controller liée au fxml
            ihmMainPage.setMainWindowController(this.mainWindowController); //On donne au controller fils une référence de son controller grand-parent
            this.StackMenu.getChildren().addAll(parent); //On ajoute le noeud parent (fxml) au noeud racine de cette vue
            IHMTools.fitSizeToParent((Region)this.StackMenu,(Region)parent);

            FXMLLoader fxmlLoader_userList = new
                    FXMLLoader(getClass().getResource("../IHMMain/UserListView.fxml"));
            Parent parent_userList = fxmlLoader_userList.load();
            UserListViewController userListViewController = fxmlLoader_userList.getController(); //On récupère la classe controller liée au fxml
            userListViewController.setMainWindowController(this.mainWindowController); //On donne au controller fils une référence de son controller grand-parent
            this.ihmMainWindowController.getMainArea().getChildren().clear();
            this.ihmMainWindowController.getMainArea().getChildren().addAll(parent_userList); //On ajoute le noeud parent (fxml) au noeud racine de cette vue
            IHMTools.fitSizeToParent((Region)this.ihmMainWindowController.getMainArea(),(Region)parent_userList);

        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    void seeMember() {
        if (!this.seeMember) {
            this.seeMember = true;
            this.ihmMainWindowController.getMainArea().getChildren().clear();
            this.ihmMainWindowController.getMainArea().getChildren().addAll(this.channelMembersDisplay.root);
            IHMTools.fitSizeToParent((Region)this.ihmMainWindowController.getMainArea(), (Region)this.channelMembersDisplay.root);

        }
        else {
            this.seeMember = false;
            this.ihmMainWindowController.getMainArea().getChildren().clear();
            this.ihmMainWindowController.getMainArea().getChildren().addAll(this.channelMessagesDisplay.root);
            IHMTools.fitSizeToParent((Region)this.ihmMainWindowController.getMainArea(), (Region)this.channelMessagesDisplay.root);
        }
    }
     public void init(){
        try{
            this.channelMessagesDisplay = new ChannelMessagesDisplay();
            this.channelMembersDisplay = new ChannelMembersDisplay();
        }
        catch (IOException exception) {
            throw new RuntimeException(exception);
        }
     }





}
