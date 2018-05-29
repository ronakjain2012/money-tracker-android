package com.example.ronak.myexpensetracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class TwoFragment extends Fragment {

    public TwoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_two, container, false);
        Accounts obj = new Accounts(getActivity());
        TextView t1, t2, t3, t4, t5, t6, t7, t8;
        t1 = (TextView) v.findViewById(R.id.totalIncome);
        t2 = (TextView) v.findViewById(R.id.weekIncome);
        t3 = (TextView) v.findViewById(R.id.monthIncome);
        t4 = (TextView) v.findViewById(R.id.yearIncome);
        t5 = (TextView) v.findViewById(R.id.totalExpense);
        t6 = (TextView) v.findViewById(R.id.weekExpense);
        t7 = (TextView) v.findViewById(R.id.monthExpense);
        t8 = (TextView) v.findViewById(R.id.yearExpense);
        t1.setText(obj.getTotlaIncome() + "");
        t2.setText(obj.getWeekIncome() + "");
        t3.setText(obj.getMonthIncome() + "");
        t4.setText(obj.getYearIncome() + "");
        t5.setText(obj.getTotlaExpense() + "");
        t6.setText(obj.getWeekExpense() + "");
        t7.setText(obj.getMonthExpense() + "");
        t8.setText(obj.getYearExpense() + "");
        return v;
    }

}
