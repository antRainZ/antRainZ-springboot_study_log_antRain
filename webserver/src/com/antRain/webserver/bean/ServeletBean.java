package com.antRain.webserver.bean;

public class ServeletBean{
    private String name;
    private String clz;

    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClz() {
        return clz;
    }

    public void setClz(String clz) {
        this.clz = clz;
    }

    public ServeletBean() {

    }

    @Override
    public String toString() {
        return "serveletBean [clz=" + clz + ", name=" + name + "]";
    }

    public ServeletBean(String name, String clz) {
        this.name = name;
        this.clz = clz;
    }
    
    
}
