package app.deyal.deyal_app.data;

public class Address {

    private String houseAddress;
    private String blockAddress;
    private String district;
    private String policeStation;
    private String postOffice;

    /* ------------------------------------------------------------------------- */

    public Address() {
    }

    public Address(String houseAddress, String blockAddress, String district, String policeStation, String postOffice) {
        this.houseAddress = houseAddress;
        this.blockAddress = blockAddress;
        this.district = district;
        this.policeStation = policeStation;
        this.postOffice = postOffice;
    }

    public String getHouseAddress() {
        return houseAddress;
    }

    public void setHouseAddress(String houseAddress) {
        this.houseAddress = houseAddress;
    }

    public String getBlockAddress() {
        return blockAddress;
    }

    public void setBlockAddress(String blockAddress) {
        this.blockAddress = blockAddress;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPoliceStation() {
        return policeStation;
    }

    public void setPoliceStation(String policeStation) {
        this.policeStation = policeStation;
    }

    public String getPostOffice() {
        return postOffice;
    }

    public void setPostOffice(String postOffice) {
        this.postOffice = postOffice;
    }

}
