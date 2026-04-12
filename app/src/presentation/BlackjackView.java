package presentation;

import java.util.Scanner;
import business.Blackjack;

// Maqueta inicial para probar el blackjack
public class BlackjackView {
	
    public void initBlackjack(int basePoints) {
        Scanner scanner = new Scanner(System.in);
        Blackjack blackjack = new Blackjack(basePoints);

        System.out.println("========BLACKJACK=========");
        System.out.println("Turno del croupier");
        int croupierVisiblePoints = blackjack.play();
        System.out.println("Cartas del croupier: [" + blackjack.getCroupierVisibleCard() + ", ?] (" + croupierVisiblePoints + "p)");
        boolean playerTurn = true, end = false;
        while(playerTurn) {
            System.out.println("Turno del jugador");
            System.out.println("Cartas del jugador: " + blackjack.getPlayerCards() + " (" + blackjack.getPlayerPoints() + "p)");
            System.out.println("Que vas a hacer?");
            System.out.println("1. Pedir carta (Hit)");
            System.out.println("2. Plantarse (Stand)");
            System.out.print("Elige una opción: ");
            int opcion = scanner.nextInt();
            if(opcion == 1) {
                int estado = blackjack.playerHit();
                
                if(estado == -1) { // LOST
                	System.out.println("Cartas del jugador: " + blackjack.getPlayerCards() + " (" + blackjack.getPlayerPoints() + "p)");
                    System.out.println("Te has pasado de 21, has perdido..");
                    playerTurn = false;
                    end = true;
                }
                else if(estado == 1) { // WON
                	System.out.println("Cartas del jugador: " + blackjack.getPlayerCards() + " (" + blackjack.getPlayerPoints() + "p)");
                    System.out.println("Has conseguido 21!");
                    playerTurn = false;
                    end = true;
                }
            }
            else if(opcion == 2) playerTurn = false; // El jugador se planta
            else System.out.println("Selecciona una opcion valida");
        }
        if(!end) {
            System.out.println("Turno del croupier");
            if(blackjack.getCroupierPoints() < blackjack.getPlayerPoints()) blackjack.croupierPlay();
            System.out.println("Cartas del croupier: " + blackjack.getCroupierCards() + " (" + blackjack.getCroupierPoints() + "p)");
        }
        System.out.println("=========RESULTADO=========");
        int result = blackjack.getResult();
        if(result == -1) System.out.println("Lo siento, gana el croupier..");
        else if(result == 0) System.out.println("Empate");
        else System.out.println("Felicidades, has ganado " + blackjack.getPointsWon() + " puntos!");
    }
}
