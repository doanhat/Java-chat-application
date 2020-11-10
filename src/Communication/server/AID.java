package Communication.server;

import Communication.client.CommunicationController;
import Communication.client.NetworkClientReader;
import Communication.client.NetworkClientWriter;
import Communication.common.NetworkMessage;

import java.io.IOException;
import java.net.Socket;
import java.util.UUID;

public class AID {

    private CommunicationServerController refToCommControler;
    private String ip;
    private UUID id;
    private NetworkServerReader reader;
    private NetworkServerWriter writer;

    public AID(CommunicationServerController ref, Socket comm) {
        refToCommControler = ref;
        try {
            reader = new NetworkServerReader(refToCommControler, comm);
            writer = new NetworkServerWriter(comm);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setIp(String ip) {
        this.ip = ip;
    }


    public void sendMessage(NetworkMessage message){
        try {
            writer.sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
