package com.challenge.opentrends.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

public class CreateRepoRequestTemplate {
    private String name;
    private ArrayList<String> tag_list;
    private String developBranch;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getTag_list() {
        return tag_list;
    }

    public void setTag_list(ArrayList<String> tag_list) {
        this.tag_list = tag_list;
    }

    public String getDevelopBranch() {
        return developBranch;
    }

    public void setDevelopBranch(String developBranch) {
        this.developBranch = developBranch;
    }
}
