package ui;

import java.util.Scanner;

public abstract class Menu {
    protected final Scanner scanner = new Scanner(System.in);
    protected Menu subMenu;

    public Menu() {}

    public Menu(Menu subMenu) {
        this.subMenu = subMenu;
    }

    public abstract void display();
    public abstract void handleChoice(int choice);

    public void run() {
        boolean shouldExit = false;
        while (!shouldExit) {
            display();
            System.out.print("Escolha uma opção: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            handleChoice(choice);
            shouldExit = choice == 0;
        }
    }
}
