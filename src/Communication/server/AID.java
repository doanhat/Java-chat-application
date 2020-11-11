package Communication.server;

import Communication.common.NetworkMessage;
import Communication.common.NetworkReader;
import Communication.common.NetworkWriter;

import java.io.IOException;
import java.net.Socket;
import java.util.UUID;

public class AID {

    private CommunicationServerController refToCommControler;
    private String ip;
    private UUID id;
    private NetworkReader reader;
    private NetworkWriter writer;

    public AID(CommunicationServerController ref, Socket comm) {
        refToCommControler = ref;
        try {
            writer = new NetworkWriter(comm);
            reader = new NetworkReader(refToCommControler, comm);
            writer.start();
            reader.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setIp(String ip) {
        this.ip = ip;
    }


    public void sendMessage(NetworkMessage message) {
        try {
            writer.notifyFileMessage();
            writer.sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
