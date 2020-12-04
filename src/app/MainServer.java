package app;
import Data.server.DataServerController;
import Communication.server.CommunicationServerController;
import common.sharedData.UserLite;

import java.util.UUID;

public class MainServer {


    public static void main (String [] argv) {
        DataServerController dataServerController = new DataServerController();
        CommunicationServerController commServerController = new CommunicationServerController();
        commServerController.setIServerCommunicationToData(dataServerController.getIServerCommunicationToData());
        commServerController.start(); //lancement du server
    }
}
