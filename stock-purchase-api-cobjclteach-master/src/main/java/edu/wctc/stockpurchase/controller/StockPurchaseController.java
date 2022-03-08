package edu.wctc.stockpurchase.controller;

import edu.wctc.stockpurchase.entity.StockPurchase;
import edu.wctc.stockpurchase.service.StockPurchaseService;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/stockpurchases")
public class StockPurchaseController {

    private StockPurchaseService service;

    @Autowired
    public StockPurchaseController(StockPurchaseService sps) {
        this.service = sps;
    }

    @GetMapping
    public List<StockPurchase> getAllStock() {
        return service.getAllStock();
    }

    @GetMapping("/{purchase_id}")
    public StockPurchase getStock(@PathVariable String purchase_id) {
        try {
            int id = Integer.parseInt(purchase_id);
            return service.getStock(id);
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Stock ID must be a number", e);
        }catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    e.getMessage(), e);
        }
    }

    @PostMapping
    public StockPurchase createStock(@RequestBody StockPurchase stockPurchase) {
        stockPurchase.setId(0);
        return service.save(stockPurchase);
    }

    @PutMapping
    public StockPurchase updateStock(@RequestBody StockPurchase stockPurchase) {
        try {
            return service.update(stockPurchase);
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    e.getMessage(), e);
        }
    }

    @DeleteMapping("/{purchase_id}")
    public String deleteStock(@PathVariable String purchase_id) {
        try {
            int id = Integer.parseInt(purchase_id);
            service.delete(id);
            return "Stock deleted: ID " + id;
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Stock ID must be a number", e);
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    e.getMessage(), e);
        }
    }
}
