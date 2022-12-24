package io.proj3ct.GameTGBot.game;

public class City {
    private String name;
    private String image;
    private String info;

    public City(String name, String info, String image){
        this.name = name;
        this.image = image;
        this.info = info;
    }

    public String getImage() {
        return image;
    }

    public String getInfo() {
        return info;
    }

    public String getName() {
        return name;
    }
}
