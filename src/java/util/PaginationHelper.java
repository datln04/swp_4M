/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

// PaginationHelper.java

import java.util.List;

public class PaginationHelper<T> {
    private int currentPage;
    private int recordsPerPage;
    private List<T> dataList;

    public PaginationHelper(List<T> dataList, int currentPage, int recordsPerPage) {
        this.dataList = dataList;
        this.currentPage = currentPage;
        this.recordsPerPage = recordsPerPage;
    }

    public List<T> getCurrentPageData() {
        int totalRecords = dataList.size();
        int totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage);
        int startRecord = (currentPage - 1) * recordsPerPage;
        int endRecord = Math.min(startRecord + recordsPerPage, totalRecords);

        return dataList.subList(startRecord, endRecord);
    }

    public int getTotalPages() {
        int totalRecords = dataList.size();
        return (int) Math.ceil((double) totalRecords / recordsPerPage);
    }
}

