package commun.Data;

import java.beans.Visibility;

public class SharedChannel extends Channel {

	public SharedChannel(int id, String name, UserLite creator,
			String description, Visibility visibility) {
		super(id, name, creator, description, visibility);
	}

}
