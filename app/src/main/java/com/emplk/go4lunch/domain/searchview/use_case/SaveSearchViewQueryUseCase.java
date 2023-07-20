package com.emplk.go4lunch.domain.searchview.use_case;

import androidx.annotation.NonNull;

import com.emplk.go4lunch.domain.searchview.SearchViewQueryRepository;

import javax.inject.Inject;

public class SaveSearchViewQueryUseCase {

    @NonNull
    private final SearchViewQueryRepository searchViewQueryRepository;

    @Inject
    public SaveSearchViewQueryUseCase(@NonNull SearchViewQueryRepository searchViewQueryRepository) {
        this.searchViewQueryRepository = searchViewQueryRepository;
    }

    public void invoke(@NonNull String query) {
        searchViewQueryRepository.saveSearchViewQuery(query);
    }
}
