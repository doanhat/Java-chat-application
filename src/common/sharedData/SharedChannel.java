package common.sharedData;

import java.util.UUID;

public class SharedChannel extends Channel {

	public SharedChannel(UUID id, String name, UserLite creator,
						 String description, Visibility visibility) {
		super(id, name, creator, description, visibility);
	}

}
