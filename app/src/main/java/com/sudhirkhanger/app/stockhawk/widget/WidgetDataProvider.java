/*
 * Copyright 2016 Sudhir Khanger
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sudhirkhanger.app.stockhawk.widget;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.sudhirkhanger.app.stockhawk.model.QuoteColumns;
import com.sudhirkhanger.app.stockhawk.model.QuoteProvider;

import java.util.ArrayList;
import java.util.List;

public class WidgetDataProvider implements RemoteViewsService.RemoteViewsFactory {

    List<String> collection = new ArrayList<>();
    Context mContext;
    Intent mIntent;

    public WidgetDataProvider(Context context, Intent intent) {
        mContext = context;
        mIntent = intent;
    }

//    private void intiData() {
//        collection.clear();
//        for (int i = 1; i <= 10; i++) {
//            collection.add("ListView Item " + i);
//        }
//    }

//    ArrayList<WhateverTypeYouWant> mArrayList = new ArrayList<WhateverTypeYouWant>();
//for(mCursor.moveToFirst(); !mCursor.isAfterLast(); mCursor.moveToNext()) {
//    // The Cursor is now set to the right position
//    mArrayList.add(mCursor.getWhateverTypeYouWant(WHATEVER_COLUMN_INDEX_YOU_WANT));
//}

    private void intiData() {
        collection.clear();
        ContentResolver contentResolver = mContext.getContentResolver();

        Cursor cursor = contentResolver.query(QuoteProvider.Quotes.CONTENT_URI,
                new String[]{QuoteColumns.SYMBOL, QuoteColumns.BIDPRICE},
                null,
                null,
                null);

        if (cursor !=null) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                collection.add(cursor.getString(cursor.getColumnIndex(QuoteColumns.SYMBOL)));
            }
        }
    }

    @Override
    public void onCreate() {
        intiData();
    }

    @Override
    public void onDataSetChanged() {
        intiData();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return collection.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(),
                android.R.layout.simple_list_item_1);
        remoteViews.setTextViewText(android.R.id.text1, collection.get(i));
        remoteViews.setTextColor(android.R.id.text1, Color.BLACK);
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
