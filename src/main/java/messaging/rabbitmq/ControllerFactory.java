package messaging.rabbitmq;

import messaging.ControllerEventService;
import messaging.rabbitmq.interfaces.IEventSender;

public class ControllerFactory {

    static ControllerEventService service = null;

    public ControllerEventService getService() {

        if (service != null) {
            return service;
        }

        return createService();
    }

    private ControllerEventService createService() {
        IEventSender b = new Sender();
        service = new ControllerEventService(b);
        Listener r = new Listener(service);
        try {
            r.listen();
            System.out.println("Controller have started!");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new Error(e);
        }
        return service;
    }
}
