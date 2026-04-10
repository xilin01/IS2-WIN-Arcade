package negocio;

import java.util.Random;

public abstract class Juego {
	protected Integer id;
	protected String nombre;
	
	protected static final Random RAND = new Random();
	
	public Juego(Integer id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}

	
	public abstract int jugar();
}
