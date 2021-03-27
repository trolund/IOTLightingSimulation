package lamp;

import com.google.gson.Gson;
import messaging.Event;
import messaging.rabbitmq.interfaces.IEventReceiver;
import messaging.rabbitmq.interfaces.IEventSender;

public class EventService implements IEventReceiver {

    private final IEventSender eventSender;
    private final Gson gson = new Gson();
    private LampInfo lamp = new LampInfo();

    public EventService(IEventSender eventSender) {
        this.eventSender = eventSender;
    }

    public EventService(IEventSender eventSender, LampInfo lamp) {
        this.eventSender = eventSender;
        this.lamp = lamp;
    }

    public void sendMyInfo(){
        try {
            eventSender.sendEvent(new Event("Info", new Object[]{lamp}));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMyGroups(){
        try {
            eventSender.sendEvent(new Event("MyGroups", new Object[]{lamp.getGroups()}));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void receiveEvent(Event eventIn) throws Exception {
        switch (eventIn.getEventType()) {
            case "AdjustIntensity":
                String s = (String) eventIn.getArguments()[0];
                adjustIntensity((int) eventIn.getArguments()[0], (float) eventIn.getArguments()[1]);
                break;
            case "AdjustColor":
                Color color = gson.fromJson(gson.toJson(eventIn.getArguments()[1]), Color.class);
                adjustColor((int) eventIn.getArguments()[0], color);
                break;
            case "GetInfo":
                sendMyInfo();
                break;
            case "AddGroup":
                addGroup(((Double) eventIn.getArguments()[0]).intValue(), (String) eventIn.getArguments()[1]);
                sendMyInfo();
                break;
            case "RemoveGroup":
                removeGroup(((Double) eventIn.getArguments()[0]).intValue(), (String) eventIn.getArguments()[1]);
                sendMyInfo();
                break;
            case "GetGroups":
                sendMyGroups();
                break;
            default:
              //  System.out.println("Ignored event with type: " + eventIn.getEventType() + ". Event: " + eventIn.toString());
                break;
        }
    }

    private void addGroup(int id, String groupName) {
        if(id == lamp.getId()){
            lamp.addToGroup(groupName);
        }
    }

    private void adjustIntensity(int id, float intensity){
        if(id == lamp.getId()){
            lamp.setIntensity(intensity);
        }
    }

    private void adjustColor(int id, Color c){
        if(id == lamp.getId()){
            lamp.setColor(c);
        }
    }

    private void removeGroup(int id, String groupName){
        if(id == lamp.getId() && lamp.getGroups().contains(groupName)){
            lamp.removeToGroup(groupName);
        }
    }
}
