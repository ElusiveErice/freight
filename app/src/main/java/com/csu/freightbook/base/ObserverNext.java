package com.csu.freightbook.base;


import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class ObserverNext<T> implements Observer<T> {

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
