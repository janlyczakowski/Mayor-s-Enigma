package com.tudresden.map;

public class PlaceModal {

    private String name;
    private double latitude;
    private double longitude;
    private boolean visited;

    public PlaceModal(String name, double latitude, double longitude, Boolean visited){

        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.visited = visited;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public boolean getVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
}
