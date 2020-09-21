package com.dev.search.model;

import com.dev.search.common.page.Pagination;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchPlaceResponseModel {

    private SearchPlaceResponseMetaModel meta;

    private List<SearchPlaceResponseDocumentsModel> documents;

    private Pagination pagination;
}
