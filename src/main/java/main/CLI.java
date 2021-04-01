package main;

import lamp.LampInfo;

import java.util.List;
import java.util.Scanner;

public class CLI {

    static ControlService service = new ControlService();

    public static void main(String[] args) {
        run();
    }

    public static String getInput(Scanner scanner) {
        return scanner.nextLine();
    }

    private static void processCommand(String input){
        if(input.contains("new")){
            service.runLamp();
            return;
        }
        if(input.contains("all")){
            List<LampInfo> res = service.all();
            System.out.println("All lamps:");
            System.out.println(res);
            return;
        }
        if(input.contains("get")){
            service.get(input);
            return;
        }
        if(input.contains("on")){
            service.on(input);
            return;
        }
        if(input.contains("groups")){
            service.getAllGroups();
            return;
        }
        if(input.contains("add-group")){
            service.addGroup(input);
            return;
        }
        if(input.contains("remove-group")){
            service.removeGroup(input);
            return;
        }
        if(input.contains("adjust-color")){
            service.adjustColor(input);
            return;
        }
        if(input.contains("adjust-group-color")){
            service.adjustGroupColor(input);
            return;
        }
        if(input.contains("adjust-intensity")){
            service.adjustIntensity(input);
            return;
        }
        if(input.contains("adjust-group-intensity")){
            service.adjustGroupIntensity(input);
            return;
        }
        if(input.contains("change-name")){
            service.changeName(input);
            return;
        }
        System.out.println("unknown command");
    }

    public static void run() {
        // addTestLamps();
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

   /* public static void addLamp(){
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
    }*/
}
