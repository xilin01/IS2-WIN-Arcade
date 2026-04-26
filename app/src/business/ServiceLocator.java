package business;

import java.util.HashMap;
import java.util.Map;

public class ServiceLocator {
    private static final Map<Class<?>, Object> services = new HashMap<>();

    private ServiceLocator() {
    }

    public static <T> void register(Class<T> type, T impl) {
        services.put(type, impl);
    }

    public static <T> T get(Class<T> type) {
        T service = type.cast(services.get(type));
        
        if (service == null) throw new IllegalStateException("No service registered for " + type.getName());
        return service;
    }
}
