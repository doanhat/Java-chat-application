package common.interfaces.server;

import common.shared_data.Channel;
import common.shared_data.UserLite;

public interface IServerDataToCommunication {

    void setIP(String addressIP);

    void setPort(int port);

    String getIP();

    int getPort();

    void start();

    void stop();

    void informUsersBanRemoved(Channel channel, UserLite bannedUser);
}
