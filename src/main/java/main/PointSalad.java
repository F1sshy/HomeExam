package main;

import java.util.Scanner;

import pile.pile;
import game.GameEngine;
import network.Server;

import player.Player;

import java.util.ArrayList;

public class PointSalad {
    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<pile> piles;

    /**
     * Constructs a PointSalad game with the specified command line arguments.
     *
     * @param args the command line arguments
     */
    public PointSalad(String[] args) {
        int numberPlayers = 0;
        int numberOfBots = 0;

        this.piles = piles;

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
        }

        try {
            Server server = new Server(players, 2048);
            server.server(numberPlayers, numberOfBots);
        } catch (Exception e) {
            e.printStackTrace();
        }

        GameEngine gameEngine = new GameEngine(players);

        gameEngine.startGame(args);


    }



}