package messaging;

import lamp.Color;

public interface IController {

    void getAllLampInfo();
    void getAllGroups();
    void changeName(int id, String name);
    void changeGroupName(String groupName, String newName);
    void addToGroup(int id, String groupName);
    void removeFromGroup(int id, String groupName);
    void adjustIntensity(String groupName, int intensity);
    void adjustColor(String groupName, Color color);
    void adjustIntensity(int id, int intensity);
    void adjustColor(int id, Color color);

}
