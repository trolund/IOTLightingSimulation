package messaging.rabbitmq;

import lamp.EventService;
import lamp.LampInfo;
import messaging.ControllerEventService;
import messaging.rabbitmq.interfaces.IEventSender;

public class Factory {

    static EventService service = null;

    public EventService getNewService(){
        return createService();
    }

    // used for testing
    public EventService getNewService(LampInfo lamp){
        return createService(lamp);
    }

    public EventService getService() {

        if (service != null) {
            return service;
        }

        return createService();
    }

    private EventService createService() {
        IEventSender b = new Sender();
        service = new EventService(b);
        Listener r = new Listener(service);
        try {
            r.listen();
            System.out.println("Lamp have started!");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new Error(e);
        }
        return service;
    }

    private EventService createService(LampInfo lamp) {
        IEventSender b = new Sender();
        service = new EventService(b, lamp);
        Listener r = new Listener(service);
        try {
            r.listen();
            System.out.println("Lamp have started!");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new Error(e);
        }
        return service;
    }
}
