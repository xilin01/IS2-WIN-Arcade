package business;

import java.util.Random;

public abstract class Game {
	protected static final Random RAND = new Random();
	
	protected String name;
	
	protected Game(String name) {
		this.name = name;
	}
	
	abstract public int play();
}
