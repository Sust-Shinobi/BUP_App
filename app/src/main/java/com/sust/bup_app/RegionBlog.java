package com.sust.bup_app;

public class RegionBlog {
    String title,soil,temp,rainfall,crop1,crop2;

    public RegionBlog() {
    }

    public RegionBlog(String title, String soil,String temp,String rainfall) {
        this.title = title;
        this.soil = soil;
        this.temp = temp;
        this.rainfall = rainfall;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSoil(){
        return soil;
    }

    public void setSoil(){
        this.soil = soil;
    }

    public String getTemp(){
        return temp;
    }

    public void setTemp(){
        this.temp = temp;
    }

    public String getRainfall(){
        return rainfall;
    }

    public void setRainfall(){
        this.rainfall = rainfall;
    }

    public String getCrop1(){
        return crop1;
    }

    public void setCrop1(){
        this.crop1 = crop1;
    }

    public void setCrop2(){
        this.crop2 = crop2;
    }

    public String getCrop2() {
        return crop2;
    }
}
