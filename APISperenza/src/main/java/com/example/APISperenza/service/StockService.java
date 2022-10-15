package com.example.APISperenza.service;

import java.util.List;

import com.example.APISperenza.dto.StockDTO;
import com.example.APISperenza.model.Stock;

public interface StockService {

    // CRUD

    List<Stock> getAll();

    Stock getByID(long id);

    Stock createStock(Stock stock);

    Stock updateStockByID(long id, Stock stock);

    void deleteStockByID(long id);

    void deleteAll();

    // convert
    Stock convertToEntity(StockDTO stockDTO);

    StockDTO convertToDTO(Stock stock);

    List<Stock> convertToListEntity(List<StockDTO> lDtos);

    List<StockDTO> convertToListDTO(List<Stock> list);
}
