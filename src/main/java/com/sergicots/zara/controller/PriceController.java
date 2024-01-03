package com.sergicots.zara.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.sergicots.zara.model.Price;
import com.sergicots.zara.repository.PriceRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/prices")
public class PriceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PriceController.class);

    @Autowired
    PriceRepository repository;

    @GetMapping("/{brandId}/{productId}/{startDate}/{endDate}")
    public ResponseEntity<Price> findPrice(
            @PathVariable Long brandId,
            @PathVariable Integer productId,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startDate,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endDate) {

        LOGGER.info("GET v1/prices/{}/{} from {} to {}", brandId, productId, startDate, endDate);

        try {
            List<Price> prices = this.repository.findPrices(brandId, productId, startDate, endDate);

            if (!prices.isEmpty()) {
                Price price = prices.get(0);  // Tomar el primer resultado (mayor prioridad)
                LOGGER.debug("GET v1/prices/{}/{} - OK", brandId, productId);
                return new ResponseEntity<>(price, HttpStatus.OK);
            } else {
                LOGGER.warn("GET v1/prices/{}/{} - NOT FOUND", brandId, productId);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            LOGGER.error("GET v1/prices/{}/{} - ERROR: {}", brandId, productId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> createPrice(@RequestBody Price price) {
        LOGGER.info("POST v1/prices/");

        try {
            this.repository.save(price);

            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            LOGGER.error("POST v1/prices/ - ERROR: {}", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<?> updatePrice(@RequestBody Price price) {
        LOGGER.info("PUT v1/prices/");

        try {
            Optional<Price> existingPrice = this.repository.findById(price.getBrandId());

            if (existingPrice.isPresent()) {
                this.repository.save(price);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            LOGGER.error("PUT v1/prices - ERROR: {}", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{brandId}/{productId}/{startDate}/{endDate}")
    public ResponseEntity<?> deletePrice(
            @PathVariable("brandId") Long brandId,
            @PathVariable("productId") Integer productId,
            @PathVariable("startDate") Date startDate,
            @PathVariable("endDate") Date endDate
    ) {
        LOGGER.info("DELETE v1/prices/{}/{} from {} to {}", brandId, productId, startDate, endDate);

        try {
            List<Price> priceToDelete = this.repository.findPrices(brandId, productId, startDate, startDate);

            if (!priceToDelete.isEmpty()) {
                this.repository.delete(priceToDelete.get(0));
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            LOGGER.error("DELETE v1/prices/{}/{} - ERROR: {}", brandId, productId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
