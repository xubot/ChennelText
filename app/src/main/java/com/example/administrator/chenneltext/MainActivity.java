package com.example.administrator.chenneltext;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.amiao.managlibary.dao.MangDao;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String[] mytitle={"热点","新闻","体育","动态"};
    private String[] other={"美女","房车","喜欢","个性"};
    private ArrayList<String> otherlist=new ArrayList<>();
    private ArrayList<String> mylist=new ArrayList<>();
    private boolean flag = false;
    private GridView gv1;
    private GridView gv2;
    private MangDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dao=new MangDao(this);
        gv1 = (GridView) findViewById(R.id.gv1);
        gv2 = (GridView) findViewById(R.id.gv2);
        setGridview();
    }
    public void setGridview()
    {
        setData();
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, mylist);
        gv1.setAdapter(adapter);
        final ArrayAdapter<String> adapter1 = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, otherlist);
        gv2.setAdapter(adapter1);
        gv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //  dao.addMore(mylist.get(position));
                //dao.deleteMy(mylist.get(position));
                dao.getMyTable(otherlist,mylist,mylist.get(position));
                adapter.notifyDataSetChanged();
                adapter1.notifyDataSetChanged();
            }
        });

        gv1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if(flag){
                    for (int i = 0; i <mylist.size() ; i++) {
                        gv1.getChildAt(i ).setBackgroundColor(Color.WHITE);
                    }
                    flag = false;
                    adapter.notifyDataSetChanged();
                    adapter1.notifyDataSetChanged();
                }else{
                    for (int i = 0; i <mylist.size() ; i++) {
                        gv1.getChildAt(i ).setBackgroundColor(Color.RED);
                    }
                    flag = true;
                    adapter.notifyDataSetChanged();
                    adapter1.notifyDataSetChanged();
                }
                return true;
            }
        });

        gv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // dao.addMy(otherlist.get(position));
                //dao.deleteMore(otherlist.get(position));
                dao.getMoreTable(otherlist,mylist,otherlist.get(position));
                adapter1.notifyDataSetChanged();
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void setData() {
        for (int i = 0; i <other.length ; i++) {
            dao.addMore(other[i]);
            otherlist.add(other[i]);
        }
        for (int i = 0; i <mytitle.length ; i++) {
            dao.addMy(mytitle[i]);
            mylist.add(mytitle[i]);
        }
    }
}
