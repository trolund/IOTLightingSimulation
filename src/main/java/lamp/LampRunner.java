package lamp;

import messaging.rabbitmq.Factory;

public class LampRunner implements Runnable {

    @Override
    public void run() {
        // LOGGER.info("The application is starting... RabbitMQ host: " + System.getenv("RABBITMQ_HOST"));
        try {
            //LOGGER.info("Starting new Lamp...");
            System.out.println("Starting new Lamp...");
            // starting the "Lamp"
            EventService s = new Factory().getNewService();
        } catch (Exception e) {
            System.out.println("Failed to start lamp...");
            // LOGGER.severe("Failed to start lamp...");
            e.printStackTrace();
        }
    }

}
