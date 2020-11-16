package common.sharedData;

import java.rmi.server.UID;

public class OwnedChannel extends Channel {

	public OwnedChannel(UID id, String name, UserLite creator,
						String description, Visibility visibility) {
		super(id, name, creator, description, visibility);
	}

}
