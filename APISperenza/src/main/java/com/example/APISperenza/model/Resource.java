package com.example.APISperenza.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    // constraint 2 apres virgules
    private float largeur;

    private float longueur;

    @ManyToMany(mappedBy = "resourceForCreate")
    private List<Product> productsToCreate;

    @ManyToMany(mappedBy = "lResources")
    private List<Fournisseur> lFournisseurs;

    @ManyToMany(mappedBy = "listeResource")
    private List<Stock> lStocks;

    @ManyToMany(mappedBy = "lResources")
    private List<File> lFiles;

    @Override
    public String toString() {
        return "Resource [id=" + id +
                ", name=" + name +
                ", largeur=" + largeur +
                ", longueur=" + longueur
                + ", productsToCreate=" + productsToCreate + "]";
    }

}
