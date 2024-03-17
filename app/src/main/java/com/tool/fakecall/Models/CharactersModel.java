package com.tool.fakecall.Models;

public class CharactersModel {
    public String name;
    public String code;
    public int image;

    public CharactersModel(String name, String code, int image) {
        this.name = name;
        this.code = code;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
