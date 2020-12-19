package Communication.messages.client_to_server.moderation;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.messages.server_to_client.moderation.UserBannedMessage;
import Communication.server.CommunicationServerController;
import common.shared_data.Channel;
import common.shared_data.UserLite;

/**
 * Classe de message allant du serveur au client servant à notifier le bannissement d'un client par le serveur
 * @author Martin Passard
 *
 */
public class AskToBanUserMessage extends ClientToServerMessage {

	private static final long serialVersionUID = 4511099479249659322L;
	private final UserLite userAsking;
	private final UserLite userToBan;
	private final Channel channel;
	private final int duration;
	private final String expl;

	/**
	 * 
	 * @param userAsking utilisateur demandant le bannissment
	 * @param userToBan utilisateur a bannir
	 * @param channel canal pour lequel effectuer le bannissement
	 * @param duration durée du bannissement en secondes ou 0 si bannissement définitif
	 * @param expl message de raison du bannissement
	 */
	public AskToBanUserMessage(UserLite userAsking, UserLite userToBan, Channel channel, int duration, String expl) {
		super();
		this.userAsking = userAsking;
		this.userToBan = userToBan;
		this.channel = channel;
		this.duration = duration;
		this.expl = expl;
	}

	@Override
	protected void handle(CommunicationServerController commServerController) {
		//On obtient la vision du serveur du channel, pour éviter un channel modifié par le client.
		Channel serverChannel = commServerController.getChannel(channel.getId());
		
		if(serverChannel.getAdministrators().contains(userAsking)){
			if(commServerController.getDataServer().banUserFromChannel(serverChannel, userToBan, duration, expl)) {
				List<UserLite> personsInChannel = channel.getJoinedPersons();
				UserBannedMessage banMessage = new UserBannedMessage(userToBan, channel, duration, expl);
				commServerController.sendMulticast(personsInChannel, banMessage);
			}else {
				Logger l = Logger.getLogger(this.getClass().getName());
				l.log(Level.WARNING, "Le module Data a refusé le bannissement de {0} par {1} du canal {2}", new Object[] {userToBan, userAsking, channel});
			}
		}else {
			Logger l = Logger.getLogger(this.getClass().getName());
			l.log(Level.WARNING, "L'utilisateur {0} a demandé à bannir l'utilisateur {1} du canal {2} sans en être admin", new Object[] {userAsking, userToBan, channel});
		}
		
	}

}