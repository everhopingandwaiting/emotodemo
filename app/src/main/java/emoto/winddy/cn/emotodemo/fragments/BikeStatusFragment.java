package emoto.winddy.cn.emotodemo.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;

import emoto.winddy.cn.emotodemo.R;
import emoto.winddy.cn.emotodemo.global.Request_all_info;

/**
 * Created by winddy on 2015/10/17.
 */
public class BikeStatusFragment extends ActSafeFragment {
    GridView gridView;
    private AQuery aQuery = null;
    private Adapter adapter = null;
    int[] res = new int[]{
            R.drawable.btn_light, R.drawable.btn_horn, R.drawable.btn_handle
            , R.drawable.btn_shaba, R.drawable.btn_battary, R.drawable.btn_engine
            , R.drawable.btn_control
    };
    int[] status_icon = new int[]{R.drawable.check,R.drawable.xx};

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        aQuery = new AQuery(view);
        aQuery.id(R.id.test_btn).clicked(this, "testStaus");

    }

    String[] names = new String[]{
           "车灯","喇叭","转把","刹把","电池","电机","控制器"
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        basicLayout = inflater.inflate(R.layout.status_layout,container,false);
        gridView = (GridView) basicLayout.findViewById(R.id.grid_view);
        adapter = new Adapter();
        gridView.setAdapter(adapter);


        return basicLayout;
    }

    void init(){

    }

     public void testStaus() {
        Request_all_info.requestForRealData(getActivity().getApplicationContext());

        handleFailureData();
    }
    void handleFailureData() {
        {

//                aQuery.getGridView().getChildAt(0).findViewById(R.id.car_status_icon).setBackgroundResource(R.drawable.xx);
//
//                aQuery.getGridView().setBackgroundColor(Color.GREEN);

            this.adapter.notifyDataSetChanged();
//             检测变动

        }
    }
    class Adapter extends BaseAdapter{

        @Override
        public int getCount() {
            return 7;
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
            if(convertView==null){
                convertView = mActivity.getLayoutInflater().inflate(R.layout.status_cell,parent,false);
                ImageView imageView = (ImageView) convertView.findViewById(R.id.icon);
                TextView tv = (TextView) convertView.findViewById(R.id.text);
                imageView.setImageResource(res[position]);
                tv.setText(names[position]);
                ImageView car_status_icon = (ImageView) convertView.findViewById(R.id.car_status_icon);


                    SharedPreferences preferences = getActivity().getSharedPreferences("RealData", Context.MODE_PRIVATE);
                    String str = preferences.getString("Fault", "errorers");
                    char[] ch = str.toCharArray();
                    for (int i = 0; i <ch.length ; i++) {
                        System.out.println(ch[i]);
                    }
//        char ch[] = new char[]{0, 0, 0, 0, 0, 0, 0, 0};

                    if ('0'==(ch[++position])) {

                       car_status_icon.setImageResource(status_icon[0]);
                    } else if ('1'==(ch[1])) {
                        car_status_icon.setImageResource(status_icon[1]);

                    } else {
                        car_status_icon.setVisibility(View.INVISIBLE);

                    }

                    System.out.println("str"+str+ ch.toString() + "@@@@@@@@@@@@@@@@@@鸡巴鬼 啊  @@@@@@@@@@@@@@@@@@@@@" + "\n" );



            }
            return convertView;
        }
    }
}
