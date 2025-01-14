package com.equity4profit.liveupdateservice.livedetail.tradingview;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

public class TradingViewModels {

    public static class MarketDataFetchResponse {

        private Integer totalCount;
        private List<StockData> data;

        public Integer getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(Integer totalCount) {
            this.totalCount = totalCount;
        }

        public List<StockData> getData() {
            return data;
        }

        public void setData(List<StockData> data) {
            this.data = data;
        }


    }

    public static class StockData {

        private String s;
        private List<Object> d;

        public String getS() {
            return s;
        }

        public void setS(String s) {
            this.s = s;
        }

        public List<Object> getD() {
            return d;
        }

        public void setD(List<Object> d) {
            this.d = d;
        }
    }

    public static class MarketDataRequest {

        private List<String> columns;
        private boolean ignore_unknown_fields;
        private Options options;
        private List<Integer> range;
        private Sort sort;
        private String preset;

        public List<String> getColumns() {
            return columns;
        }

        public void setColumns(List<String> columns) {
            this.columns = columns;
        }

        public boolean isIgnore_unknown_fields() {
            return ignore_unknown_fields;
        }

        public void setIgnore_unknown_fields(boolean ignore_unknown_fields) {
            this.ignore_unknown_fields = ignore_unknown_fields;
        }

        public Options getOptions() {
            return options;
        }

        public void setOptions(Options options) {
            this.options = options;
        }

        public List<Integer> getRange() {
            return range;
        }

        public void setRange(List<Integer> range) {
            this.range = range;
        }

        public Sort getSort() {
            return sort;
        }

        public void setSort(Sort sort) {
            this.sort = sort;
        }

        public String getPreset() {
            return preset;
        }

        public void setPreset(String preset) {
            this.preset = preset;
        }

        public static class Sort {
            private String sortBy;
            private String sortOrder;
            private boolean nullsFirst;

            public Sort(String sortBy, String sortOrder, boolean nullsFirst) {
                this.sortBy = sortBy;
                this.sortOrder = sortOrder;
                this.nullsFirst = nullsFirst;
            }

            public String getSortBy() {
                return sortBy;
            }

            public void setSortBy(String sortBy) {
                this.sortBy = sortBy;
            }

            public String getSortOrder() {
                return sortOrder;
            }

            public void setSortOrder(String sortOrder) {
                this.sortOrder = sortOrder;
            }

            public boolean isNullsFirst() {
                return nullsFirst;
            }

            public void setNullsFirst(boolean nullsFirst) {
                this.nullsFirst = nullsFirst;
            }
        }

        public static class Options {
            private String lang;

            public Options(String lang) {
                this.lang = lang;
            }

            public String getLang() {
                return lang;
            }

            public void setLang(String lang) {
                this.lang = lang;
            }
        }
    }


    public static enum DataColumns {

        name(DataColumns.COLUMN_NAME, ColumnType.STRING_TYPE_REFERENCE),
        description(DataColumns.COLUMN_DESCRIPTION, ColumnType.STRING_TYPE_REFERENCE), close(DataColumns.COLUMN_CLOSE, ColumnType.DOUBLE_TYPE_REFERENCE), market_cap_basic(DataColumns.COLUMN_MARKET_CAP_BASIC, ColumnType.DOUBLE_TYPE_REFERENCE), sector(DataColumns.COLUMN_SECTOR, ColumnType.STRING_TYPE_REFERENCE), industry(DataColumns.COLUMN_INDUSTRY, ColumnType.STRING_TYPE_REFERENCE),
        ;

        private final String value;

        private final TypeReference<?> typeReference;

        DataColumns(String value, TypeReference<?> typeReference) {
            this.value = value;
            this.typeReference = typeReference;
        }

        public String getValue() {
            return value;
        }

        public TypeReference<?> getTypeReference() {
            return typeReference;
        }


        public final static String COLUMN_NAME = "name";
        public final static String COLUMN_DESCRIPTION = "description";
        public final static String COLUMN_CLOSE = "close";
        public final static String COLUMN_MARKET_CAP_BASIC = "market_cap_basic";
        public final static String COLUMN_SECTOR = "sector";
        public final static String COLUMN_INDUSTRY = "industry";


        public static class ColumnType {
            public final static TypeReference<String> STRING_TYPE_REFERENCE = new TypeReference<>() {
            };
            public final static TypeReference<Double> DOUBLE_TYPE_REFERENCE = new TypeReference<>() {
            };
        }
    }
}
