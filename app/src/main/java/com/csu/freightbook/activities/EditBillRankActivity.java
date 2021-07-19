package com.csu.freightbook.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.csu.freightbook.R;
import com.csu.freightbook.XUIActivity;
import com.csu.freightbook.db.FreightBookDataBase;
import com.csu.freightbook.db.daos.BillDao;
import com.csu.freightbook.db.daos.RankDao;
import com.csu.freightbook.db.entities.Bill;
import com.csu.freightbook.db.entities.Rank;
import com.xuexiang.citypicker.CityPicker;
import com.xuexiang.citypicker.adapter.OnLocationListener;
import com.xuexiang.citypicker.adapter.OnPickListener;
import com.xuexiang.citypicker.model.City;
import com.xuexiang.citypicker.model.HotCity;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.button.shadowbutton.ShadowButton;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.xuexiang.xui.widget.edittext.MultiLineEditText;
import com.xuexiang.xui.widget.edittext.ValidatorEditText;
import com.xuexiang.xui.widget.picker.widget.TimePickerView;
import com.xuexiang.xui.widget.picker.widget.builder.TimePickerBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class EditBillRankActivity extends XUIActivity {

    private static final String TAG = "EditBillRankActivity";

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, EditBillRankActivity.class);
        return intent;
    }


    private TitleBar mTitleBar;
    private RecyclerView mRVBills;
    private ShadowButton mBTNewBill;

    private BillsAdapter mAdapter;
    private RankDao mRankDao;
    private BillDao mBillDao;
    List<HotCity> mHotCities;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_bill_rank);

        mHotCities = new ArrayList<>();
        mHotCities.add(new HotCity("北京", "北京", "101010100"));
        mHotCities.add(new HotCity("上海", "上海", "101020100"));
        mHotCities.add(new HotCity("广州", "广东", "101280101"));
        mHotCities.add(new HotCity("深圳", "广东", "101280601"));
        mHotCities.add(new HotCity("杭州", "浙江", "101210101"));

        FreightBookDataBase db = FreightBookDataBase.getInstance(this);
        mRankDao = db.rankDao();
        mBillDao = db.billDao();

        mRVBills = findViewById(R.id.rv_bills);
        mRVBills.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new BillsAdapter();
        mRVBills.setAdapter(mAdapter);

        mTitleBar = findViewById(R.id.title_bar);
        mTitleBar.addAction(new TitleBar.TextAction("保存") {
            @Override
            public void performAction(View view) {
                Observable.create((ObservableOnSubscribe<Long>) e -> {
                    List<Bill> bills = mAdapter.getBills();
                    Rank rank = new Rank();
                    rank.setCreateDate(bills.get(0).getCreateDate());
                    mRankDao.insertRank(rank);
                    for (Bill bill : bills) {
                        bill.setRankId(rank.getRankId());
                    }
                    mBillDao.insertBills(bills);
                    e.onComplete();
                }).subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<Long>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(Long aLong) {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {
                                EditBillRankActivity.this.finish();
                            }
                        });
            }
        });

        mBTNewBill = findViewById(R.id.bt_new_bill);
        mBTNewBill.setOnClickListener(v -> mAdapter.newBill());
    }

    private class BillViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTVGoodName;
        private final TextView mTVDeparture;
        private final TextView mTVDestination;
        private final ImageView mIVSwap;
        private final LinearLayout mLLDate;
        private final TextView mTVDate;
        private final ValidatorEditText mInputPrice;
        private final ValidatorEditText mInputWeight;
        private final ValidatorEditText mInputTotalPrice;
        private final ValidatorEditText mInputOilCard;
        private final ImageView mIVNote;
        private final MultiLineEditText mInputNote;
        private final CheckBox mCBTotalPrice;
        private final CheckBox mCBOilCard;

        private final TextView mTVIndex;
        private final ShadowButton mBTDelete;

        public BillViewHolder(@NonNull View itemView) {
            super(itemView);
            mTVGoodName = itemView.findViewById(R.id.tv_good_name);
            mTVDeparture = itemView.findViewById(R.id.tv_departure);
            mTVDestination = itemView.findViewById(R.id.tv_destination);
            mIVSwap = itemView.findViewById(R.id.iv_swap);
            mLLDate = itemView.findViewById(R.id.ll_date);
            mTVDate = itemView.findViewById(R.id.tv_date);
            mInputPrice = itemView.findViewById(R.id.input_price);
            mInputWeight = itemView.findViewById(R.id.input_weight);
            mInputTotalPrice = itemView.findViewById(R.id.input_total_price);
            mInputOilCard = itemView.findViewById(R.id.input_oil_card);
            mIVNote = itemView.findViewById(R.id.iv_note);
            mInputNote = itemView.findViewById(R.id.input_note);
            mCBTotalPrice = itemView.findViewById(R.id.cb_total_price);
            mCBOilCard = itemView.findViewById(R.id.cb_oil_card);
            mTVIndex = itemView.findViewById(R.id.tv_index);
            mBTDelete = itemView.findViewById(R.id.bt_delete);
        }

        public void bind(Bill bill, int position) {
            mTVGoodName.setText(bill.getGoodName());
            mTVDeparture.setText(bill.getDeparture());
            mTVDestination.setText(bill.getDestination());
            mTVDate.setText(bill.getDateText());
            mInputPrice.setText(String.valueOf(bill.getPrice()));
            mInputWeight.setText(String.valueOf(bill.getWeight()));
            mInputTotalPrice.setText(String.valueOf(bill.getTotalPrice()));
            mInputOilCard.setText(String.valueOf(bill.getOilCard()));
            mInputNote.setContentText(bill.getNotes());
            mTVIndex.setText(String.valueOf(position + 1));
            bindListener(bill);
        }

        private void openCityPicker(OnPickListener listener) {
            CityPicker.from(EditBillRankActivity.this)
                    .setLocatedCity(null)
                    .setHotCities(mHotCities)
                    .setOnPickListener(listener)
                    .show();
        }

        public void bindListener(Bill bill) {
            mBTDelete.setOnClickListener(v -> mAdapter.removeBill(bill));
            mTVGoodName.setOnClickListener(v -> {
                new MaterialDialog.Builder(EditBillRankActivity.this)
                        .content("货物名称")
                        .inputType(InputType.TYPE_CLASS_TEXT)
                        .input(
                                getString(R.string.hint_please_input_good_type),
                                "",
                                false,
                                ((dialog, input) -> Log.i(TAG, input.toString())))
                        .positiveText(R.string.submit)
                        .negativeText(R.string.cancel)
                        .onPositive(((dialog, which) -> {
                            assert dialog.getInputEditText() != null;
                            bill.setGoodName(dialog.getInputEditText().getText().toString());
                            mTVGoodName.setText(bill.getGoodName());
                        }))
                        .cancelable(false)
                        .show();
            });
            mTVDeparture.setOnClickListener(v ->
                    openCityPicker(new OnPickListener() {

                        @Override
                        public void onPick(int position, City data) {
                            bill.setDeparture(data.getName());
                            mTVDeparture.setText(data.getName());
                        }

                        @Override
                        public void onCancel() {

                        }

                        @Override
                        public void onLocate(final OnLocationListener locationListener) {
                            //开始定位
                        }

                    }));
            mTVDestination.setOnClickListener(v ->
                    openCityPicker(new OnPickListener() {

                        @Override
                        public void onPick(int position, City data) {
                            bill.setDestination(data.getName());
                            mTVDestination.setText(data.getName());
                        }

                        @Override
                        public void onCancel() {

                        }

                        @Override
                        public void onLocate(final OnLocationListener locationListener) {
                            //开始定位
                        }

                    })
            );
            mIVSwap.setOnClickListener(v -> {
                String departure = bill.getDeparture();
                bill.setDeparture(bill.getDestination());
                bill.setDestination(departure);
                mTVDeparture.setText(bill.getDeparture());
                mTVDestination.setText(bill.getDestination());
            });
            mLLDate.setOnClickListener(v -> {
                TimePickerView datePicker = new TimePickerBuilder(EditBillRankActivity.this, (date, v1) -> {
                    bill.setCreateDate(date);
                    mTVDate.setText(bill.getDateText());
                })
                        .setTimeSelectChangeListener(date -> {

                        })
                        .setTitleText("日期选择")
                        .build();

                datePicker.show();
            });
            mIVNote.setOnClickListener(v -> mInputNote.setVisibility(mInputNote.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE));
            mInputPrice.setOnFocusChangeListener((v, hasFocus) -> {
                if (!hasFocus) {
                    try {
                        bill.setPrice(Double.parseDouble(Objects.requireNonNull(mInputPrice.getText()).toString()));
                    } catch (NumberFormatException e) {
                        bill.setPrice(0);
                        mInputPrice.setText(String.valueOf(0));
                    }

                    bill.setTotalPrice(bill.getPrice() * bill.getWeight());
                    mInputTotalPrice.setText(String.valueOf(bill.getTotalPrice()));
                }
            });
            mInputWeight.setOnFocusChangeListener((v, hasFocus) -> {
                if (!hasFocus) {
                    try {
                        bill.setWeight(Double.parseDouble(Objects.requireNonNull(mInputWeight.getText()).toString()));
                    } catch (NumberFormatException e) {
                        bill.setWeight(0);
                        mInputWeight.setText(String.valueOf(0));
                    }

                    bill.setTotalPrice(bill.getPrice() * bill.getWeight());
                    mInputTotalPrice.setText(String.valueOf(bill.getTotalPrice()));
                }
            });
            mInputTotalPrice.setOnFocusChangeListener((v, hasFocus) -> {
                if (!hasFocus) {
                    try {
                        bill.setTotalPrice(Double.parseDouble(Objects.requireNonNull(mInputTotalPrice.getText()).toString()));
                    } catch (NumberFormatException e) {
                        bill.setTotalPrice(0);
                        mInputTotalPrice.setText(String.valueOf(0));
                    }

                }
            });
            mInputOilCard.setOnFocusChangeListener((v, hasFocus) -> {
                if (!hasFocus) {
                    try {
                        bill.setOilCard(Double.parseDouble(Objects.requireNonNull(mInputOilCard.getText()).toString()));
                    } catch (NumberFormatException e) {
                        bill.setOilCard(0);
                        mInputOilCard.setText(String.valueOf(0));
                    }

                }
            });
            mInputNote.setOnFocusChangeListener((v, hasFocus) -> {
                if (!hasFocus) {
                    bill.setNotes(mInputNote.getContentText());
                }
            });
            mCBTotalPrice.setOnCheckedChangeListener((buttonView, isChecked) -> bill.setTotalPriceSolve(isChecked));
            mCBOilCard.setOnCheckedChangeListener(((buttonView, isChecked) -> bill.setOilCardSolve(isChecked)));
        }
    }

    private class BillsAdapter extends RecyclerView.Adapter<BillViewHolder> {

        private List<Bill> mBills;

        public BillsAdapter() {
            mBills = new ArrayList<>();
            mBills.add(new Bill());
        }

        @NonNull
        @Override
        public BillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new BillViewHolder(getLayoutInflater().inflate(R.layout.list_item_edit_bill, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull BillViewHolder holder, int position) {
            holder.bind(mBills.get(position), position);
        }

        @Override
        public int getItemCount() {
            return mBills.size();
        }

        public List<Bill> getBills() {
            return mBills;
        }

        public void newBill() {
            mBills.add(new Bill());
            this.notifyDataSetChanged();
        }

        public void removeBill(Bill bill) {
            mBills.remove(bill);
            this.notifyDataSetChanged();
        }
    }
}
