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

package com.sudhirkhanger.app.stockhawk.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sudhirkhanger.app.stockhawk.R;
import com.sudhirkhanger.app.stockhawk.model.Stock;
import com.sudhirkhanger.app.stockhawk.rest.QuoteDeserializer;
import com.sudhirkhanger.app.stockhawk.rest.StockService;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyStockGraphActivity extends Activity {

    private static final String BASE_URL = "https://query.yahooapis.com/";
    private Call<Stock> mStockServiceCall;
    private Stock mStock;
    private List<Stock.StockItem> mStockItem;

    @BindView(R.id.testTextView)
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_stock_graph);
//        getActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        Intent intent = getIntent();

        String symbol = intent.getStringExtra("stock_symbol");

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Stock.StockItem.class, new QuoteDeserializer())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        StockService.StockYql stockYql = retrofit.create(StockService.StockYql.class);

        String startDate = "2015-06-10";
        String endDate = "2016-06-10";

        String query = "select * from yahoo.finance.historicaldata where symbol = \'" + symbol + "\' and startDate = \'" + startDate + "\' and endDate = \'" + endDate + "\'";

        mStockServiceCall = stockYql.getHistoricalData(query);
        mStockServiceCall.enqueue(new Callback<Stock>() {
            @Override
            public void onResponse(Call<Stock> call, Response<Stock> response) {

                try {
                    mStock = response.body();
                    Log.d("MyStockGraphActivity", response.errorBody().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                } catch (NullPointerException e) {
//                    Toast toast = null;
//                    if (response.code() == 401) {
//                        toast = Toast.makeText(MyStockGraphActivity.this, "Unauthenticated", Toast.LENGTH_SHORT);
//                    } else if (response.code() >= 400) {
//                        toast = Toast.makeText(MyStockGraphActivity.this, "Client Error " + response.code()
//                                + " " + response.message(), Toast.LENGTH_SHORT);
//                    }
//                    toast.show();
//                }
//                mStockItem = mStock.getStockItems();
//                mTextView.setText(mStockItem.get(0).getClose());
            }

            @Override
            public void onFailure(Call<Stock> call, Throwable t) {
                Log.d("MyStockGraphActivity", t.getMessage());
            }
        });

    }


//    @Override
//    public void onResponse(Response<User> response, Retrofit retrofit) {
//
//        if (!response.isSuccess()) {
//            try {
//                Log.e("LOG", "Retrofit Response: " + response.errorBody().string());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
