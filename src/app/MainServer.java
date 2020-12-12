package app;
import common.shared_data.Channel;
import common.shared_data.ChannelType;
import common.shared_data.UserLite;
import common.shared_data.Visibility;
import data.server.DataServerController;
import Communication.server.CommunicationServerController;

public class MainServer {


    public static void main (String [] argv) {
        DataServerController dataServerController = new DataServerController();
        CommunicationServerController commServerController = new CommunicationServerController();
        commServerController.setIServerCommunicationToData(dataServerController.getIServerCommunicationToData());
        commServerController.start(); //lancement du server
    }
}
