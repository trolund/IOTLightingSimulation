package cucumber;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lamp.Color;
import lamp.LampInfo;
import main.ControlService;
import messaging.ControllerEventService;
import messaging.Event;
import messaging.IController;
import messaging.rabbitmq.ControllerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PaymentEventsSteps {

    ControllerEventService s;
    ControlService helper;
    List<LampInfo> lamps;
    Event event;


    public PaymentEventsSteps() {
        s = new ControllerFactory().getService();
        helper = new ControlService();
        lamps = new ArrayList<>();
    }

    @Given("a list of test devices")
    public void sendAccountEvent(DataTable table) {
        for (Map<Object, Object> row : table.asMaps(String.class, String.class)) {
            int id = Integer.parseInt((String) row.get("id"));
            String name = (String) row.get("name");
            float intensity = Float.parseFloat((String) row.get("intensity"));
            Color color = helper.parseColor((String) row.get("color"));
            boolean isOn = ((String) row.get("isOn")).equals("ON");

            LampInfo lamp = new LampInfo(id, name, intensity, color, isOn);
            lamps.add(lamp);
        }
    }

    @Then("I connect the all lamps")
    public void connectLamps(){
        for (LampInfo l: lamps) {
            try {
                helper.runLamp(l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Then("i lookup all lamps")
    public void lookup(){

    }

    @Given("a list of lamps")
    public void listOfLamps(DataTable table){
        for (Map<Object, Object> row : table.asMaps(String.class, String.class)) {
            int id = Integer.parseInt((String) row.get("id"));
            String name = (String) row.get("name");


        }
    }

    @Then("i turn {string} all lamps")
    public void turnAll(String s){
        boolean isOn = s.equals("ON");

    }

    @And("then i check all lamps is know {string}")
    public void checkTurnAll(String s){
        boolean isOn = s.equals("ON");

    }
}

