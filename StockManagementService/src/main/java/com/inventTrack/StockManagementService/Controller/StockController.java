package com.inventTrack.StockManagementService.Controller;

import com.inventTrack.StockManagementService.Model.Stock;
import com.inventTrack.StockManagementService.Service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stocks")
public class StockController {
    @Autowired
    private StockService stockService;

    @GetMapping
    public ResponseEntity<List<Stock>> getAllStocks(){
        return ResponseEntity.ok(stockService.getAllStocks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Stock> getStockById(@PathVariable("id") Long id){
        return ResponseEntity.ok(stockService.getStockById(id));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<Stock> getStockByProductId(@PathVariable("productId") Long productId){
        return ResponseEntity.ok(stockService.getStockByProductId(productId));
    }

    @PostMapping
    public ResponseEntity<Stock> createStock(@RequestBody Stock stock){
        return ResponseEntity.ok(stockService.createStock(stock));
    }

    @PutMapping("/{id}/quantity")
    public ResponseEntity<Stock> updateStock(@PathVariable("id") Long id, @RequestParam int quantity) {
        return ResponseEntity.ok(stockService.updateStock(id, quantity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable("id") Long id){
        stockService.deleteStock(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/increase/{productId}")
    public ResponseEntity<Stock> increaseStock(@PathVariable("productId") Long productId, @RequestParam int amount){
        return ResponseEntity.ok(stockService.increaseStock(productId, amount));
    }

    @PatchMapping("/decrease/{productId}")
    public ResponseEntity<Stock> decreaseStock(@PathVariable("productId") Long productId, @RequestParam int amount){
        return ResponseEntity.ok(stockService.decreaseStock(productId, amount));
    }


}
