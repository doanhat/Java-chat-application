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
        int port;
        try {
            port = Integer.parseInt(argv[0]);
        }
        catch(NumberFormatException e){
            port = 8080;
        }
        commInterface.setPort(port);

        commInterface.start(); //lancement du server
    }
}
