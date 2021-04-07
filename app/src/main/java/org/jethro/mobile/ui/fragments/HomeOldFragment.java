package org.jethro.mobile.ui.fragments;

import android.animation.LayoutTransition;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.jethro.mobile.R;
import org.jethro.mobile.api.local.PreferencesHelper;
import org.jethro.mobile.models.accounts.loan.LoanAccount;
import org.jethro.mobile.models.accounts.savings.SavingAccount;
import org.jethro.mobile.models.accounts.savings.SavingsWithAssociations;
import org.jethro.mobile.models.accounts.savings.Transactions;
import org.jethro.mobile.models.accounts.share.ShareAccount;
import org.jethro.mobile.models.client.Client;
import org.jethro.mobile.presenters.AccountsPresenter;
import org.jethro.mobile.presenters.HomeOldPresenter;
import org.jethro.mobile.presenters.SavingAccountsTransactionPresenter;
import org.jethro.mobile.ui.activities.HomeActivity;
import org.jethro.mobile.ui.activities.LoanApplicationActivity;
import org.jethro.mobile.ui.activities.NotificationActivity;
import org.jethro.mobile.ui.activities.UserProfileActivity;
import org.jethro.mobile.ui.activities.base.BaseActivity;
import org.jethro.mobile.ui.adapters.HomeLoanListAdapter;
import org.jethro.mobile.ui.adapters.HomeShareListAdapter;
import org.jethro.mobile.ui.enums.AccountType;
import org.jethro.mobile.ui.enums.ChargeType;
import org.jethro.mobile.ui.fragments.base.BaseFragment;
import org.jethro.mobile.ui.views.AccountsView;
import org.jethro.mobile.ui.views.HomeOldView;
import org.jethro.mobile.ui.views.SavingAccountsTransactionView;
import org.jethro.mobile.utils.CircularImageView;
import org.jethro.mobile.utils.ComparatorBasedOnId;
import org.jethro.mobile.utils.Constants;
import org.jethro.mobile.utils.CurrencyUtil;
import org.jethro.mobile.utils.DateHelper;
import org.jethro.mobile.utils.MaterialDialog;
import org.jethro.mobile.utils.TextDrawable;
import org.jethro.mobile.utils.Toaster;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by michaelsosnick on 1/1/17.
 */

public class HomeOldFragment extends BaseFragment implements HomeOldView, AccountsView, SavingAccountsTransactionView,
        SwipeRefreshLayout.OnRefreshListener {

    public static final String LOG_TAG = HomeFragment.class.getSimpleName();

    @BindView(R.id.tv_saving_total_amount)
    TextView tvSavingTotalAmount;

    @BindView(R.id.tv_loan_total_amount)
    TextView tvLoanTotalAmount;

    @BindView(R.id.ll_account_detail)
    LinearLayout llAccountDetail;

    @BindView(R.id.iv_visibility)
    ImageView ivVisibility;

    @BindView(R.id.iv_user_image)
    ImageView ivUserImage;

    @BindView(R.id.iv_circular_user_image)
    CircularImageView ivCircularUserImage;

    @BindView(R.id.tv_user_name)
    TextView tvUserName;

    @BindView(R.id.swipe_home_container)
    SwipeRefreshLayout slHomeContainer;

    @BindView(R.id.ll_container)
    LinearLayout llContainer;

    @BindView(R.id.tv_homeAccountTab)
    TextView tv_homeAccountTab;

    @BindView(R.id.tv_homeAccountType)
    TextView tv_homeAccountType;

    @BindView(R.id.tv_homeAccountAmount)
    TextView tv_homeAccountAmount;

    @BindView(R.id.tv_homeAccountViewAll)
    TextView tv_homeAccountViewAll;

    @Inject
    SavingAccountsTransactionPresenter savingAccountsTransactionPresenter;


    @BindView(R.id.tv_homeLoanTab)
    TextView tv_homeLoanTab;

    @BindView(R.id.tv_homeShareTab)
    TextView tv_homeShareTab;

    @BindView(R.id.ll_homeAccountLayout)
    LinearLayout ll_homeAccountLayout;

    @BindView(R.id.ll_homeLoanLayout)
    LinearLayout ll_homeLoanLayout;

    @BindView(R.id.ll_homeShareLayout)
    LinearLayout ll_homeShareLayout;

    @BindView(R.id.rv_homeShare)
    RecyclerView rv_homeShare;

    @BindView(R.id.rv_homeLoan)
    RecyclerView rv_homeLoan;

    @BindView(R.id.spinner_accounts)
    Spinner spinner_accounts;

    @BindView(R.id.tv_homeAccountLatestTransactionNumber)
    TextView tv_homeAccountLatestTransactionNumber;

    @BindView(R.id.tv_homeAccountLatestTransactionTime)
    TextView tv_homeAccountLatestTransactionTime;
    @BindView(R.id.tv_homeAccountLatestTransactionType)
    TextView tv_homeAccountLatestTransactionType;
    @BindView(R.id.tv_homeAccountLatestTransactionAmount)
    TextView tv_homeAccountLatestTransactionAmount;


    @Inject
    HomeOldPresenter presenter;

    @Inject
    AccountsPresenter accountsPresenter;

    @Inject
    PreferencesHelper preferencesHelper;

    View rootView;
    private double totalLoanAmount, totalSavingAmount;
    private Client client;
    private long clientId;
    private View toolbarView;
    private boolean isDetailVisible;
    private boolean isReceiverRegistered = false;
    private TextView tvNotificationCount;
    private ArrayList spinnerList = new ArrayList<String>();
    private List<SavingAccount> savingAccounts;
    private Long selectedAccountID = 0L;
    private List<Transactions> transactionsList;

    public static HomeOldFragment newInstance() {
        HomeOldFragment fragment = new HomeOldFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home_old, container, false);
        ((HomeActivity) getActivity()).getActivityComponent().inject(this);
        ButterKnife.bind(this, rootView);
        clientId = preferencesHelper.getClientId();

        presenter.attachView(this);
        setHasOptionsMenu(true);

        accountsPresenter.attachView(this);
        savingAccountsTransactionPresenter.attachView(this);

        slHomeContainer.setColorSchemeResources(R.color.blue_light, R.color.green_light, R
                .color.orange_light, R.color.red_light);
        slHomeContainer.setOnRefreshListener(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            llContainer.getLayoutTransition()
                    .enableTransitionType(LayoutTransition.CHANGING);
        }
        if (savedInstanceState == null) {
            loadClientData();
            accountsPresenter.loadAccounts(Constants.SAVINGS_ACCOUNTS);
        }
        spinnerList.clear();
        spinner_accounts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setAccountUIOnSpinnerValueChange(spinner_accounts.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        setToolbarTitle(getString(R.string.home));
        showUserInterface();
        return rootView;
    }

    private void setAccountUIOnSpinnerValueChange(String accountNo) {
        SavingAccount selectedSavingAccount = null;
        for (int index = 0; index < savingAccounts.size(); index++) {
            if (savingAccounts.get(index).getAccountNo().equals(accountNo)) {
                selectedSavingAccount = savingAccounts.get(index);
                selectedAccountID = selectedSavingAccount.getId();
                break;
            }
        }
        tv_homeAccountAmount.setText(String.valueOf(selectedSavingAccount.getAccountBalance()));
        tv_homeAccountType.setText(selectedSavingAccount.getProductName());
        savingAccountsTransactionPresenter.loadSavingsWithAssociations(selectedSavingAccount.getId());

    }

    @OnClick(R.id.tv_homeAccountViewAll)
    public void onClickViewAll() {
        if (selectedAccountID != 0) {
            ((BaseActivity) getActivity()).replaceFragment(SavingAccountsTransactionFragment.
                    newInstance(selectedAccountID), true, R.id.container);
            selectedAccountID = 0L;
        }
    }

    private BroadcastReceiver notificationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            getActivity().invalidateOptionsMenu();
        }
    };

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.menu_notifications);
        View count = menuItem.getActionView();
        tvNotificationCount = count.findViewById(R.id.tv_notification_indicator);
        presenter.getUnreadNotificationsCount();
        count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getContext(), NotificationActivity.class));
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver();
        getActivity().invalidateOptionsMenu();
    }

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(notificationReceiver);
        isReceiverRegistered = false;
        super.onPause();
    }

    private void registerReceiver() {
        if (!isReceiverRegistered) {
            LocalBroadcastManager.getInstance(getActivity()).registerReceiver(notificationReceiver,
                    new IntentFilter(Constants.NOTIFY_HOME_FRAGMENT));
            isReceiverRegistered = true;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putDouble(Constants.TOTAL_LOAN, totalLoanAmount);
        outState.putDouble(Constants.TOTAL_SAVINGS, totalSavingAmount);
        outState.putParcelable(Constants.USER_DETAILS, client);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            showUserDetails((Client) savedInstanceState.getParcelable(Constants.USER_DETAILS));
            presenter.setUserProfile(preferencesHelper.getUserProfileImage());
            showLoanAccountDetails(savedInstanceState.getDouble(Constants.TOTAL_LOAN));
            showSavingAccountDetails(savedInstanceState.getDouble(Constants.TOTAL_SAVINGS));
        }
    }

    @Override
    public void onRefresh() {
        loadClientData();
    }

    private void loadClientData() {
        presenter.loadClientAccountDetails();
        presenter.getUserDetails();
        presenter.getUserImage();
    }

    @Override
    public void showUserInterface() {
        toolbarView = ((HomeActivity) getActivity()).getToolbar().getRootView();
        isDetailVisible = preferencesHelper.overviewState();
        if (isDetailVisible) {
            showOverviewState();
        } else {
            hideOverviewState();
        }
    }

    @Override
    public void showSavingAccountsDetail(SavingsWithAssociations savingsWithAssociations) {
        transactionsList = savingsWithAssociations.getTransactions();
        if (transactionsList.size() > 0) {
            tv_homeAccountLatestTransactionAmount.setText(transactionsList.get(0).getAmount().toString());
            tv_homeAccountLatestTransactionType.setText(transactionsList.get(0).getTransactionType().getValue());
            tv_homeAccountLatestTransactionTime.setText(DateHelper.
                    getDateAsString(transactionsList.get(0).getDate()));
            tv_homeAccountLatestTransactionNumber.setText(String.valueOf(transactionsList.get(0).getAccountNo()));

        } else {
            tv_homeAccountLatestTransactionAmount.setText("");
            tv_homeAccountLatestTransactionType.setText("No Recent Transaction Found");
            tv_homeAccountLatestTransactionTime.setText("");
            tv_homeAccountLatestTransactionNumber.setText("");
            selectedAccountID = 0L;
        }

    }

    @Override
    public void showErrorFetchingSavingAccountsDetail(String message) {

    }

    @Override
    public void showFilteredList(List<Transactions> list) {

    }

    @Override
    public void showEmptyTransactions() {

    }

    /**
     * Opens {@link ClientAccountsFragment} according to the {@code accountType} provided
     *
     * @param accountType Enum of {@link AccountType}
     */
    public void openAccount(AccountType accountType) {
        ((BaseActivity) getActivity()).replaceFragment(
                ClientAccountsFragment.newInstance(accountType), true, R.id.container);
    }

    /**
     * Provides {@code totalLoanAmount} fetched from server
     *
     * @param totalLoanAmount Total Loan amount
     */
    @Override
    public void showLoanAccountDetails(double totalLoanAmount) {
        this.totalLoanAmount = totalLoanAmount;
        tvLoanTotalAmount.setText(CurrencyUtil.formatCurrency(getContext(), totalLoanAmount));
    }

    /**
     * Open LOAN tab under ClientAccountsFragment
     */
    @OnClick(R.id.ll_total_loan)
    public void onClickLoan() {
        openAccount(AccountType.LOAN);
        ((HomeActivity) getActivity()).setNavigationViewSelectedItem(R.id.item_accounts);
    }

    /**
     * Provides {@code totalSavingAmount} fetched from server
     *
     * @param totalSavingAmount Total Saving amount
     */
    @Override
    public void showSavingAccountDetails(double totalSavingAmount) {
        this.totalSavingAmount = totalSavingAmount;
        tvSavingTotalAmount.setText(CurrencyUtil.formatCurrency(getContext(), totalSavingAmount));
    }

    /**
     * Open SAVINGS tab under ClientAccountsFragment
     */
    @OnClick(R.id.ll_total_savings)
    public void onClickSavings() {
        openAccount(AccountType.SAVINGS);
        ((HomeActivity) getActivity()).setNavigationViewSelectedItem(R.id.item_accounts);
    }

    /**
     * Fetches Client details and display clientName
     *
     * @param client Details about client
     */
    @Override
    public void showUserDetails(Client client) {
        this.client = client;
        tvUserName.setText(getString(R.string.hello_client, client.getDisplayName()));
    }

    /**
     * Provides with Client image fetched from server
     *
     * @param bitmap Client Image
     */
    @Override
    public void showUserImage(final Bitmap bitmap) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (bitmap != null) {

                    ivUserImage.setVisibility(View.GONE);
                    ivCircularUserImage.setVisibility(View.VISIBLE);
                    ivCircularUserImage.setImageBitmap(bitmap);

                } else {

                    String userName;
                    if (!preferencesHelper.getClientName().isEmpty()) {

                        userName = preferencesHelper.getClientName();
                    } else {

                        userName = getString(R.string.app_name);
                    }
                    TextDrawable drawable = TextDrawable.builder()
                            .beginConfig()
                            .toUpperCase()
                            .endConfig()
                            .buildRound(userName.substring(0, 1),
                                    ContextCompat.getColor(
                                            getContext(), R.color.primary));
                    ivUserImage.setVisibility(View.VISIBLE);
                    ivUserImage.setImageDrawable(drawable);
                    ivCircularUserImage.setVisibility(View.GONE);

                }
            }
        });
    }

    @Override
    public void showNotificationCount(int count) {

        if (count > 0) {
            tvNotificationCount.setVisibility(View.VISIBLE);
            tvNotificationCount.setText(String.valueOf(count));
        } else {
            tvNotificationCount.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.iv_user_image, R.id.iv_circular_user_image})
    public void userImageClicked() {
        startActivity(new Intent(getActivity(), UserProfileActivity.class));
    }

    /**
     * Reverses the state of Account Overview section i.e. visible to hidden or vice a versa
     */
    @OnClick(R.id.iv_visibility)
    public void reverseDetailState() {
        if (isDetailVisible) {
            isDetailVisible = false;
            preferencesHelper.setOverviewState(false);
            hideOverviewState();
        } else {
            isDetailVisible = true;
            preferencesHelper.setOverviewState(true);
            showOverviewState();
        }
    }

    /**
     * Makes Overview state visible
     */
    private void showOverviewState() {
        ivVisibility.setImageDrawable(getResources().getDrawable(R.drawable.ic_visibility_24px));
        ivVisibility.setColorFilter(ContextCompat.getColor(getActivity(), R.color.gray_dark));
        llAccountDetail.setVisibility(View.VISIBLE);
    }

    /**
     * Hides Overview state
     */
    private void hideOverviewState() {
        ivVisibility.setImageDrawable(getResources()
                .getDrawable(R.drawable.ic_visibility_off_24px));
        ivVisibility.setColorFilter(ContextCompat.getColor(getActivity(), R.color.light_grey));
        llAccountDetail.setVisibility(View.GONE);
    }


    /**
     * Calls {@code openAccount()} for opening {@link ClientAccountsFragment}
     */
    @OnClick(R.id.ll_accounts)
    public void accountsClicked() {
        openAccount(AccountType.SAVINGS);
        ((HomeActivity) getActivity()).setNavigationViewSelectedItem(R.id.item_accounts);
    }

    /**
     * Shows a dialog with options: Normal Transfer and Third Party Transfer
     */
    @OnClick(R.id.ll_transfer)
    public void transferClicked() {
        String[] transferTypes = {getString(R.string.transfer), getString(R.string.
                third_party_transfer)};
        new MaterialDialog.Builder().init(getActivity())
                .setTitle(R.string.choose_transfer_type)
                .setItems(transferTypes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            ((HomeActivity) getActivity()).replaceFragment(
                                    SavingsMakeTransferFragment.newInstance(1, ""), true,
                                    R.id.container);
                        } else {
                            ((HomeActivity) getActivity()).replaceFragment(
                                    ThirdPartyTransferFragment.newInstance(), true, R.id.container);
                        }
                    }
                })
                .createMaterialDialog()
                .show();
    }

    /**
     * Opens {@link ClientChargeFragment} to display all Charges associated with client's account
     */
    @OnClick(R.id.ll_charges)
    public void chargesClicked() {
        ((HomeActivity) getActivity()).replaceFragment(ClientChargeFragment.newInstance(clientId,
                ChargeType.CLIENT), true, R.id.container);
    }

    /**
     * Opens {@link LoanApplicationFragment} to apply for a loan
     */
    @OnClick(R.id.ll_apply_for_loan)
    public void applyForLoan() {
        startActivity(new Intent(getActivity(), LoanApplicationActivity.class));
    }

    /**
     * Opens {@link BeneficiaryListFragment} which contains list of Beneficiaries associated with
     * Client's account
     */
    @OnClick(R.id.ll_beneficiaries)
    public void beneficiaries() {
        ((HomeActivity) getActivity()).replaceFragment(BeneficiaryListFragment.
                newInstance(), true, R.id.container);
    }

    @OnClick(R.id.ll_surveys)
    public void surveys() {

    }

    @Override
    public void showLoanAccounts(List<LoanAccount> loanAccounts) {
        if (loanAccounts.size() > 0) {
            Collections.sort(loanAccounts, new ComparatorBasedOnId());
            setLoanRecyclerView(loanAccounts);
        } else {
            Toast.makeText(requireContext(), "No Data Found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showSavingsAccounts(List<SavingAccount> savingAccounts) {
        if (savingAccounts.size() > 0) {
            this.savingAccounts = savingAccounts;
            createAccountList();
        } else {
            Toast.makeText(requireContext(), "No Data Found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showShareAccounts(List<ShareAccount> shareAccounts) {
        if (shareAccounts.size() > 0) {
            Collections.sort(shareAccounts, new ComparatorBasedOnId());
            setShareRecyclerView(shareAccounts);
        } else {
            Toast.makeText(requireContext(), "No Data Found", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * It is called whenever any error occurs while executing a request
     *
     * @param errorMessage Error message that tells the user about the problem.
     */
    @Override
    public void showError(String errorMessage) {
        int checkedItem = ((HomeActivity) getActivity()).getCheckedItem();
        if (checkedItem == R.id.item_about_us || checkedItem == R.id.item_help ||
                checkedItem == R.id.item_settings) {
            return;
        }
        BaseActivity.showAlertDialogForError(getContext(), errorMessage);
    }

    /**
     * Shows {@link SwipeRefreshLayout}
     */
    @Override
    public void showProgress() {
        slHomeContainer.setRefreshing(true);
    }

    /**
     * Hides {@link SwipeRefreshLayout}
     */
    @Override
    public void hideProgress() {
        slHomeContainer.setRefreshing(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (slHomeContainer.isRefreshing()) {
            slHomeContainer.setRefreshing(false);
            slHomeContainer.removeAllViews();
        }
        presenter.detachView();
        accountsPresenter.detachView();
        savingAccountsTransactionPresenter.detachView();
    }

    @OnClick(R.id.tv_homeAccountTab)
    public void selectAccountTab() {
        changeSelectedTabUI(tv_homeAccountTab);
        changeUnselectedTabUI(tv_homeLoanTab);
        changeUnselectedTabUI(tv_homeShareTab);
        showView(ll_homeAccountLayout);
        hideView(ll_homeLoanLayout);
        hideView(ll_homeShareLayout);
        accountsPresenter.loadAccounts(Constants.SAVINGS_ACCOUNTS);
    }

    @OnClick(R.id.tv_homeLoanTab)
    public void selectLoanTab() {
        changeSelectedTabUI(tv_homeLoanTab);
        changeUnselectedTabUI(tv_homeAccountTab);
        changeUnselectedTabUI(tv_homeShareTab);
        hideView(ll_homeAccountLayout);
        showView(ll_homeLoanLayout);
        hideView(ll_homeShareLayout);
        accountsPresenter.loadAccounts(Constants.LOAN_ACCOUNTS);
    }

    @OnClick(R.id.tv_homeShareTab)
    public void selectShareTab() {
        changeSelectedTabUI(tv_homeShareTab);
        changeUnselectedTabUI(tv_homeAccountTab);
        changeUnselectedTabUI(tv_homeLoanTab);
        hideView(ll_homeAccountLayout);
        hideView(ll_homeLoanLayout);
        showView(ll_homeShareLayout);
        accountsPresenter.loadAccounts(Constants.SHARE_ACCOUNTS);

    }

    private void changeSelectedTabUI(TextView textView) {
        textView.setBackgroundResource(R.drawable.rounded_corner_background);
        textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.primary));
        textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
    }

    private void changeUnselectedTabUI(TextView textView) {
        textView.setBackgroundResource(0);
        textView.setTypeface(textView.getTypeface(), Typeface.NORMAL);
        textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.home_views_color));
    }

    private void hideView(LinearLayout view) {
        view.setVisibility(View.GONE);
    }

    private void showView(LinearLayout view) {
        view.setVisibility(View.VISIBLE);
    }

    private void setLoanRecyclerView(List<LoanAccount> loanAccounts) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        rv_homeLoan.setLayoutManager(layoutManager);
        rv_homeLoan.setHasFixedSize(true);
        HomeLoanListAdapter adapter = new HomeLoanListAdapter(loanAccounts);
        rv_homeLoan.setAdapter(adapter);
    }

    private void setShareRecyclerView(List<ShareAccount> shareAccounts) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        rv_homeShare.setLayoutManager(layoutManager);
        rv_homeShare.setHasFixedSize(true);
        HomeShareListAdapter adapter = new HomeShareListAdapter(shareAccounts);
        rv_homeShare.setAdapter(adapter);
    }

    private void createAccountList() {
        spinnerList.clear();
        for (int index = 0; index < savingAccounts.size(); index++) {
            spinnerList.add(savingAccounts.get(index).getAccountNo());
        }
        if (spinnerList.size() > 0) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                    R.layout.home_account_spinner_text_layout, spinnerList);
            spinner_accounts.setAdapter(adapter);
            spinner_accounts.setSelection(0);
        }
    }


}

