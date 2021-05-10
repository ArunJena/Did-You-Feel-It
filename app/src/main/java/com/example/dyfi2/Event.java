package com.example.dyfi2;

public class Event {
    private String title;
    private String numPeople;
    private String felt;

    public Event(String title, String numPeople, String felt) {
        this.title = title;
        this.numPeople = numPeople;
        this.felt = felt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNumPeople() {
        return numPeople;
    }

    public void setNumPeople(String numPeople) {
        this.numPeople = numPeople;
    }

    public String getFelt() {
        return felt;
    }

    public void setFelt(String felt) {
        this.felt = felt;
    }
}

