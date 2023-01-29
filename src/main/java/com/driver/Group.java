package com.driver;

public class Group {


    private String name;
    private int numberOfParticipants;

    public int getCountOfMessage() {
        return countOfMessage;
    }

    public void setCountOfMessage(int countOfMessage) {
        this.countOfMessage = countOfMessage;
    }

    private int countOfMessage;

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    private String admin;

    public Group(){

    }

    public Group(String name,int numberOfParticipants,String admin,int countOfMessage){
        this.name = name;
        this.numberOfParticipants = numberOfParticipants;
        this.admin = admin;
        this.countOfMessage = countOfMessage;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public void setNumberOfParticipants(int numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
    }


}
