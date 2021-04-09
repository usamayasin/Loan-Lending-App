package org.jethro.mobile.ui.fragments;

import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import org.jethro.mobile.R;
import org.jethro.mobile.models.payload.ThirdPartyTransferPayload;
import org.jethro.mobile.models.payload.TransferPayload;
import org.jethro.mobile.presenters.TransferProcessPresenter;
import org.jethro.mobile.ui.activities.SavingsAccountContainerActivity;
import org.jethro.mobile.ui.activities.base.BaseActivity;
import org.jethro.mobile.ui.enums.TransferType;
import org.jethro.mobile.ui.fragments.base.BaseFragment;
import org.jethro.mobile.ui.views.TransferProcessView;
import org.jethro.mobile.utils.Constants;
import org.jethro.mobile.utils.CurrencyUtil;
import org.jethro.mobile.utils.Network;
import org.jethro.mobile.utils.Toaster;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by dilpreet on 1/7/17.
 */

public class TransferProcessFragment extends BaseFragment implements TransferProcessView {

    @BindView(R.id.tv_amount)
    TextView tvAmount;

    @BindView(R.id.tv_pay_to)
    TextView tvPayTo;

    @BindView(R.id.tv_pay_from)
    TextView tvPayFrom;

    @BindView(R.id.tv_date)
    TextView tvDate;

    @BindView(R.id.tv_remark)
    TextView tvRemark;

    @BindView(R.id.iv_success)
    ImageView ivSuccess;

    @BindView(R.id.ll_transfer)
    LinearLayout llTransfer;

    @BindView(R.id.btn_close)
    AppCompatButton btnClose;

    @Inject
    TransferProcessPresenter presenter;

    private View rootView;
    private TransferPayload payload;
    private ThirdPartyTransferPayload thirdPartyTransferPayload;
    private TransferType transferType;

    /**
     * Used for TPT Transfer and own Account Transfer.<br>
     * Use {@code type} as TransferType.TPT for TPT and TransferType.SELF for self Account Transfer
     *
     * @param payload Transfer Information
     * @param type    enum of {@link TransferType}
     * @return Instance of {@link TransferProcessFragment}
     */
    public static TransferProcessFragment newInstance(Object payload, TransferType type) {
        TransferProcessFragment fragment = new TransferProcessFragment();
        Bundle args = new Bundle();
        if(type ==  TransferType.SELF){
            args.putParcelable(Constants.PAYLOAD, (TransferPayload) payload);
        } else {
            args.putParcelable(Constants.PAYLOAD, (ThirdPartyTransferPayload) payload);
        }
        args.putSerializable(Constants.TRANSFER_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() != null) {
            if((TransferType) getArguments().getSerializable(Constants.TRANSFER_TYPE) == TransferType.SELF){
                payload = getArguments().getParcelable(Constants.PAYLOAD);
            }else {
                thirdPartyTransferPayload = getArguments().getParcelable(Constants.PAYLOAD);
            }
            transferType = (TransferType) getArguments().getSerializable(Constants.TRANSFER_TYPE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_transfer_process, container, false);
        ((BaseActivity) getActivity()).getActivityComponent().inject(this);
        setToolbarTitle(getString(R.string.transfer));

        ButterKnife.bind(this, rootView);
        presenter.attachView(this);

        if((TransferType) getArguments().getSerializable(Constants.TRANSFER_TYPE) == TransferType.SELF){
            tvAmount.setText(CurrencyUtil.formatCurrency(getActivity(), payload.getTransferAmount()));
            tvPayFrom.setText(String.valueOf(payload.getFromAccountNumber()));
            tvPayTo.setText(String.valueOf(payload.getToAccountNumber()));
            tvDate.setText(payload.getTransferDate());
            tvRemark.setText(payload.getTransferDescription());
        }else {
            tvAmount.setText(thirdPartyTransferPayload.getTransferAmount());
            tvPayFrom.setText(String.valueOf(thirdPartyTransferPayload.getFromAccountNumber()));
            tvPayTo.setText(String.valueOf(thirdPartyTransferPayload.getToAccountNumber()));
            tvDate.setText(thirdPartyTransferPayload.getTransferDate());
            tvRemark.setText(thirdPartyTransferPayload.getTransferDescription());
        }

        return rootView;
    }

    /**
     * Initiates a transfer depending upon {@code transferType}
     */
    @OnClick(R.id.btn_start_transfer)
    public void startTransfer() {
        if (!Network.isConnected(getActivity())) {
            BaseActivity.showAlertDialogForError(getContext(),  getString(R.string.internet_not_connected));
            return;
        }
        if (transferType == TransferType.SELF) {
            presenter.makeSavingsTransfer(payload);
        } else if (transferType == TransferType.TPT) {
            presenter.makeTPTTransfer(thirdPartyTransferPayload);
        }
    }

    /**
     * Cancels the Transfer and pops fragment
     */
    @OnClick(R.id.btn_cancel_transfer)
    public void cancelTransfer() {
        new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Alert")
                .setContentText(getString(R.string.cancel_transfer))
                .setConfirmText("Yes")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        getActivity().getSupportFragmentManager().popBackStack();
                        getActivity().getSupportFragmentManager().popBackStack();
                        sDialog.dismissWithAnimation();
                        ((BaseActivity) getActivity()).replaceFragment(ThirdPartyTransferFragment.newInstance(), true, R.id.container);
                    }
                })
                .setCancelText("No")
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                    }
                })
                .show();

    }

    /**
     * Closes the transfer fragment
     */
    @OnClick(R.id.btn_close)
    public void closeClicked() {
        getActivity().getSupportFragmentManager().popBackStack();
        getActivity().getSupportFragmentManager().popBackStack();
    }

    /**
     * Shows a {@link Snackbar} on succesfull transfer of money
     */
    @Override
    public void showTransferredSuccessfully() {
        new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Alert")
                .setContentText(getString(R.string.transferred_successfully))
                .setConfirmText("Ok")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        getActivity().getSupportFragmentManager().popBackStack();
                        sDialog.dismissWithAnimation();
                    }
                })
                .show();
        ivSuccess.setVisibility(View.VISIBLE);
        ((Animatable) ivSuccess.getDrawable()).start();
        btnClose.setVisibility(View.VISIBLE);
        llTransfer.setVisibility(View.GONE);
        SavingsAccountContainerActivity.transferSuccess = true;
    }

    /**
     * It is called whenever any error occurs while executing a request
     *
     * @param msg Error message that tells the user about the problem.
     */
    @Override
    public void showError(String msg) {
        BaseActivity.showAlertDialogForError(getContext(), msg);
    }

    @Override
    public void showProgress() {
        showMifosProgressDialog(getString(R.string.please_wait));
    }

    @Override
    public void hideProgress() {
        hideMifosProgressDialog();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }
}
