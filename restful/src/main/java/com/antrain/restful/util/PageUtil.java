package com.antrain.restful.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class PageUtil extends LinkedHashMap<String, Object> {
    private int offset;//偏移量
    private int page;//当前页码
    private int limit;//每页条数

    public PageUtil(Map<String, Object> params) {
        this.putAll(params);

        //分页参数
        this.offset = Integer.parseInt(params.get("offset").toString());
        this.limit = Integer.parseInt(params.get("limit").toString());
        this.page = offset/(limit+1);
        this.put("offset", offset);
        this.put("page", page);
        this.put("limit", limit);
    }


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    @Override
    public String toString() {
        return "PageUtil{" +
                "offset=" + offset +
                ", page=" + page +
                ", limit=" + limit +
                '}';
    }
}
