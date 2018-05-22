package com.bunny.healthkitchengymtrainer.Model;

public class Foods {

    private  String Description;
    private String Discount;
    private String Image;
    private String MenuCategory;
    private String Name;
    private String Price;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    private String key;
    @Override
    public String toString() {
        return "Foods{" +
                "Description='" + Description + '\'' +
                ", Discount='" + Discount + '\'' +
                ", Image='" + Image + '\'' +
                ", MenuCategory='" + MenuCategory + '\'' +
                ", Name='" + Name + '\'' +
                ", Price='" + Price + '\'' +
                '}';
    }

    public Foods(){

    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getMenuCategory() {
        return MenuCategory;
    }

    public void setMenuCategory(String menuCategory) {
        MenuCategory = menuCategory;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public Foods(String description, String discount, String image, String menuCategory, String name, String price) {
        Description = description;
        Discount = discount;

        Image = image;
        MenuCategory = menuCategory;
        Name = name;
        Price = price;
    }
}
