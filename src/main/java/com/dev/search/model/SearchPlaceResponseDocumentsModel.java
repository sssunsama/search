package com.dev.search.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class SearchPlaceResponseDocumentsModel {

    private String id;

    private String place_name;

    private String category_name;

    private String category_group_code;

    private String category_group_name;

    private String phone;

    private String address_name;

    private String road_address_name;

    private double x;

    private double y;

    private String place_url;

    private String distance;
}
