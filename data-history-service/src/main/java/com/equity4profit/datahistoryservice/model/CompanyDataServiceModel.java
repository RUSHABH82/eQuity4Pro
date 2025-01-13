package com.equity4profit.datahistoryservice.model;

import com.equity4profit.datahistoryservice.entity.CompanyCategory;

import java.util.List;

public class CompanyDataServiceModel {

    public static class UpdateCategoryRequest {

        private CompanyCategory category;
        private List<String> company;

        public CompanyCategory getCategory() {
            return category;
        }

        public void setCategory(CompanyCategory category) {
            this.category = category;
        }

        public List<String> getCompany() {
            return company;
        }

        public void setCompany(List<String> company) {
            this.company = company;
        }
    }


}
