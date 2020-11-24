package IHMChannel;

import IHMChannel.controllers.ChannelController;
import IHMChannel.controllers.ChannelPageController;
import IHMChannel.interfaces.CommunicationToIHMChannel;
import IHMChannel.interfaces.DataToIHMChannel;
import IHMChannel.interfaces.IHMMainToIHMChannel;
import common.interfaces.client.*;
import common.sharedData.*;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Controller principal de IHMChannel
 */
public class IHMChannelController {
    private ChannelPageController channelPageController;
    private Parent root;
    /**
     * Constructeur
     */
    public IHMChannelController() {


        // Initialisation des interfaces
        interfaceForCommunication = new CommunicationToIHMChannel(this);
        interfaceForData = new DataToIHMChannel(this);
        interfaceForIHMMain = new IHMMainToIHMChannel(this);
        // TODO: récupérer le channel depuis l'appel de getChannelWindow()
        try{
            Channel newOwnedChannel = new Channel("LO23",new UserLite("Aïda", null),"channel pour l'UV LO23", Visibility.PUBLIC, ChannelType.OWNED);

            UserLite usr1 = new UserLite("Aïda", null);
            usr1.setNickName("toto");
            UserLite usr2 = new UserLite("Léa", null);
            usr2.setNickName("titi");

            List<Message> listMessages = new ArrayList<Message>();
            listMessages.add(new Message("Salut, vous allez bien ?",usr1));
            listMessages.add(new Message("Oui super et toi ?",usr2));
            listMessages.add(new Message("T'as avancé le projet LO23 ?",usr1));

            newOwnedChannel.setMessages(listMessages);
            ChannelPageDisplay channelPageDisplay = new ChannelPageDisplay(newOwnedChannel, this);
            channelPageDisplay.getChannelPageController().getChannelController(newOwnedChannel.getId()).setChannel(newOwnedChannel);

        }catch(IOException e){
            e.printStackTrace();
        }


    }

    /**
     * Getter de l'interface vers Data
     * @return interface vers Data
     */
    public IIHMChannelToData getInterfaceToData() {
        return interfaceToData;
    }

    /**
     * Setter de l'interface vers Data
     * @param interfaceToData interface vers Data
     */
    public void setInterfaceToData(IIHMChannelToData interfaceToData) {
        this.interfaceToData = interfaceToData;
    }

    /**
     * Getter de l'interface vers Communication
     * @return Interface vers communication
     */
    public IIHMChannelToCommunication getInterfaceToCommunication() {
        return interfaceToCommunication;
    }

    /**
     * Setter de l'interface vers communication
     * @param interfaceToCommunication Interface vers Communication
     */
    public void setInterfaceToCommunication(IIHMChannelToCommunication interfaceToCommunication) {
        this.interfaceToCommunication = interfaceToCommunication;
    }

    /**
     * Getter de l'interface vers IHMMain
     * @return interface vers IHMMain
     */
    public IIHMChannelToIHMMain getInterfaceToIHMMain() {
        return interfaceToIHMMain;
    }

    /**
     * Setter de l'interface vers IHMMain
     * @param interfaceToIHMMain interface vers IHMMain
     */
    public void setInterfaceToIHMMain(IIHMChannelToIHMMain interfaceToIHMMain) {
        this.interfaceToIHMMain = interfaceToIHMMain;
    }

    /**
     * Getter de l'interface pour IHMMain vers IHMChannel
     * @return l'interface pour IHMMain vers IHMChannel
     */
    public IHMMainToIHMChannel getInterfaceForIHMMain() {
        return interfaceForIHMMain;
    }

    /**
     * Getter de l'interface pour Communication vers IHMChannel
     * @return l'interface pour Communication vers IHMChannel
     */
    public ICommunicationToIHMChannel getInterfaceForCommunication() {
        return interfaceForCommunication;
    }

    /**
     * Getter de l'interface pour Data vers IHMChannel
     * @return l'interface pour Data vers IHMChannel
     */
    public IDataToIHMChannel getInterfaceForData() {
        return interfaceForData;
    }

    private IIHMChannelToData interfaceToData;

    private IIHMChannelToCommunication interfaceToCommunication;

    private IIHMChannelToIHMMain interfaceToIHMMain;

    private IHMMainToIHMChannel interfaceForIHMMain;

    private CommunicationToIHMChannel interfaceForCommunication;

    private DataToIHMChannel interfaceForData;


    public ChannelPageController getChannelPageController() {
        return channelPageController;
    }

    public void setChannelPageController(ChannelPageController channelPageController) {
        this.channelPageController = channelPageController;
    }

    public Channel initTestData() throws IOException {
        Channel newOwnedChannel = new Channel("LO23",new UserLite("Aïda", null),"channel pour l'UV LO23", Visibility.PUBLIC, ChannelType.OWNED);

        UserLite usr1 = new UserLite( "Aïda", null);
        usr1.setNickName("toto");
        UserLite usr2 = new UserLite("Léa", null);
        usr2.setNickName("titi");

        List<Message> listMessages = new ArrayList<Message>();
        listMessages.add(new Message("Salut, vous allez bien ?",usr1));
        listMessages.add(new Message("Oui super et toi ?",usr2));
        listMessages.add(new Message("T'as avancé le projet LO23 ?",usr1));


        return newOwnedChannel;
    }

    public Parent getRoot() {
        return this.root;
    }

    public void setRoot(Parent root) {
        this.root = root;
    }
}
