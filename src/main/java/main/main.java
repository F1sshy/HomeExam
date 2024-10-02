package main;

public class main {

    public static void main(String[] args) {
        try {
            PointSalad game = new PointSalad(args);
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

}
