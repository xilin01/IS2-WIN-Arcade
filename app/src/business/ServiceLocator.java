package business;

import java.util.HashMap;
import java.util.Map;

// Patrón Service Locator
public class ServiceLocator {
    private static final Map<Class<?>, Object> services = new HashMap<>();

    private ServiceLocator() {
    }

    public static <T> void register(Class<T> type, T impl) {
    	if(type == null || impl == null) throw new IllegalArgumentException("Invalid args for register in ServiceLocator");
        services.put(type, impl);
    }

    public static <T> T get(Class<T> type) {
    	if(type == null) throw new IllegalArgumentException("Invalid arg for get in ServiceLocator");
        T service = type.cast(services.get(type));
        if(service == null) throw new IllegalStateException("No service registered for " + type.getName());
        return service;
    }
}
