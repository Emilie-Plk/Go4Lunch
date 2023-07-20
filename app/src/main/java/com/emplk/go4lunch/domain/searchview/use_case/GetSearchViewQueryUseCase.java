package com.emplk.go4lunch.domain.searchview.use_case;

import androidx.lifecycle.LiveData;

import com.emplk.go4lunch.domain.searchview.SearchViewQueryRepository;

import org.checkerframework.checker.nullness.qual.NonNull;

import javax.inject.Inject;

public class GetSearchViewQueryUseCase {

    @NonNull
    private final SearchViewQueryRepository searchViewQueryRepository;

    @Inject
    public GetSearchViewQueryUseCase(@NonNull SearchViewQueryRepository searchViewQueryRepository) {
        this.searchViewQueryRepository = searchViewQueryRepository;
    }

    public LiveData<String> invoke() {
        return searchViewQueryRepository.getSearchViewQuery();
    }
}
