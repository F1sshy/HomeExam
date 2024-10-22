package main;

import network.Client;

/**
 * The main class is the entry point for the application.
 */
public class main {
    public static void main(String[] args) {
        try {
            if (args.length > 0) {
                //
                if (args[0].matches("\\b(?:\\d{1,3}\\.){3}\\d{1,5}\\b")) {
                    System.out.println("Connecting to client...");
                    Client client = new Client(args[0], args[1]);
                }
                    else if (args[0].matches("\\d+")) {
                    System.out.println("Connecting to server...");
                    PointSalad game = new PointSalad(args);
                } else {
                    System.out.println("Invalid input. Usage: java main <number_of_players> <number_of_bots> or java main <server_ip>");
                }
            } else {
                System.out.println("Usage: java main <number_of_players> <number_of_bots> or java main <server_ip>");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}