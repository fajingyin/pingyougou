package com.pingyougou.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @author yin
 * @Date 2018/8/12 20:58
 * @Method
 */
public class PageResult<T> implements Serializable {
    private Long total;
    private List<T> rows;

    public PageResult(Long total, List<T> rows) {
        super();
        this.total=total;
        this.rows=rows;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
