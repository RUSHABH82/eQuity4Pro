package com.equity4profit.datahistoryservice.service;

import com.equity4profit.datahistoryservice.entity.CompanyDetails;
import com.equity4profit.datahistoryservice.exception.DataHistoryServiceException;
import com.equity4profit.datahistoryservice.model.CompanyDataServiceModel;
import com.equity4profit.datahistoryservice.repository.CompanyDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CompanyDataService implements ICompanyDataService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyDataService.class);
    private final CompanyDetailsRepository companyDetailsRepository;

    public CompanyDataService(CompanyDetailsRepository companyDetailsRepository) {
        this.companyDetailsRepository = companyDetailsRepository;
    }

    @Override
    public List<CompanyDetails> updateCategory(List<CompanyDataServiceModel.UpdateCategoryRequest> updateCategoryRequests) throws DataHistoryServiceException {
        try {
            List<CompanyDetails> companyDetails = new ArrayList<>();
            Collections.sort(updateCategoryRequests);
            for (CompanyDataServiceModel.UpdateCategoryRequest updateCategoryRequest : updateCategoryRequests) {
                List<CompanyDetails> companyDetailsByCategory = companyDetailsRepository.findAllBySymbolIsIn(updateCategoryRequest.getCompany());
                companyDetailsByCategory.forEach(cd -> cd.setCategory(updateCategoryRequest.getCategory()));
                companyDetails.addAll(companyDetailsRepository.saveAll(companyDetailsByCategory));
            }
            companyDetails.forEach(c -> c.setHistoryData(Collections.emptySet()));
            return companyDetails;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new DataHistoryServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public CompanyDetails getHistorySymbol(String symbol) throws DataHistoryServiceException {
        return companyDetailsRepository.findById(symbol)
                .orElseThrow(() -> new DataHistoryServiceException("Company data not found with symbol " + symbol, HttpStatus.BAD_REQUEST));
    }

}
