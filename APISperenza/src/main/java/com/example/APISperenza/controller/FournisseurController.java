package com.example.APISperenza.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.APISperenza.dto.FournisseurDTO;
import com.example.APISperenza.model.Fournisseur;
import com.example.APISperenza.service.FournisseurService;

@RestController
@RequestMapping("/api/fournisseur/")
public class FournisseurController {

    private final FournisseurService fournisseurService;

    public FournisseurController(FournisseurService fournisseurService) {
        this.fournisseurService = fournisseurService;
    }

    @GetMapping("all")
    public ResponseEntity<List<FournisseurDTO>> getAll() {
        List<Fournisseur> list = fournisseurService.getAll();

        List<FournisseurDTO> lDtos = fournisseurService.convertToListDTO(list);

        return new ResponseEntity<>(lDtos, HttpStatus.ACCEPTED);
    }

    @GetMapping("{id}")
    public ResponseEntity<FournisseurDTO> getByID(@PathVariable long id) {
        Fournisseur fournisseur = fournisseurService.getByID(id);

        FournisseurDTO fournisseurDTO = fournisseurService.convertToDTO(fournisseur);

        return new ResponseEntity<>(fournisseurDTO, HttpStatus.ACCEPTED);
    }

    @PostMapping("create")
    public ResponseEntity<FournisseurDTO> create(@RequestBody FournisseurDTO fournisseurDTO) {
        Fournisseur fournisseur = fournisseurService.convertToEntity(fournisseurDTO);

        Fournisseur fournisseurCreated = fournisseurService.createFournisseur(fournisseur);

        FournisseurDTO fournisseurDTO2 = fournisseurService.convertToDTO(fournisseurCreated);

        return new ResponseEntity<>(fournisseurDTO2, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<FournisseurDTO> updateFournisseur(@PathVariable long id,
            @RequestBody FournisseurDTO fournisseurDTO)

    {
        Fournisseur fournisseur = fournisseurService.convertToEntity(fournisseurDTO);

        Fournisseur fournisseuUpdated = fournisseurService.updatFournisseurByID(id, fournisseur);

        FournisseurDTO fournisseurDTO2 = fournisseurService.convertToDTO(fournisseuUpdated);

        return new ResponseEntity<>(fournisseurDTO2, HttpStatus.ACCEPTED);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteByID(@PathVariable long id) {
        fournisseurService.deleteFournisseurById(id);
        return new ResponseEntity<>("Deleted ", HttpStatus.ACCEPTED);
    }

    @DeleteMapping("all")
    public ResponseEntity<String> deleteAll() {
        fournisseurService.deleteAll();
        return new ResponseEntity<>(" All Deleted ", HttpStatus.ACCEPTED);
    }

}
