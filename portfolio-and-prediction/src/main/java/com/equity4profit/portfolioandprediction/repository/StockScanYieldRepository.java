package com.equity4profit.portfolioandprediction.repository;

import com.equity4profit.portfolioandprediction.entity.StockScanYield;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockScanYieldRepository extends JpaRepository<StockScanYield, Long> {

    void deleteAllBySymbol(String symbol);
}
