package com.dev.search.controller;

import com.dev.search.model.SearchPlaceParameterModel;
import com.dev.search.service.SearchService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/search")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping(value = "/searchView")
    public ModelAndView searchView(HttpServletRequest request) {
        return new ModelAndView("/search/searchPage");
    }

    @GetMapping(value="/getSearchPlaceList")
    public ModelAndView getSearchPlaceList(@ModelAttribute SearchPlaceParameterModel searchPlaceParameterModel) {
        ModelAndView modelAndView = new ModelAndView("/search/list");
        modelAndView.addObject("searchList", searchService.searchKeyword(searchPlaceParameterModel));
        modelAndView.addObject("params", searchPlaceParameterModel);
        return modelAndView;
    }

    @GetMapping(value = "/getSearchTopWordList")
    public ModelAndView getSearchTopWordList() {
        ModelAndView modelAndView = new ModelAndView("/search/topList");
        modelAndView.addObject("searchTopList", searchService.getSearchWordTop());
        return modelAndView;
    }
}
