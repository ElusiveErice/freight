package com.csu.freightbook.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.csu.freightbook.R;
import com.csu.freightbook.activities.EditBillRankActivity;
import com.csu.freightbook.db.FreightBookDataBase;
import com.csu.freightbook.db.daos.BillDao;
import com.csu.freightbook.db.daos.RankDao;
import com.csu.freightbook.db.entities.Bill;
import com.csu.freightbook.db.entities.Rank;
import com.xuexiang.xui.widget.actionbar.TitleBar;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WriteBillFragment extends Fragment {

    public static WriteBillFragment newInstance() {
        return new WriteBillFragment();
    }

    private TitleBar mTitleBar;
    private SwipeRefreshLayout mSRLBills;
    private RecyclerView mRecyclerView;

    private BillDao mBillDao;
    private RankDao mRankDao;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_write_bill, container, false);

        mBillDao = FreightBookDataBase.getInstance(getActivity()).billDao();
        mRankDao = FreightBookDataBase.getInstance(getActivity()).rankDao();

        mTitleBar = view.findViewById(R.id.title_bar);
        mSRLBills = view.findViewById(R.id.srl_bills);
        mRecyclerView = view.findViewById(R.id.rv_ranks);

        //TODO 解决LayoutManager导致SwipeRefreshLayout不显示问题
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mTitleBar.addAction(new TitleBar.ImageAction(R.drawable.ic_add) {
            @Override
            public void performAction(View view) {
                startActivity(EditBillRankActivity.newIntent(getActivity()));
            }
        });

        mSRLBills.setOnRefreshListener(this::update);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        update();
    }

    public void update() {
        Observable.create((ObservableOnSubscribe<List<Rank>>) e -> {
            e.onNext(mRankDao.getRanks(false));
            e.onComplete();
        }).observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Rank>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Rank> ranks) {
                        RanksAdapter ranksAdapter = new RanksAdapter(mRankDao.getRanks());
                        mRecyclerView.setAdapter(ranksAdapter);
                        mSRLBills.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private class BillViewHolder extends RecyclerView.ViewHolder {

        public BillViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(Bill bill) {

        }
    }

    private class BillAdapter extends RecyclerView.Adapter<BillViewHolder> {

        private List<Bill> mBills;

        public BillAdapter(List<Bill> bills) {
            mBills = bills == null ? new ArrayList<>() : bills;
        }

        @NonNull
        @Override
        public BillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new BillViewHolder(getLayoutInflater().inflate(R.layout.list_item_bill, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull BillViewHolder holder, int position) {
            holder.bind(mBills.get(position));
        }

        @Override
        public int getItemCount() {
            return mBills.size();
        }
    }

    private class RankViewHolder extends RecyclerView.ViewHolder {

        private RecyclerView mRVBills;

        public RankViewHolder(@NonNull View itemView) {

            super(itemView);

            mRVBills = itemView.findViewById(R.id.rv_bills);
            mRVBills.setLayoutManager(new LinearLayoutManager(getActivity()));
        }

        public void bind(Rank rank) {
            Observable.create((ObservableOnSubscribe<List<Bill>>) e -> {
                e.onNext(mBillDao.getBillsByRankId(rank.getRankId()));
                e.onComplete();
            }).observeOn(Schedulers.io())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<Bill>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(List<Bill> bills) {
                            BillAdapter billAdapter = new BillAdapter(bills);
                            mRVBills.setAdapter(billAdapter);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }

    private class RanksAdapter extends RecyclerView.Adapter<RankViewHolder> {

        private final List<Rank> mRanks;

        public RanksAdapter(List<Rank> ranks) {
            mRanks = ranks == null ? new ArrayList<>() : ranks;
        }

        @NonNull
        @Override
        public RankViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new RankViewHolder(getLayoutInflater().inflate(R.layout.list_item_rank, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull RankViewHolder holder, int position) {
            holder.bind(mRanks.get(position));
        }

        @Override
        public int getItemCount() {
            return mRanks.size();
        }
    }
}
