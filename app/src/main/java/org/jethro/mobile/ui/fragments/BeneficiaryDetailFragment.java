package org.jethro.mobile.ui.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import org.jethro.mobile.R;
import org.jethro.mobile.models.beneficiary.Beneficiary;
import org.jethro.mobile.presenters.BeneficiaryDetailPresenter;
import org.jethro.mobile.ui.activities.base.BaseActivity;
import org.jethro.mobile.ui.enums.BeneficiaryState;
import org.jethro.mobile.ui.fragments.base.BaseFragment;
import org.jethro.mobile.ui.views.BeneficiaryDetailView;
import org.jethro.mobile.utils.Constants;
import org.jethro.mobile.utils.CurrencyUtil;
import org.jethro.mobile.utils.MaterialDialog;
import org.jethro.mobile.utils.Toaster;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dilpreet on 15/6/17.
 */

public class BeneficiaryDetailFragment extends BaseFragment implements BeneficiaryDetailView {

    @BindView(R.id.tv_beneficiary_name)
    TextView tvName;

    @BindView(R.id.tv_account_number)
    TextView tvAccountNumber;

    @BindView(R.id.tv_client_name)
    TextView tvClientName;

    @BindView(R.id.tv_account_type)
    TextView tvAccountType;

    @BindView(R.id.tv_transfer_limit)
    TextView tvTransferLimit;

    @BindView(R.id.tv_office_name)
    TextView tvOfficeName;

    @BindView(R.id.item_update_beneficiary)
    AppCompatButton updateButton;

    @BindView(R.id.item_delete_beneficiary)
    AppCompatButton deleteButton;

    @Inject
    BeneficiaryDetailPresenter presenter;

    private Beneficiary beneficiary;
    private View rootView;

    public static BeneficiaryDetailFragment newInstance(Beneficiary beneficiary) {
        BeneficiaryDetailFragment fragment = new BeneficiaryDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.BENEFICIARY, beneficiary);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            beneficiary = getArguments().getParcelable(Constants.BENEFICIARY);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateButtonClicked(v);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteButtonClicked(v);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_beneficiary_detail, container, false);
        ((BaseActivity) getActivity()).getActivityComponent().inject(this);
        setToolbarTitle(getString(R.string.beneficiary_detail));

        ButterKnife.bind(this, rootView);
        presenter.attachView(this);

        showUserInterface();

        return rootView;
    }

    /**
     * Used for setting up of User Interface
     */
    @Override
    public void showUserInterface() {
        tvName.setText(beneficiary.getName());
        tvAccountNumber.setText(beneficiary.getAccountNumber());
        tvClientName.setText(beneficiary.getClientName());
        tvAccountType.setText(beneficiary.getAccountType().getValue());
        tvTransferLimit.setText(CurrencyUtil.formatCurrency(getActivity(), beneficiary.
                getTransferLimit()));
        tvOfficeName.setText(beneficiary.getOfficeName());
    }


    public void updateButtonClicked(View view){
        ((BaseActivity) getActivity()).replaceFragment(BeneficiaryApplicationFragment.
                newInstance(BeneficiaryState.UPDATE, beneficiary), true, R.id.container);
    }

    public void deleteButtonClicked(View view){
        new MaterialDialog.Builder().init(getActivity())
                .setTitle(getString(R.string.delete_beneficiary))
                .setMessage(getString(R.string.delete_beneficiary_confirmation))
                .setPositiveButton(getString(R.string.delete),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                presenter.deleteBeneficiary(beneficiary.getId());
                            }
                        })
                .setNegativeButton(getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                .createMaterialDialog()
                .show();
    }

    /**
     * Shows a {@link Snackbar} on successfull deletion of a
     * Beneficiary and then pops current fragment
     */
    @Override
    public void showBeneficiaryDeletedSuccessfully() {
        Toaster.show(rootView, getString(R.string.beneficiary_deleted_successfully));
        getActivity().getSupportFragmentManager().popBackStack();
    }

    /**
     * It is called whenever any error occurs while executing a request
     *
     * @param msg Error message that tells the user about the problem.
     */
    @Override
    public void showError(String msg) {
        Toaster.show(rootView, msg);
    }

    /**
     * Shows {@link org.jethro.mobile.utils.ProgressBarHandler}
     */
    @Override
    public void showProgress() {
        showProgressBar();
    }

    /**
     * Hides {@link org.jethro.mobile.utils.ProgressBarHandler}
     */
    @Override
    public void hideProgress() {
        hideProgressBar();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        hideProgress();
        presenter.detachView();
    }
}
