package IHMChannel.controllers;

import IHMChannel.IHMChannelController;
import common.shared_data.*;
import javafx.application.Platform;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.util.*;

/**
 * Contrôleur de la partie Channel de l'interface.
 */
public class ChannelPageController {
    UUID currentChannel; //channel à afficher dans l'interface
    UserLite connectedUser; //tmp
    ObservableSet<Channel> openedChannels; //channel auxquels l'utilisateur est connecté
    HashMap<UUID, ChannelController> channelMap;
    private IHMChannelController ihmChannelController;

    @FXML
    BorderPane pageToDisplay;

    @FXML
    TabPane tabs;



    public void addOpenedChannel(Channel channel) throws IOException {

        openedChannels.add(channel);
        currentChannel = channel.getId();
        ihmChannelController.getInterfaceToIHMMain().setCurrentVisibleChannel(channel);

        //Création du nouvel onglet pour le channel ajouté

        FXMLLoader fxmlLoader =
                new FXMLLoader(getClass().getResource("/IHMChannel/views/Channel.fxml"));
        Parent root = fxmlLoader.load();

        ChannelController ctrl = fxmlLoader.getController();
        ctrl.setIhmChannelController(ihmChannelController);
        ctrl.getIhmChannelController().setChannelPageController(this);
        ctrl.configureMessageDisplay(ihmChannelController);
        ctrl.configureMemberDisplay(ihmChannelController);
        ctrl.setChannel(channel);
        ctrl.setChannelPageController(this);


        Tab tab = new Tab(channel.getName());
        tab.setId(channel.getId().toString());
        tab.setOnClosed((event -> {
            this.openedChannels.remove(channel);
            this.channelMap.remove(channel.getId());
            /* On notifie Comm de la fermeture de l'onglet */
            ihmChannelController.getInterfaceToCommunication().leaveChannel(channel);
            /* On notifie IHM-Main avec la nouvelle liste de channels ouverts */
            ihmChannelController.getInterfaceToIHMMain().setOpenedChannelsList(ihmChannelController.getOpenedChannelsList());
            /* On notifie IHM-Main avec le nouveau currentOpenedChannel dans le handler de changement de tab */
            /* On revient à la page d'accueil si plus aucun channel à afficher */
            if (openedChannels.isEmpty()) {
                ihmChannelController.getInterfaceToIHMMain().redirectToHomePage();
            }
        }));

        tab.setOnSelectionChanged(e ->
                {
                    if (tab.isSelected()) {
                        handleChangeTab(channel);
                    }
                }
        );


        tabs.getTabs().add(tab);
        tab.setContent((Node) root);
        tabs.getSelectionModel().select(tab);

        Region r = (Region) root;

        r.prefHeightProperty().bind(tab.getTabPane().heightProperty());
        r.prefWidthProperty().bind(tab.getTabPane().widthProperty());
        r.minHeightProperty().bind(tab.getTabPane().minHeightProperty());
        r.minWidthProperty().bind(tab.getTabPane().minWidthProperty());


        channelMap.put(channel.getId(), ctrl);

    }

    /**
     * Constructeur : Appelé par le FXML Loader.
     * On y fait l'initialisation des données (et non pas de l'affichage)
     */
    public ChannelPageController() {
        //initialisation de oppenedChannel
        openedChannels = FXCollections.observableSet();
        channelMap = new HashMap<>();

    }

    /**
     * Automatically called by FXML Loader
     */
    public void initialize() throws IOException {
        /* Dans cette méthode, on met tout ce qui doit être fait à l'init de la vue.
        Par exemple, le chargement des messages du channel, l'affichage de la photo de profil de l'utilisateur connecté près de la zone de message,...
        Cette méthode contient aussi les LISTENERS
        */


    }



    public IHMChannelController getIhmChannelController() {
        return ihmChannelController;
    }

    public void setIhmChannelController(IHMChannelController ihmChannelController) {
        this.ihmChannelController = ihmChannelController;
    }

    public ChannelController getChannelController(UUID channelId) {
        return channelMap.get(channelId);
    }



    public ObservableSet<Channel> getOpenedChannels() {
        return this.openedChannels;
    }

    public void leaveChannel(UUID channelID, UserLite u) {
        /**
         * Si il y'a une pool exeception rajouter Platform.RunLater(new Runnable() { .... })
         */
        Channel channel = null;
        //On recupere le channel en question
        for (Channel c : openedChannels) {
            if (c.getId().equals(channelID))
                channel = c;
        }

        TabPane toRemove = new TabPane();

        if (channel.getCreator().getId().equals(u.getId())) { //si celui qui quitte le channel est le proprio on ferme le channel

            for (Tab t : tabs.getTabs()) {
                if (t.getId().equals(channel.getId().toString())) {
                    toRemove.getTabs().add(t);
                }
            }
            tabs.getTabs().removeAll(toRemove.getTabs());
        }

        openedChannels.remove(channel);
        channelMap.remove(channelID);


    }

    /**
     * Handler pour le changement de Tab
     *
     * @param channel
     */
    public void handleChangeTab(Channel channel) {
        currentChannel = channel.getId();
        ihmChannelController.getInterfaceToIHMMain().setCurrentVisibleChannel(channel);
    }

    public void changeTab(Channel channel) {
        for (Tab c : tabs.getTabs()) {
            if (c.getId().equals(channel.getId().toString())) {
                tabs.getSelectionModel().select(c);
            }
        }
    }

    /**
     * Supprime une tab d'un channel
     * Par exemple lors que le propriétaire du channel disparait ou que l'on ai kick du channel
     *
     * @param channelID
     */
    public void removeTab(UUID channelID) {
        tabs.getTabs().removeIf(tab -> tab.getId().equals(channelID.toString()));
        this.openedChannels.removeIf(channel -> channel.getId().equals(channelID));
        /* On notifie IHM-Main avec la nouvelle liste de channels ouverts */
        ihmChannelController.getInterfaceToIHMMain().setOpenedChannelsList(ihmChannelController.getOpenedChannelsList());
        /* On revient à la page d'accueil si plus aucun channel à afficher */
        if (openedChannels.isEmpty()) {
            ihmChannelController.getInterfaceToIHMMain().redirectToHomePage();
        }
        /* On revient à la page d'accueil si plus aucun channel à afficher */
        if (openedChannels.isEmpty()) {
            ihmChannelController.getInterfaceToIHMMain().redirectToHomePage();
        }
    }

    /**
     * Permet de retirer le channel d'ID channelID des channels s (utilisé dans l'interface de DataToIHMChannel quand un channel est fermé par son créateur)
     *
     * @param channelID ID du channel concerné
     */
    public void removeChannel(UUID channelID) {
        Channel channel = null;
        for (Channel c : openedChannels) {
            if (c.getId().equals(channelID))
                channel = c;
        }
        if (channel != null) {
            String tmp = channel.getName();
            tabs.getTabs().removeIf(c -> c.getId().equals(tmp));
            openedChannels.remove(channel);
        }
        channelMap.remove(channelID);
        if (openedChannels.isEmpty()) {
            ihmChannelController.getInterfaceToIHMMain().redirectToHomePage();
        }
    }

    public void quitChannel(UUID channelId) {
        Platform.runLater(() -> removeChannel(channelId));

        /* On notifie IHM-Main avec la nouvelle liste de channels ouverts */
        ihmChannelController.getInterfaceToIHMMain().setOpenedChannelsList(ihmChannelController.getOpenedChannelsList());
        /* On notifie IHM-Main avec le nouveau currentOpenedChannel dans le handler de changement de tab */

    }


    public void modifyChannel(UUID channelId, String name, String description, Visibility visibility) {

        Tab tab = new Tab();
        for (Tab t : tabs.getTabs()) {
            if(t.getId().equals(channelId.toString())){
                tab = t;
            }
        }
        tab.setText(name);

        for(Channel c: openedChannels) {
            if (c.getId().equals(channelId))
                updateOppenedChannel(c, name, description, visibility);
        }


        channelMap.get(channelId).updateUI(name, description);
    }

    public void updateOppenedChannel(Channel c, String name, String desc, Visibility visibility){
        c.setName(name);
        c.setDescription(desc);
        c.setVisibility(visibility);
    }
}
