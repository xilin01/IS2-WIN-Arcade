package business.builders;

import org.json.JSONObject;

public abstract class Builder <T> {
	private String typeTag;  

	public Builder(String typeTag) {
		if(typeTag == null || typeTag.isBlank()) throw new IllegalArgumentException("Invalid typeTag for a Builder");  
		this.typeTag = typeTag;  
	}

	public String getTypeTag() {
		return typeTag;
	}

	abstract protected T createInstance(JSONObject data);  
}
