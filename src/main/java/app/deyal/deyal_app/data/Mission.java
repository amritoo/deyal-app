package app.deyal.deyal_app.data;

import app.deyal.deyal_app.managers.DataManager;

public class Mission {
    private String id;

    private String title;
    private String description;
    private String longDescription;
    private MissionDifficulty difficulty;

    private String creatorId;
    private String creatorName;
    private String contractorId;
    private String contractorName;

    public Mission() {
    }

    public Mission(String title, String description, String longDescription, MissionDifficulty difficulty) {
        this.title = title;
        this.description = description;
        this.longDescription = longDescription;
        this.difficulty = difficulty;
    }

    public String findStatus(String id) {
        MissionInfo missionInfo = DataManager.getInstance().userData.getMissionInfo();
        if (missionInfo.getCompleted().contains(id))
            return "Completed";
        else if (missionInfo.getCreated().contains(id))
            return "Created";
        else if (missionInfo.getFailed().contains(id))
            return "Failed";
        else if (missionInfo.getOngoing().contains(id))
            return "Ongoing";
        else
            return "Unknown";
    }

    public String getDifficultyAsString() {
        return switch (difficulty) {
            case VERY_EASY -> "Very Easy";
            case EASY -> "Easy";
            case MEDIUM -> "Medium";
            case HARD -> "Hard";
            case VERY_HARD -> "Very Hard";
            default -> "Unknown";
        };
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public MissionDifficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(MissionDifficulty difficulty) {
        this.difficulty = difficulty;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getContractorId() {
        return contractorId;
    }

    public void setContractorId(String contractorId) {
        this.contractorId = contractorId;
    }

    public String getContractorName() {
        return contractorName;
    }

    public void setContractorName(String contractorName) {
        this.contractorName = contractorName;
    }

}
