package com.example.APISperenza.service;

import java.util.List;

import com.example.APISperenza.dto.FournisseurDTO;
import com.example.APISperenza.model.Fournisseur;

public interface FournisseurService {

    // CRUD

    List<Fournisseur> getAll();

    Fournisseur getByID(long id);

    Fournisseur createFournisseur(Fournisseur fournisseur);

    Fournisseur updatFournisseurByID(long id, Fournisseur fournisseur);

    void deleteFournisseurById(long id);

    void deleteAll();

    // convert
    Fournisseur convertToEntity(FournisseurDTO fournisseurDTO);

    FournisseurDTO convertToDTO(Fournisseur fournisseur);

    List<Fournisseur> convertToListEntity(List<FournisseurDTO> lDtos);

    List<FournisseurDTO> convertToListDTO(List<Fournisseur> list);
}
