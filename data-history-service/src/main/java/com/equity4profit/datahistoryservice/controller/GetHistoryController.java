package com.equity4profit.datahistoryservice.controller;

import com.equity4profit.datahistoryservice.entity.CompanyDetails;
import com.equity4profit.datahistoryservice.exception.DataHistoryServiceException;
import com.equity4profit.datahistoryservice.service.CompanyDataService;
import com.equity4profit.datahistoryservice.service.ICompanyDataService;
import com.equity4profit.datahistoryservice.model.CompanyDataServiceModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping
public class GetHistoryController {

    private final ICompanyDataService companyDataService;


    public GetHistoryController(CompanyDataService companyDataService) {
        this.companyDataService = companyDataService;
    }


    @PutMapping("company/category")
    List<CompanyDetails> updateCategory(@RequestBody List<CompanyDataServiceModel.UpdateCategoryRequest> updateCategoryRequests) throws DataHistoryServiceException {
        return companyDataService.updateCategory(updateCategoryRequests);
    }

    @GetMapping("company/{symbol}")
    CompanyDetails getHistorySymbol(@PathVariable String symbol) throws DataHistoryServiceException {
        return companyDataService.getHistorySymbol(symbol.toUpperCase());
    }

}
