package presentation;

import java.util.Scanner;
import business.Blackjack;

public class BlackjackView {
	
    public void initBlackjack() {
        Scanner scanner = new Scanner(System.in);
        Blackjack blackjack = new Blackjack();

        System.out.println("BLACKJACK");
        int croupierVisiblePoints = blackjack.play();
        System.out.println("El croupier muestra una carta. Puntos visibles: " + croupierVisiblePoints);
        boolean turnoJugador = true;
        while (turnoJugador) {
            System.out.println("Turno jugador");
            System.out.println("Tus cartas: " + blackjack.getPlayerCards());
            System.out.println("Tus puntos actuales: " + blackjack.getPlayerPoints());
            System.out.println("Que vas a hacer?");
            System.out.println("1. Pedir carta (Hit)");
            System.out.println("2. Plantarse (Stand)");
            System.out.print("Elige una opción: ");
            int opcion = scanner.nextInt();
            if(opcion == 1) {
                int estado = blackjack.playerHit();
                
                if(estado == -1) { // LOST
                    System.out.println("Tus cartas: " + blackjack.getPlayerCards());
                    System.out.println("Tus puntos: " + blackjack.getPlayerPoints());
                    System.out.println("Te has pasado de 21, has perdido..");
                    turnoJugador = false;
                } 
                else if(estado == 1) {
                    System.out.println("Has conseguido 21!");
                    turnoJugador = false;
                }
            } 
            else if(opcion == 2) turnoJugador = false; // El jugador se planta
            else System.out.println("Seleccione una opcion valida");
        }
        // Turno del croupier
        System.out.println("Turno del croupier");
        blackjack.croupierPlay();
        System.out.println("Cartas del croupier: " + blackjack.getCroupierCards());
        System.out.println("Puntos totales del croupier: " + blackjack.getCroupierPoints());
        // Resultado
        System.out.println("RESULTADO");
        if(blackjack.hasPlayerWon()) System.out.println("Felicidades, has ganado la partida!");
        else System.out.println("Lo siento, gana CROUPIER..");
    }
}
