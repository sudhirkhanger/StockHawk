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

package com.sudhirkhanger.app.stockhawk.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Stock {

    List<StockItem> mStockItems;

    public List<StockItem> getStockItems() {
        return mStockItems;
    }

    public void setStockItems(List<StockItem> stockItems) {
        mStockItems = stockItems;
    }

    public static class StockItem {

        @SerializedName("Date")
        String date;

        @SerializedName("Close")
        String close;

        @Override
        public String toString() {
            return "StockItem{" +
                    "date='" + date + '\'' +
                    ", close='" + close + '\'' +
                    '}';
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getClose() {
            return close;
        }

        public void setClose(String close) {
            this.close = close;
        }
    }
}