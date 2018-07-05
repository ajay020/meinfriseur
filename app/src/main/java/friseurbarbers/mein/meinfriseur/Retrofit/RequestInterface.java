package friseurbarbers.mein.meinfriseur.Retrofit;

import java.util.ArrayList;
import java.util.Map;

import friseurbarbers.mein.meinfriseur.Modules.MeinFriseurModule;
import friseurbarbers.mein.meinfriseur.Modules.MeinFriseurModuleHelper;
import friseurbarbers.mein.meinfriseur.ServerClass.Constant;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by Khushi on 8/16/2017.
 */

public interface RequestInterface {
    @GET
    Call<ArrayList<MeinFriseurModuleHelper>> getData(@Url String url, @QueryMap Map<String, String> option);
    @Multipart
    @POST
    Call<ArrayList<MeinFriseurModuleHelper>> updateProfile(@Url String url, @QueryMap Map<String, String> option, @Part MultipartBody.Part part);
    @POST
    Call<ArrayList<MeinFriseurModuleHelper>> fetchProfile(@Url String url, @QueryMap Map<String,String> option);

}
