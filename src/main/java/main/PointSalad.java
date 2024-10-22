package main;

import java.util.Scanner;

import pile.VeggiePile;
import game.GameEngine;
import network.Server;
import player.IPlayer;

import java.util.ArrayList;

public class PointSalad {
    private ArrayList<IPlayer> players = new ArrayList<>();
    private ArrayList<VeggiePile> veggiePiles;

    /**
     * Constructs a PointSalad game with the specified command line arguments.
     *
     * @param args the command line arguments
     */
    public PointSalad(String[] args) {
        int numberPlayers = 0;
        int numberOfBots = 0;
        int maxPlayers = 6;
        int minPlayers = 2;

        this.veggiePiles = veggiePiles;

        if (args.length == 0) {
            System.out.println("Please enter the number of players (1-6): ");
            Scanner in = new Scanner(System.in);
            numberPlayers = in.nextInt();
            System.out.println("Please enter the number of bots (0-5): ");
            numberOfBots = in.nextInt();


        } else {
            if (args[0].matches("\\d+")) {
                numberPlayers = Integer.parseInt(args[0]);
                numberOfBots = Integer.parseInt(args[1]);
            }
            if (numberPlayers + numberOfBots > maxPlayers || numberPlayers + numberOfBots < minPlayers) {
                throw new IllegalArgumentException("Invalid number of players.");
            }

            try {
                Server server = new Server(players, 2048);
                server.server(numberPlayers, numberOfBots);
            } catch (Exception e) {
                e.printStackTrace();
            }

            GameEngine gameEngine = GameEngine.getInstance(players);

            gameEngine.startGame(args);


        }


    }
}