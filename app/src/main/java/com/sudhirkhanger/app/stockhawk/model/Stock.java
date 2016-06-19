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

        String Date;
        String Close;

        public StockItem(String date, String close) {
            Date = date;
            Close = close;
        }

        public String getDate() {
            return Date;
        }

        public void setDate(String date) {
            this.Date = date;
        }

        public String getClose() {
            return Close;
        }

        public void setClose(String close) {
            this.Close = close;
        }

        @Override
        public String toString() {
            return "StockItem{" +
                    "Date='" + Date + '\'' +
                    ", Close='" + Close + '\'' +
                    '}';
        }
    }
}