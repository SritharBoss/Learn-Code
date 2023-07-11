package com.example.restarting.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restarting.Adapters.RecyclerViewAdapter;
import com.example.restarting.GroceryDBHelper;
import com.example.restarting.ItemContract;
import com.example.restarting.R;

public class SqliteActivity extends AppCompatActivity {
    private SQLiteDatabase mSqLiteDatabase;

    Button minusBtn,plusBtn,addBtn;
    TextView count;
    RecyclerView recyclerView;
    RecyclerViewAdapter mAdapter;
    int mAmount=0;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.MaterialActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);

        editText=findViewById(R.id.edittext_sqlite);
        minusBtn=findViewById(R.id.btn_minus);
        plusBtn=findViewById(R.id.btn_plus);
        addBtn=findViewById(R.id.btn_add);
        count=findViewById(R.id.tv_count);
        recyclerView=findViewById(R.id.recyclerview_sqlite);

        GroceryDBHelper dbHelper=new GroceryDBHelper(this);
        mSqLiteDatabase=dbHelper.getWritableDatabase();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter= new RecyclerViewAdapter(this, getAllItems());
        recyclerView.setAdapter(mAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                removeItem((long) viewHolder.itemView.getTag());
            }
        }).attachToRecyclerView(recyclerView);

        plusBtn.setOnClickListener(v -> {
            mAmount++;
            count.setText(String.valueOf(mAmount));
        });

        minusBtn.setOnClickListener(v -> {
            if(mAmount>0){
            mAmount--;
            count.setText(String.valueOf(mAmount));
            }
        });

        addBtn.setOnClickListener(v-> {
                if (editText.getText().toString().trim().length() == 0 || mAmount == 0) {
                    return;
                }

                String name = editText.getText().toString();
                ContentValues cv = new ContentValues();
                cv.put(ItemContract.ContractEntry.COLUMN_NAME, name);
                cv.put(ItemContract.ContractEntry.COLUMN_COUNT, mAmount);

                mSqLiteDatabase.insert(ItemContract.ContractEntry.TABLE_NAME, null, cv);

                Toast.makeText(getApplicationContext(),""+cv,Toast.LENGTH_SHORT).show();
                mAdapter.swapCursor(getAllItems());
                editText.getText().clear();
                mAmount=0;
                count.setText("0");
            }
        );
    }

    private void removeItem(long id) {
        mSqLiteDatabase.delete(ItemContract.ContractEntry.TABLE_NAME,
                ItemContract.ContractEntry._ID + "=" + id, null);
        mAdapter.swapCursor(getAllItems());
    }
    private Cursor getAllItems() {
        return mSqLiteDatabase.query(
                ItemContract.ContractEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                ItemContract.ContractEntry.COLUMN_TIMESTAMP + " DESC"
        );
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }
}
