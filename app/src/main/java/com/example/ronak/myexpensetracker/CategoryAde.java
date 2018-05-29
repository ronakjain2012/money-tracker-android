package com.example.ronak.myexpensetracker;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class CategoryAde extends BaseAdapter {
    private static LayoutInflater inflater = null;
    String[] result;
    Context context;

    public CategoryAde(Category mainActivity, String[] values) {
        result = values;
        context = mainActivity;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int postion, View v, final ViewGroup parent) {
        final ViewHolder h = new ViewHolder();
        final View row;
        row = inflater.inflate(R.layout.category_list_ad, null);
        h.t1 = (TextView) row.findViewById(R.id.category);
        h.t1.setText(result[postion]);
        final String t = result[postion];
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("Action")
                        .setMessage("Do you want to delete?")
                        .setCancelable(false)
                        .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Accounts obj = new Accounts(context);
                                if (obj.deleteCateg(t)) {
                                    Toast.makeText(context, "Deleted.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "Error While Detetion.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).show();
            }
        });
        return row;
    }

    static class ViewHolder {
        TextView t1;
    }
}
