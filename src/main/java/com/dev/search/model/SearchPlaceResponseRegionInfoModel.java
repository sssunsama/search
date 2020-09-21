package com.dev.search.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchPlaceResponseRegionInfoModel {

    private String[] region;

    private String keyword;

    private String selected_region;
}
