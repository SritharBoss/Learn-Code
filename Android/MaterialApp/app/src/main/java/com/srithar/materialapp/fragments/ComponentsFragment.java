package com.srithar.materialapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.srithar.materialapp.activites.BottomBarActivity;
import com.srithar.materialapp.R;

public class ComponentsFragment extends Fragment implements View.OnClickListener {
    NestedScrollView nestedScrollView;
    MaterialButton button1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        nestedScrollView=(NestedScrollView)inflater.inflate(R.layout.fragment_components, container, false);
        button1=nestedScrollView.findViewById(R.id.button_bottom_appbar);
        return nestedScrollView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        button1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_bottom_appbar:
                Intent intent=new Intent(getActivity(), BottomBarActivity.class);
                startActivity(intent);
                break;
        }
    }
}