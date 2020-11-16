package common.sharedData;

import java.beans.Visibility;

public class OwnedChannel extends Channel {

	public OwnedChannel(int id, String name, UserLite creator,
			String description, Visibility visibility) {
		super(id, name, creator, description, visibility);
	}

}
