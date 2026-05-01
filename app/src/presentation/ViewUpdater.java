package presentation;

@FunctionalInterface
public interface ViewUpdater {
	
	void update(int event, Object data);
}
