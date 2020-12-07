package IHMChannel.controllers;

import common.IHMTools.IHMTools;
import common.sharedData.Message;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

/**
 * Classe Contrôleur du contrôle (widget) "Message".
 */
public class MessageController {
    Message messageToDisplay;
    private ChannelMessagesController channelMessagesController;
    @FXML
    ImageView profilePic;
    @FXML
    Text author;
    @FXML
    TextArea content;
    @FXML
    Text time;
    @FXML
    Button like;
    @FXML
    Button answer;
    @FXML
    Button edit;
    @FXML
    Button delete;

    /**
     * Setter pour fixer le message qui sera affiché par ce widget.
     * Met à jour l'affichage
     * @param messageToDisplay objet message devant être lié au contrôle
     */
    public void setMessageToDisplay(Message messageToDisplay) {
        this.messageToDisplay = messageToDisplay;
        author.setText(messageToDisplay.getAuthor().getNickName());
        content.setText(messageToDisplay.getMessage());
    }

    /**
     * Appelée automatiquement par le FXML Loader
     * C'est la méthode dans laquelle on initialise les affichages.
     */
    public void initialize(){
        iconsInit();
        time.setText("10:06");
        content.setEditable(false);
    }

    public void iconsInit(){
        //Edit

        Image editImage = new Image("IHMChannel/icons/edit-solid.png");
        ImageView editIcon = new ImageView(editImage);
        editIcon.setFitHeight(15);
        editIcon.setFitWidth(15);
        edit.setGraphic(editIcon);


        //Like
        Image likeImage = new Image("IHMChannel/icons/heart-regular.png");
        ImageView likeIcon = new ImageView(likeImage);
        likeIcon.setFitHeight(15);
        likeIcon.setFitWidth(15);
        like.setGraphic(likeIcon);

        //Reply
        Image replyImage = new Image("IHMChannel/icons/reply-solid.png");
        ImageView replyIcon = new ImageView(replyImage);
        replyIcon.setFitHeight(15);
        replyIcon.setFitWidth(15);
        answer.setGraphic(replyIcon);

        //Delete
        Image deleteImage = new Image("IHMChannel/icons/trash-solid.png");
        ImageView deleteIcon = new ImageView(deleteImage);
        deleteIcon.setFitHeight(15);
        deleteIcon.setFitWidth(15);
        delete.setGraphic(deleteIcon);
    }
    /**
     * Méthode appelée au clic sur le bouton de like
     */
    public void likeMessage(){
        System.out.println("like du message "+this.content.getText());
    }

    /**
     * Méthode appelée au clic sur le bouton de réponse
     */
    public void answerMessage(){
        // TODO: préparer l'edit de la réponse : dire à ChannelMessageController d'afficher le message parent au dessus de la barre de saisie
        // getChannelMessagesController.setParentMessage(messageToDisplay);
        // getChannelMessagesController.setResponseView();
        this.channelMessagesController.setIsReponse(true);
        this.channelMessagesController.userNameReceiver.setText(messageToDisplay.getAuthor().getNickName() + " a dit :     ");
        this.channelMessagesController.messageReceiver.setText(messageToDisplay.getMessage());
        this.channelMessagesController.setParentMessage(messageToDisplay);
    }

    /**
     * Méthode appelée au clic sur le bouton d'édition
     */
    public void editMessage(){
        System.out.println("édition du message "+this.content.getText());
    }

    /**
     * Méthode appelée au clic sur le bouton de suppresion
     */
    public void deleteMessage(){



        boolean result = IHMTools.confirmationPopup("Voulez vous supprimer le message ?");

        if (result){
            System.out.println("suppression du message "+this.content.getText());
            this.content.setText("message supprimé");
        }

        //Attention, ici on ne màj que l'affichage, les data ne sont pas impactées.
    }

    public void setChannelMessagesController(ChannelMessagesController channelMessagesController) {
        this.channelMessagesController = channelMessagesController;
    }

}
