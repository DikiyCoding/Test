package com.example.test.utils

import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/* IO */
fun <T> Observable<T>.subscribeObservableOnIoObserveOnUi(): Observable<T> =
    subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

fun <T> Flowable<T>.subscribeFlowableOnIoObserveOnUi(): Flowable<T> =
    subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

fun <T> Single<T>.subscribeSingleOnIoObserveOnUi(): Single<T> =
    subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

fun Completable.subscribeCompletableOnIoObserveOnUi(): Completable =
    subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

fun <T> Maybe<T>.subscribeMaybeOnIoObserveOnUi(): Maybe<T> =
    subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

/* Compose with Schedulers */
fun <T> Observable<T>.async(background: Scheduler, observe: Scheduler): Observable<T> =
    subscribeOn(background).observeOn(observe)

fun <T> Single<T>.observeOnIo(): Single<T> = observeOn(Schedulers.io())

fun <T> Single<T>.subscribeOnIo(): Single<T> = subscribeOn(Schedulers.io())
