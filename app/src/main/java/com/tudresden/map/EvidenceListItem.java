package com.tudresden.map;

public class EvidenceListItem {
    private int locationImage; // id of location image
    private int locationTitle; // id of location title
    private String locationNumber;

    public EvidenceListItem(int locationImage, int locationTitle, String locationNumber){
        this.locationImage = locationImage;
        this.locationTitle = locationTitle;
        this.locationNumber = locationNumber;
    }

    public int getLocationImage(){
        return locationImage;
    }

    public void setLocationImage(int locationImage) {
        this.locationImage = locationImage;
    }
    public int getLocationTitle(){
        return locationTitle;
    }

    public void setLocationTitle(int locationTitle) {
        this.locationTitle = locationTitle;
    }
    public String getLocationNumber(){
        return locationNumber;
    }

    public void setLocationNumber(String locationNumber) {
        this.locationNumber = locationNumber;
    }
}
