package com.w2.beans;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Page<T> {
    private int pageNow;
    private long pageTotal;
    private List<T> res;
}
