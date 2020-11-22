package tests.communication;

import Communication.client.CommunicationClientController;
import Communication.common.Parameters;
import Communication.server.CommunicationServerController;
import common.sharedData.User;
import common.sharedData.UserLite;

import java.util.Scanner;

public class CommClientConnectionTest {
    public static void main(String[] args)
    {
        CommunicationClientController commClient = new CommunicationClientController();

        Scanner reader = new Scanner(System.in);

        System.out.print("Enter pseudo: ");

        String username = reader.nextLine();

        UserLite localUser = new UserLite(username, "dernier maitre de l'air");

        commClient.start(Parameters.SERVER_IP, Parameters.PORT, localUser);

        try {
            Thread.sleep(5000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        commClient.stop();
    }
}
