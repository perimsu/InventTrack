package com.inventTrack.StockManagementService.Service;


import com.inventTrack.StockManagementService.Model.Stock;
import com.inventTrack.StockManagementService.Repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockService {
    @Autowired
    private StockRepository stockRepository;

    public List<Stock> getAllStocks(){
        return stockRepository.findAll();
    }

    public Stock getStockById(Long id){
        return stockRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stock not found with id: " + id));
    }

    public Stock getStockByProductId(Long productId){
        return stockRepository.findByProductId(productId)
                .orElseThrow(()-> new RuntimeException("Stock not found for productId: " + productId));
    }

    public Stock createStock(Stock stock){
        //check if stock exists
        Optional<Stock> existingStock = stockRepository.findByProductId(stock.getProductId());
        if(existingStock.isPresent()){
            throw new RuntimeException("Stock with productId" + stock.getProductId() + "already exists. Try a different action");
        }

        return stockRepository.save(stock);
    }

    public Stock updateStock(Long id, int quantity){
        Stock existingStock = getStockById(id);
        existingStock.setQuantity(quantity);
        return stockRepository.save(existingStock);
    }

    public void deleteStock(Long id){
        if(!stockRepository.existsById(id)){
            throw new RuntimeException("Stock with id number " + id + "doesn't exist");
        }
        stockRepository.deleteById(id);
    }

    public Stock increaseStock(Long productId, int increase){
        Stock stock = stockRepository.findByProductId(productId)
                .orElseThrow(()-> new RuntimeException("Stock not found for productId: " + productId));
        int newQuantity = stock.getQuantity() + increase;
        stock.setQuantity(newQuantity);
        return stockRepository.save(stock);
    }

    public Stock decreaseStock(Long productId, int decrease){
        Stock stock = stockRepository.findByProductId(productId)
                .orElseThrow(()-> new RuntimeException("Stock not found for productId: " + productId));
        if (decrease < 0) {
            throw new RuntimeException("Decrease amount must be positive");
        }
        if(stock.getQuantity() == 0){
            throw new RuntimeException("Stock level is already 0.");
        }
        if(stock.getQuantity() < decrease){
            throw new RuntimeException("Stock level is lower than " + decrease + ". Current stock level for productId: " + productId +" is " + stock.getQuantity());
        }
        int newQuantity = stock.getQuantity() - decrease;
        stock.setQuantity(newQuantity);
        return stockRepository.save(stock);
    }

}
