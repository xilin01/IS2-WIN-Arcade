package negocio;

public class tJugador extends tUsuario {

	private int puntos;
	private int partidasJugadas;
	
	public tJugador(String dni, String nombre, String contrasenya, boolean activo) {
		super(dni, nombre, contrasenya, activo);
		puntos = 0;
		partidasJugadas = 0;
	}
	
	public tJugador(String dni, String nombre, String contrasenya, boolean activo, int puntos, int partidasJugadas) {
		super(dni, nombre, contrasenya, activo);
		this.puntos = puntos;
		this.partidasJugadas = partidasJugadas;
	}

	public int getPuntos() {
		return puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}

	public int getPartidasJugadas() {
		return partidasJugadas;
	}

	public void setPartidasJugadas(int partidasJugadas) {
		this.partidasJugadas = partidasJugadas;
	}

}
