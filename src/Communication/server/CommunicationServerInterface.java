package Communication.server;

import Communication.common.Parameters;
import common.interfaces.server.IServerDataToCommunication;

public class CommunicationServerInterface implements IServerDataToCommunication {

    private final CommunicationServerController commController;

    public CommunicationServerInterface(CommunicationServerController commServerController) {
        this.commController = commServerController;
    }

    @Override
    public void setIP(String addressIP) {
        Parameters.SERVER_IP = addressIP;
    }

    @Override
    public void setPort(int port) {
        Parameters.PORT = port;
    }

    @Override
    public void start() {
        commController.start();
    }

    @Override
    public void stop() {
        commController.stop();
    }
}
