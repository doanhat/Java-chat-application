package IHMChannel.controllers;

import common.IHMTools.IHMTools;
import common.shared_data.Message;
import common.shared_data.UserLite;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.text.SimpleDateFormat;

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
    Text isEditedText;
    @FXML
    TextArea content;
    @FXML
    Text time;
    @FXML
    private
    Button likeButton;
    @FXML
    Text likeCounter;
    @FXML
    Button answer;
    @FXML
    Button edit;
    @FXML
    Button delete;


    private boolean containsUser(List<UserLite> list, UserLite user){
        for(UserLite u : list){
            if(u.getId().equals(user.getId())){
                return true;
            }
        }
        return false;
    }
    /**
     * Setter pour fixer le message qui sera affiché par ce widget.
     * Met à jour l'affichage
     *
     * @param messageToDisplay objet message devant être lié au contrôle
     */
    public void setMessageToDisplay(Message messageToDisplay) {
        this.messageToDisplay = messageToDisplay;
        String nickname = channelMessagesController.channel.getNickNames().get(messageToDisplay.getAuthor().getId().toString());
        if (nickname != null) {
            this.author.setText(nickname);
        } else {
            this.author.setText(messageToDisplay.getAuthor().getNickName());
        }
        content.setText(messageToDisplay.getMessage());
        if (messageToDisplay.isEdited()) {
            if (messageToDisplay.isDeletedByAdmin() || messageToDisplay.isDeletedByUser()) {
                isEditedText.setVisible(false);
            } else {
                isEditedText.setText(" message édité");
            }

        }

        likeCounter.setText(String.valueOf(messageToDisplay.countLikes()));


        List<UserLite> likeList = messageToDisplay.getLikes();
        UserLite user = channelMessagesController.getIhmChannelController().getInterfaceToData().getLocalUser();
        if (!containsUser(likeList, user)) {
            //likeList.remove(user); //dislike
            //update icon
            Image likeImage = null;
            if (likeList.size() > 0) { //other user still like the message
                likeImage = new Image("IHMChannel/icons/heart-solid.png");
            } else {
                likeImage = new Image("IHMChannel/icons/heart-regular.png");
            }
            ImageView likeIcon = new ImageView(likeImage);
            likeIcon.setFitHeight(15);
            likeIcon.setFitWidth(15);
            likeButton.setGraphic(likeIcon);
        } else {
            //likeList.add(user); //like
            //update icon to red heart
            Image likeImage = new Image("IHMChannel/icons/heart-solid-red.png");
            ImageView likeIcon = new ImageView(likeImage);
            likeIcon.setFitHeight(15);
            likeIcon.setFitWidth(15);
            likeButton.setGraphic(likeIcon);
        }



        //date formatting
        String df = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(messageToDisplay.getDate());
        time.setText(df);

        //Gestion de l'affichage des boutons
        //bouton édition visible que c'est c'est notre message

        //TODO à décommenter pour l'intégration
        //Pour le moment, le getUser() est null
        /*
        if(this.channelMessagesController.getIhmChannelController().getInterfaceToData().getLocalUser().getId()==messageToDisplay.getAuthor().getId()){
            edit.setVisible(true);
        }
        else{
            edit.setVisible(false);
        }
         */

        //TODO bouton suppression

    }

    public Text getIsEditedText() {
        return isEditedText;
    }

    /**
     * Appelée automatiquement par le FXML Loader
     * C'est la méthode dans laquelle on initialise les affichages.
     */
    public void initialize() {
        //iconsInit();
        content.setEditable(false);
    }

    public void iconsInit() {
        UUID localUser = channelMessagesController.getIhmChannelController().getInterfaceToData().getLocalUser().getId();
        //Edit
        if (localUser.equals(messageToDisplay.getAuthor().getId()) && !(messageToDisplay.isDeletedByUser() || messageToDisplay.isDeletedByAdmin())) {
            Image editImage = new Image("IHMChannel/icons/edit-solid.png");
            ImageView editIcon = new ImageView(editImage);
            editIcon.setFitHeight(15);
            editIcon.setFitWidth(15);
            edit.setGraphic(editIcon);
        } else {
            edit.setVisible(false);
        }

        //Reply
        Image replyImage = new Image("IHMChannel/icons/reply-solid.png");
        ImageView replyIcon = new ImageView(replyImage);
        replyIcon.setFitHeight(15);
        replyIcon.setFitWidth(15);
        answer.setGraphic(replyIcon);

        //Delete
        if (localUser.equals(messageToDisplay.getAuthor().getId()) || channelMessagesController.channel.userIsAdmin(localUser)) {
            Image deleteImage = new Image("IHMChannel/icons/trash-solid.png");
            ImageView deleteIcon = new ImageView(deleteImage);
            deleteIcon.setFitHeight(15);
            deleteIcon.setFitWidth(15);
            delete.setGraphic(deleteIcon);
        } else {
            delete.setVisible(false);
        }

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
        System.out.println("like du message " + this.content.getText());
        channelMessagesController.getIhmChannelController().getInterfaceToCommunication().likeMessage(
                channelMessagesController.channel,
                messageToDisplay,
                channelMessagesController.getIhmChannelController().getInterfaceToData().getLocalUser());
    }

    /**
     * Méthode appelée au clic sur le bouton de réponse
     */
    public void answerMessage() {
        this.channelMessagesController.setIsReponse(true);
        this.channelMessagesController.userNameReceiver.setText(messageToDisplay.getAuthor().getNickName() + " a dit :     ");
        this.channelMessagesController.messageReceiver.setText(messageToDisplay.getMessage());
        this.channelMessagesController.setParentMessage(messageToDisplay);
    }

    /**
     * Méthode appelée au clic sur le bouton d'édition
     */
    public void editMessage() {
        //Zone de texte editable
        this.content.setEditable(true);

        //Handler pour valider la modification à l'appui sur entrée
        content.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {

                if (keyEvent.getCode() == KeyCode.ENTER) {
                    Message newMsg = new Message();
                    newMsg.setMessage(content.getText());
                    newMsg.setId(messageToDisplay.getId());
                    newMsg.setAuthor(messageToDisplay.getAuthor());
                    newMsg.setAnswers(messageToDisplay.getAnswers());
                    newMsg.setDate(messageToDisplay.getDate());
                    newMsg.setDeletedByAdmin(messageToDisplay.isDeletedByAdmin());
                    newMsg.setDeletedByUser(messageToDisplay.isDeletedByUser());
                    newMsg.setEdited(messageToDisplay.isEdited());
                    newMsg.setLikes(messageToDisplay.getLikes());
                    newMsg.setParentMessageId(messageToDisplay.getParentMessageId());
                    channelMessagesController.getIhmChannelController().getInterfaceToCommunication().editMessage(
                            messageToDisplay,
                            newMsg,
                            channelMessagesController.channel
                    );

                    content.setEditable(false);
                }
            }
        });
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
            }

        } else {
            IHMTools.informationPopup("Vous n'avez pas les droits nécessaires pour supprimer ce message.");
        }

        //Attention, ici on ne màj que l'affichage, les data ne sont pas impactées.
    }

    /**
     * Traite le retour serveur de la suppression du message.
     */
    public void replaceDeletedMessage(boolean deletedByCreator) {

        messageToDisplay.delete(deletedByCreator);
        this.content.setText(messageToDisplay.getMessage());
        isEditedText.setVisible(false);

    }

    public void setChannelMessagesController(ChannelMessagesController channelMessagesController) {
        this.channelMessagesController = channelMessagesController;
    }

    public Button getLikeButton() {
        return likeButton;
    }

    public void setLikeButton(Button likeButton) {
        this.likeButton = likeButton;
    }

    public TextArea getContent() {
        return content;
    }

    public void setAuthorNickname(String nickName) {
        author.setText(nickName);
    }
}
