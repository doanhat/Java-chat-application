package common.sharedData;

public class OwnedChannel extends Channel {

	public OwnedChannel(int id, String name, UserLite creator,
			String description, Visibility visibility) {
		super(id, name, creator, description, visibility);
	}

}
