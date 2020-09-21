package com.dev.search.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
@Entity
public class SearchWord {

    @Id
    @Column(nullable = false)
    private String searchWord;

    @Column
    private int searchCount;

    @Builder
    public SearchWord(String searchWord, int searchCount) {
        this.searchWord = searchWord;
        this.searchCount = searchCount;
    }
}
