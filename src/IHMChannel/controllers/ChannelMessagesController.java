package IHMChannel.controllers;

import IHMChannel.IHMChannelController;
import IHMChannel.MessageDisplay;
import common.sharedData.Channel;
import common.sharedData.Message;
import common.sharedData.UserLite;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.UUID;

/**
 * Contrôleur de la vue "ChannelMessages" dans laquelle on retrouve l'affichage et la saisie de messages d'un channel
 */
public class ChannelMessagesController{
    UserLite connectedUser; //tmp
    private Channel channel;
    private IHMChannelController ihmChannelController;
    private Message parentMessage = null;

    @FXML
    ListView listMessages;
    @FXML
    ImageView profilePic;
    @FXML
    TextArea typedText;
    @FXML
    Button sendBtn;
    @FXML
    Button testReception; //utilisé pour test uniquement

    /**
     * Liste de HBox pour la disposition des messages dans l'IHM
     */
    private ObservableList<HBox> messagesToDisplay = FXCollections.observableArrayList();
    /**
     * Liste mappée à Channel.messages afin de faire le binding de la liste des messages à l'IHM grâce à une liste de Listeners.
     * Cette liste doit être mise à jour à chaque ajout/suppression/modification de message dans la Channel.messages.
     * Comportement transparent aux autres modules.
     */
    private ObservableList<Message> observableMessages;

    /**
     * Liste des listeners mappée à la liste des messages du Channel.
     */
    ListChangeListener<Message> messageListListener;

    /**
     * Constructeur
     */
    public ChannelMessagesController(){
        //cette ligne ne sert qu'à la création d'objets de la classe Message en dur pour les tests.
        connectedUser = new UserLite(UUID.randomUUID(), "Léa", null);
    }

<<<<<<< HEAD
    public void setCurrentChannel(Channel channel){
        this.channel = channel;
//        System.out.println(channel);
        try{
            displayMessagesList();
        }
        catch (Exception e){
            System.out.println("Problème lors de l'affichage des messages");
        }
=======
    /**
     * Getter du contrôleur principal de IHM-Channel
     * @return le contrôleur principal de IHM-Channel
     */
    public IHMChannelController getIhmChannelController() {
        return ihmChannelController;
    }
>>>>>>> integration_POC_et_CSS

    /**
     * Setter du contrôleur principal de IHM-Channel
     * @param ihmChannelController le contrôleur principal de IHM-Channel
     */
    public void setIhmChannelController(IHMChannelController ihmChannelController) {
        this.ihmChannelController = ihmChannelController;
    }

    /**
     * Getter de parentMessage
     * @return parentMessage
     */
    public Message getParentMessage() {
        return parentMessage;
    }

    /**
     * Setter de parentMessage
     * @param parentMessage parentMessage à affecter
     */
    public void setParentMessage(Message parentMessage) {
        this.parentMessage = parentMessage;
    }

    /**
     * Getter de messagesToDisplay
     * @return messagesToDisplay
     */
    public ObservableList<HBox> getMessagesToDisplay() {
        return messagesToDisplay;
    }

    /**
     * Setter de messagesToDisplay.
     * Génère à nouveau une liste d'observables à partir de la liste des messages du channel lié à cette vue.
     */
    public void setMessagesToDisplay() {
        messagesToDisplay = FXCollections.observableArrayList();
        this.channel.getMessages().forEach(message -> {
            try {
                this.messagesToDisplay.add((HBox) new MessageDisplay(message).root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void initialize() throws IOException {
        //Icone envoyer
        Image sendImage = new Image("IHMChannel/icons/paper-plane-solid.png");
        ImageView sendIcon = new ImageView(sendImage);
        sendIcon.setFitHeight(15);
        sendIcon.setFitWidth(15);
        sendBtn.setGraphic(sendIcon);

        // Définition listener sur la liste de messages
        messageListListener = changed -> {
            changed.next();
            if(changed.wasAdded()){
                for(Message msgAdded : changed.getAddedSubList()){
                    try {
                        getMessagesToDisplay().add((HBox) new MessageDisplay(msgAdded).root);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }


    /**
     * Permet de mettre à jour la liste d'observables pour l'affichage des messages.
     * @param message message à ajouter
     */
    public void addMessageToObservableList(Message message){
        observableMessages.add(message);
    }

    /**
     * Setter du channel correspondant à la liste de messages gérée par la vue attachée à ce contrôleur.
     * Cette méthode est appelée lors de l'initialisation de la vue.
     * @param channel channel à affecter
     */
    public void setCurrentChannel(Channel channel){
        this.channel = channel;
        System.out.println(channel);
        try{
            displayMessagesList();
        }
        catch (Exception e){
            System.out.println("Problème lors de l'affichage des messages");
        }

        //setMessagesToDisplay();

        // initialisation de la liste des messages observés et de leurs listeners.
        observableMessages = FXCollections.observableArrayList(this.channel.getMessages());
        observableMessages.addListener(messageListListener);
    }


    /**
     * Méthode déclenchée au clic sur le bouton d'envoi de message.
     */
    public void sendMessage() throws IOException {
        if(!typedText.getText().isEmpty()){
            //ATTENTION l'id du message est écrit en dur, on ne sait pas comment il est généré pour le moment.
            // Ne paraît pas logique qu'il soit généré par IHM Channel, donc penser à un constructeur sans id
            Message newMsg = new Message(0,typedText.getText(),connectedUser);
            ihmChannelController.getInterfaceToCommunication().sendMessage(newMsg, channel, parentMessage);
            //messagesToDisplay.add((HBox)new MessageDisplay(new Message(1,typedText.getText(),connectedUser)).root);
            typedText.setText("");
        }
    }

    /**
     * Méthode de test déclenchée à l'appui sur le bouton "test réception"
     * Génère l'ajout d'un message dans la liste de messages du channel.
     */
    public void receiveMessage(){
        // cet appel est juste pour les test
       //Message message = new Message(2, "message reçu test", connectedUser);
         getIhmChannelController().getInterfaceForData().receiveMessage(new Message(2, "message reçu test", connectedUser),
                          this.channel, null);

        //Message newMsg = new Message(99,"Salut, je suis un message reçu via le bouton de test",connectedUser);
        //this.channel.addMessage(newMsg);
    }

    /**
     * Initialise l'affichage de la liste des messages contenus dans l'attribut channel de la classe.
     * Chaque message est affiché grâce à la vue views/Message.fxml loadée par les instances de la classes MessageDisplay
     */
    private void displayMessagesList() throws IOException {
        getMessagesToDisplay().removeAll(); //réinitialisation
        for (Message msg : this.channel.getMessages()){
            getMessagesToDisplay().add((HBox) new MessageDisplay(msg).root);
        }
        listMessages.setItems(getMessagesToDisplay());
    }

}
