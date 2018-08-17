package com.example.carlosje.creditravel;

/**
 * Created by carlosje on 8/15/2018.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by carlosje on 4/7/2018.
 */


public class CustomAdapter extends BaseAdapter {
    Context context;
    String Secc[],SeccDesc[];
    int flags[];
    LayoutInflater inflter;

    public CustomAdapter(Context applicationContext, String[] SerCod, String[] SerCodDesc) {
        this.context = context;
        this.Secc = SerCod;
        this.SeccDesc = SerCodDesc;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return Secc.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.activity_listview, null);
        TextView TV_SC = (TextView) view.findViewById(R.id.TV_SC);
        TextView TV_SC_Desc = (TextView) view.findViewById(R.id.TV_SC_Desc);
        TV_SC.setText(Secc[i]);
        TV_SC_Desc.setText(SeccDesc[i]);

        return view;
    }

}
