package com.dev.search.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchPlaceResponseMetaModel {
    
    private boolean is_end;
    private int pageable_count;
    private int total_count;
    private SearchPlaceResponseRegionInfoModel same_name;

    
}
