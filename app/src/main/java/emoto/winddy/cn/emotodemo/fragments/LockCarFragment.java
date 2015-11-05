package emoto.winddy.cn.emotodemo.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.google.gson.JsonObject;

import emoto.winddy.cn.emotodemo.R;
import emoto.winddy.cn.emotodemo.global.Request_all_info;
import emoto.winddy.cn.emotodemo.util.CarOrder;
import emoto.winddy.cn.emotodemo.util.net_util;

/**
 * Created by winddy on 2015/10/17.
 */
public class LockCarFragment extends ActSafeFragment {
    private AQuery aQuery = null;
    private Handler handler;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        basicLayout = inflater.inflate(R.layout.lock_page_layout,container,false);
        return basicLayout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        aQuery = new AQuery(view);
        aQuery.id(R.id.lock_on_start).clicked(this, "carUnLock");
        aQuery.id(R.id.lock_off_start).clicked(this,"carLOck");
        handler = new Handler();
    }

    public void carUnLock() {
        if (aQuery.id(R.id.mySwitch).isChecked()) {


//            aQuery.ajax(net_util.order_info, CarOrder.orderList(getActivity()).get("XCBKQ"), JsonObject.class, new AjaxCallback<JsonObject>() {
//                @Override
//                public void callback(String url, JsonObject object, AjaxStatus status) {
////                        Toast.makeText(getActivity(),object.toString()+"*********"+status,Toast.LENGTH_SHORT).show();
//                    Toast.makeText(getActivity(), "请先打开状态开关！", Toast.LENGTH_SHORT).show();
//                }
//            });

            Request_all_info.requestOrderCallback(getActivity(), CarOrder.orderList(getActivity()).get("JF"),handler);
        } else {
            Toast.makeText(getActivity(), "请先打开状态开关！", Toast.LENGTH_SHORT).show();
        }

    }

    public void carLOck() {
        if (aQuery.id(R.id.mySwitch).isChecked()) {
            Request_all_info.requestOrderCallback(getActivity(), CarOrder.orderList(getActivity()).get("SF"),handler);
        } else {
            Toast.makeText(getActivity(), "请先打开状态开关！", Toast.LENGTH_SHORT).show();
        }
    }
}
