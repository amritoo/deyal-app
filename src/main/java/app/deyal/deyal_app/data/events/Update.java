package app.deyal.deyal_app.data.events;

import app.deyal.deyal_app.data.Mission;

public class Update {
    Mission oldMission;

    public Mission getOldMission() {
        return oldMission;
    }

    public void setOldMission(Mission oldMission) {
        this.oldMission = oldMission;
    }
}
