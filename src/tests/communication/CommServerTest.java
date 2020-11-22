package tests.communication;

import Communication.server.CommunicationServerController;

public class CommServerTest {

    public static void main(String[] args)
    {
        CommunicationServerController commServer = new CommunicationServerController();

        commServer.start();

        while(true) {

        }
    }
}
