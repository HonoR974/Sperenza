package com.example.APISperenza.dto;

import lombok.Data;

@Data
public class FileDTO {

    public long id;

    private String url;

    private String name;

    public FileDTO() {

    }

    public FileDTO(String url_champs, String nom) {
        this.url = url_champs;
        this.name = nom;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
