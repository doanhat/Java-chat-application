package app;
import common.interfaces.server.IServerDataToCommunication;
import data.server.DataServerController;
import Communication.server.CommunicationServerController;

public class MainServer {


    public static void main (String [] argv) {
        DataServerController dataServerController = new DataServerController();
        CommunicationServerController commServerController = new CommunicationServerController();
        commServerController.setIServerCommunicationToData(dataServerController.getIServerCommunicationToData());

        IServerDataToCommunication commInterface = commServerController.getDataToCommunication();
        dataServerController.setIServerDataToCommunication(commInterface);

        // Setup IP:port for server
        commInterface.setIP("127.0.0.1");
        commInterface.setPort(commInterface.getPort());

        commInterface.start(); //lancement du server
    }
}
