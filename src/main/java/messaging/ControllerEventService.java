package messaging;

import com.google.gson.Gson;
import lamp.Color;
import lamp.LampInfo;
import messaging.rabbitmq.interfaces.IEventReceiver;
import messaging.rabbitmq.interfaces.IEventSender;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.locks.ReentrantLock;

public class ControllerEventService implements IEventReceiver, IController {

    private final IEventSender eventSender;
    private final Gson gson = new Gson();

    private CompletableFuture<List<LampInfo>> lampsResult;
    private CompletableFuture<LampInfo> lampResult;

    private ReentrantLock idMutex = new ReentrantLock();
    private int lampID;


    private Set<String> groupsCache = new HashSet<>();
    private List<LampInfo> curLamps = new ArrayList<>();

    public ControllerEventService(IEventSender eventSender) {
        this.eventSender = eventSender;
    }

    private int getLampID() {
        return lampID;
    }

    private void setLampID(int lampID) {
        idMutex.lock();
        this.lampID = lampID;
    }

    public List<LampInfo> getAllLampInfo(){
        try {
            // clear current view of the lamps
            curLamps = new ArrayList<>();

            // create new CompletableFuture to wait for the result
            lampsResult = new CompletableFuture();

            // send event to all devices
            eventSender.sendEvent(new Event("GetInfo", new Object[]{}));

            // block until all have been received
            System.out.println("Loading....");
            List<LampInfo> res = lampsResult.join();
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public synchronized LampInfo get(int id){
        try {
            // create new CompletableFuture to wait for the result
            lampResult = new CompletableFuture();
            setLampID(id);

            // send event to all devices
            eventSender.sendEvent(new Event("GetInfo", new Object[]{id}));

            // block until all have been received
            LampInfo res = lampResult.join();
            idMutex.unlock();
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void getAllGroups(){
        try {
            System.out.println("All groups:");
            eventSender.sendEvent(new Event("GetGroups", new Object[]{}));
            //lampsResult.complete(curLamps);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setOn(int id, boolean isOn){
        try {
            eventSender.sendEvent(new Event("SetIsOn", new Object[]{id, isOn}));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeName(int id, String name){
        try {
            eventSender.sendEvent(new Event("ChangeName", new Object[]{id, name}));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeGroupName(String groupName, String newName){
        try {
            eventSender.sendEvent(new Event("ChangeGroupName", new Object[]{groupName, newName}));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addToGroup(int id, String groupName){
        try {
            eventSender.sendEvent(new Event("AddGroup", new Object[]{id, groupName}));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeFromGroup(int id, String groupName){
        try {
            eventSender.sendEvent(new Event("RemoveGroup", new Object[]{id, groupName}));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void adjustIntensity(String groupName, int intensity){
        try {
            eventSender.sendEvent(new Event("AdjustIntensity", new Object[]{groupName, intensity}));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void adjustColor(String groupName, Color color){
        try {
            eventSender.sendEvent(new Event("AdjustColor", new Object[]{groupName, color}));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void adjustIntensity(int id, int intensity){
        try {
            eventSender.sendEvent(new Event("AdjustIntensity", new Object[]{id, intensity}));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void adjustColor(int id, Color color){
        try {
            eventSender.sendEvent(new Event("AdjustColor", new Object[]{id, color}));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendExit(int id){
        try {
            eventSender.sendEvent(new Event("Exit", new Object[]{id}));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void receiveEvent(Event eventIn) throws Exception {
        switch (eventIn.getEventType()) {
            case "Info":
                LampInfo info = gson.fromJson(gson.toJson(eventIn.getArguments()[0]), LampInfo.class);
                curLamps.add(info);
                // System.out.println(info.toString());

                if(info.getId() == lampID){
                    lampResult.complete(info);
                }

                // wait for the devices to send there info.
                if(!lampsResult.isDone()){
                    Thread newThread = new Thread(() -> {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        lampsResult.complete(curLamps);
                    });
                    newThread.start();
                }

              break;
            case "MyGroups":
                List<String> groups = (List<String>) eventIn.getArguments()[0];
                groupsCache.addAll(groups);
                System.out.println(groups);
            case "Error":
                String msg = (String) eventIn.getArguments()[0];
                // System.err.println(msg);
            default:
                // System.out.println("Ignored event with type: " + eventIn.getEventType() + ". Event: " + eventIn.toString());
                break;
        }
    }
}
