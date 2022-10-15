package com.example.APISperenza.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    // contrainte : 2 apres la virgule
    private float prix;

    private float note;

    private float largeur;

    private float longueur;

    @ManyToMany
    @JoinTable(name = "product_resource", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "resource_id"))
    private List<Resource> resourceForCreate;

    @Override
    public String toString() {
        return "Product [id=" + id +
                ", name=" + name +
                ", prix=" + prix +
                ", note=" + note +
                ", largeur=" + largeur
                + ", longueur=" + longueur +
                ", resourceForCreate=" + resourceForCreate + "]";
    }

}
