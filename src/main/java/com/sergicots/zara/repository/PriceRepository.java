package com.sergicots.zara.repository;

import com.sergicots.zara.model.Price;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PriceRepository extends CrudRepository<Price, Long> {

    // Define una consulta personalizada para buscar un precio según los parámetros proporcionados
    @Query("SELECT p FROM Price p " +
            "WHERE p.brandId = :brandId " +
            "AND p.productId = :productId " +
            "AND p.startDate = :startDate " +
            "AND p.endDate = :endDate")
    Optional<Price> findPrice(
            @Param("brandId") Long brandId,
            @Param("productId") Integer productId,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate
    );

    // Define una consulta personalizada para listar todos los precios disponibles
    @Query("SELECT p FROM Price p")
    List<Price> findAllPrices();
}
