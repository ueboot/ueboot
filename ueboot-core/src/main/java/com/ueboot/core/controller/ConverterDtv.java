package com.ueboot.core.controller;

import com.ueboot.core.http.response.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

/**
 * 数据对象转换成视图对象工具类
 *
 * @author felix
 */
public class ConverterDtv<T> {

    public <U> Response<Page<U>> map(Page<T> info, Function<? super T, ? extends U> converter) {
        List<U> listU = new LinkedList<>();
        info.forEach((o) -> {
            U u = converter.apply(o);
            listU.add(u);
        });
        Page<U> result = new PageImpl<U>(listU, info.getPageable(), info.getTotalElements());
        BeanUtils.copyProperties(info, result);
        return new Response<>(result);
    }

    public <U> Response<List<U>> map(List<T> info, Function<? super T, ? extends U> converter) {
        List<U> listU = new LinkedList<>();
        info.forEach((o) -> {
            U u = converter.apply(o);
            listU.add(u);
        });
        return new Response<>(listU);
    }
}
