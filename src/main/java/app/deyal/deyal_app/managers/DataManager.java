package app.deyal.deyal_app.managers;

import app.deyal.deyal_app.data.Mission;
import app.deyal.deyal_app.data.MissionEvent;
import app.deyal.deyal_app.data.User;
import app.deyal.deyal_app.data.events.EventType;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;

public class DataManager {

    public String token;
    public User userData;
    public ArrayList<Mission> allMissionsList;
    public ArrayList<Mission> myMissionsList;

    public User tempUser;
    public Mission tempMission;
    public ArrayList<Mission> tempMissionList;
    public ArrayList<MissionEvent> tempMissionEventList;

    public String tempMessage;
    public boolean tempChoice, tempApprove;

    public StackPane mainRoot;
    public Node mainContentRoot;

    public static DataManager getInstance() {
        return DataManager.Singleton.INSTANCE;
    }

    public ArrayList<MissionEvent> getRequestEvents() {
        ArrayList<MissionEvent> missionEvents = new ArrayList<>();
        for (MissionEvent event : tempMissionEventList) {
            if (event.getEventType() == EventType.REQUEST) {
                missionEvents.add(event);
            }
        }
        return missionEvents;
    }

    public Mission searchMissionById(String missionId) {
        for (Mission mission : allMissionsList) {
            if (mission.getId().equals(missionId)) {
                return mission;
            }
        }
        return null;
    }

    public ArrayList<Mission> searchMissionByTitle(String title) {
        ArrayList<Mission> missionArrayList = new ArrayList<>();
        for (Mission mission : allMissionsList) {
            if (mission.getTitle().contains(title)) {
                missionArrayList.add(mission);
            }
        }
        return missionArrayList;
    }

    public void clearAllData() {
        token = null;
        userData = null;
        allMissionsList = null;
        myMissionsList = null;
        tempUser = null;
        tempMission = null;
        tempMissionList = null;
        tempMissionEventList = null;
        tempChoice = false;
        tempMessage = null;
    }

    public String getToken() {
        return token;
    }

    private static class Singleton {
        private static final DataManager INSTANCE = new DataManager();
    }

}
