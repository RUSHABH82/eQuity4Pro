package com.equity4profit.datahistoryservice.repository;

import com.equity4profit.datahistoryservice.entity.CompanyCategory;
import com.equity4profit.datahistoryservice.entity.CompanyDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface CompanyDetailsRepository extends JpaRepository<CompanyDetails, String> {

    List<CompanyDetails> findAllBySymbolIsIn(Collection<String> symbols);

    List<CompanyDetails> findAllByCategoryIsIn(List<CompanyCategory> categories);

    List<CompanyDetails> findAllByMarketCapInCrIsAfter(Double marketCapInCr);
}
