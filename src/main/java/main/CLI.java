package main;

import lamp.Color;
import lamp.LampInfo;
import lamp.LampRunner;
import messaging.IController;
import messaging.rabbitmq.ControllerFactory;

import java.util.Scanner;

public class CLI {

    static IController s = new ControllerFactory().getService();

    public static void main(String[] args) {
        run();
    }

    public static String getInput(Scanner scanner) {
        return scanner.nextLine();
    }

    private static void processCommand(String input){
        if(input.contains("new")){
            addLamp();
            return;
        }
        if(input.contains("all")){
            s.getAllLampInfo();
            return;
        }
        if(input.contains("on")){
            String[] parts = input.split(" ");
            int id = Integer.parseInt(parts[1]);
            boolean isOn = Boolean.parseBoolean(parts[2]);
            s.setOn(id, isOn);
            return;
        }
        if(input.contains("groups")){
            s.getAllGroups();
            return;
        }
        if(input.contains("add-group")){
            String[] parts = input.split(" ");
            int id = Integer.parseInt(parts[1]);
            String groupName = parts[2];
            s.addToGroup(id, groupName);
            return;
        }
        if(input.contains("remove-group")){
            String[] parts = input.split(" ");
            int id = Integer.parseInt(parts[1]);
            String groupName = parts[2];
            s.removeFromGroup(id, groupName);
            return;
        }
        if(input.contains("adjust-color")){
            String[] parts = input.split(" ");
            int id = Integer.parseInt(parts[1]);
            String color = parts[2];
            Color c = parseColor(color);
            s.adjustColor(id, c);
            return;
        }
        if(input.contains("adjust-group-color")){
            String[] parts = input.split(" ");
            String groupName = parts[1];
            String color = parts[2];
            Color c = parseColor(color);
            s.adjustColor(groupName, c);
            return;
        }
        if(input.contains("adjust-intensity")){
            String[] parts = input.split(" ");
            int id = Integer.parseInt(parts[1]);
            int intensity = Integer.parseInt(parts[2]);
            s.adjustIntensity(id, intensity);
            return;
        }
        if(input.contains("adjust-group-intensity")){
            String[] parts = input.split(" ");
            String groupName = parts[1];
            int intensity = Integer.parseInt(parts[2]);
            s.adjustIntensity(groupName, intensity);
            return;
        }
        if(input.contains("change-name")){
            String[] parts = input.split(" ");
            int id = Integer.parseInt(parts[0]);
            String newName = parts[2];
            s.changeName(id, newName);
            return;
        }
        System.out.println("unknown command");
    }

    public static void run() {
        addTestLamps();

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

    public static void addLamp(LampInfo lampInfo) throws InterruptedException {
        // System.out.println("Inside : " + Thread.currentThread().getName());

        // System.out.println("Creating Runnable...");
        LampRunner runnable = new LampRunner();
        runnable.setInfo(lampInfo);

        // System.out.println("Creating Thread...");
        Thread thread = new Thread(runnable);

        // System.out.println("Starting Thread...");
        thread.start();
        Thread.sleep(100);
    }

    public static Color parseColor(String c){
        String input = c.substring(1, c.length() -1);
        String[] parts = input.split(",");
        return new Color(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
    }

    public static void addTestLamps(){
        System.out.println("Adding test lamps");
        String house = "Hus";
        LampInfo l1 = new LampInfo(1, "Enhed_1", 50, new Color(200,250,100));
        l1.addToGroup("Stue");
        l1.addToGroup(house);
        LampInfo l2 = new LampInfo(2, "Enhed_2", 100, new Color(200,240,80));
        l2.addToGroup("Stue");
        l2.addToGroup(house);
        LampInfo l3 = new LampInfo(3, "Enhed_3", 75, new Color(210,220,10));
        l3.addToGroup("Køkken");
        l3.addToGroup(house);

        try {
            addLamp(l1);
            addLamp(l2);
            addLamp(l3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
