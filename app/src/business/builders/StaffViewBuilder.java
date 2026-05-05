package business.builders;

import javax.swing.JFrame;
import org.json.JSONObject;
import presentation.user.StaffView;

public class StaffViewBuilder extends Builder<JFrame>{

	public StaffViewBuilder() {
		super("StaffView");
	}

	@Override
	protected JFrame createInstance(JSONObject data) {
		if(data == null || !data.isEmpty()) throw new IllegalArgumentException("Invalid data in createInstance for StaffViewBuilder");
		return new StaffView();
	}
}
