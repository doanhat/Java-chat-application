package tests.communication;

import communication.client.CommunicationClientController;
import communication.common.Parameters;
import common.shared_data.UserLite;

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
