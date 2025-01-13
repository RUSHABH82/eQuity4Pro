package com.equity2profit.datahistoryservice.service;

import com.equity2profit.datahistoryservice.entity.CompanyDetails;
import com.equity2profit.datahistoryservice.exception.DataHistoryServiceException;

import java.util.List;

import static com.equity2profit.datahistoryservice.model.CompanyDataServiceModel.UpdateCategoryRequest;

public interface ICompanyDataService {
    List<CompanyDetails> updateCategory(List<UpdateCategoryRequest> updateCategoryRequests) throws DataHistoryServiceException;

    CompanyDetails getHistorySymbol(String symbol) throws DataHistoryServiceException;

}
