package com.tudresden.map;

public class Suspect {

    private int suspectImage; //id of suspect image
    private String suspectName;

    public Suspect(int suspectImage, String suspectName){
        this.suspectImage = suspectImage;
        this.suspectName = suspectName;
    }

    public int getSuspectImage(){
        return suspectImage;
    }

    public void setSuspectImage(int suspectImage){
        this.suspectImage = suspectImage;
    }

    public String getSuspectName(){
        return suspectName;
    }

    public void setSuspectName(String suspectName){
        this.suspectName = suspectName;
    }
}
