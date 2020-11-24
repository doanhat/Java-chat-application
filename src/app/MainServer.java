package app;
import Data.server.DataServerController;
import Communication.server.CommunicationServerController;

public class MainServer {


    public static void main (String [] argv) {
        DataServerController dataServerController = new DataServerController();
        CommunicationServerController commServerController = CommunicationServerController.instance();
        commServerController.setupInterfaces(dataServerController.getIServerCommunicationToData());
        commServerController.start(); //lancement du server
    }
}
