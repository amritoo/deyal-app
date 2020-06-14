package app.deyal.deyal_app.data.events;

import app.deyal.deyal_app.data.Mission;

public class Update {
    Mission oldMission;

    @Override
    public String toString() {
        String string = "id: " + oldMission.getId() +
                ",\t" + "title: " + oldMission.getTitle() +
                ",\t" + "description: " + oldMission.getDescription() +
                ",\t" + "Long description: " + oldMission.getLongDescription() +
                ",\t" + "Difficulty: " + oldMission.getDifficulty() +
                ",\t" + "Creator id: " + oldMission.getCreatorId() +
                ",\t" + "Contractor id: " + oldMission.getContractorId();
        System.out.println(string);
        return string;
    }

    public Mission getOldMission() {
        return oldMission;
    }

    public void setOldMission(Mission oldMission) {
        this.oldMission = oldMission;
    }
}
