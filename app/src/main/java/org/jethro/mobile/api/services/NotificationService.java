package org.jethro.mobile.api.services;

import org.jethro.mobile.api.ApiEndPoints;
import org.jethro.mobile.models.notification.NotificationRegisterPayload;
import org.jethro.mobile.models.notification.NotificationUserDetail;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by dilpreet on 26/09/17.
 */

public interface NotificationService {

    @GET(ApiEndPoints.DEVICE + "/registration/client/{clientId}")
    Observable<NotificationUserDetail> getUserNotificationId(@Path("clientId")long clientId);

    @POST(ApiEndPoints.DEVICE + "/registration")
    Observable<ResponseBody> registerNotification(@Body NotificationRegisterPayload payload);

    @PUT(ApiEndPoints.DEVICE + "/registration/{id}")
    Observable<ResponseBody> updateRegisterNotification(@Path("id")long id,
                                                  @Body NotificationRegisterPayload payload);
}
