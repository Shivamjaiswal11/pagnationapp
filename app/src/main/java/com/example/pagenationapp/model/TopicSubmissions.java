
package com.example.pagenationapp.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class TopicSubmissions {

    @SerializedName("people")
    @Expose
    private People people;

    public People getPeople() {
        return people;
    }

    public void setPeople(People people) {
        this.people = people;
    }

}
