package IHMChannel.controllers;

import IHMChannel.MessageDisplay;
import common.sharedData.Channel;
import common.sharedData.Message;
import common.sharedData.UserLite;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.IOException;

/**
 * Contrôleur de la vue "ChannelMessages" dans laquelle on retrouve l'affichage et la saisie de messages d'un channel
 */
public class ChannelMessagesController {
    UserLite connectedUser; //tmp
    Channel channel;

    @FXML
    ListView listMessages;
    @FXML
    ImageView profilePic;
    @FXML
    TextArea typedText;
    @FXML
    Button sendBtn;

    //Liste de HBox (= contrôle message)
    ObservableList<HBox> messagesToDisplay = FXCollections.observableArrayList();

    public void setChannel(Channel channel){
        this.channel = channel;
        System.out.println(channel);
        try{
            displayMessagesList();
        }
        catch (Exception e){
            System.out.println("Problème lors de l'affichage des messages");
        }


    }

    public ChannelMessagesController(){
        connectedUser = new UserLite();
        connectedUser.setNickName("Léa");
    }
    public void initialize() throws IOException {

        //Icone envoyer
        Image sendImage = new Image("IHMChannel/icons/paper-plane-solid.png");
        ImageView sendIcon = new ImageView(sendImage);
        sendIcon.setFitHeight(15);
        sendIcon.setFitWidth(15);
        sendBtn.setGraphic(sendIcon);

        /*
        //Messages
        HBox tmp = (HBox) new MessageDisplay(new Message(1,"Salut",connectedUser)).root;
        HBox tmp2 = (HBox) new MessageDisplay(new Message(2,"Comment ça va ?",connectedUser)).root;
        messagesToDisplay.add(tmp);
        messagesToDisplay.add(tmp2);
        listMessages.setItems(messagesToDisplay);
         */
    }

    /**
     * Méthode déclenchée au clic sur le bouton d'envoi de message.
     */
    public void sendMessage() throws IOException {

        //TODO
        /*
        if(!typedText.getText().isEmpty()){
            messagesToDisplay.add((HBox)new MessageDisplay(new Message(1,typedText.getText(),connectedUser)).root);
            typedText.setText("");
        }
         */
    }

    /**
     * Initialise l'affichage de la liste des messages contenus dans l'attribut channel de la classe
     */
    private void displayMessagesList() throws IOException {
        //TODO :
        //Réinitialiser la liste actuelle de HBox (listMessages)
        //Balayer la liste de messages du channel :
            // Pour chaque message, on créé une HBox (MessageDisplay) et on l'ajoute à la liste
        //listMessages.setItems

        messagesToDisplay.removeAll(); //réinitialisation
        for (Message msg : this.channel.getMessages()){
            messagesToDisplay.add((HBox) new MessageDisplay(msg).root);
        }
        listMessages.setItems(messagesToDisplay);

    }
}
