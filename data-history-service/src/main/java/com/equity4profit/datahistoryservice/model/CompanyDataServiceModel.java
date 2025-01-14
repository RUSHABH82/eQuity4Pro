package com.equity4profit.datahistoryservice.model;

import com.equity4profit.datahistoryservice.entity.CompanyCategory;

import java.util.List;
import java.util.Set;

public class CompanyDataServiceModel {

    public static class UpdateCategoryRequest implements Comparable<UpdateCategoryRequest>{

        private CompanyCategory category;
        private Set<String> company;

        public CompanyCategory getCategory() {
            return category;
        }

        public void setCategory(CompanyCategory category) {
            this.category = category;
        }

        public Set<String> getCompany() {
            return company;
        }

        public void setCompany(Set<String> company) {
            this.company = company;
        }

        @Override
        public int compareTo(UpdateCategoryRequest o) {
            return Integer.compare(getCategoryOrder(this.category), getCategoryOrder(o.category));
        }

        private int getCategoryOrder(CompanyCategory category) {
            return switch (category) {
                case OTHERS -> 0;
                case S250 -> 1;
                case S40NEXT -> 2;
                case S40 -> 3;
            };
        }
    }


}
