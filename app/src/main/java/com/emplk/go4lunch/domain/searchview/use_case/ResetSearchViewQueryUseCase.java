package com.emplk.go4lunch.domain.searchview.use_case;

import com.emplk.go4lunch.domain.searchview.SearchViewQueryRepository;

import org.checkerframework.checker.nullness.qual.NonNull;

import javax.inject.Inject;

public class ResetSearchViewQueryUseCase {

    @NonNull
    private final SearchViewQueryRepository searchViewQueryRepository;

    @Inject
    public ResetSearchViewQueryUseCase(@NonNull SearchViewQueryRepository searchViewQueryRepository) {
        this.searchViewQueryRepository = searchViewQueryRepository;
    }

    public void invoke() {
        searchViewQueryRepository.resetSearchViewQuery();
    }
}
