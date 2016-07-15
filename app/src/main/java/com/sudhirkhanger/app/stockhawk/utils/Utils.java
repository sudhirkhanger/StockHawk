package com.sudhirkhanger.app.stockhawk.utils;

import android.content.ContentProviderOperation;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.sudhirkhanger.app.stockhawk.model.QuoteColumns;
import com.sudhirkhanger.app.stockhawk.model.QuoteProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by sam_chordas on 10/8/15.
 */
public class Utils {

    public static final String CHANGEIN_PERCENT = "ChangeinPercent";
    public static final String CHANGE = "Change";
    public static final String NULL = "null";
    public static final String BID = "Bid";
    public static final String QUERY = "query";
    public static final String COUNT = "count";
    public static final String RESULTS = "results";
    public static final String QUOTE = "quote";
    public static final String SYMBOL = "symbol";
    private static String LOG_TAG = Utils.class.getSimpleName();

    public static boolean showPercent = true;

    public static ArrayList quoteJsonToContentVals(String JSON, Context context) {
        ArrayList<ContentProviderOperation> batchOperations = new ArrayList<>();
        JSONObject jsonObject = null;
        JSONArray resultsArray = null;
        try {
            jsonObject = new JSONObject(JSON);
            if (jsonObject != null && jsonObject.length() != 0) {
                jsonObject = jsonObject.getJSONObject(QUERY);
                int count = Integer.parseInt(jsonObject.getString(COUNT));
                if (count == 1) {
                    jsonObject = jsonObject.getJSONObject(RESULTS)
                            .getJSONObject(QUOTE);

                    ContentProviderOperation contentProviderOperation =
                            buildBatchOperation(jsonObject, context);

                    if (contentProviderOperation != null) {
                        Log.d(LOG_TAG, "quoteJsonToContentVals() reached 44");
                        batchOperations.add(contentProviderOperation);
                    } else {
                        Log.d(LOG_TAG, "quoteJsonToContentVals: " +
                                jsonObject.getString("symbol") +
                                " not found");
                        batchOperations = null;
                    }

                } else {
                    resultsArray = jsonObject.getJSONObject(RESULTS)
                            .getJSONArray(QUOTE);

                    Log.d(LOG_TAG, "jsonObject is " + resultsArray.toString());

                    if (resultsArray != null && resultsArray.length() != 0) {
                        for (int i = 0; i < resultsArray.length(); i++) {
                            jsonObject = resultsArray.getJSONObject(i);

                            ContentProviderOperation contentProviderOperation =
                                    buildBatchOperation(jsonObject, context);

                            if (contentProviderOperation != null) {
                                Log.d(LOG_TAG, "quoteJsonToContentVals() reached 69");
                                batchOperations.add(contentProviderOperation);
                            } else {
                                Log.d(LOG_TAG, "quoteJsonToContentVals: " +
                                        jsonObject.getString("symbol") +
                                        " not found");
                                batchOperations = null;
                            }
                        }
                    }
                }
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "String to JSON failed: " + e);
        }
        return batchOperations;
    }

    public static String truncateBidPrice(String bidPrice) {
        bidPrice = String.format("%.2f", Float.parseFloat(bidPrice));
        return bidPrice;
    }

    public static String truncateChange(String change, boolean isPercentChange) {
        String weight = change.substring(0, 1);
        String ampersand = "";
        if (isPercentChange) {
            ampersand = change.substring(change.length() - 1, change.length());
            change = change.substring(0, change.length() - 1);
        }
        change = change.substring(1, change.length());
        double round = (double) Math.round(Double.parseDouble(change) * 100) / 100;
        change = String.format("%.2f", round);
        StringBuffer changeBuffer = new StringBuffer(change);
        changeBuffer.insert(0, weight);
        changeBuffer.append(ampersand);
        change = changeBuffer.toString();
        return change;
    }

    public static ContentProviderOperation buildBatchOperation(JSONObject jsonObject, Context context) {
        ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(
                QuoteProvider.Quotes.CONTENT_URI);

        String bidPrice = null;
        String symbol = "";
        try {
            bidPrice = jsonObject.getString(BID);
            symbol = jsonObject.getString(SYMBOL);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (bidPrice == null || bidPrice.equals(NULL)) {
            Log.d(LOG_TAG, "buildBatchOperation() " + symbol + " not found");
            return null;
        } else {
            try {
                String change = jsonObject.getString(CHANGE);
                builder.withValue(QuoteColumns.SYMBOL, symbol);
                builder.withValue(QuoteColumns.BIDPRICE, truncateBidPrice(bidPrice));
                builder.withValue(QuoteColumns.PERCENT_CHANGE, truncateChange(
                        jsonObject.getString(CHANGEIN_PERCENT), true));
                builder.withValue(QuoteColumns.CHANGE, truncateChange(change, false));
                builder.withValue(QuoteColumns.ISCURRENT, 1);
                if (change.charAt(0) == '-') {
                    builder.withValue(QuoteColumns.ISUP, 0);
                } else {
                    builder.withValue(QuoteColumns.ISUP, 1);
                }
                Log.d(LOG_TAG, "buildBatchOperation() " + symbol + " found");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return builder.build();
        }
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }
}
