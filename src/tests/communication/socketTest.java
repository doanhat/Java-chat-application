package tests.communication;

import Communication.client.CommunicationClientController;
import Communication.client.NetworkClient;
import Communication.messages.server_to_client.AcceptationMessage;
import Communication.messages.server_to_client.NewUserConnectedMessage;
import Communication.messages.server_to_client.UserDisconnectedMessage;
import Communication.server.CommunicationServerController;
import common.sharedData.UserLite;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class socketTest {
    UUID uuid;
    UserLite usr;
    CommunicationClientController client;
    CommunicationServerController server;

    @BeforeEach
    public void init() throws InterruptedException {
        client = new CommunicationClientController();
        server = new CommunicationServerController();
        usr = new UserLite("test", "dernier maitre de l'air");

        server.start();
        client.start("localhost", 8080, usr);
        Thread.sleep(500);
    }

    @Test
    public void connectionTest(){
        List<UserLite> usrs = server.onlineUsers();
        assertEquals(1, usrs.size());
        assertEquals(usr.getId(), usrs.get(0).getId());
    }

    @Test
    public void sendNewUserConnectedMessage() throws InterruptedException {
        client.sendMessage(new NewUserConnectedMessage(usr));
        Thread.sleep(500);
        assertEquals(1, server.onlineUsers().size());
    }

    @AfterEach
    public  void destroy(){
        System.out.println("Fin");
        client.stop();
        server.stop();
    }
}
