package com.graphqlexample.model;

public record Player(Integer Id, String name, Team team) {
    public Integer getId(){
        return Id;
    }
    public String getName() {
        return name;
    }
    public Team getTeam(){
        return team;
    }
}
