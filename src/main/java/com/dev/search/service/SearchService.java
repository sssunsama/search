package com.dev.search.service;

import com.dev.search.model.SearchPlaceParameterModel;
import com.dev.search.model.SearchPlaceResponseModel;
import com.dev.search.model.SearchPlaceTopWordModel;

import java.util.List;

public interface SearchService {
    // 장소키워드 검색
    SearchPlaceResponseModel searchKeyword(SearchPlaceParameterModel searchPlaceParameterModel);

    // 인기검색어 조회
    List<SearchPlaceTopWordModel> getSearchWordTop();

}
