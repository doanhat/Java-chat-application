package common.interfaces.server;

public interface IServerDataToCommunication {

    void setIP(String addressIP);

    void setPort(int port);

    void start();

    void stop();
}
