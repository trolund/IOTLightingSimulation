package lamp;

import com.google.gson.Gson;
import messaging.Event;
import messaging.rabbitmq.interfaces.IEventReceiver;
import messaging.rabbitmq.interfaces.IEventSender;

public class EventService implements IEventReceiver {

    private final IEventSender eventSender;
    private final Gson gson = new Gson();
    LampInfo lamp = new LampInfo();

    public EventService(IEventSender eventSender) {
        this.eventSender = eventSender;
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
            case "ChangeSettings":
                int id = (int) eventIn.getArguments()[1];
                if(id == -1 || id == lamp.getId()){
                    LampInfo info = (LampInfo) eventIn.getArguments()[1];
                    lamp = info;
                }
                break;
            case "ChangeSettingsGroup":
                String groupName = (String) eventIn.getArguments()[0];
                if(lamp.getGroups().contains(groupName)){
                    LampInfo info = (LampInfo) eventIn.getArguments()[1];
                    lamp = info;
                }
                break;
            case "GetInfo":
                sendMyInfo();
                break;
            case "AddGroup":
                modifyGroup(eventIn, true);
                break;
            case "RemoveGroup":
                modifyGroup(eventIn, false);
                break;
            case "GetGroups":
                sendMyGroups();
                break;
            default:
              //  System.out.println("Ignored event with type: " + eventIn.getEventType() + ". Event: " + eventIn.toString());
                break;
        }
    }

    private void modifyGroup(Event eventIn, boolean isAdd) {
        int lampId = (int) eventIn.getArguments()[0];
        if(lampId == lamp.getId()){
            String name = (String) eventIn.getArguments()[1];
            if(isAdd){
                lamp.addToGroup(name);
            }else {
                lamp.removeToGroup(name);
            }
        }
    }
}
