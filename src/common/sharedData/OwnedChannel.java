package common.sharedData;

import java.util.UUID;

public class OwnedChannel extends Channel {

	public OwnedChannel(UUID id, String name, UserLite creator,
						String description, Visibility visibility) {
		super(id, name, creator, description, visibility);
	}

	public OwnedChannel(String name, UserLite creator,
						String description, Visibility visibility) {
		super(name, creator, description, visibility);
	}

	public OwnedChannel() {
		super();
	}
}
