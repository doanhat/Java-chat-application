package tests.communication;

import Communication.client.CommunicationClientController;
import Communication.client.CommunicationClientInterfaceImpl;
import Communication.common.Parameters;
import common.sharedData.Channel;
import common.sharedData.SharedChannel;
import common.sharedData.UserLite;
import common.sharedData.Visibility;

import java.util.Scanner;

public class CommClientCreateChannelTest {
    public static void main(String[] args)
    {
        CommunicationClientController commClient = new CommunicationClientController();

        Scanner reader = new Scanner(System.in);

        System.out.print("Enter pseudo: ");

        String username     = reader.nextLine();
        UserLite localUser  = new UserLite(username, "dernier maitre de l'air");

        commClient.start(Parameters.SERVER_IP, Parameters.PORT, localUser);

        // Wait for connection established
        try {
            Thread.sleep(3000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        CommunicationClientInterfaceImpl commInterface = new CommunicationClientInterfaceImpl(commClient);

        Channel channel = new SharedChannel("Test Channel", localUser, "Test", Visibility.PUBLIC);

        commInterface.createChannel(channel, true, true, localUser);

        //commClient.stop();
    }
}
