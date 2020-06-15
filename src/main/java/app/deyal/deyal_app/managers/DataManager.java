package app.deyal.deyal_app.managers;

import app.deyal.deyal_app.data.Mission;
import app.deyal.deyal_app.data.MissionEvent;
import app.deyal.deyal_app.data.User;
import app.deyal.deyal_app.data.events.EventType;

import java.util.ArrayList;
import java.util.Map;

public class DataManager {

    public static String server = "http://localhost:3030/v1";

    public String token;
    public User userData;
    public ArrayList<Mission> allMissionsList;
    public ArrayList<Mission> myMissionsList;
    public Map<String, String> userIdAndNameMap;

    public User tempUser;
    public Mission tempMission;
    public ArrayList<Mission> tempMissionList;
    public ArrayList<MissionEvent> tempMissionEventList;

    public String tempMessage;
    public boolean tempChoice;

    public String getUserName(String userId) {
        return userIdAndNameMap.get(userId);
    }

    public ArrayList<MissionEvent> getRequestEvents() {
        ArrayList<MissionEvent> missionEvents = new ArrayList<>();
        for (MissionEvent event : tempMissionEventList) {
            if (event.getEventType() == EventType.REQUEST) {
                missionEvents.add(event);
                System.out.println(event.getEventType());
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
            if (mission.getTitle().equals(title)) {
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
        userIdAndNameMap = null;
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

    public static DataManager getInstance() {
        return DataManager.Singleton.INSTANCE;
    }

    private static class Singleton {
        private static final DataManager INSTANCE = new DataManager();
    }

}
