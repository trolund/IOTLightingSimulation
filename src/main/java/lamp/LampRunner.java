package lamp;

import messaging.rabbitmq.Factory;

public class LampRunner implements Runnable {

    private LampInfo info = null;

    public LampInfo getInfo() {
        return info;
    }

    public void setInfo(LampInfo info) {
        this.info = info;
    }

    @Override
    public void run() {
        // LOGGER.info("The application is starting... RabbitMQ host: " + System.getenv("RABBITMQ_HOST"));
        try {
            //LOGGER.info("Starting new Lamp...");
            System.out.println("Starting new Lamp...");
            // starting the "Lamp"
            if(getInfo() != null){
                EventService s = new Factory().getNewService(getInfo());
            }else {
                EventService s = new Factory().getNewService();
            }
        } catch (Exception e) {
            System.out.println("Failed to start lamp...");
            // LOGGER.severe("Failed to start lamp...");
            e.printStackTrace();
        }
    }

}
