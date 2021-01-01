package IHMChannel.controllers;

import IHMChannel.IHMChannelController;
import IHMChannel.MessageDisplay;
import common.IHMTools.IHMTools;
import common.shared_data.Channel;
import common.shared_data.Message;
import common.shared_data.UserLite;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Contrôleur de la vue "ChannelMessages" dans laquelle on retrouve l'affichage et la saisie de messages d'un channel
 */
public class ChannelMessagesController{
    UserLite connectedUser; //tmp
    Channel channel;
    private IHMChannelController ihmChannelController;
    private ConnectedMembersController connectedMembersController;
    private Message parentMessage = null;
    private boolean isReponse = false;
    private HashMap<UUID, MessageController> messagesMap = new HashMap<>();
    @FXML
    ImageView imgReceiver;

    @FXML
    Text userNameReceiver;

    @FXML
    TextArea messageReceiver;

    @FXML
    Button removeBtn;

    @FXML
    HBox reponseArea;
    @FXML
    ListView listMessages;
    @FXML
    ImageView profilePic;
    @FXML
    TextArea typedText;
    @FXML
    Button sendBtn;
    @FXML
    BorderPane connectedMembers;

    //Liste de HBox (= contrôle message)
    private ObservableList<HBox> messagesToDisplay = FXCollections.observableArrayList();
    private ObservableList<Message> observableMessages;

    ListChangeListener<Message> messageListListener;

    public void addMessageToObservableList(Message message){
        observableMessages.add(message);
    }

    public void setCurrentChannel(Channel channel){
        this.channel = channel;
        observableMessages = FXCollections.observableArrayList(this.channel.getMessages());
        observableMessages.addListener(messageListListener);
        try{
            displayMessagesList();
        }
        catch (Exception e){
            System.out.println("Problème lors de l'affichage des messages");
            e.printStackTrace();
        }
    }

    public ChannelMessagesController(){

    }
    public void initialize() throws IOException {
        //Icone envoyer
        Image sendImage = new Image("IHMChannel/icons/paper-plane-solid.png");
        ImageView sendIcon = new ImageView(sendImage);
        sendIcon.setFitHeight(15);
        sendIcon.setFitWidth(15);
        sendBtn.setGraphic(sendIcon);
        Image closeImage = new Image("IHMChannel/icons/close_icon.png");
        ImageView closeIcon = new ImageView(closeImage);
        closeIcon.setFitHeight(15);
        closeIcon.setFitWidth(15);
        removeBtn.setGraphic(closeIcon);
        reponseArea.setVisible(false);
        HBox.setMargin(removeBtn, new Insets(0, 50, 0, 0));

        messageReceiver.setEditable(false);
        // Définition listener sur la liste de messages
        messageListListener = changed -> {
            changed.next();
            if(changed.wasAdded()){
                for(Message msgAdded : changed.getAddedSubList()){
                        ChannelMessagesController that = this;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    if (msgAdded.getParentMessageId() == null) {
                                        getMessagesToDisplay().add((HBox) new MessageDisplay(msgAdded, that).root);
                                    }
                                    if (msgAdded.getParentMessageId() != null){
                                        HBox rpArea = new HBox();
                                        Pane pn = new Pane();
                                        pn.setPrefWidth(50);
                                        rpArea.setPrefHeight(50);
                                        rpArea.setVisible(true);
                                        ImageView newImgReceiver = new ImageView();
                                        Text newUserNameReceiver = new Text();
                                        TextArea newMessageReceiver = new TextArea();
                                        Message prtMessage = null;
                                        for (Message prt : observableMessages) {
                                            if (prt.getId().equals(msgAdded.getParentMessageId())) {
                                                prtMessage = prt;
                                                break;
                                            }
                                        }
                                        newUserNameReceiver.setText(prtMessage.getAuthor().getNickName() + " a dit :     ");
                                        newMessageReceiver.setText(prtMessage.getMessage());
                                        newMessageReceiver.setEditable(false);
                                        newMessageReceiver.setStyle("-fx-control-inner-background: #E5E5E5");
                                        rpArea.getChildren().addAll(pn, newImgReceiver, newUserNameReceiver, newMessageReceiver);
                                        rpArea.setAlignment(Pos.CENTER_LEFT);
                                        getMessagesToDisplay().add(rpArea);
                                        HBox msg = (HBox) new MessageDisplay(msgAdded, that).root;
                                        HBox.setHgrow(msg.getChildren().get(0), Priority.ALWAYS);
                                        Separator separator = new Separator();
                                        separator.setOrientation(Orientation.VERTICAL);
                                        separator.setStyle("-fx-background-color: #B9E6FF;  -fx-background-radius: 2;");
                                        msg.getChildren().add(1, separator);
                                        getMessagesToDisplay().add(msg);
                                    }
                                    setIsReponse(false);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                }
            }
        };
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/ConnectedMembers.fxml"));
        Parent root = fxmlLoader.load();
        connectedMembers.setRight(root);
        connectedMembersController = fxmlLoader.getController();
    }

    /**
     * Méthode déclenchée au clic sur le bouton d'envoi de message.
     */
    public void sendMessage(){
        if (!isReponse) {
            if (!typedText.getText().isEmpty()) {
                //ATTENTION l'id du message est écrit en dur, on ne sait pas comment il est généré pour le moment.
                // Ne paraît pas logique qu'il soit généré par IHM Channel, donc penser à un constructeur sans id
                Message newMsg = new Message(typedText.getText(), ihmChannelController.getInterfaceToData().getLocalUser().getUserLite());
                ihmChannelController.getInterfaceToCommunication().sendMessage(newMsg, channel, parentMessage);
                //messagesToDisplay.add((HBox)new MessageDisplay(new Message(1,typedText.getText(),connectedUser)).root);
                typedText.setText("");
            }
        }
        else {
            if(!typedText.getText().isEmpty()) {
                Message newMsg = new Message(typedText.getText(), ihmChannelController.getInterfaceToData().getLocalUser().getUserLite());
                ihmChannelController.getInterfaceToCommunication().sendMessage(newMsg, channel, parentMessage);
                typedText.setText("");
            }
        }
        this.parentMessage = null; // D'après envoyer message, parent message devient nul
    }

    /**
     * Initialise l'affichage de la liste des messages contenus dans l'attribut channel de la classe
     */
    private void displayMessagesList() throws IOException {
        getMessagesToDisplay().clear(); //réinitialisation
        for (Message msg : observableMessages) {
            if (msg.getParentMessageId() == null) {
                getMessagesToDisplay().add((HBox) new MessageDisplay(msg, this).root);
            }
            else {
                Message prtMessage = null;
                for (Message prt : observableMessages) {
                    if (prt.getId().equals(msg.getParentMessageId())) {
                        prtMessage = prt;
                        break;
                    }
                }
                System.out.println(prtMessage);
                HBox rpArea = new HBox();
                rpArea.setVisible(true);
                rpArea.setPrefHeight(50);
                Pane pn = new Pane();
                pn.setPrefWidth(50);
                ImageView newImgReceiver = new ImageView();
                Text newUserNameReceiver = new Text();
                TextArea newMessageReceiver = new TextArea();
                newUserNameReceiver.setText(prtMessage.getAuthor().getNickName() + " a dit :     ");
                newMessageReceiver.setText(prtMessage.getMessage());
                newMessageReceiver.setEditable(false);
                newMessageReceiver.setStyle("-fx-control-inner-background: #E5E5E5");
                rpArea.getChildren().addAll(pn, newImgReceiver, newUserNameReceiver, newMessageReceiver);
                rpArea.setAlignment(Pos.CENTER_LEFT);
                getMessagesToDisplay().add(rpArea);
                HBox msg1 = (HBox) new MessageDisplay(msg, this).root;
                HBox.setHgrow(msg1.getChildren().get(0), Priority.ALWAYS);
                Separator separator = new Separator();
                separator.setOrientation(Orientation.VERTICAL);
                separator.setStyle("-fx-background-color: #B9E6FF;  -fx-background-radius: 2;");
                msg1.getChildren().add(1, separator);
                getMessagesToDisplay().add(msg1);
            }
        }
        listMessages.setItems(getMessagesToDisplay());
    }


    public IHMChannelController getIhmChannelController() {
        return ihmChannelController;
    }

    /**
     * Setter de ihmChannelController pour ChannelMessagesController et ConnectedMembersController.
     * @param ihmChannelController
     */
    public void setIhmChannelController(IHMChannelController ihmChannelController) {
        this.ihmChannelController = ihmChannelController;
        this.connectedMembersController.setIhmChannelController(ihmChannelController);
    }

    public Message getParentMessage() {
        return parentMessage;
    }

    public void setParentMessage(Message parentMessage) {
        this.parentMessage = parentMessage;
    }

    public ObservableList<HBox> getMessagesToDisplay() {
        return messagesToDisplay;
    }

    public void setConnectedMembersList(List<UserLite> connectedMembersList) {
        this.connectedMembersController.setConnectedMembersList(connectedMembersList);
    }

    public void addMemberToObservableList(UserLite user) {
        this.connectedMembersController.addMemberToObservableList(user);
    }

    public void removeMemberFromObservableList(UserLite user) {
        this.connectedMembersController.removeMemberFromObservableList(user);
    }

    /**
     * test
     */

    public ObservableList<Message> getObservableMessages() {
        return observableMessages;
    }

    public void setIsReponse(boolean isReponse){
        reponseArea.setVisible(isReponse);
        this.isReponse = isReponse;
    }
    @FXML
    void removeReponse() {
        setIsReponse(false);
        this.parentMessage = null;
    }

    public HashMap<UUID, MessageController> getMessagesMap() {
        return messagesMap;
    }

    public void setMessagesMap(HashMap<UUID, MessageController> messagesMap) {
        this.messagesMap = messagesMap;
    }

    private boolean containsUser(List<UserLite> list, UserLite user){
        for(UserLite u : list){
            if(u.getId().equals(user.getId())){
                return true;
            }
        }
        return false;
    }

    public void likeMessage(Message message, UserLite user) {
        for(Message m : observableMessages){
            if(m.getId().equals(message.getId())){
                List<UserLite> likeList = m.getLikes();
                if(containsUser(likeList, user)){
                    likeList.removeIf((UserLite u) -> u.getId().equals(user.getId())); //dislike
                    m.setLikes(likeList);

                }else{
                    likeList.add(user); //like
                    m.setLikes(likeList);
                }
                //TODO inspecter liste likes transmise
                messagesMap.get(message.getId()).setMessageToDisplay(m); //mise à jour de l'affichage
                break;
            }
        }
    }

    public HashMap<UUID, MessageController> getMessageMap() {
        return messagesMap;
    }

    public void setMessageMap(HashMap<UUID, MessageController> messageMap) {
        this.messagesMap = messageMap;
    }

    public void editMessage(Message message, Message newMessage) {
        //if(message.getAuthor().getId().equals(ihmChannelController.getInterfaceToData().getLocalUser().getId())){
            for(Message m : observableMessages){
                if(m.getId().equals(message.getId())){
                    // pas besoin de màj le content ici car on l'a màj dans la copie locale du channel, ça se répercute automatiquement sur l'affichage
                    //affichage "message édité"
                    messagesMap.get(m.getId()).getIsEditedText().setText("message édité");
                    messagesMap.get(message.getId()).setMessageToDisplay(m); //mise à jour de l'affichage
                    break;
                }
            }
//        }else {
//            IHMTools.informationPopup("Vous ne pouvez pas éditer ce message");
//        }
    }


    public void changeNickname() throws IOException {
        displayMessagesList();
    }
}
