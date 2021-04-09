package org.jethro.mobile.presenters;

import android.content.Context;

import org.jethro.mobile.R;
import org.jethro.mobile.api.DataManager;
import org.jethro.mobile.injection.ApplicationContext;
import org.jethro.mobile.models.payload.ThirdPartyTransferPayload;
import org.jethro.mobile.models.payload.TransferPayload;
import org.jethro.mobile.presenters.base.BasePresenter;
import org.jethro.mobile.ui.views.TransferProcessView;
import org.jethro.mobile.utils.MFErrorParser;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;


/**
 * Created by dilpreet on 1/7/17.
 */

public class TransferProcessPresenter extends BasePresenter<TransferProcessView> {

    public final DataManager dataManager;
    public CompositeDisposable compositeDisposables;

    /**
     * Initialises the RecentTransactionsPresenter by automatically injecting an instance of
     * {@link DataManager} and {@link Context}.
     *
     * @param dataManager DataManager class that provides access to the data
     *                    via the API.
     * @param context     Context of the view attached to the presenter. In this case
     *                    it is that of an {@link androidx.appcompat.app.AppCompatActivity}
     */
    @Inject
    public TransferProcessPresenter(DataManager dataManager,
            @ApplicationContext Context context) {
        super(context);
        this.dataManager = dataManager;
        compositeDisposables = new CompositeDisposable();
    }

    @Override
    public void attachView(TransferProcessView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        compositeDisposables.clear();
    }

    /**
     * Used for making a Transfer with the help of {@code transferPayload} provided in function
     * parameter. It notifies the view after successful making a Transfer. And in case of any error
     * during transfer, it notifies the view.
     *
     * @param transferPayload Contains details about the Transfer
     */
    public void makeSavingsTransfer(TransferPayload transferPayload) {
        checkViewAttached();
        getMvpView().showProgress();
        compositeDisposables.add(dataManager.makeTransfer(transferPayload)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableObserver<ResponseBody>() {
                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().hideProgress();
                        getMvpView().showError(MFErrorParser.errorMessage(e));
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        getMvpView().hideProgress();
                        getMvpView().showTransferredSuccessfully();
                    }
                })
        );
    }

    /**
     * Used for making a Third Party Transfer with the help of {@code transferPayload} provided in
     * function parameter. It notifies the view after successful making a Third Party Transfer. And
     * in case of any error during transfer, it notifies the view.
     *
     * @param thirdPartyTransferPayload Contains details about the Third Party Transfer
     */
    public void makeTPTTransfer(ThirdPartyTransferPayload thirdPartyTransferPayload) {
        checkViewAttached();
        getMvpView().showProgress();
        compositeDisposables.add(dataManager.makeThirdPartyTransfer(thirdPartyTransferPayload)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableObserver<ResponseBody>() {
                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().hideProgress();
                        getMvpView().showError(context.getString(R.string.transfer_error));
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        getMvpView().hideProgress();
                        getMvpView().showTransferredSuccessfully();
                    }
                })
        );
    }


}
