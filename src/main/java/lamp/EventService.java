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
                if(eventIn.getArguments()[0] instanceof Double){
                    adjustIntensity(((Double) eventIn.getArguments()[0]).intValue(), ((Double) eventIn.getArguments()[1]).intValue());
                }else {
                    adjustIntensity((String) eventIn.getArguments()[0], ((Double) eventIn.getArguments()[1]).intValue());
                }
                break;
            case "AdjustColor":
                Color color = gson.fromJson(gson.toJson(eventIn.getArguments()[1]), Color.class);
                if(eventIn.getArguments()[0] instanceof Double){
                    adjustColor(((Double) eventIn.getArguments()[0]).intValue(), color);
                }else {
                    adjustColor((String) eventIn.getArguments()[0], color);
                }
                break;
            case "SetIsOn":
                boolean b = (Boolean) eventIn.getArguments()[1];
                setOn(((Double) eventIn.getArguments()[0]).intValue(), b);
                sendMyInfo();
                break;
            case "GetInfo":
                if(eventIn.getArguments().length > 0){
                    int id = ((Double) eventIn.getArguments()[0]).intValue();
                    if(id == lamp.getId()){
                        sendMyInfo();
                    }
                }else {
                    sendMyInfo();
                }
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
            case "ChangeName":
                setName(((Double) eventIn.getArguments()[0]).intValue(), (String) eventIn.getArguments()[1]);
            case "Exit":
                // only for testing
                Exit(((Double) eventIn.getArguments()[0]).intValue());
            default:
              // System.out.println("Ignored event with type: " + eventIn.getEventType() + ". Event: " + eventIn.toString());
                break;
        }
    }

    private void Exit(int id){
        if(id == lamp.getId()){
            Thread.currentThread().interrupt();
        }
    }

    private void setName(int id, String name){
        if(id == lamp.getId()){
            lamp.setName(name);
        }
    }

    private void setOn(int id, boolean isOn){
        if(id == lamp.getId()){
            lamp.setOn(isOn);
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

    private void adjustIntensity(String groupName, float intensity){
        if(lamp.getGroups().contains(groupName)){
            lamp.setIntensity(intensity);
        }
    }

    private void adjustColor(int id, Color c){
        if(id == lamp.getId()){
            lamp.setColor(c);
        }
    }

    private void adjustColor(String groupName, Color c){
        if(lamp.getGroups().contains(groupName)){
            lamp.setColor(c);
        }
    }

    private void removeGroup(int id, String groupName){
        if(id == lamp.getId() && lamp.getGroups().contains(groupName)){
            lamp.removeToGroup(groupName);
        }
    }
}
