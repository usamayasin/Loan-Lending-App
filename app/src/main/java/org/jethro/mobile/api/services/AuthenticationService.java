package org.jethro.mobile.api.services;

import org.jethro.mobile.api.ApiEndPoints;
import org.jethro.mobile.models.User;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author Vishwajeet
 * @since 09/06/16
 */

public interface AuthenticationService {

    @POST(ApiEndPoints.AUTHENTICATION)
    Observable<User> authenticate(@Query("username") String username,
                                  @Query("password") String password);
}
