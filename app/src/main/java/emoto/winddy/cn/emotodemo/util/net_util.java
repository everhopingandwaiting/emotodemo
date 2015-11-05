package emoto.winddy.cn.emotodemo.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created by john on 15-10-1.
 */
public class net_util implements Serializable {
    public static final MediaType MEDIA_TYPE_MARKDOWN =
            MediaType.parse("text/x-markdown; charset=utf-8");

    public static String mainUrl = "http://123.57.210.236:1050/BHttpServer/";
    public static String loginCheck = "User.ashx";

    public static String RealTimeDataBl = "rtData.ashx";
    public static String getLocationInfo = "rtData.ashx";
    public static String getVehicleList = "ListGet.ashx";
    public static String order_info = "Unit.ashx";

    public synchronized static void postForResult(final String url, final RequestBody requestBody, final ONHttpCallBack onHttpCallBack) {
        new Thread(){
            @Override
            public void run() {

                Request request  = new Request.Builder()
                        .url(url)
                        .post(requestBody).build();
                requestForResponse(request, onHttpCallBack);

            }
        }.start();

    }

    public  synchronized static void postForLogin(final JsonObject jsonObject, final ONHttpCallBack onHttpCallBack) {
         new Thread(){
             @Override
             public void run() {

                 Request request = new Request.Builder().url(mainUrl+loginCheck)
                         .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, jsonObject.toString()))
                         .build();
                 requestForResponse(request, onHttpCallBack);
             }
         }.start();
    }

    public synchronized static void goFor( String url, final ONHttpCallBack onHttpCallBack, final JsonObject jsonObject) {
        final String newURL = mainUrl + url;
        System.out.println(newURL+"*********************我是请求地址*****************************");
        new Thread() {
            @Override
            public void run() {
                Request request = new Request.Builder()
                        .url(newURL)
                        .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, jsonObject.toString())).build();
                requestForResponse(request, onHttpCallBack);
            }
        }.start();
    }

    public  synchronized static void requestForResponse(Request request, ONHttpCallBack onHttpCallBack) {
        OkHttpClient httpClient = new OkHttpClient();
        Response response = null;

        try {
            response = httpClient.newCall(request).execute();
            JsonParser parser = new JsonParser();
            String respStr = response.body().string();
            System.out.println(respStr+"-----------------------------我是返回结果-----------------------------------");
            JsonElement element = parser.parse(respStr);
            if (response.code() >= 400) {
                onHttpCallBack.onHttpCallBack(null);
                return;
            }
            if (!element.isJsonObject()) {
              //  Log.e("not a json Object!" + request.urlString());
                return;
            }
            JsonObject object = element.getAsJsonObject();
            onHttpCallBack.onHttpCallBack(object);
//            if (object.has("state")) {
//                String stat = object.get("state").getAsString();
//                if (stat.equals("ExceptionFailure")) {
//                    onHttpCallBack.onHttpCallBack(null);
//                    return;
//                }
//            }

        } catch (IOException e) {
            e.printStackTrace();
            onHttpCallBack.onHttpCallBack(null);

        }


    }
}
