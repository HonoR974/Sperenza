package com.example.APISperenza.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.APISperenza.dto.StockDTO;
import com.example.APISperenza.exception.ResourceNotFoundException;
import com.example.APISperenza.model.Stock;
import com.example.APISperenza.repository.StockRepository;

@Service
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;

    private final ModelMapper modelMapper;

    public StockServiceImpl(StockRepository stockRepository, ModelMapper modelMapper) {
        this.stockRepository = stockRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Stock> getAll() {
        return stockRepository.findAll();
    }

    @Override
    public Stock getByID(long id) {

        return stockRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Le stock avec l'id " + id + " n'existe pas "));
    }

    @Override
    public Stock createStock(Stock stock) {

        return stockRepository.save(stock);
    }

    @Override
    public Stock updateStockByID(long id, Stock stock) {

        Stock stock2 = stockRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Le stock avec l'id " + id + " n'existe pas "));

        stock2.setName(stock.getName());
        stock2.setListeResource(stock.getListeResource());

        return stockRepository.save(stock);
    }

    @Override
    public void deleteStockByID(long id) {

        Stock stock = stockRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Le stock avec l'id " + id + " n'existe pas "));

        stockRepository.delete(stock);

    }

    @Override
    public void deleteAll() {
        stockRepository.deleteAll();

    }

    // ---------------------- Convert ----------------//
    @Override
    public Stock convertToEntity(StockDTO stockDTO) {

        return modelMapper.map(stockDTO, Stock.class);
    }

    @Override
    public StockDTO convertToDTO(Stock stock) {

        return modelMapper.map(stock, StockDTO.class);
    }

    @Override
    public List<Stock> convertToListEntity(List<StockDTO> lDtos) {

        List<Stock> list = new ArrayList<>();

        for (StockDTO stockDTO : lDtos) {
            Stock stock = modelMapper.map(stockDTO, Stock.class);
            list.add(stock);
        }
        return list;
    }

    @Override
    public List<StockDTO> convertToListDTO(List<Stock> list) {

        List<StockDTO> list2 = new ArrayList<>();

        for (Stock stock : list) {
            StockDTO stockDTO = modelMapper.map(stock, StockDTO.class);
            list2.add(stockDTO);
        }
        return list2;
    }

}
