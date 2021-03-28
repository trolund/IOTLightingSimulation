package main;

import lamp.Color;
import lamp.LampInfo;
import lamp.LampRunner;
import messaging.IController;
import messaging.rabbitmq.ControllerFactory;

public class ControlService {

    static IController s = new ControllerFactory().getService();

    public void changeName(String input){
        String[] parts = input.split(" ");
        int id = Integer.parseInt(parts[0]);
        String newName = parts[2];
        s.changeName(id, newName);
    }

    public void adjustGroupIntensity(String input){
        String[] parts = input.split(" ");
        String groupName = parts[1];
        int intensity = Integer.parseInt(parts[2]);
        s.adjustIntensity(groupName, intensity);
    }

    public void adjustIntensity(String input){
        String[] parts = input.split(" ");
        int id = Integer.parseInt(parts[1]);
        int intensity = Integer.parseInt(parts[2]);
        s.adjustIntensity(id, intensity);
    }

    public void adjustGroupColor(String input){
        String[] parts = input.split(" ");
        String groupName = parts[1];
        String color = parts[2];
        Color c = parseColor(color);
        s.adjustColor(groupName, c);
    }

    public void adjustColor(String input){
        String[] parts = input.split(" ");
        int id = Integer.parseInt(parts[1]);
        String color = parts[2];
        Color c = parseColor(color);
        s.adjustColor(id, c);
    }

    public void removeGroup(String input){
        String[] parts = input.split(" ");
        int id = Integer.parseInt(parts[1]);
        String groupName = parts[2];
        s.removeFromGroup(id, groupName);
    }

    public void addGroup(String input){
        String[] parts = input.split(" ");
        int id = Integer.parseInt(parts[1]);
        String groupName = parts[2];
        s.addToGroup(id, groupName);
    }

    public void on(String input){
        String[] parts = input.split(" ");
        int id = Integer.parseInt(parts[1]);
        boolean isOn = Boolean.parseBoolean(parts[2]);
        s.setOn(id, isOn);
    }

    public void all(String input){
        s.getAllLampInfo();
    }

    public void getAllGroups(){
        s.getAllGroups();
    }

    public void runLamp(){
        // System.out.println("Inside : " + Thread.currentThread().getName());

        // System.out.println("Creating Runnable...");
        Runnable runnable = new LampRunner();

        // System.out.println("Creating Thread...");
        Thread thread = new Thread(runnable);

        // System.out.println("Starting Thread...");
        thread.start();
    }

    public void runLamp(LampInfo lampInfo) throws InterruptedException {
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
}
