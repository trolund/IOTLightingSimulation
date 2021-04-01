package cucumber;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cucumber.models.GroupDTO;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lamp.Color;
import lamp.LampInfo;
import main.ControlService;
import messaging.ControllerEventService;
import messaging.Event;
import messaging.rabbitmq.ControllerFactory;

import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class TurnOnAndOffEventsSteps {

    ControllerEventService service;
    ControlService helper;
    List<LampInfo> lamps;
    List<LampInfo> singlelamps;
    List<LampInfo> ids;
    List<GroupDTO> groups;
    Event event;


    public TurnOnAndOffEventsSteps() {
        service = new ControllerFactory().getService();
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

            Type listType = new TypeToken<Set<String>>() {}.getType();
            Set<String> groups = new Gson().fromJson((String) row.get("group"), listType);

            LampInfo lamp = new LampInfo(id, name, intensity, color, isOn, groups);
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
        lamps = helper.all();
    }

    @Then("i lookup each lamp")
    public void lookupEach(){
        singlelamps = new ArrayList<>();

        for (LampInfo l: ids) {
            LampInfo lamp = service.get(l.getId());
            if(l.getId() == lamp.getId()){
                singlelamps.add(lamp);
            }
        }
    }

    @Given("a list of lamps")
    public void listOfLamps(DataTable table){
        ids = new ArrayList<>();
        for (Map<Object, Object> row : table.asMaps(String.class, String.class)) {
            int id = Integer.parseInt((String) row.get("id"));
            String name = (String) row.get("name");

            float intensity;
            Color color;
            boolean isOn;
            if(row.get("intensity") != null){
                intensity = Float.parseFloat((String) row.get("intensity"));
            }else {
                intensity = 100;
            }
            if(row.get("color") != null){
                color = helper.parseColor((String) row.get("color"));
            }else{
                color = new Color();
            }
            if(row.get("isOn") != null){
                isOn = ((String) row.get("isOn")).equals("ON");
            }else {
                isOn = true;
            }

            ids.add(new LampInfo(id, name, intensity, color, isOn));
        }
    }


    @Given("a list of groups")
    public void listOfGroups(DataTable table){
        groups = new ArrayList<>();
        for (Map<Object, Object> row : table.asMaps(String.class, String.class)) {
            String name = (String) row.get("groupName");
            float intensity = 0;
            Color c = new Color(0,0,0);

            if(row.get("intensity") != null){
                intensity = Float.parseFloat((String) row.get("intensity"));
            }

            if(row.get("color") != null){
                c = helper.parseColor((String) row.get("color"));
            }


            LampInfo l = new LampInfo();
            l.setIntensity(intensity);
            l.setColor(c);

            groups.add(new GroupDTO(name, l));
        }
    }

    @Then("i turn {string} all lamps")
    public void turnAll(String s){
        boolean isOn = s.equals("ON");

        // send event for all lamps
        for (LampInfo l: ids) {
            service.setOn(l.getId(), isOn);
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @And("then i check all lamps is know {string}")
    public void checkTurnAll(String s){
        boolean isOn = s.equals("ON");
        boolean res = lamps.stream().allMatch(l -> l.isOn() == isOn);
        lamps = new ArrayList<>();
        assertTrue(res);
    }

    @Then("disconnect all devices")
    public void exit(){
        if(ids != null){
            for (LampInfo l: ids) {
                service.sendExit(l.getId());
            }
        }else {
            for (LampInfo l: lamps) {
                service.sendExit(l.getId());
            }
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @And("then i check all lamps is know have the new intensity")
    public void checkIntensity(){
        for (LampInfo l: ids) {
            int id = l.getId();
            // float in = l.getIntensity();

            LampInfo lamp = lamps.stream().filter(a -> a.getId() == id).collect(Collectors.toList()).get(0);

            assertEquals(l.getIntensity(), lamp.getIntensity());
        }
    }

    @And("then i check all lamps is know have the new color")
    public void checkColor(){
        for (LampInfo l: ids) {
            int id = l.getId();
            // float in = l.getIntensity();

            LampInfo lamp = lamps.stream().filter(a -> a.getId() == id).collect(Collectors.toList()).get(0);

            assertEquals(l.getColor(), lamp.getColor());
        }
    }

    @And("then i check all lamps is know have the new name")
    public void checkName(){
        for (LampInfo l: ids) {
            int id = l.getId();
            LampInfo lamp = lamps.stream().filter(a -> a.getId() == id).collect(Collectors.toList()).get(0);

            assertEquals(l.getName(), lamp.getName());
        }
    }

    @And("then i check all groups are there")
    public void checkAllGroups(){
        for (LampInfo l: ids) {
            int id = l.getId();
            LampInfo lamp = lamps.stream().filter(a -> a.getId() == id).collect(Collectors.toList()).get(0);

            Set<String> expectedGroups = l.getGroups();
            assertTrue(lamp.getGroups().containsAll(expectedGroups));
        }
    }

    @And("then i check the removed groups are deleted")
    public void CheckGroups(){
        for (LampInfo l: ids) {
            int id = l.getId();
            LampInfo lamp = lamps.stream().filter(a -> a.getId() == id).collect(Collectors.toList()).get(0);
            Set<String> expectedNotToBeThereGroups = l.getGroups();

            for (String g: expectedNotToBeThereGroups) {
                assertFalse(lamp.getGroups().contains(g));
            }
        }
    }

    @And("then i check that all lamps in the group know have the new intensity")
    public void CheckGroupsIntensity(){
        for (GroupDTO g: groups) {
            List<LampInfo> lampsInGroup = lamps.stream().filter(a -> a.getGroups().contains(g.getGroupName())).collect(Collectors.toList());

            for (LampInfo l: lampsInGroup) {
                assertEquals(l.getIntensity(), g.getLampInfo().getIntensity());
            }
        }
    }

    @And("then i check that all lamps in the group know have the new color")
    public void CheckGroupsColor(){
        for (GroupDTO g: groups) {
            List<LampInfo> lampsInGroup = lamps.stream().filter(a -> a.getGroups().contains(g.getGroupName())).collect(Collectors.toList());

            for (LampInfo l: lampsInGroup) {
                assertEquals(l.getColor(), g.getLampInfo().getColor());
            }
        }
    }

    @And("then i check all lamps is there")
    public void checkLampsIsThere(){
        for (LampInfo l: ids) {
            LampInfo lamp = singlelamps.stream().filter(a -> a.getId() == l.getId()).findFirst().get();
            assertNotNull(lamp);
        }
    }

    @Then("i set the intensity of all lamps")
    public  void setIntensity(){
        for (LampInfo l: ids) {
            service.adjustIntensity(l.getId(), (int) l.getIntensity());
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("i set the color of all lamps in the group")
    public void setGroupColor(){
        for (GroupDTO group: groups) {
            service.adjustColor(group.getGroupName(), group.getLampInfo().getColor());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Then("i set the intensity of all lamps in the group")
    public void setGroupIntensity(){
        for (GroupDTO group: groups) {
            service.adjustIntensity(group.getGroupName(), (int) group.getLampInfo().getIntensity());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Then("i remove all groups to all the lamps")
    public void removeGroup(){
        for (LampInfo l: ids) {
            Set<String> groupsToRemove = l.getGroups();

            for (String group: groupsToRemove) {
                service.removeFromGroup(l.getId(), group);
            }
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("i add a group to all the lamps")
    public void addGroup(){
        for (LampInfo l: ids) {
            Set<String> groups = l.getGroups();

            for (String g: groups) {
                service.addToGroup(l.getId(), g);
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("i set the color of all lamps")
    public void setColor(){
        for (LampInfo l: ids) {
            service.adjustColor(l.getId(), l.getColor());
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("i set the name of all lamps")
    public void setName(){
        for (LampInfo l: ids) {
            service.changeName(l.getId(), l.getName());
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

