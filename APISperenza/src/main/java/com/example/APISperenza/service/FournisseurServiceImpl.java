package com.example.APISperenza.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.APISperenza.dto.FournisseurDTO;
import com.example.APISperenza.exception.ResourceNotFoundException;
import com.example.APISperenza.model.Fournisseur;
import com.example.APISperenza.repository.FournisseurRepository;

@Service
public class FournisseurServiceImpl implements FournisseurService {

    private final FournisseurRepository fournisseurRepository;

    private final ModelMapper modelMapper;

    public FournisseurServiceImpl(FournisseurRepository fournisseurRepository, ModelMapper modelMapper) {
        this.fournisseurRepository = fournisseurRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Fournisseur> getAll() {

        return fournisseurRepository.findAll();
    }

    @Override
    public Fournisseur getByID(long id) {

        return fournisseurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Le fournisseur avec l'id " + id + " n'existe pas "));
    }

    @Override
    public Fournisseur createFournisseur(Fournisseur fournisseur) {

        return fournisseurRepository.save(fournisseur);
    }

    @Override
    public Fournisseur updatFournisseurByID(long id, Fournisseur fournisseurRequest) {

        Fournisseur fournisseur = fournisseurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Le fournisseur avec l'id " + id + " n'existe pas "));

        fournisseur.setName(fournisseurRequest.getName());
        fournisseur.setAdresse(fournisseurRequest.getAdresse());
        fournisseur.setCodePostal(fournisseurRequest.getCodePostal());
        fournisseur.setNumeroTelephone(fournisseur.getNumeroTelephone());
        fournisseur.setLResources(fournisseurRequest.getLResources());

        return fournisseurRepository.save(fournisseur);
    }

    @Override
    public void deleteFournisseurById(long id) {

        Fournisseur fournisseur = fournisseurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Le fournisseur avec l'id " + id + " n'existe pas "));

        fournisseurRepository.delete(fournisseur);

    }

    @Override
    public void deleteAll() {

        fournisseurRepository.deleteAll();

    }
    // -------------------------------- Convert ----------------//

    @Override
    public Fournisseur convertToEntity(FournisseurDTO fournisseurDTO) {

        Fournisseur fournisseur = modelMapper.map(fournisseurDTO, Fournisseur.class);

        return fournisseur;
    }

    @Override
    public FournisseurDTO convertToDTO(Fournisseur fournisseur) {

        FournisseurDTO fournisseurDTO = modelMapper.map(fournisseur, FournisseurDTO.class);
        return fournisseurDTO;
    }

    @Override
    public List<Fournisseur> convertToListEntity(List<FournisseurDTO> lDtos) {

        List<Fournisseur> list = new ArrayList<>();

        for (FournisseurDTO fournisseurDTO : lDtos) {
            Fournisseur fournisseur = modelMapper.map(fournisseurDTO, Fournisseur.class);
            list.add(fournisseur);
        }
        return list;
    }

    @Override
    public List<FournisseurDTO> convertToListDTO(List<Fournisseur> list) {

        List<FournisseurDTO> lDtos = new ArrayList<>();

        for (Fournisseur fournisseur : list) {
            FournisseurDTO fournisseurDTO = modelMapper.map(fournisseur, FournisseurDTO.class);
            lDtos.add(fournisseurDTO);
        }
        return lDtos;
    }

}
