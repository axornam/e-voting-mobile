package com.progress.e_voting.models;

public class Voter {

    private String voter_id;
    private String email;
    private String DOB;

    public Voter(String voter_id, String voter_email, String dob) {
        this.voter_id = voter_id;
        this.DOB = dob;
        this.email = voter_email;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVoter_id() {
        return voter_id;
    }

    public void setVoter_id(String voter_id) {
        this.voter_id = voter_id;
    }

}
