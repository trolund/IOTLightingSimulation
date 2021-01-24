package main;

import lamp.LampRunner;
import messaging.ControllerEventService;
import messaging.rabbitmq.ControllerFactory;

import java.util.Scanner;

public class main {

    static ControllerEventService s = new ControllerFactory().getService();

    public static void main(String[] args) {
        run();
    }

    public static String getInput(Scanner scanner) {
        return scanner.nextLine();
    }

    private static void processCommand(String input){
        if(input.equals("")){
            return;
        }
        if(input.contains("new")){
            String[] i = input.split(" ");
            addLamp();
            return;
        }
        if(input.contains("intensity")){
            String[] i = input.split(" ");
            addLamp();
            return;
        }
        if(input.contains("listAll")){
            s.getAllLampInfo();
            return;
        }
        System.out.println("unknown command");
    }

    public static void run() {
        Scanner input = new Scanner(System.in);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("- Lamp CLI -");
        while (true){
            System.out.print(">> ");
            processCommand(getInput(input));
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void addLamp(){
        // System.out.println("Inside : " + Thread.currentThread().getName());

        // System.out.println("Creating Runnable...");
        Runnable runnable = new LampRunner();

        // System.out.println("Creating Thread...");
        Thread thread = new Thread(runnable);

        // System.out.println("Starting Thread...");
        thread.start();
    }
}
