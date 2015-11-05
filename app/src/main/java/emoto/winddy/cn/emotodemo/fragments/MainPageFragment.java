package emoto.winddy.cn.emotodemo.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import emoto.winddy.cn.emotodemo.R;
import emoto.winddy.cn.emotodemo.fragments.ui.Map_activity;
import emoto.winddy.cn.emotodemo.global.Request_all_info;
import emoto.winddy.cn.emotodemo.util.CarOrder;

/**
 * Created by winddy on 2015/10/17.
 */
public class MainPageFragment extends ActSafeFragment implements AdapterView.OnItemClickListener {
    GridView gridView;
    private Handler handler;
    int[] res = new int[]{
            R.drawable.grid_btn_warning, R.drawable.grid_btn_location, R.drawable.grid_btn_support
            , R.drawable.grid_btn_my_path, R.drawable.grid_btn_remote_lock, R.drawable.grid_btn_remote_find
            , R.drawable.grid_btn_bike_mall, R.drawable.grid_btn_service, R.drawable.grid_btn_help
    };
    int[] bg = new int[]{
            android.R.color.holo_blue_bright, android.R.color.holo_orange_light, android.R.color.holo_green_dark,
            android.R.color.holo_blue_dark, android.R.color.holo_red_light, android.R.color.holo_blue_bright,
            android.R.color.holo_red_dark, android.R.color.holo_green_light, android.R.color.black
    };
    String[] names = new String[]{
            "故障报警", "位置信息", "双支撑起落"
            , "我的轨迹", "远程设防", "远程寻车"
            , "新车商城", "周边服务", "一键救援"
    };
    Point size;

    static Rect rect = new Rect();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        basicLayout = inflater.inflate(R.layout.main_page_layout, container, false);
        gridView = (GridView) basicLayout.findViewById(R.id.grid_view);

        List<HashMap<String, Object>> imagelist = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("res", res[i]);
            map.put("names", names[i]);
            imagelist.add(map);

        }
        gridView.setAdapter(new Adapter());

        gridView.setOnItemClickListener(this);

        Display display = mActivity.getWindowManager().getDefaultDisplay();
        size = new Point();
        display.getSize(size);
        AQuery aQuery = new AQuery( gridView);

        return basicLayout;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        handler = new Handler();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), Map_activity.class);
                switch (position) {
                    case 1:
//                        Toast.makeText(getActivity(), "hhh", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        break;
                    case 3:
                        startActivity(intent);
                        break;
                    case 5:
                        startActivity(intent);
                        break;
                    case 0:
                        Toast.makeText(getActivity(), "敬请期待", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(getActivity(), "敬请期待", Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Request_all_info.requestOrderCallback(getActivity(), CarOrder.orderList(getActivity()).get("SF"), handler);
                        break;
                    case 6:
                        Toast.makeText(getActivity(), "敬请期待", Toast.LENGTH_SHORT).show();
                        break;
                    case 7:
                        Toast.makeText(getActivity(), "敬请期待", Toast.LENGTH_SHORT).show();
                        break;
                    case 8:
                        Toast.makeText(getActivity(), "敬请期待", Toast.LENGTH_SHORT).show();
                        break;

                }
            
        }
    

    class Adapter extends BaseAdapter {
        @Override
        public int getCount() {
            return 9;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = mActivity.getLayoutInflater().inflate(R.layout.grid_cell, parent, false);
                ImageView imageButton = (ImageView) convertView.findViewById(R.id.cell);
                ViewGroup.LayoutParams layoutParams = convertView.getLayoutParams();
                int wid = size.x / 3;
                layoutParams.height = layoutParams.width = wid;
                convertView.setLayoutParams(layoutParams);

                layoutParams = imageButton.getLayoutParams();
                imageButton.setImageResource(res[position]);
                convertView.setBackgroundColor(getResources().getColor(bg[position]));
                layoutParams.height = layoutParams.width = wid - 20;
                imageButton.setLayoutParams(layoutParams);
                TextView tv = (TextView) convertView.findViewById(R.id.cell_name);
                tv.setText(names[position]);
                buttonEffect(convertView, Color.parseColor("#d3d3d3"));
//                convertView.setOnTouchListener(new View.OnTouchListener() {
//                    @Override
//                    public boolean onTouch(View v, MotionEvent event) {
//                        v.getHitRect(rect);
//                        int action = event.getAction();
//                        switch (action) {
//                            case MotionEvent.ACTION_DOWN:
//                                GradientDrawable bgShape = (GradientDrawable) v.getBackground();
//                                bgShape.setColor(Color.parseColor("#BBDEFB"));
//                                break;
//                            case MotionEvent.ACTION_UP:
//                            case MotionEvent.ACTION_OUTSIDE:
//                            case MotionEvent.ACTION_CANCEL:
//                                GradientDrawable bgShape1 = (GradientDrawable) v.getBackground();
//                                bgShape1.setColor(Color.WHITE);
//                                break;
//
//                        }
//                        return false;
//                    }
//                });
            }

            return convertView;
        }
    }

    public static void buttonEffect(View button, int clickColor) {
        int color = 0x50888888;
        if (clickColor != -1) {
            color = clickColor;
        }
        final int mColor = color;
        button.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN: {
                        if (v.getBackground() == null) {
                            v.setAlpha(0.5f);
                        } else {

                            v.getBackground().setColorFilter(mColor, PorterDuff.Mode.MULTIPLY);
                        }

                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        if (v.getBackground() == null) {
                            v.setAlpha(1f);
                        } else {
                            v.getBackground().clearColorFilter();
                        }
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_CANCEL:
                        if (v.getBackground() == null) {
                            v.setAlpha(1f);
                        } else {
                            v.getBackground().clearColorFilter();
                        }
                        v.invalidate();
                        break;
                }
                return false;
            }
        });
    }





}
