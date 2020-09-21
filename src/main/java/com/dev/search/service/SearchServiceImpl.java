package com.dev.search.service;

import com.dev.search.common.Pagination;
import com.dev.search.entity.SearchWord;
import com.dev.search.model.SearchPlaceParameterModel;
import com.dev.search.model.SearchPlaceResponseModel;
import com.dev.search.model.SearchPlaceTopWordModel;
import com.dev.search.repository.SearchWordRepository;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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
        String responseStr = this.callByAPI(searchPlaceParameterModel);
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

    private String callByAPI(SearchPlaceParameterModel searchPlaceParameterModel) {
        StringBuilder stringBuilder = new StringBuilder();
        String endPoint;
        try {
            // API 검색
            endPoint = apiUrl + "keyword" + format + "?"
                    + "page=" + searchPlaceParameterModel.getPage() + "&size=" + searchPlaceParameterModel.getSize()
                    + "&sort=" + searchPlaceParameterModel.getSort() + "&query=" + URLEncoder.encode(searchPlaceParameterModel.getQuery(), charset);

            URL url = new URL(endPoint);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("Authorization", restApiKey);

            int responseCode = httpURLConnection.getResponseCode();

            BufferedReader br;
            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(httpURLConnection.getErrorStream()));
            }
            String line;
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line);
            }
            br.close();
        } catch (IOException e) {
            log.error("API error == {}", e.getMessage());
        }
        return stringBuilder.toString();
    }

    private void saveSearchWord(String keyword) {
        Optional<SearchWord> searchWord = searchWordRepository.findSearchWordBySearchWord(keyword);
        searchWordRepository.save(new SearchWord(keyword, searchWord.map(count -> count.getSearchCount() + 1).orElse(1)));
    }

    @Override
    public List<SearchPlaceTopWordModel> getSearchWordTop() {
        List<SearchWord> list = searchWordRepository.findTop10ByOrderBySearchCountDesc();
        List<SearchPlaceTopWordModel> resultList = new ArrayList<>();
        if(list != null && !list.isEmpty()) {
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
