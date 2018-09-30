package com.otitan.dclz.net;

import com.otitan.dclz.bean.Banners;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by sp on 2018/9/27
 * Retrofit 接口
 */

public interface RetrofitService {

    // 登录
    @GET("/webservice.asmx/UserLogin")
    Observable<String> checkLogin(@Query("username") String username, @Query("password") String password);

    // 获取首页图片
    @GET("/webservice.asmx/GetPicture")
    Observable<List<Banners>> getBannerPath();

    // 获取用户信息
    @GET("/webservice.asmx/GetUserInfo")
    Observable<String> getUserInfo(@Query("mobilephone") String mobilephone, @Query("usercode") String usercode, @Query("password") String password);

    // 修改密码
    @GET("/GISWebService.asmx/UpdatePassword")
    Observable<String> changePassword(@Query("mobilephone") String mobilephone, @Query("newpassword") String newpassword);

    // 遥感监测数据
    @GET("/GISWebService.asmx/GetNewShInfoByDate")
    Observable<String> getMonitorData(@Query("date") String date, @Query("yg_type") int yg_type);

    // 浮标站数据
    @GET("/webservice.asmx/GetFBDataByDate")
    Observable<String> getBuoyData(@Query("startDate") String startDate, @Query("endDate") String endDate);

    // 获取水华发生概率
    @GET("/webservice.asmx/GetSHFSGL")
    Observable<String> getAlgalBloomsOdds(@Query("strArr") String strArr);

    // 获取巡护事件
    @GET("/webservice.asmx/GetPatrolEvent")
    Observable<String> getPatrolInfo(@Query("sbbh") String sbbh, @Query("startDate") String startDate, @Query("endDate") String endDate);
}
