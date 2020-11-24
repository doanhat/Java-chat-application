package tests.communication;

import Communication.client.CommunicationClientController;
import Communication.common.Parameters;
import common.sharedData.UserLite;

import java.util.Scanner;

public class CommClientConnectionTest {
    public static void main(String[] args)
    {
        CommunicationClientController commClient = CommunicationClientController.instance();

        Scanner reader = new Scanner(System.in);

        System.out.print("Enter pseudo: ");

        String username = reader.nextLine();

        UserLite localUser = new UserLite(username, "dernier maitre de l'air");

        commClient.start(Parameters.SERVER_IP, Parameters.PORT, localUser);

        while (true) {
            System.out.print("Disconnect: ");

            if("exit".equals(reader.nextLine())) {
                break;
            }
        }

        commClient.disconnect(localUser.getId());

        return;
    }
}
