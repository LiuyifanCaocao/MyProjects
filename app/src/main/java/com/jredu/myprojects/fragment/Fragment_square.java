package com.jredu.myprojects.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListPopupWindow;
import android.widget.ListView;

import com.jredu.myprojects.R;
import com.jredu.myprojects.baseadpater.SquareAdapter;
import com.jredu.myprojects.entity.Square;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_square extends Fragment {
    List<Square> list;
    ListView listView;
    Square square;
    EditText editText;
    ListPopupWindow listPopupWindow;
    String[] history = {"安卓7.0", "够炫酷", "20省份"};
    public Fragment_square() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fragment_square, container, false);
        editText = (EditText) v.findViewById(R.id.squre_edit);
        listView = (ListView) v.findViewById(R.id.square_listView);
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();;
        listPopupWindow = new ListPopupWindow(getContext());
        listPopupWindow.setAdapter(new ArrayAdapter(getContext(), R.layout.list_itemlayout, history));
        listPopupWindow.setAnchorView(editText);
        listPopupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        listPopupWindow.setHeight(200);
        listPopupWindow.setModal(true);
        listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editText.setText(history[position]);
                listPopupWindow.dismiss();
            }
        });
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listPopupWindow.show();
            }
        });
        list = new ArrayList<Square>();
        square = new Square(1, "20省份明确城乡医保并轨，民众能享受到啥优惠？", "", 0, R.drawable.square_yibao1, R.drawable.square_yibao2, R.drawable.square_yibao3, 0);
        list.add(square);
        square = new Square(0, "安卓7.0！这就是诺基亚安卓手机：配置感人", "诺基亚手机归来，运行Android 7.0系统，你还会期待吗？", R.drawable.square_phone, 0, 0, 0, 0);
        list.add(square);
        square = new Square(2,"青岛遇天文大潮，大片贝壳堆积成滩规模壮观","",0,0,0,0,R.drawable.icon_qingdao);
        list.add(square);
        square = new Square(1, "20省份明确城乡医保并轨，民众能享受到啥优惠？", "", 0, R.drawable.square_yibao1, R.drawable.square_yibao2, R.drawable.square_yibao3, 0);
        list.add(square);
        square = new Square(2,"青岛遇天文大潮，大片贝壳堆积成滩规模壮观","",0,0,0,0,R.drawable.icon_qingdao);
        list.add(square);
        SquareAdapter squareAdapter = new SquareAdapter(getContext(), list);
        listView.setAdapter(squareAdapter);
        return v;
    }

}
