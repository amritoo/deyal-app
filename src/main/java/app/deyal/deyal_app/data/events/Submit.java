package app.deyal.deyal_app.data.events;

public class Submit {
    private String proofOfWork;

    public Submit(String proofOfWork) {
        this.proofOfWork = proofOfWork;
    }

    public String getProofOfWork() {
        return proofOfWork;
    }

    public void setProofOfWork(String proofOfWork) {
        this.proofOfWork = proofOfWork;
    }
}
