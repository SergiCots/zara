package com.sergicots.zara.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sergicots.zara.model.Price;
import com.sergicots.zara.repository.PriceRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/prices")
public class PriceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PriceController.class);

    @Autowired
    PriceRepository repository;

    @GetMapping("/{brandId}/{productId}/{startDate}/{endDate}")
    public ResponseEntity<?> findPrice(
            @PathVariable("brandId") Long brandId,
            @PathVariable("productId") Integer productId,
            @PathVariable("startDate") String startDate,
            @PathVariable("endDate") String endDate
    ) {
        LOGGER.info("GET v1/prices/{}/{} from {} to {}", brandId, productId, startDate, endDate);

        try {
            // Implementa la lógica para buscar un precio según los parámetros proporcionados
            // Puedes usar el repository para buscar en la base de datos
            Optional<Price> price = this.repository.findPrice(brandId, productId, startDate, endDate);

            if (price.isPresent()) {
                LOGGER.debug("GET v1/prices/{}/{} - OK", brandId, productId);
                return new ResponseEntity<>(price.get(), HttpStatus.OK);
            } else {
                LOGGER.warn("GET v1/prices/{}/{} - NOT FOUND", brandId, productId);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            LOGGER.error("GET v1/prices/{}/{} - ERROR: {}", brandId, productId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> listAllPrices() {
        LOGGER.info("GET v1/prices/list");

        try {
            // Implementa la lógica para listar todos los precios disponibles
            // Puedes usar el repository para buscar en la base de datos
            List<Price> prices = this.repository.findAllPrices();

            return new ResponseEntity<>(prices, HttpStatus.OK);

        } catch (Exception e) {
            LOGGER.error("GET v1/prices/list - ERROR: {}", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> createPrice(@RequestBody Price price) {
        LOGGER.info("POST v1/prices/");

        try {
            // Implementa la lógica para crear un nuevo precio
            // Puedes usar el repository para guardar en la base de datos
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
            // Implementa la lógica para actualizar un precio existente
            // Puedes usar el repository para guardar en la base de datos
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
            @PathVariable("startDate") String startDate,
            @PathVariable("endDate") String endDate
    ) {
        LOGGER.info("DELETE v1/prices/{}/{} from {} to {}", brandId, productId, startDate, endDate);

        try {
            // Implementa la lógica para eliminar un precio según los parámetros proporcionados
            // Puedes usar el repository para eliminar de la base de datos
            Optional<Price> priceToDelete = this.repository.findPrice(brandId, productId, startDate, endDate);

            if (priceToDelete.isPresent()) {
                this.repository.delete(priceToDelete.get());
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
