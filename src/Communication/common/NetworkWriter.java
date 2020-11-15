package Communication.common;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NetworkWriter extends Thread
{
    private final List<DeliveryPacket> messagesQueue;

    public NetworkWriter()
    {
        messagesQueue = Collections.synchronizedList(new ArrayList<>());
    }

    public void sendMessage(DeliveryPacket packet)
    {
        synchronized (messagesQueue)
        {
            messagesQueue.add(packet);
            messagesQueue.notifyAll();
        }
    }

    @Override
    public void run()
    {
        while (true)
        {
            try
            {
                synchronized (messagesQueue)
                {
                    if (!messagesQueue.isEmpty())
                    {
                        DeliveryPacket packet = messagesQueue.remove(0);
                        packet.send();
                    }
                    else {
                        messagesQueue.wait();
                    }
                }
            }
            catch (IOException | InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    public static class DeliveryPacket
    {
        private ObjectOutputStream receiver;
        private NetworkMessage     message;

        public DeliveryPacket(ObjectOutputStream receiver, NetworkMessage message)
        {
            this.receiver = receiver;
            this.message  = message;
        }

        public void send() throws IOException
        {
            receiver.writeObject(message);
        }
    }
}
