package messaging.rabbitmq.interfaces;

import messaging.Event;

public interface IEventSender {

	void sendEvent(Event event) throws Exception;

}
