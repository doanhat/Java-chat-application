package app;
import common.interfaces.server.IServerDataToCommunication;
import data.server.DataServerController;
import communication.server.CommunicationServerController;

public class MainServer {


    public static void main (String [] argv) {
        DataServerController dataServerController = new DataServerController();
        CommunicationServerController commServerController = new CommunicationServerController();
        commServerController.setIServerCommunicationToData(dataServerController.getIServerCommunicationToData());

        IServerDataToCommunication commInterface = commServerController.getDataToCommunication();
        dataServerController.setIServerDataToCommunication(commInterface);

        // Setup IP:port for server
        commInterface.setIP("0.0.0.0");
        commInterface.setPort(commInterface.getPort());

        commInterface.start(); //lancement du server
    }
}
