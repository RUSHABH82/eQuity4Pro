package com.equity4profit.datahistoryservice.service;

import com.equity4profit.datahistoryservice.entity.CompanyDetails;
import com.equity4profit.datahistoryservice.exception.DataHistoryServiceException;
import com.equity4profit.datahistoryservice.model.CompanyDataServiceModel;

import java.util.List;

public interface ICompanyDataService {
    List<CompanyDetails> updateCategory(List<CompanyDataServiceModel.UpdateCategoryRequest> updateCategoryRequests) throws DataHistoryServiceException;

    CompanyDetails getHistorySymbol(String symbol) throws DataHistoryServiceException;

}
