package common.interfaces.server;

public interface IServerDataToCommunication {

    public void setIP(String addressIP);

    public void setPort(int port);

    public void start();

    public void stop();
}
