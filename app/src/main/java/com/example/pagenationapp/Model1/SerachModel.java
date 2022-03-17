package com.example.pagenationapp.Model1;

import java.util.ArrayList;

public class SerachModel {
    private ArrayList<ImageModel> results;

    public SerachModel(ArrayList<ImageModel> results) {
        this.results = results;
    }

    public ArrayList<ImageModel> getResults() {
        return results;
    }

    public void setResults(ArrayList<ImageModel> results) {
        this.results = results;
    }
}
