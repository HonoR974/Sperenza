package com.example.APISperenza.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String url;

    private String name;

    @ManyToMany
    @JoinTable(name = "file_resource", joinColumns = @JoinColumn(name = "file_id"), inverseJoinColumns = @JoinColumn(name = "resource_id"))
    private List<Resource> lResources;

    public File() {
    }

    public File(String urlSend, String nameSend) {
        this.url = urlSend;
        this.name = nameSend;
    }

}
