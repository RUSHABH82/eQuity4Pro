package com.equity2profit.datahistoryservice.controller;

import com.equity2profit.datahistoryservice.entity.CompanyDetails;
import com.equity2profit.datahistoryservice.exception.DataHistoryServiceException;
import com.equity2profit.datahistoryservice.service.CompanyDataService;
import com.equity2profit.datahistoryservice.service.ICompanyDataService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.equity2profit.datahistoryservice.model.CompanyDataServiceModel.UpdateCategoryRequest;


@RestController
@RequestMapping
public class GetHistoryController {

    private final ICompanyDataService companyDataService;


    public GetHistoryController(CompanyDataService companyDataService) {
        this.companyDataService = companyDataService;
    }


    @PutMapping("company/category")
    List<CompanyDetails> updateCategory(@RequestBody List<UpdateCategoryRequest> updateCategoryRequests) throws DataHistoryServiceException {
        return companyDataService.updateCategory(updateCategoryRequests);
    }

    @GetMapping("company/{symbol}")
    CompanyDetails getHistorySymbol(@PathVariable String symbol) throws DataHistoryServiceException {
        return companyDataService.getHistorySymbol(symbol.toUpperCase());
    }

}
