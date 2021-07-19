package com.csu.freightbook.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.csu.freightbook.R;
import com.csu.freightbook.activities.EditBillRankActivity;
import com.csu.freightbook.base.ObserverNext;
import com.csu.freightbook.db.FreightBookDataBase;
import com.csu.freightbook.db.daos.BillDao;
import com.csu.freightbook.db.daos.MaintenanceDao;
import com.csu.freightbook.db.daos.RankDao;
import com.csu.freightbook.db.entities.Bill;
import com.csu.freightbook.db.entities.Maintenance;
import com.csu.freightbook.db.entities.Rank;

import java.util.List;
import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PooledAnalysisFragment extends Fragment {

    public static PooledAnalysisFragment newInstance() {
        return new PooledAnalysisFragment();
    }

    private RankDao mRankDao;
    private BillDao mBillDao;
    private MaintenanceDao mMaintenanceDao;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FreightBookDataBase db = FreightBookDataBase.getInstance(Objects.requireNonNull(getActivity()).getApplicationContext());
        mRankDao = db.rankDao();
        mBillDao = db.billDao();
        mMaintenanceDao = db.maintenanceDao();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.fragment_pooled_analysis, container, false);

        TextView tvBillInfo, tvRankInfo, tvMaintenanceInfo;
        tvBillInfo = view.findViewById(R.id.tv_bill_info);
        tvRankInfo = view.findViewById(R.id.tv_rank_info);
        tvMaintenanceInfo = view.findViewById(R.id.tv_maintenance_info);

        Observable.create((ObservableOnSubscribe<List<Bill>>) e -> e.onNext(mBillDao.getBills()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ObserverNext<List<Bill>>() {
                    @Override
                    public void onNext(List<Bill> bills) {
                        tvBillInfo.setText(String.valueOf(bills.size()));
                    }
                });
        Observable.create((ObservableOnSubscribe<List<Rank>>) e -> e.onNext(mRankDao.getRanks()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ObserverNext<List<Rank>>() {
                    @Override
                    public void onNext(List<Rank> ranks) {
                        tvRankInfo.setText(String.valueOf(ranks.size()));
                    }
                });
        Observable.create((ObservableOnSubscribe<List<Maintenance>>) e -> e.onNext(mMaintenanceDao.getMaintenances()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ObserverNext<List<Maintenance>>() {
                    @Override
                    public void onNext(List<Maintenance> maintenances) {
                        tvMaintenanceInfo.setText(String.valueOf(maintenances.size()));
                    }
                });
        return view;
    }
}
