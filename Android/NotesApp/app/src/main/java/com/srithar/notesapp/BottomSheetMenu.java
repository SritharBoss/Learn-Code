package com.srithar.notesapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetMenu extends BottomSheetDialogFragment {

    public interface BottomSheetListener{
        void onButtonClicked(String text);
    }

    BottomSheetListener mListener;
    LinearLayout bottomSheetDelete,bottomSheetEdit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.bottom_sheet_menu,container,false);
        bottomSheetDelete=view.findViewById(R.id.bottom_sheet_delete);
        bottomSheetEdit=view.findViewById(R.id.bottom_sheet_edit);

        bottomSheetDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onButtonClicked("Clicked Delete");
                dismiss();
            }
        });

        bottomSheetEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onButtonClicked("Clicked Edit");
                dismiss();
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener=(BottomSheetListener)context;
    }
}
