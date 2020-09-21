package com.dev.search.service;

import com.dev.search.common.page.Pagination;
import com.dev.search.common.rest.RestApiClient;
import com.dev.search.entity.SearchWord;
import com.dev.search.model.SearchPlaceParameterModel;
import com.dev.search.model.SearchPlaceResponseModel;
import com.dev.search.model.SearchPlaceTopWordModel;
import com.dev.search.repository.SearchWordRepository;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class SearchServiceImpl implements SearchService {

    @Value("${kakao.rest.api.key}")
    private String restApiKey;

    @Value("${kakao.rest.api.url}")
    private String apiUrl;

    @Value("${kakao.rest.api.format}")
    private String format;

    @Value("${kakao.rest.api.charset}")
    private String charset;

    private final SearchWordRepository searchWordRepository;

    public SearchServiceImpl(SearchWordRepository searchWordRepository) {
        this.searchWordRepository = searchWordRepository;
    }

    @Override
    public SearchPlaceResponseModel searchKeyword(SearchPlaceParameterModel searchPlaceParameterModel) {
        // API 호출
        String endPoint = apiUrl + "keyword" + format + "?" + "page=" + searchPlaceParameterModel.getPage()
                + "&size=" + searchPlaceParameterModel.getSize() + "&sort=" + searchPlaceParameterModel.getSort()
                + "&query=" + searchPlaceParameterModel.getQuery();

        String responseStr = RestApiClient.callByGetApi(endPoint, restApiKey);

        // 데이터 변환
        Gson gson = new Gson();
        SearchPlaceResponseModel searchPlaceResponseModel = gson.fromJson(responseStr, SearchPlaceResponseModel.class);
        searchPlaceResponseModel.setPagination(new Pagination(searchPlaceResponseModel.getMeta().getPageable_count(), searchPlaceParameterModel.getPage()));

        // 인기검색어 저장
        if (searchPlaceParameterModel.isCall_back()) {
            this.saveSearchWord(searchPlaceResponseModel.getMeta().getSame_name().getKeyword());
        }

        return searchPlaceResponseModel;
    }

    private void saveSearchWord(String keyword) {
        Optional<SearchWord> searchWord = searchWordRepository.findSearchWordBySearchWord(keyword);
        searchWordRepository.save(new SearchWord(keyword, searchWord.map(count -> count.getSearchCount() + 1).orElse(1)));
    }

    @Override
    public List<SearchPlaceTopWordModel> getSearchWordTop() {
        List<SearchWord> list = searchWordRepository.findTop10ByOrderBySearchCountDesc();
        List<SearchPlaceTopWordModel> resultList = null;

        if(list != null && !list.isEmpty()) {
            resultList = new ArrayList<>();
            for (SearchWord searchWord : list) {
                SearchPlaceTopWordModel searchPlaceTopWordModel = new SearchPlaceTopWordModel();
                searchPlaceTopWordModel.setSearch_word(searchWord.getSearchWord());
                searchPlaceTopWordModel.setSearch_count(searchWord.getSearchCount());
                resultList.add(searchPlaceTopWordModel);
            }
        }

        return resultList;
    }
}
