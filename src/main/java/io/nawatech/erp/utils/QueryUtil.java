package io.nawatech.erp.utils;


import jakarta.persistence.Query;

import java.util.List;
import java.util.stream.IntStream;

public class QueryUtil {

    public static final String OFFSET_AND_LIMIT = " LIMIT :offset, :limit";

    public static String generateSearchColumns(List<String> columns, boolean withAnd) {
        int size = columns != null ? columns.size() : 0;
        if (size == 0) return "";

        StringBuilder builder = new StringBuilder();
        IntStream.range(0, size).forEach(i -> builder.append(i == 0 && withAnd ? " AND " : "").append(i == 0 ? "(" : "").append(i > 0 ? " OR " : "").append(columns.get(i)).append(" LIKE :keyword").append(i == size - 1 ? ")" : ""));

        return builder + " ";
    }

    public static String getCountColumn(String tableAlias) {
        return "COUNT(" + (tableAlias != null ? tableAlias + "." : "") + "id) AS total ";
    }

    public static void setOffsetAndLimit(Query query, Integer offset, Integer limit) {
        query.setParameter("offset", offset).setParameter("limit", limit);
    }

    public static void setKeyword(Query query, String keyword) {
        query.setParameter("keyword", "%" + keyword.trim() + "%");
    }

}
