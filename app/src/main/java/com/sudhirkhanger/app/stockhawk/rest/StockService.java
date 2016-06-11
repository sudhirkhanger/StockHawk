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

package com.sudhirkhanger.app.stockhawk.rest;

// https://query.yahooapis.com/v1/public/yql?q=
// select%20*%20from%20yahoo.finance.historicaldata%20where%20symbol%20%3D%20%22YHOO%22%20and%20startDate%20%3D%20%222015-06-10%22%20and%20endDate%20%3D%20%222016-06-10%22
// &format=json&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=

import com.sudhirkhanger.app.stockhawk.model.Stock;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public final class StockService {

    public interface StockYql {
        @GET("v1/public/yql?&format=json&diagnostics=true&env=store://datatables.org/alltableswithkeys&callback=")
        Call<Stock> getHistoricalData(
                @Query("q") String query);
    }
}
