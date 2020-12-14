package IHMChannel.controllers;

import common.IHMTools.IHMTools;
import common.sharedData.Message;
import common.sharedData.UserLite;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe Contrôleur du contrôle (widget) "Message".
 */
public class MessageController {
    Message messageToDisplay;
    private ChannelMessagesController channelMessagesController;

    private final Logger logger = Logger.getLogger(this.getClass().getName());

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
     *
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
    public void initialize() {
        iconsInit();
        time.setText("10:06");
        content.setEditable(false);
    }

    public void iconsInit() {
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
     * Indique si un utilisateur donné est administrateur du channel courant.
     *
     * @param id UUID de l'utilisateur à tester
     * @return true si c'est un administrateur de ce channel, false sinon
     */
    public boolean isAdministrator(UUID id) {
        List<UserLite> administrators = channelMessagesController.channel.getAdministrators();
        for (UserLite a : administrators) {
            if (a.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Méthode appelée au clic sur le bouton de like
     */
    public void likeMessage() {
        logger.log(Level.INFO, "like du message {}", this.content.getText());
    }

    /**
     * Méthode appelée au clic sur le bouton de réponse
     */
    public void answerMessage() {
        // TODO: préparer l'edit de la réponse : dire à ChannelMessageController d'afficher le message parent au dessus de la barre de saisie
        // getChannelMessagesController.setParentMessage(messageToDisplay);
        // getChannelMessagesController.setResponseView();
        this.channelMessagesController.setIsReponse(true);
        this.channelMessagesController.userNameReceiver.setText(messageToDisplay.getAuthor().getNickName() + " a dit :");
        this.channelMessagesController.messageReceiver.setText(messageToDisplay.getMessage());
        this.channelMessagesController.setParentMessage(messageToDisplay);
    }

    /**
     * Méthode appelée au clic sur le bouton d'édition
     */
    public void editMessage() {
        logger.log(Level.INFO, "édition du message {}", this.content.getText());
    }

    /**
     * Méthode appelée au clic sur le bouton de suppresion
     */
    public void deleteMessage() {
        UserLite localUser = channelMessagesController.getIhmChannelController().getInterfaceToData().getLocalUser();

        if (messageToDisplay.getAuthor().getId().equals(localUser.getId()) ||
                isAdministrator(localUser.getId())) { /* si on est l'auteur du message ou admin du channel => on peut supprimer */

            boolean result = IHMTools.confirmationPopup("Voulez vous supprimer le message ?");

            if (result) {
                logger.log(Level.INFO, "suppression du message {}", this.content.getText());

                this.channelMessagesController.getIhmChannelController().getInterfaceToCommunication().suppMessage(
                        messageToDisplay,
                        channelMessagesController.channel,
                        channelMessagesController.getIhmChannelController().getInterfaceToData().getLocalUser());
                //TODO enlever avant integration. Ne sert qu'aux tests
                channelMessagesController.getIhmChannelController().getInterfaceForData().deleteMessage(
                        messageToDisplay,
                        channelMessagesController.channel,
                        messageToDisplay.getAuthor().getId().equals(localUser.getId()));
            }

        } else {
            IHMTools.InformationPopup("Vous n'avez pas les droits nécessaires pour supprimer ce message.");
        }

        //Attention, ici on ne màj que l'affichage, les data ne sont pas impactées.
    }

    /**
     * Traite le retour serveur de la suppression du message.
     */
    public void replaceDeletedMessage(boolean deletedByCreator) {

        messageToDisplay.delete(deletedByCreator);
        this.content.setText(messageToDisplay.getMessage());


    }

    public void setChannelMessagesController(ChannelMessagesController channelMessagesController) {
        this.channelMessagesController = channelMessagesController;
    }

}
