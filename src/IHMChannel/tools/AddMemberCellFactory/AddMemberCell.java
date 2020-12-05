package IHMChannel.tools.AddMemberCellFactory;

import IHMChannel.controllers.AddMemberPopUpController;
import IHMChannel.controllers.ChannelController;
import IHMChannel.controllers.PopUpConfirmationController;
import common.sharedData.Channel;
import common.sharedData.UserLite;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AddMemberCell extends ListCell<UserLite> {

    @FXML
    Button addBtn;
    @FXML
    ImageView profilePicture;
    @FXML
    Text username;

    UserLite user;

    ChannelController channelController;

    /**
     * Constructeur
     * Appelé par la CellFactory
     */
    public AddMemberCell(ChannelController channelController){
        loadFXML();
        this.channelController = channelController;
    }

    public void initialize(){
        //Action sur le bouton
        addBtn.setOnAction((ActionEvent event) -> {
            FXMLLoader fxmlLoader =
                    new FXMLLoader(getClass().getResource("../../views/PopUpConfirmation.fxml"));
            Parent root = null;
            try {
                root = fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Channel channel = this.channelController.getCurrentChannel();

            //Pop-up init
            PopUpConfirmationController popUpConfirmationController = fxmlLoader.getController();
            popUpConfirmationController.setText("Voulez-vous vraiment ajouter " + user.getNickName() + " au channel "+channel+" ?");
            Stage popUpWindow = new Stage();
            popUpWindow.setWidth(300);
            popUpWindow.setHeight(100);
            popUpWindow.initModality(Modality.APPLICATION_MODAL);
            popUpWindow.setTitle("Confirmation");
            popUpWindow.setScene(new Scene(root));
            popUpWindow.setResizable(false);
            popUpWindow.show();

            /**
             * Gestion du clic sur le bouton annuler
             */
            popUpConfirmationController.getAnnulerBtn().setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e)
                {
                    popUpWindow.close();
                }
            });

            /**
             * Gestion du clic sur le bouton confirmer
             */
            popUpConfirmationController.getConfirmerBtn().setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e)
                {
                    System.out.println("You have invited " + user.getNickName() + " to channel " + channel.getName());
                    //TODO appel interface
                    //channelController.getIhmChannelController().getInterfaceToCommunication().
                    popUpWindow.close();
                }
            });

        });
    }

    /**
     * Chargement du FXML "AddMemberCell" qui contient la structure d'une liste de la liste
     */
    private void loadFXML(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddMemberCell.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void updateItem(UserLite item, boolean empty) {
        super.updateItem(item, empty);
        this.user = item;

        if(empty) {
            setText(null);
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        }
        else {
            username.setText(item.getNickName());
            //TODO voir pour l'image
            //profilePicture.setImage(new Image(item.getAvatar()));
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        }
    }
}
