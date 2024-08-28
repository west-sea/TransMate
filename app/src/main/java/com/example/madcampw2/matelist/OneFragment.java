package com.example.madcampw2.matelist;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madcampw2.R;
import com.example.madcampw2.adapter.UserAdapter;
import com.example.madcampw2.model.User;
import com.example.madcampw2.retrofit.RetrofitService;
import com.example.madcampw2.retrofit.UserAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OneFragment extends Fragment {

    private RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.category_recyclerview, container, false);

        // Setting up RecyclerView
        recyclerView = view.findViewById(R.id.userList_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        loadUsers();

        return view;
    }

    private void loadUsers() {
        RetrofitService retrofitService = new RetrofitService();
        UserAPI userAPI = retrofitService.getRetrofit().create(UserAPI.class);
        userAPI.getAllUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    populateListView(response.body());
                } else {
                    Toast.makeText(getActivity(), "Failed to load users", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed to load users", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void populateListView(List<User> userList) {
        UserAdapter userAdapter = new UserAdapter(userList);
        recyclerView.setAdapter(userAdapter);
    }

    // Optional: Handle any other fragment lifecycle methods as needed
}