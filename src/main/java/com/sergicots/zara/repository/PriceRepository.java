package com.sergicots.zara.repository;

import com.sergicots.zara.model.Price;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface PriceRepository extends CrudRepository<Price, Long> {

    @Query("SELECT p FROM Price p WHERE p.brandId = :brandId " +
            "AND p.productId = :productId " +
            "AND p.startDate <= :startDate " +
            "AND p.endDate >= :endDate " +
            "ORDER BY p.priority DESC")
    List<Price> findPrices(
            @Param("brandId") Long brandId,
            @Param("productId") Integer productId,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);

    @Query("SELECT p FROM Price p")
    List<Price> findAllPrices();
}
