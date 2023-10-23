package com.shop.polyedrum.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationResponse <T>{
    private List<T> items;
    private int pageNo;
    private int pageSize;
    private long totalCount;
    private int totalPages;
}