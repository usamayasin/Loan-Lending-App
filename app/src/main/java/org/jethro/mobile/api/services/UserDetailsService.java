package org.jethro.mobile.api.services;

/*
 * Created by saksham on 13/July/2018
 */

import org.jethro.mobile.api.ApiEndPoints;
import org.jethro.mobile.models.UpdatePasswordPayload;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.PUT;

public interface UserDetailsService {

    @PUT(ApiEndPoints.USER)
    Observable<ResponseBody> updateAccountPassword(@Body UpdatePasswordPayload payload);
}
