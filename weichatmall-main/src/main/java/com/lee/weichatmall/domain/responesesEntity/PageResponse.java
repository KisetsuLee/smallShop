package com.lee.weichatmall.domain.responesesEntity;

import java.util.List;

/**
 * Description:
 * User: Lzj
 * Date: 2020-04-08
 * Time: 12:47
 */
public class PageResponse<T> {
    private int pageNum;
    private int pageSize;
    private int totalPage;
    private List<T> data;

    private PageResponse() {
    }

    public static <T> PageResponse<T> newInstance(Integer pageNum, Integer pageSize, List<T> data, int totalCount) {
        PageResponse<T> pageResponse = new PageResponse<>();
        pageResponse.setPageNum(pageNum);
        pageResponse.setPageSize(pageSize);
        pageResponse.setTotalPage(totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1);
        pageResponse.setData(data);
        return pageResponse;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
