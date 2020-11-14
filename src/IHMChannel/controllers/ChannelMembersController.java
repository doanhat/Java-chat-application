package IHMChannel.controllers;

import IHMChannel.MemberDisplay;
import IHMChannel.MessageDisplay;
import common.sharedData.Channel;
import common.sharedData.Message;
import common.sharedData.UserLite;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

import java.io.IOException;

/**
 * Contrôleur de la vue "ChannelMembers" qui contient la liste des membres d'un channel et les options d'affichage de cette liste
 */
public class ChannelMembersController {
    @FXML
    ListView membersList;
    @FXML
    ToggleGroup modeAffichage;
    @FXML
    Toggle alphaBtn;

    ObservableList<HBox> membersToDisplay = FXCollections.observableArrayList();
    Channel channel;
    ListChangeListener<UserLite> membersListListener;
    ListChangeListener<UserLite> adminsListListener;

    /**
     * Setter du channel
     * Met à jour la liste des membres en conséquence
     * @param channel
     */
    public void setChannel(Channel channel){
        this.channel = channel;
        try {
            displayMessagesList();
        } catch (IOException e) {
            System.out.println("Erreur lors de l'affichage des membres du channel");
            e.printStackTrace();
        }
        this.channel.getAcceptedPersons().addListener(membersListListener);
        this.channel.getAdministrators().addListener(adminsListListener);
    }

    //TODO gérer les radio buttons /!\ listener

    public void initialize() throws IOException {
        //Par défaut, sélection de l'affichage alphabétique :
        alphaBtn.setSelected(true);

        // Définition listener sur la liste de membres
        membersListListener = changed -> {
            changed.next();
            if(changed.wasAdded()){
                for(UserLite usr : changed.getAddedSubList()){
                    try {
                        membersToDisplay.add((HBox) new MemberDisplay(usr).root);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            else if(changed.wasRemoved()){
                //TODO suppression du contrôle correspondant au membre supprimé
            }
        };

        //Listener sur la liste d'admins
        adminsListListener = changed -> {
            changed.next();
            if(changed.wasAdded()){
                //TODO ajout de l'admin
                // = mise à jour du toggle sur true
            }
            else if(changed.wasRemoved()){
                //TODO suppression du contrôle correspondant à l'admin supprimé
                // = mise à jour du toggle sur false
            }
        };

        //Listener sur le radio button de tri
        modeAffichage.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                RadioButton selectedRadioButton = (RadioButton) modeAffichage.getSelectedToggle();
                if (selectedRadioButton.getId().equals("alphaBtn")) {
                    System.out.println("Ordre alphabétique");
                    //TODO implémenter le tri par ordre alphabétique
                }
                else if(selectedRadioButton.getId().equals("autorBtn")){
                    System.out.println("Par autorisations");
                    //TODO implémenter le tri par autorisations
                }
                else if(selectedRadioButton.getId().equals("onlineBtn")){
                    System.out.println("Si connecté");
                    //TODO implémenter le tri par connecté / non connecté
                }
                else{
                    System.out.println("Il y a eu une erreur dans le tri des membres");
                }
            }
        });
    }

    /**
     * Initialise l'affichage de la liste des membres (acceptedPerson) contenus dans l'attribut channel de la classe
     */
    private void displayMessagesList() throws IOException {
        membersToDisplay.removeAll(); //réinitialisation
        for (UserLite usr : this.channel.getAcceptedPersons()){
            membersToDisplay.add((HBox) new MemberDisplay(usr).root);
        }
        membersList.setItems(membersToDisplay);
    }
}
