package com.example.poleuniversev20;

public class SportsmeDataHolder {
    private String mName;
    private boolean ratedTechnique;
    private boolean ratedChoreography;
    private boolean ratedArtistic;
    private boolean ratedPenalty;

    public boolean isRatedTechnique() {
        return ratedTechnique;
    }

    public boolean isRatedChoreography() {
        return ratedChoreography;
    }

    public boolean isRatedArtistic() {
        return ratedArtistic;
    }

    public boolean isRatedPenalty() {
        return ratedPenalty;
    }


    public String getmName() {
        return mName;
    }


    public SportsmeDataHolder(String mName, boolean ratedTechnique, boolean ratedChoreography, boolean ratedArtistic, boolean ratedPenalty) {
        this.mName = mName;
        this.ratedTechnique = ratedTechnique;
        this.ratedChoreography = ratedChoreography;
        this.ratedArtistic = ratedArtistic;
        this.ratedPenalty = ratedPenalty;
    }
}
