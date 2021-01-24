package messaging.rabbitmq.interfaces;

import messaging.Event;

public interface IEventReceiver {
	void receiveEvent(Event event) throws Exception;
}
