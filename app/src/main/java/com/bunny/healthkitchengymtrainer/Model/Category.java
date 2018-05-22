package com.bunny.healthkitchengymtrainer.Model;

public class Category {

    private String name;
    private String image;

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

    public Category(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public Category(){

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
