package tracker;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Learning Progress Tracker");

        Controller controller = new Controller();

        while (controller.getIsCycle()) {
            controller.commandProcessor(in.nextLine());
        }

    }
}
