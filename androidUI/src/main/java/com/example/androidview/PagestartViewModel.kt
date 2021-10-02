package com.example.androidview

import android.annotation.SuppressLint
import android.os.Looper
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.ViewModel
import com.di.DaggerAppComp
import entities.CarResponse
import interfaces.NetworkInterface
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class PagestartViewModel : ViewModel() {

    companion object {
        var dagger = DaggerAppComp.create()
        val service: NetworkInterface = dagger.getNetwwork()
        var data: Observable<CarResponse?>? = service.GetMakeForManufacturer("a")
        var bs: BehaviorSubject<CarResponse?> = BehaviorSubject.create<CarResponse?>();
        var loader: BehaviorSubject<Boolean?> = BehaviorSubject.create<Boolean?>();

        @SuppressLint("CheckResult")
        fun call(q: String): BehaviorSubject<CarResponse?>? {
            Log.e("Pageviewmodel",Thread.currentThread().name)
            loader.onNext(true)
            service.GetMakeForManufacturer(q)
                .subscribe { s ->
                    bs.onNext(s!!);
                    loader.onNext(false);
                    Log.e("Pageviewmodel", Thread.currentThread().name)
                }
            return bs
        }

        fun result(o: CarResponse?){
            Log.e("Pageviewmodel inresult", Thread.currentThread().name)
            bs.onNext(o!!)
        }
    }


}


