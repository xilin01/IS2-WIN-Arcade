package presentation.builders;

import javax.swing.JFrame;
import org.json.JSONObject;
import presentation.packages.PackageManagerView;

public class PackageManagerViewBuilder extends Builder<JFrame>{

	public PackageManagerViewBuilder() {
		super("PackageManagerView");
	}

	@Override
	protected JFrame createInstance(JSONObject data) {
		if(data == null || !data.isEmpty()) throw new IllegalArgumentException("Invalid data for createInstance in PackageManagerViewBuilder");
		return new PackageManagerView();
	}
}
