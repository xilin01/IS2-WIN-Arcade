package business.builders;

import org.json.JSONObject;

public interface Factory<T> {
	
	public T createInstance(JSONObject j);  
}
