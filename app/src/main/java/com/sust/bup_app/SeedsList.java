package com.sust.bup_app;

public class SeedsList {
    String title,desc,image,price;

    public SeedsList() {
    }

    public SeedsList(String title, String desc,String price, String image) {
        this.title = title;
        this.desc = desc;
        this.image = image;
        this.price = price;
    }

    public String getPrice() {return price;}

    public void setPrice() {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
