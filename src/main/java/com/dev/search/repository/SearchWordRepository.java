package com.dev.search.repository;

import com.dev.search.entity.SearchWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SearchWordRepository extends JpaRepository<SearchWord, String> {
    Optional<SearchWord> findSearchWordBySearchWord(String searchWord);
    List<SearchWord> findTop10ByOrderBySearchCountDesc();
}
