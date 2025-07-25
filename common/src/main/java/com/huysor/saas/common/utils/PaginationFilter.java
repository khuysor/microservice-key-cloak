package com.huysor.saas.common.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class PaginationFilter {
    @JsonProperty("page")
    private Integer page;
    @JsonProperty("size")
    private Integer size;
    @JsonProperty("sortBy")
    private String sortBy;
    @JsonProperty("sortDirection")
    private String sortDirection;

    public Pageable toPageable() {
        int p = (page != null && page > 0) ? page - 1 : 0;
        int s = (size != null && size > 0) ? size : 30;
        Sort.Direction direction = Sort.Direction.DESC;
        String filed = StringUtils.isValidString(sortBy) ? sortBy : "id";
        if ("asc".equalsIgnoreCase(sortDirection)) {
            direction = Sort.Direction.ASC;
        }
        return PageRequest.of(p, s, Sort.by(direction, filed));
    }
}
