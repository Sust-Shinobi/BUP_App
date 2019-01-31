package com.sust.bup_app;

public class Helpblog {


    String name,occupation,phoneNo;

    public Helpblog(){

    }

    public Helpblog(String name,String occupation,String phoneNo){
        this.name = name;
        this.occupation = occupation;
        this.phoneNo = phoneNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

}
