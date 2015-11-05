package emoto.winddy.cn.emotodemo.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by john on 15-11-5.
 */
public class CarOrder {
    public static Map<String, JsonObject> orderList(Context context) {

        Map<String, JsonObject> jsonObjectMap = new HashMap<>();
        String action = "action";
        String unitnumber = "unitnumber";
        String listOrder[] = new String[]{"SF", "JF", "DMDK", "DMJT", "XCBKQ", "XCKQ", "DWXXBSB", "DWXXSB", "DWZQ0", "DWZQ1", "DWZQ2", "DWZQ3"
                , "DWZQ4", "DWZQ5", "DWZQ6", "DWZQ7"};
        SharedPreferences preferences = context.getSharedPreferences("RealData", context.MODE_PRIVATE);
        String num = preferences.getString("unitnumber", "error");
        for (int i = 0; i < listOrder.length; i++) {

            JsonObject object = new JsonObject();
            object.addProperty(action, listOrder[i]);
            object.addProperty(unitnumber, num);
            jsonObjectMap.put(listOrder[i], object);
        }

        return jsonObjectMap;
    }
}
