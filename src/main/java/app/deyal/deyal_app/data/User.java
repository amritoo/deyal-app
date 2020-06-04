package app.deyal.deyal_app.data;

import java.time.LocalDate;
import java.util.Date;

public class User {

    private String id;

    private String userName;
    private String fullName;

    private String email;

    private String password;
    private long dateOfBirth;
    private String phoneNumber;

    private Address address;

    private MissionInfo missionInfo;
    private int reputation;

    private Date registrationDate;

    public void print() {
        System.out.println(id);
        System.out.println(userName);
        System.out.println(fullName);
        System.out.println(email);
        System.out.println(password);
        System.out.println(dateOfBirth);
        System.out.println(phoneNumber);
        System.out.println(address);
        System.out.println(missionInfo);
        System.out.println(reputation);
        System.out.println(registrationDate);
    }

    /*............................................................................................................*/

    public User() {
    }

    public String getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(long dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public MissionInfo getMissionInfo() {
        return missionInfo;
    }

    public void setMissionInfo(MissionInfo missionInfo) {
        this.missionInfo = missionInfo;
    }

    public int getReputation() {
        return reputation;
    }

    public void setReputation(int reputation) {
        this.reputation = reputation;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }
}
