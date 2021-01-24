package messaging;

import com.google.gson.Gson;
import lamp.LampInfo;
import messaging.Event;
import messaging.rabbitmq.Listener;
import messaging.rabbitmq.interfaces.IEventReceiver;
import messaging.rabbitmq.interfaces.IEventSender;

import java.util.ArrayList;
import java.util.List;

public class ControllerEventService implements IEventReceiver {

    private final IEventSender eventSender;
    private final Gson gson = new Gson();
    LampInfo lamp = new LampInfo();

    private static List<LampInfo> lamps = new ArrayList<>();

    public static List<LampInfo> getLamps() {
        return lamps;
    }

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

    public void addToGroup(float id, String groupName){
        try {
            eventSender.sendEvent(new Event("AddGroup", new Object[]{id, groupName}));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeToGroup(float id, String groupName){
        try {
            eventSender.sendEvent(new Event("RemoveGroup", new Object[]{id, groupName}));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void receiveEvent(Event eventIn) throws Exception {
        switch (eventIn.getEventType()) {
            case "Info":
                System.out.println(eventIn.getArguments()[0].toString());
              break;
            default:
                // System.out.println("Ignored event with type: " + eventIn.getEventType() + ". Event: " + eventIn.toString());
                break;
        }
    }
}
