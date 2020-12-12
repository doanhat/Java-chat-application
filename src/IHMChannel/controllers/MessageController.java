package IHMChannel.controllers;

import common.IHMTools.IHMTools;
import common.sharedData.Message;
import common.sharedData.UserLite;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

import java.text.SimpleDateFormat;

import java.util.UUID;

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


    /**
     * Setter pour fixer le message qui sera affiché par ce widget.
     * Met à jour l'affichage
     * @param messageToDisplay objet message devant être lié au contrôle
     */
    public void setMessageToDisplay(Message messageToDisplay) {
        this.messageToDisplay = messageToDisplay;
        author.setText(messageToDisplay.getAuthor().getNickName());
        content.setText(messageToDisplay.getMessage());

        likeCounter.setText(String.valueOf(messageToDisplay.countLikes()));

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
    public void initialize(){
        iconsInit();
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
        getLikeButton().setGraphic(likeIcon);

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
        channelMessagesController.getIhmChannelController().getInterfaceToCommunication().likeMessage(
                channelMessagesController.channel,
                messageToDisplay,
                channelMessagesController.getIhmChannelController().getInterfaceToData().getLocalUser());
        //TODO à enlever pour l'intégration, ne sert qu'aux tests
        channelMessagesController.getIhmChannelController().getInterfaceForData().likeMessage(
                channelMessagesController.channel,
                messageToDisplay,
                channelMessagesController.getIhmChannelController().getInterfaceToData().getLocalUser());
    }

    /**
     * Méthode appelée au clic sur le bouton de réponse
     */
    public void answerMessage(){
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
    public void editMessage(){
        //Zone de texte editable
        this.content.setEditable(true);

        //Handler pour valider la modification à l'appui sur entrée
        content.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER)  {
                    Message newMsg = new Message();
                    newMsg.setMessage(content.getText());
                    channelMessagesController.getIhmChannelController().getInterfaceToCommunication().editMessage(
                            messageToDisplay,
                            newMsg,
                            channelMessagesController.channel
                    );

                    content.setEditable(false);

                    //TODO à enlever pour l'intégration, ne sert qu'aux tests
                    channelMessagesController.getIhmChannelController().getInterfaceForData().editMessage(
                            messageToDisplay,
                            newMsg,
                            channelMessagesController.channel
                    );
                }
            }
        });
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
