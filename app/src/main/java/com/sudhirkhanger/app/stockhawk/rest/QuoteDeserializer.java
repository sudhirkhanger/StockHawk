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

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.sudhirkhanger.app.stockhawk.model.Stock;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class QuoteDeserializer implements JsonDeserializer<Stock> {
    @Override
    public Stock deserialize(JsonElement json,
                             Type typeOfT,
                             JsonDeserializationContext context)
            throws JsonParseException {

        ArrayList<Stock.StockItem> stockArrayList = new ArrayList<>();

        try {
            JsonObject queryObject = json.getAsJsonObject();
            JsonElement quoteElement = queryObject.get("quote");
            JsonArray quoteArray = quoteElement.getAsJsonArray();

            for (int i = 0; i < quoteArray.size(); i++) {
                final JsonElement stockItemElement = quoteArray.get(i);

                final JsonElement dateElement = stockItemElement.getAsJsonObject().get("Date");
                final JsonElement closeElement = stockItemElement.getAsJsonObject().get("Close");
                final String date = dateElement.getAsString();
                final String close = closeElement.getAsString();

                final Stock.StockItem stockItem = new Stock.StockItem(date, close);
                stockArrayList.add(stockItem);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        Stock stock = new Stock();
        stock.setStockItems(stockArrayList);

        return stock;
    }
}
