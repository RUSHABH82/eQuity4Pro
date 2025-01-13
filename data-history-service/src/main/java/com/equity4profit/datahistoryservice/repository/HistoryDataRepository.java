package com.equity4profit.datahistoryservice.repository;

import com.equity4profit.datahistoryservice.entity.CompanyDetails;
import com.equity4profit.datahistoryservice.entity.HistoryData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface HistoryDataRepository extends JpaRepository<HistoryData, Long> {

    @Query("SELECT  MAX(hd.timestamp) FROM HistoryData hd where hd.companyDetails = :companyDetails")
    Optional<Long> getLastRecordedTimestampByCompanyDetails(CompanyDetails companyDetails);
}
