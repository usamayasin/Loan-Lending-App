package org.jethro.mobile.injection.component;

import org.jethro.mobile.injection.PerActivity;
import org.jethro.mobile.injection.module.ActivityModule;
import org.jethro.mobile.ui.activities.HomeActivity;
import org.jethro.mobile.ui.activities.SplashActivity;
import org.jethro.mobile.ui.fragments.AccountOverviewFragment;
import org.jethro.mobile.ui.fragments.AddGuarantorFragment;
import org.jethro.mobile.ui.fragments.BeneficiaryAddOptionsFragment;
import org.jethro.mobile.ui.activities.PassCodeActivity;
import org.jethro.mobile.ui.fragments.BeneficiaryApplicationFragment;
import org.jethro.mobile.ui.fragments.BeneficiaryDetailFragment;
import org.jethro.mobile.ui.fragments.BeneficiaryListFragment;
import org.jethro.mobile.ui.fragments.GuarantorDetailFragment;
import org.jethro.mobile.ui.fragments.GuarantorListFragment;
import org.jethro.mobile.ui.fragments.HelpFragment;
import org.jethro.mobile.ui.fragments.HomeOldFragment;
import org.jethro.mobile.ui.fragments.LoanAccountTransactionFragment;
import org.jethro.mobile.ui.fragments.LoanAccountWithdrawFragment;
import org.jethro.mobile.ui.fragments.LoanAccountsDetailFragment;
import org.jethro.mobile.ui.activities.LoginActivity;
import org.jethro.mobile.ui.fragments.LoanApplicationFragment;
import org.jethro.mobile.ui.fragments.NotificationFragment;
import org.jethro.mobile.ui.fragments.QrCodeImportFragment;
import org.jethro.mobile.ui.fragments.RegistrationFragment;
import org.jethro.mobile.ui.fragments.RegistrationVerificationFragment;
import org.jethro.mobile.ui.fragments.ReviewLoanApplicationFragment;
import org.jethro.mobile.ui.fragments.SavingAccountsDetailFragment;
import org.jethro.mobile.ui.fragments.AccountsFragment;
import org.jethro.mobile.ui.fragments.HomeFragment;
import org.jethro.mobile.ui.fragments.ClientAccountsFragment;
import org.jethro.mobile.ui.fragments.ClientChargeFragment;
import org.jethro.mobile.ui.fragments.LoanAccountSummaryFragment;
import org.jethro.mobile.ui.fragments.LoanRepaymentScheduleFragment;
import org.jethro.mobile.ui.fragments.RecentTransactionsFragment;
import org.jethro.mobile.ui.fragments.SavingAccountsTransactionFragment;
import org.jethro.mobile.ui.fragments.SavingsAccountApplicationFragment;
import org.jethro.mobile.ui.fragments.SavingsAccountWithdrawFragment;
import org.jethro.mobile.ui.fragments.SavingsMakeTransferFragment;
import org.jethro.mobile.ui.fragments.ThirdPartyTransferFragment;
import org.jethro.mobile.ui.fragments.TransferProcessFragment;
import org.jethro.mobile.ui.fragments.UpdatePasswordFragment;
import org.jethro.mobile.ui.fragments.UserProfileFragment;

import dagger.Component;

/**
 * @author ishan
 * @since 08/07/16
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(LoginActivity loginActivity);

    void inject(HomeActivity homeActivity);

    void inject(PassCodeActivity passCodeActivity);

    void inject(HomeFragment homeFragment);

    void inject(ClientAccountsFragment clientAccountsFragment);

    void inject(RecentTransactionsFragment recentTransactionsFragment);

    void inject(ClientChargeFragment clientChargeFragment);

    void inject(SavingAccountsDetailFragment savingAccountsDetailActivity);

    void inject(LoanAccountsDetailFragment loanAccountsDetailActivity);

    void inject(AccountsFragment accountsFragment);

    void inject(LoanAccountSummaryFragment loanAccountSummaryFragment);

    void inject(LoanAccountTransactionFragment loanAccountTransactionFragment);

    void inject(LoanRepaymentScheduleFragment loanRepaymentScheduleFragment);

    void inject(LoanApplicationFragment loanApplicationFragment);

    void inject(LoanAccountWithdrawFragment loanAccountWithdrawFragment);

    void inject(SavingAccountsTransactionFragment savingAccountsTransactionFragment);

    void inject(SavingsMakeTransferFragment savingsMakeTransferFragment);

    void inject(BeneficiaryAddOptionsFragment beneficiaryAddOptionsFragment);

    void inject(BeneficiaryListFragment beneficiaryListFragment);

    void inject(BeneficiaryApplicationFragment beneficiaryApplicationFragment);

    void inject(BeneficiaryDetailFragment beneficiaryDetailFragment);

    void inject(ThirdPartyTransferFragment thirdPartyTransferFragment);

    void inject(TransferProcessFragment transferProcessFragment);

    void inject(UserProfileFragment userProfileFragment);

    void inject(HelpFragment helpFragment);

    void inject(RegistrationFragment registrationFragment);

    void inject(RegistrationVerificationFragment registrationVerificationFragment);

    void inject(AccountOverviewFragment accountOverviewFragment);

    void inject(HomeOldFragment homeOldFragment);

    void inject(NotificationFragment notificationFragment);
    
    void inject(QrCodeImportFragment qrCodeImportFragment);

    void inject(SplashActivity splashActivity);

    void inject(AddGuarantorFragment addGuarantorFragment);

    void inject(GuarantorListFragment guarantorListFragment);

    void inject(GuarantorDetailFragment guarantorDetailFragment);

    void inject(UpdatePasswordFragment updatePasswordFragment);

    void inject(SavingsAccountApplicationFragment savingsAccountApplicationFragment);

    void inject(SavingsAccountWithdrawFragment savingsAccountWithdrawFragment);

    void inject(ReviewLoanApplicationFragment reviewLoanApplicationFragment);
}
