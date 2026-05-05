package business.builders;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

public class BuilderBasedFactory <T> implements Factory<T> {  
	private Map<String, Builder<T>> builders;  

	public BuilderBasedFactory() {  
		builders = new HashMap<>();
	}

	public BuilderBasedFactory(List<Builder<T>> builders){  
		this();
		if(builders == null) throw new IllegalArgumentException("Invalid arg for Builder in BuilderBasedFactory");
		for(Builder<T> b: builders) addBuilder(b);
	}

	public void addBuilder(Builder<T> b) { 
		builders.put(b.getTypeTag(), b);
	}

	@Override  
	public T createInstance(JSONObject info) {  
		if (info == null || !info.has("name")) throw new IllegalArgumentException("Invalid info for createInstance in BuilderBasedFactory");
		T instance = null;
		String name = info.getString("name");
		if(builders.containsKey(name)) instance = builders.get(name).createInstance(info.getJSONObject("data"));
		if(instance != null) return instance;
		throw new IllegalArgumentException("Unrecognized " + name + " for the builder");
	}
}
