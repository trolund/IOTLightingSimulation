package messaging;

import com.google.gson.Gson;
import lamp.Color;
import lamp.LampInfo;
import messaging.rabbitmq.interfaces.IEventReceiver;
import messaging.rabbitmq.interfaces.IEventSender;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ControllerEventService implements IEventReceiver, IController {

    private final IEventSender eventSender;
    private final Gson gson = new Gson();

    private Set<String> groupsCache = new HashSet<>();

    public ControllerEventService(IEventSender eventSender) {
        this.eventSender = eventSender;
    }

    public void getAllLampInfo(){
        try {
            System.out.println("All lamps:");
            eventSender.sendEvent(new Event("GetInfo", new Object[]{}));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getAllGroups(){
        try {
            System.out.println("All groups:");
            eventSender.sendEvent(new Event("GetGroups", new Object[]{}));
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

    @Override
    public void receiveEvent(Event eventIn) throws Exception {
        switch (eventIn.getEventType()) {
            case "Info":
                LampInfo info = gson.fromJson(gson.toJson(eventIn.getArguments()[0]), LampInfo.class);
                System.out.println(info.toString());
              break;
            case "MyGroups":
                List<String> groups = (List<String>) eventIn.getArguments()[0];
                groupsCache.addAll(groups);
                System.out.println(groups);
            case "Error":
                String msg = (String) eventIn.getArguments()[0];
                System.err.println(msg);
            default:
                // System.out.println("Ignored event with type: " + eventIn.getEventType() + ". Event: " + eventIn.toString());
                break;
        }
    }
}
