package com.example.restarting;

import android.provider.BaseColumns;

public class ItemContract {

    private ItemContract(){}

    public static final class ContractEntry implements BaseColumns {
        public static final String TABLE_NAME="favoriteItems";
        public static final String COLUMN_NAME="name";
        public static final String COLUMN_COUNT="count";
        public static final String COLUMN_TIMESTAMP="timeStamp";
    }
}
