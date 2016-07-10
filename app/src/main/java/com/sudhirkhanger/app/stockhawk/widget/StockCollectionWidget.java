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

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.sudhirkhanger.app.stockhawk.service.StockTaskService;

/**
 * Implementation of App Widget functionality.
 */
public class StockCollectionWidget extends AppWidgetProvider {

//    @Override
//    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
//        // There may be multiple widgets active, so update all of them
//        for (int appWidgetId : appWidgetIds) {
//            // Construct the RemoteViews object
//            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.stock_collection_widget);
//
//            // Set up the collection
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
//                setRemoteAdapter(context, views);
//            } else {
//                setRemoteAdapterV11(context, views);
//            }
//
//            // Instruct the widget manager to update the widget
//            appWidgetManager.updateAppWidget(appWidgetId, views);
//
//        }
//        super.onUpdate(context, appWidgetManager, appWidgetIds);
//    }

//    /**
//     * Sets the remote adapter used to fill in the list items
//     *
//     * @param views RemoteViews to set the RemoteAdapter
//     */
//    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
//    private static void setRemoteAdapter(Context context, @NonNull final RemoteViews views) {
//        views.setRemoteAdapter(R.id.widget_list,
//                new Intent(context, WidgetService.class));
//    }
//
//    /**
//     * Sets the remote adapter used to fill in the list items
//     *
//     * @param views RemoteViews to set the RemoteAdapter
//     */
//    @SuppressWarnings("deprecation")
//    private static void setRemoteAdapterV11(Context context, @NonNull final RemoteViews views) {
//        views.setRemoteAdapter(0, R.id.widget_list,
//                new Intent(context, WidgetService.class));
//    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        context.startService(new Intent(context, StockWidgetIntentService.class));
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager,
                                          int appWidgetId, Bundle newOptions) {
        context.startService(new Intent(context, StockWidgetIntentService.class));
    }

    @Override
    public void onReceive(@NonNull Context context, @NonNull Intent intent) {
        super.onReceive(context, intent);
        if (StockTaskService.ACTION_DATA_UPDATED.equals(intent.getAction())) {
            context.startService(new Intent(context, StockWidgetIntentService.class));
        }
    }
}

