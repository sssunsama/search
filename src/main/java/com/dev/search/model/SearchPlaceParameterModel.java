package com.dev.search.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchPlaceParameterModel {

    private String query;

    private String category_group_code;

    private double x;

    private double y;

    private Integer radius;

    private String rect;

    private Integer page;

    private Integer size;

    private String sort;

    private boolean call_back;

    public SearchPlaceParameterModel() {
        this.size = 15;
        this.sort = "accuracy";
    }
}
