package edu.wctc.stockpurchase.service;

import edu.wctc.stockpurchase.entity.StockPurchase;
import edu.wctc.stockpurchase.repo.StockPurchaseRepository;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class StockPurchaseService {
    private StockPurchaseRepository repo;

    @Autowired
    public StockPurchaseService(StockPurchaseRepository spr) {
        this.repo = spr;
    }

    public List<StockPurchase> getAllStock() {
        List<StockPurchase> list = new ArrayList<>();
        repo.findAll().forEach(list::add);
        return list;
    }

    public StockPurchase getStock(int id) throws ResourceNotFoundException {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("StockPurchase"));
    }

    public StockPurchase save(StockPurchase stockPurchase) {
        return repo.save(stockPurchase);
    }

    public StockPurchase update(StockPurchase stockPurchase)
            throws ResourceNotFoundException {
        if (repo.existsById(stockPurchase.getId())) {
            return repo.save(stockPurchase);
        } else {
            throw new ResourceNotFoundException("StockPurchase");
        }
    }

    public void delete(int id) throws ResourceNotFoundException {
        if (repo.existsById(id)) {
            repo.deleteById(id);
        } else {
            throw new ResourceNotFoundException("StockPurchase");
        }
    }
}
