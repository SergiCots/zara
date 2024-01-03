package com.sergicots.zara;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sergicots.zara.model.Price;
import com.sergicots.zara.repository.PriceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ZaraApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PriceRepository priceRepository;

    @Test
    public void testRequest1() throws Exception {
        performRequest("2020-06-14 10:00:00", 35455, 1, 1, new BigDecimal("35.50"), "EUR");
    }

    @Test
    public void testRequest2() throws Exception {
        performRequest("2020-06-14 16:00:00", 35455, 1, 2, new BigDecimal("25.45"), "EUR");
    }

    @Test
    public void testRequest3() throws Exception {
        performRequest("2020-06-14 21:00:00", 35455, 1, 1, new BigDecimal("35.50"), "EUR");
    }

    @Test
    public void testRequest4() throws Exception {
        performRequest("2020-06-15 10:00:00", 35455, 1, 3, new BigDecimal("30.50"), "EUR");
    }

    @Test
    public void testRequest5() throws Exception {
        performRequest("2020-06-16 21:00:00", 35455, 1, 4, new BigDecimal("38.95"), "EUR");
    }

    private void performRequest(String requestTime, int productId, int brandId, int priceList, BigDecimal expectedPrice, String expectedCurr) throws Exception {

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/prices/{brandId}/{productId}/{startDate}/{endDate}", brandId, productId, requestTime, requestTime)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Price price = new ObjectMapper().readValue(content, Price.class);

        assertEquals(priceList, price.getPriceList());
        assertEquals(expectedPrice, price.getPrice());
        assertEquals(expectedCurr, price.getCurrency());
    }
}
