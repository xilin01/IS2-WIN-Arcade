package business.builders;

import javax.swing.JFrame;
import org.json.JSONObject;
import presentation.product.ProductManagerView;

public class ProductManagerViewBuilder extends Builder<JFrame>{

	public ProductManagerViewBuilder() {
		super("ProductManagerView");
	}

	@Override
	protected JFrame createInstance(JSONObject data) {
		if(data == null || !data.isEmpty()) throw new IllegalArgumentException("Invalid data for createInstance in ProductManagerViewBuilder");
		return new ProductManagerView();
	}
}
