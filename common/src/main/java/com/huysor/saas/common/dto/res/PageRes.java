package com.huysor.saas.common.dto.res;

public record PageRes<T> (
        int  totalPage,
        Long totalSize,
        T list
) {
}