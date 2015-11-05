package emoto.winddy.cn.emotodemo.global;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.io.Serializable;

import emoto.winddy.cn.emotodemo.util.ONHttpCallBack;
import emoto.winddy.cn.emotodemo.util.net_util;


/**
 * Created by john on 15-10-3.
 */
public class Request_all_info implements Serializable {
  public  synchronized   static void requestForRealData( final Context context) {
        JsonObject object = new JsonObject();
        object.addProperty("action", "rtdata");

      object.addProperty("unitnumber", context.getSharedPreferences("RealData", Context.MODE_PRIVATE).getString("unitnumber", "865067020417682"));
        net_util.goFor(net_util.RealTimeDataBl, new ONHttpCallBack() {
            @Override
            public void onHttpCallBack(JsonObject jsonObject) {
                if (jsonObject == null) {

                    return;
                }
                SharedPreferences preferences = context.getSharedPreferences("RealData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("unitnumber", jsonObject.get("unitnumber").getAsString())
                        .putString("MonitoringInfo", jsonObject.get("MonitoringInfo").getAsString())
                        .putString("Fault", jsonObject.get("Fault").getAsString())
                        .putString("StandbyElectricity", jsonObject.get("StandbyElectricity").getAsString())
                        .putString("Electricity", jsonObject.get("Electricity").getAsString())
                        .apply();


            }
        }, object);

    }

    public  synchronized static void requestOrderCallback(final Context context, final JsonObject object, final Handler handler) {
                 net_util.goFor(net_util.order_info, new ONHttpCallBack() {
                     @Override
                     public void onHttpCallBack(JsonObject jsonObject) {
                         final String state = jsonObject.get("state").getAsString();
                         context.getSharedPreferences("OrderStatus", Context.MODE_PRIVATE).edit().putString("state", state).apply();
                         handler.post(new Runnable() {
                             @Override
                             public void run() {
                                 if (state.equals("success".toUpperCase())) {
                                     Toast.makeText(context,"设置成功！等待服务端统一处理。",Toast.LENGTH_SHORT).show();
                                 }
                             }
                         });
                     }
                 },object);
    }
}
