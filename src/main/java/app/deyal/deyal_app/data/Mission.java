package app.deyal.deyal_app.data;

public class Mission {
    private String id;

    private String title;
    private String description;
    private String longDescription;
    private int difficulty;

    private String creatorId;

    private String contractorId;

    public Mission() {
    }

    public Mission(String title, String description, String longDescription, int difficulty) {
        this.title = title;
        this.description = description;
        this.longDescription = longDescription;
        this.difficulty = difficulty;
    }

    public void print() {
        System.out.println("id: " + getId());
        System.out.println("title: " + getTitle());
        System.out.println("description: " + getDescription());
        System.out.println("Long description: " + getLongDescription());
        System.out.println("Difficulty: " + getDifficulty());
        System.out.println("Creator id: " + getCreatorId());
        System.out.println("Contractor id: " + getContractorId());
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

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getContractorId() {
        return contractorId;
    }

    public void setContractorId(String contractorId) {
        this.contractorId = contractorId;
    }
}
