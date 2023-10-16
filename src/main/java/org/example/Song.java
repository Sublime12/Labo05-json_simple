package org.example;

public class Song {
    private String title;

    private String artist;

    private String type;

    private long publication;

    private long rating;

    public Song() {

    }

    public Song(String title, String artist, String type, long publication,
                long rating) {
        this.title = title;
        this.artist = artist;
        this.type = type;
        this.publication = publication;
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getPublication() {
        return publication;
    }

    public void setPublication(long publication) {
        this.publication = publication;
    }

    public long getRating() {
        return rating;
    }

    public void setRating(long rating) {
        this.rating = rating;
    }
}
