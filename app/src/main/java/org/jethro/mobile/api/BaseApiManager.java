package org.jethro.mobile.api;

import org.jethro.mobile.api.local.PreferencesHelper;
import org.jethro.mobile.api.services.AuthenticationService;
import org.jethro.mobile.api.services.BeneficiaryService;
import org.jethro.mobile.api.services.ClientChargeService;
import org.jethro.mobile.api.services.ClientService;
import org.jethro.mobile.api.services.GuarantorService;
import org.jethro.mobile.api.services.LoanAccountsListService;
import org.jethro.mobile.api.services.NotificationService;
import org.jethro.mobile.api.services.RecentTransactionsService;
import org.jethro.mobile.api.services.RegistrationService;
import org.jethro.mobile.api.services.SavingAccountsListService;
import org.jethro.mobile.api.services.ThirdPartyTransferService;
import org.jethro.mobile.api.services.UserDetailsService;

import javax.inject.Inject;

import okhttp3.Credentials;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Vishwajeet
 * @since 13/6/16
 */
public class BaseApiManager {

    private static Retrofit retrofit;
    private static Retrofit signUpretrofit;
    private static Retrofit beneficiaryRetrofit;
    private static AuthenticationService authenticationApi;
    private static ClientService clientsApi;
    private static SavingAccountsListService savingAccountsListApi;
    private static LoanAccountsListService loanAccountsListApi;
    private static RecentTransactionsService recentTransactionsApi;
    private static ClientChargeService clientChargeApi;
    private static BeneficiaryService beneficiaryApi;
    private static ThirdPartyTransferService thirdPartyTransferApi;
    private static RegistrationService registrationApi;
    private static NotificationService notificationApi;
    private static GuarantorService guarantorService;
    private static UserDetailsService userDetailsService;

    @Inject
    public BaseApiManager(PreferencesHelper preferencesHelper) {
        createService(preferencesHelper.getBaseUrl(), preferencesHelper.getTenant(),
                preferencesHelper.getToken());

        String signUpauthenticationToken = Credentials.basic("genuser", "R3s0lut#657");
        createSignUpService("https://ahead-dev.com/fineract-provider/api/v1",
                SelfServiceInterceptor.DEFAULT_TENANT,signUpauthenticationToken);

        String authenticationToken = Credentials.basic(preferencesHelper.getUserName().toString(),
                preferencesHelper.getPassword().toString());
        createBeneficiaryService(preferencesHelper.getBaseUrl(),
                SelfServiceInterceptor.DEFAULT_TENANT, authenticationToken);

    }

    private static void init() {
        authenticationApi = createApi(AuthenticationService.class);
        clientsApi = createApi(ClientService.class);
        savingAccountsListApi = createApi(SavingAccountsListService.class);
        loanAccountsListApi = createApi(LoanAccountsListService.class);
        recentTransactionsApi = createApi(RecentTransactionsService.class);
        clientChargeApi = createApi(ClientChargeService.class);
        thirdPartyTransferApi = createApi(ThirdPartyTransferService.class);
        notificationApi = createApi(NotificationService.class);
        guarantorService = createApi(GuarantorService.class);
        userDetailsService = createApi(UserDetailsService.class);
    }

    private static <T> T createApi(Class<T> clazz) {
        return retrofit.create(clazz);
    }

    private static <T> T createSignUpApi(Class<T> clazz) {
        return signUpretrofit.create(clazz);
    }

    public static void createSignUpService(String endpoint, String tenant, String authToken) {
       signUpretrofit = new Retrofit.Builder()
                    .baseUrl(new BaseURL().getUrl(endpoint))
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(new SelfServiceOkHttpClient(tenant, authToken).getMifosOkHttpClient())
                    .build();
        registrationApi = createSignUpApi(RegistrationService.class);
    }

    public static void createService(String endpoint, String tenant, String authToken) {
        retrofit = new Retrofit.Builder()
                .baseUrl(new BaseURL().getUrl(endpoint))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(new SelfServiceOkHttpClient(tenant, authToken).getMifosOkHttpClient())
                .build();
        init();
    }


    private static <T> T createBeneficiaryApi(Class<T> clazz) {
        return beneficiaryRetrofit.create(clazz);
    }

    public static void createBeneficiaryService(String endpoint, String tenant, String authToken) {
        beneficiaryRetrofit = new Retrofit.Builder()
                .baseUrl(new BaseURL().getUrl(endpoint))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(new SelfServiceOkHttpClient(tenant, authToken).getMifosOkHttpClient())
                .build();
        beneficiaryApi = createBeneficiaryApi(BeneficiaryService.class);
    }

    public AuthenticationService getAuthenticationApi() {
        return authenticationApi;
    }

    public ClientService getClientsApi() {
        return clientsApi;
    }

    public SavingAccountsListService getSavingAccountsListApi() {
        return savingAccountsListApi;
    }

    public LoanAccountsListService getLoanAccountsListApi() {
        return loanAccountsListApi;
    }

    public RecentTransactionsService getRecentTransactionsApi() {
        return recentTransactionsApi;
    }

    public ClientChargeService getClientChargeApi() {
        return clientChargeApi;
    }

    public BeneficiaryService getBeneficiaryApi() {
        return beneficiaryApi;
    }

    public ThirdPartyTransferService getThirdPartyTransferApi() {
        return thirdPartyTransferApi;
    }

    public RegistrationService getRegistrationApi() {
        return registrationApi;
    }

    public NotificationService getNotificationApi() {
        return notificationApi;
    }

    public GuarantorService getGuarantorApi() {
        return guarantorService;
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }
}
