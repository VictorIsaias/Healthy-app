package com.example.healthyapp.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.healthyapp.Model.Objects.ScreenSelectorRequest;
import com.example.healthyapp.Repository.HabitsRepository;
import com.example.healthyapp.Response.ScreenSelectorResponse;

public class ScreenSelectorViewModel extends ViewModel {

    private HabitsRepository habitsRepository;

    public ScreenSelectorViewModel() {
        habitsRepository = new HabitsRepository();
    }

    public LiveData<ScreenSelectorResponse> selectScreen(String tokenAuth, ScreenSelectorRequest request) {
        return habitsRepository.selectScreen(tokenAuth, request);
    }
}
