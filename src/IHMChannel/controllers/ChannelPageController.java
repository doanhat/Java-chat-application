package IHMChannel.controllers;

import IHMChannel.IHMChannelController;
import common.IHMTools.IHMTools;
import common.shared_data.*;
import javafx.application.Platform;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.*;

/**
 * Contrôleur de la partie Channel de l'interface.
 * TODO ajouter les interfaces
 */
public class ChannelPageController {
    UUID currentChannel; //channel à afficher dans l'interface
    UserLite connectedUser; //tmp
    ObservableSet<Channel> openedChannels; //channel auxquels l'utilisateur est connecté
    HashMap<UUID, ChannelController > channelMap;
    private IHMChannelController ihmChannelController;

    @FXML
    Button back;
    @FXML
    Text channelName;
    @FXML
    Text channelDescription;
    @FXML
    Button seeMembersBtn;
    @FXML
    Button addMemberBtn;
    @FXML
    Button leaveChannelBtn;
    @FXML
    Button addUserBtn; //pour test
    @FXML
    Button removeUserBtn; //pour test

    @FXML
    private TextField canalText;

    @FXML
    private Button createCanalBtn;

    @FXML
    BorderPane pageToDisplay;

    @FXML
    TabPane tabs;

    //Pour tests
    UserLite userTemp = new UserLite();


    public void addOpenedChannel(Channel channel) throws IOException {

        openedChannels.add(channel);
        currentChannel = channel.getId(); //TODO mieux gérer la mise à jour de cette valeur
        ihmChannelController.getInterfaceToIHMMain().setCurrentVisibleChannel(channel);

        //Création du nouvel onglet pour le channel ajouté

        FXMLLoader fxmlLoader =
                new FXMLLoader(getClass().getResource("/IHMChannel/views/Channel.fxml"));
        Parent root = fxmlLoader.load();

        ChannelController ctrl = fxmlLoader.getController();
        //ctrl.setChannel(channel);
        ctrl.setIhmChannelController(ihmChannelController);
        ctrl.getIhmChannelController().setChannelPageController(this);
        ctrl.configureMessageDisplay(ihmChannelController);
        ctrl.configureMemberDisplay(ihmChannelController);
        ctrl.setChannel(channel);


        Tab tab = new Tab(channel.getName());
        tab.setId(channel.getId().toString());
        tab.setOnClosed((event->{
            this.openedChannels.remove(channel);
            this.channelMap.remove(channel.getId());
            /* On notifie Comm de la fermeture de l'onglet */
            ihmChannelController.getInterfaceToCommunication().leaveChannel(channel);
            /* On notifie IHM-Main avec la nouvelle liste de channels ouverts */
            ihmChannelController.getInterfaceToIHMMain().setOpenedChannelsList(ihmChannelController.getOpenedChannelsList());
            /* On notifie IHM-Main avec le nouveau currentOpenedChannel dans le handler de changement de tab */
            /* On revient à la page d'accueil si plus aucun channel à afficher */
            if(openedChannels.isEmpty()){
                ihmChannelController.getInterfaceToIHMMain().redirectToHomePage();
            }
        }));

        tab.setOnSelectionChanged (e ->
                {
                    if (tab.isSelected()) {
                        handleChangeTab(channel);
                    } else {
                        System.out.println("Unselected");
                    }
                }
        );


        tabs.getTabs().add(tab);
        tab.setContent((Node) root);
        tabs.getSelectionModel().select(tab);
        //tab1.setGraphic(root);


        channelMap.put(channel.getId(), ctrl);

    }

    /**
     * Constructeur : Appelé par le FXML Loader.
     * On y fait l'initialisation des données (et non pas de l'affichage)
     */
    public ChannelPageController(){
        //tmp
        // permet d'avoir un utilisateur temporaire pour l'affichage des messages
        connectedUser = new UserLite("newUser", null);
        connectedUser.setNickName("Léa");

        //initialisation de oppenedChannel
        openedChannels = FXCollections.observableSet();
        channelMap = new HashMap<>();

        userTemp.setId(UUID.randomUUID());
        userTemp.setNickName("Clément");
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


    /**
     * Méthode déclenchée au clic sur le bouton "voir les membres"
     */
    public void seeMembers(){
//        channelMap.get(currentChannel).seeMembers();
    }

    /**
     * Méthode déclenchée au clic sur le bouton "ajouter un membre"
     */
    public void addUserToChannel(){

    }

    /**
     * Méthode de test déclenchée à l'appui sur le bouton "test réception"
     * Génère l'ajout d'un message dans la liste de messages du channel.
     */
    public void receiveMessage(){
        //channelMap.get(currentChannel).receiveMessage();
    }


    public IHMChannelController getIhmChannelController() {
        return ihmChannelController;
    }

    public void setIhmChannelController(IHMChannelController ihmChannelController) {
        this.ihmChannelController = ihmChannelController;
    }

    public ChannelController getChannelController(UUID channelId){
        return channelMap.get(channelId);
    }

    @FXML
        // Test method for dev
    void createChannel(){
        String channelName = canalText.getText();
        int count = 0;
        for (Channel c : openedChannels) {
            if (c.getName().equals(channelName)) {
                count = 1;
                break;
            }
        }
        if (count == 0) {

            Channel c = new Channel(channelName, new UserLite("Léa", null), "channel pour l'UV " + channelName, Visibility.PUBLIC,ChannelType.OWNED);
            //Membres connectés
            List<String> nickName = new ArrayList<>();
            nickName.add("Léa");
            nickName.add("Aida");
            nickName.add("Lucas");
            nickName.add("Vladimir");
            nickName.add("Jérôme");
            nickName.add("Van-Triet");
            List<UserLite> connectedUsers = new ArrayList<>();
            for(int i=0; i < nickName.size(); i++){
                UserLite u = new UserLite();
                u.setNickName(nickName.get(i));
                connectedUsers.add(u);
            }
            List<Message> history = new ArrayList<>();
            for(int i=0; i < 7; i++){
                Message m = new Message();
                m.setAuthor(connectedUsers.get((i+1)%connectedUsers.size()));
                m.setMessage("hello " + connectedUsers.get((i+2)%connectedUsers.size()).getNickName());
                m.setDate(new Date());
                history.add(m);
            }
            ihmChannelController.getInterfaceForCommunication().displayChannelHistory(c, history, connectedUsers);
            //this.addOpenedChannel(c);
        }
        else {
            for (Tab c : tabs.getTabs()) {
                if (c.getId().equals(channelName)) {
                    tabs.getSelectionModel().select(c);
                }
            }
        }
    }

    @FXML
    /**
     * Pour tester l'interface proposée à comm
     */
    public void addUser(){
        ihmChannelController.getInterfaceForCommunication().addConnectedUser(currentChannel, userTemp);
    }

    @FXML
    /**
     * Pour tester l'interface proposée à comm
     */
    public void removeUser(){
        ihmChannelController.getInterfaceForCommunication().removeConnectedUser(currentChannel, userTemp);
    }

    public ObservableSet<Channel> getOpenedChannels() {
        return this.openedChannels;
    }

    public void leaveChannel(UUID channelID, UserLite u) {
        /**
         * TODO: Vérifier si cela fonctionne
         * Si il y'a une pool exeception rajouter Platform.RunLater(new Runnable() { .... })
         */
        Channel channel = null;
        //On recupere le channel en question
        for(Channel c: openedChannels) {
            if (c.getId().equals(channelID))
                channel = c;
        }

        TabPane toRemove = new TabPane();

        if (channel.getCreator().getId().equals(u.getId())) { //si celui qui quitte le channel est le proprio on ferme le channel

            for (Tab t : tabs.getTabs()) {
                    if(t.getId().equals(channel.getId().toString())){
                        toRemove.getTabs().add(t);
                    }
            }
            tabs.getTabs().removeAll(toRemove.getTabs());
        }

        openedChannels.remove(channel);
        channelMap.remove(channelID);


        if(openedChannels.size() == 0) { //rediriger vers la main page
            ihmChannelController.getInterfaceToIHMMain().redirectToHomePage();
        }

    }

    /**
     * Handler pour le changement de Tab
     * @param channel
     */
    public void handleChangeTab(Channel channel) {
        currentChannel = channel.getId();
        ihmChannelController.getInterfaceToIHMMain().setCurrentVisibleChannel(channel);
    }

    /**
     * Permet de retirer le channel d'ID channelID des channels s (utilisé dans l'interface de DataToIHMChannel quand un channel est fermé par son créateur)
     * @param channelID ID du channel concerné
     */
    public void removeChannel(UUID channelID) {
        Channel channel = null;
        for(Channel c: openedChannels) {
            if (c.getId().equals(channelID))
                channel = c;
        }
        if(channel !=null){
            String tmp = channel.getName();
            tabs.getTabs().removeIf(c -> c.getId().equals(tmp));
            openedChannels.remove(channel);
        }
        channelMap.remove(channelID);
        if(openedChannels.isEmpty()) {
            ihmChannelController.getInterfaceToIHMMain().redirectToHomePage();
        }
    }
}
