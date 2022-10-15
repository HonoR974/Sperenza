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

import com.example.APISperenza.dto.StockDTO;
import com.example.APISperenza.model.Stock;
import com.example.APISperenza.service.StockService;

@RestController
@RequestMapping("/api/stock/")
public class StockController {

    private final StockService stockService;

    public StockController(StockService service) {
        this.stockService = service;
    }

    @GetMapping("all")
    public ResponseEntity<List<StockDTO>> getAll() {
        List<Stock> list = stockService.getAll();

        List<StockDTO> lDtos = stockService.convertToListDTO(list);

        return new ResponseEntity<>(lDtos, HttpStatus.ACCEPTED);
    }

    @GetMapping("{id}")
    public ResponseEntity<StockDTO> id(@PathVariable long id) {

        Stock stock = stockService.getByID(id);

        StockDTO stockDTO = stockService.convertToDTO(stock);

        return new ResponseEntity<>(stockDTO, HttpStatus.ACCEPTED);
    }

    @PostMapping("create")
    public ResponseEntity<String> create(@RequestBody StockDTO stockDTO) {

        Stock stock = stockService.convertToEntity(stockDTO);

        stockService.createStock(stock);

        return new ResponseEntity<>("CREATED", HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<StockDTO> update(@PathVariable long id,
            @RequestBody StockDTO stockDTO) {

        Stock stock = stockService.updateStockByID(id, stockService.convertToEntity(stockDTO));

        StockDTO stockDTO2 = stockService.convertToDTO(stock);
        return new ResponseEntity<>(stockDTO2, HttpStatus.ACCEPTED);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {

        stockService.deleteStockByID(id);

        return new ResponseEntity<>("Deleted", HttpStatus.ACCEPTED);

    }

    @DeleteMapping("all")

    public ResponseEntity<String> deleteAll() {

        stockService.deleteAll();

        return new ResponseEntity<>("Deleted All ", HttpStatus.ACCEPTED);

    }

}
