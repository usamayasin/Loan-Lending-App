package org.jethro.mobile.api.services;

import org.jethro.mobile.api.ApiEndPoints;
import org.jethro.mobile.models.Charge;
import org.jethro.mobile.models.Page;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author Vishwajeet
 * @since 17/8/16.
 */
public interface ClientChargeService {
    @GET(ApiEndPoints.CLIENTS + "/{clientId}/charges")
    Observable<Page<Charge>> getClientChargeList(@Path("clientId") long clientId);

    @GET(ApiEndPoints.LOANS + "/{loanId}/charges")
    Observable<List<Charge>> getLoanAccountChargeList(@Path("loanId") long loanId);

    @GET(ApiEndPoints.SAVINGS_ACCOUNTS + "/{savingsId}/charges")
    Observable<List<Charge>> getSavingsAccountChargeList(@Path("savingsId") long savingsId);
}
