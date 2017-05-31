//package common;
//
//import android.content.Context;
//import android.os.Environment;
//import android.os.Handler;
//import android.os.Looper;
//import android.text.TextUtils;
//import android.util.Log;
//
//import com.google.gson.Gson;
//import com.uton.utonbuyer.Constant;
//import com.uton.utonbuyer.MyApplication;
//import com.uton.utonbuyer.R;
//import com.uton.utonbuyer.util.LogUtil;
//import com.uton.utonbuyer.util.SharedPreferencesUtils;
//import com.uton.utonbuyer.util.Utils;
//
//import org.json.JSONObject;
//
//import java.io.IOException;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.Set;
//import java.util.concurrent.TimeUnit;
//
//import okhttp3.Cache;
//import okhttp3.Call;
//import okhttp3.Callback;
//import okhttp3.MediaType;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okhttp3.Response;
//
//
//
//public class NewOkTool implements NewNetInterface {
//    private boolean isSub = true;
//    private OkHttpClient mOkHttpClient;
//    private Handler mHandler = new Handler(Looper.getMainLooper());
//    private Gson mGson;
//    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
//    private String token = "token";
//
//    public NewOkTool() {
//        //初始化Gson对象
//        mGson = new Gson();
//        //初始化对象
//
//        mOkHttpClient = new OkHttpClient.Builder()
//                .retryOnConnectionFailure(true)
//                .connectTimeout(15, TimeUnit.SECONDS)
//                .cache(new Cache(Environment.getExternalStorageDirectory(), 10 * 1024 * 1024))
//                .build();
//    }
//
//    //暂时不用String 类型的
//    //传入个网址即可
//    @Override
//    public void startRequest(Context context,String url,Map map,final Boolean isShowLoading,final NewCallBack<String> callBack) {
//
//
//        if(!Utils.checkNetworkAvailable(context)){
//            Utils.showShortToast(MyApplication.getmContext().getResources().getString(R.string.net_error));
//            return;
//        }
//        SharedPreferencesUtils.getSon(MyApplication.getmContext());
//        if (isSub) {
//            token = "token";
//        } else {
//            token = "subToken";
//        }
//        final String jsonStr = mGson.toJson(map);
//        RequestBody requestBody = RequestBody.create(JSON, jsonStr);
//        Request request = new Request.Builder().url(url)
//                .post(requestBody)
//                .addHeader("Content-Type", StaticValues.CONTENT)
//                .addHeader(token, SharedPreferencesUtils.getToken(MyApplication.getmContext()))
//                .build();
//        if(isShowLoading){
//            Utils.ShowProgressDialog(context);
//        }
//
//        mOkHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, final IOException e) {
//                mHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        callBack.onError(e);
//                        if(isShowLoading){
//                            Utils.DismissProgressDialog();
//                        }
//                        Utils.showShortToast(MyApplication.getmContext().getResources().getString(R.string.net_error));
//                    }
//                });
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                final String str = response.body().string();
//                mHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        callBack.onSuccess(str);
//                        if(isShowLoading){
//                            Utils.DismissProgressDialog();
//                        }
//                    }
//                });
//            }
//        });
//    }
//
//
//
//     // 调用接口，有错误码判断
//
//
//    @Override
//    public <T> void startRequest(Context context, final Boolean isShowLoading, final String url, Map map, final Class<T> tClass, final NewCallBack<T> callBack) {
//
//        if(!Utils.checkNetworkAvailable(context)){
//            Utils.showShortToast(MyApplication.getmContext().getResources().getString(R.string.net_error));
//            return;
//        }
//        isSub =  SharedPreferencesUtils.getSon(MyApplication.getmContext());
//        if (!isSub) {
//            token = "token";
//        } else {
//            token = "subToken";
//        }
//        final String jsonStr = mGson.toJson(map);
//        //接口回调错误信息
//
//   Log.e("接口回调错误信息","---"+url+"**********"+jsonStr);
//
//        RequestBody requestBody = RequestBody.create(JSON, jsonStr);
//        Request request = new Request.Builder().url(url)
//                .post(requestBody)
//                .addHeader("Content-Type", StaticValues.CONTENT)
//                .addHeader(token, SharedPreferencesUtils.getToken(MyApplication.getmContext()))
//                .build();
//        if(isShowLoading){
//            Utils.ShowProgressDialog(context);
//        }
//
//        mOkHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, final IOException e) {
//                mHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        if(isShowLoading){
//                            Utils.DismissProgressDialog();
//                        }
//                        Utils.showShortToast(MyApplication.getmContext().getResources().getString(R.string.net_error));
//                        callBack.onError(e);
//                    }
//                });
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String str = response.body().string();
//
//                if(isShowLoading){
//                    Utils.DismissProgressDialog();
//                }
//                if(TextUtils.isEmpty(str)){
//
//                    mHandler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            callBack.onError(null);
//                            Utils.showShortToast(MyApplication.getmContext().getResources().getString(R.string.net_error));
//                        }
//                    });
//                    return;
//                }
//                final T result;
//
//                String rstError="";
//                //接口回调错误码
//                String rstCode="";
//
//                try {
//                    JSONObject jsonObject=new JSONObject(str);
//
//                    rstCode=jsonObject.getString("retCode");
//                    rstError=jsonObject.getString("retMsg");
//                    if(Constant.KEY_SUCCESS.equals(rstCode)){
//                        result = mGson.fromJson(str, tClass);
//
//                        mHandler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                callBack.onSuccess(result);
//                            }
//                        });
//                    }else{
//                        if(TextUtils.isEmpty(rstError)){
//                            Utils.showShortToast(MyApplication.getmContext().getResources().getString(R.string.net_error));
//                        }else{
//                            Utils.showShortToast(rstError);
//                        }
//                    }
//                } catch (final Throwable e) {
//                    final String finalRstError = rstError;
//                    mHandler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            callBack.onError(e);
//                            if(TextUtils.isEmpty(finalRstError)){
//                                Utils.showShortToast(MyApplication.getmContext().getResources().getString(R.string.net_error));
//                            }else{
//                                Utils.showShortToast(finalRstError);
//                            }
//                        }
//                    });
//                }
//            }
//        });
//    }
//
//
//   /* *
//     * 调用接口，无返回状态判断
//    **/
//    @Override
//    public <T> void startRequestNoSuccess(Context context, final Boolean isShowLoading, final String url, Map<String, String> map, final Class<T> tClass, final NewCallBack<T> callBack) {
//
//        if(!Utils.checkNetworkAvailable(context)){
//            Utils.showShortToast(MyApplication.getmContext().getResources().getString(R.string.net_error));
//
//
//        }
//        isSub =  SharedPreferencesUtils.getSon(MyApplication.getmContext());
//        if (isSub) {
//            token = "token";
//        } else {
//            token = "subToken";
//        }
//        final String jsonStr = mGson.toJson(map);
//
//        Log.i("*********",url+"**********"+jsonStr);
//        RequestBody requestBody = RequestBody.create(JSON, jsonStr);
//        Request request = new Request.Builder().url(url)
//                .post(requestBody)
//                .addHeader("Content-Type", StaticValues.CONTENT)
//                .addHeader(token, SharedPreferencesUtils.getToken(MyApplication.getmContext()))
//                .build();
//        if(isShowLoading){
//            Utils.ShowProgressDialog(context);
//        }
//
//        mOkHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, final IOException e) {
//                mHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        if(isShowLoading){
//                            Utils.DismissProgressDialog();
//                        }
//                        callBack.onError(e);
//                    }
//                });
//            }
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String str = response.body().string();
//                Log.i("*********",url+"**********"+jsonStr);
//                if(isShowLoading){
//                    Utils.DismissProgressDialog();
//                }
//                if(TextUtils.isEmpty(str)){
//
//                    mHandler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            callBack.onError(null);
//                            Utils.showShortToast(MyApplication.getmContext().getResources().getString(R.string.net_error));
//                        }
//                    });
//                    return;
//                }
//                final T result;
//
//
//                try {
//                    result = mGson.fromJson(str, tClass);
//                    mHandler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            callBack.onSuccess(result);
//                        }
//                    });
//                } catch (final Throwable e) {
//                    mHandler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            callBack.onError(e);
//                            Utils.showShortToast(MyApplication.getmContext().getResources().getString(R.string.net_error));
//                        }
//                    });
//                }
//            }
//        });
//    }
//
//  /**
//   *  * get 请求 有返回值判断
//   * @param context
//   * @param isShowLoading
//   * @param url
//   * @param map
//   * @param tClass
//   * @param callBack
//   * @param <T>
//   * **/
//    @Override
//    public <T> void startGetRequest(Context context, final Boolean isShowLoading, final String url, Map map, final Class<T> tClass, final NewCallBack<T> callBack) {
//
//        if(!Utils.checkNetworkAvailable(context)){
//            Utils.showShortToast(MyApplication.getmContext().getResources().getString(R.string.net_error));
//            return;
//        }
//        isSub =  SharedPreferencesUtils.getSon(MyApplication.getmContext());
//        if (!isSub) {
//            token = "token";
//        } else {
//            token = "subToken";
//        }
//        final String jsonStr = mGson.toJson(map);
//        //接口回调错误信息
//        String getUrl= formatGetParameter(url,map);
//
//        LogUtil.d("*********"+url+"**********"+getUrl);
//        LogUtil.d("*********"+url+"**********"+SharedPreferencesUtils.getToken(MyApplication.getmContext()));
//
//        Request request = new Request.Builder().url(getUrl)
//                .get()
//                .addHeader("Content-Type", StaticValues.CONTENT)
//                .addHeader(token, SharedPreferencesUtils.getToken(MyApplication.getmContext()))
//                .build();
//        if(isShowLoading){
//            Utils.ShowProgressDialog(context);
//        }
//
//        mOkHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, final IOException e) {
//                mHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        if(isShowLoading){
//                            Utils.DismissProgressDialog();
//                        }
//                        callBack.onError(e);
//                    }
//                });
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String str = response.body().string();
//                LogUtil.d("*********"+url+"**********"+str);
//
//                String rstCode="";
//                if(isShowLoading){
//                    Utils.DismissProgressDialog();
//                }
//                if(TextUtils.isEmpty(str)){
//
//                    mHandler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            callBack.onError(null);
//                            Utils.showShortToast(MyApplication.getmContext().getResources().getString(R.string.net_error));
//                        }
//                    });
//                    return;
//                }
//                final T result;
//
//                String rstError="";
//                //接口回调错误码
//
//                try {
//                    JSONObject jsonObject=new JSONObject(str);
//
//                    rstCode=jsonObject.getString("retCode");
//                    rstError=jsonObject.getString("retMsg");
//                    if(Constant.KEY_SUCCESS.equals(rstCode)){
//                        result = mGson.fromJson(str, tClass);
//
//                        mHandler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                callBack.onSuccess(result);
//                            }
//                        });
//                    }else{
//                        if(TextUtils.isEmpty(rstError)){
//                            Utils.showShortToast(MyApplication.getmContext().getResources().getString(R.string.net_error));
//                        }else{
//                            Utils.showShortToast(rstError);
//                        }
//                    }
//                } catch (final Throwable e) {
//                    final String finalRstError = rstError;
//                    mHandler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            callBack.onError(e);
//                            if(TextUtils.isEmpty(finalRstError)){
//                                Utils.showShortToast(MyApplication.getmContext().getResources().getString(R.string.net_error));
//                            }else{
//                                Utils.showShortToast(finalRstError);
//                            }
//                        }
//                    });
//                }
//            }
//        });
//    }
//    *
//     * get 请求 有返回值判断 String 类型
//
//    @Override
//    public void startGetRequest(Context context,String url,Map<String,String> map,final Boolean isShowLoading,final NewCallBack<String> callBack) {
//
//        if(!Utils.checkNetworkAvailable(context)){
//            Utils.showShortToast(MyApplication.getmContext().getResources().getString(R.string.net_error));
//            return;
//        }
//        isSub =  SharedPreferencesUtils.getSon(MyApplication.getmContext());
//        if (!isSub) {
//            token = "token";
//        } else {
//            token = "subToken";
//        }
//        final String jsonStr = mGson.toJson(map);
//        //接口回调错误信息
//        String getUrl= formatGetParameter(url,map);
//
//        Request request = new Request.Builder().url(getUrl)
//                .get()
//                .addHeader("Content-Type", StaticValues.CONTENT)
//                .addHeader(token, SharedPreferencesUtils.getToken(MyApplication.getmContext()))
//                .build();
//        if(isShowLoading){
//            Utils.ShowProgressDialog(context);
//        }
//
//        mOkHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, final IOException e) {
//                mHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        callBack.onError(e);
//                        if(isShowLoading){
//                            Utils.DismissProgressDialog();
//                        }
//                        Utils.showShortToast(MyApplication.getmContext().getResources().getString(R.string.net_error));
//                    }
//                });
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                final String str = response.body().string();
//                mHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        callBack.onSuccess(str);
//                        if(isShowLoading){
//                            Utils.DismissProgressDialog();
//                        }
//                    }
//                });
//            }
//        });
//    }
//    @Override
//    public <T> void startGetRequestNoSuccess(Context context, final Boolean isShowLoading, final String url, Map<String, String> map, final Class<T> tClass, final NewCallBack<T> callBack) {
//        isSub =  SharedPreferencesUtils.getSon(MyApplication.getmContext());
//        if (isSub) {
//            token = "token";
//        } else {
//            token = "subToken";
//        }
//        final String jsonStr = mGson.toJson(map);
//        Log.i("*********",url+"**********"+jsonStr);
//        String getUrl= formatGetParameter(url,map);
//        Log.i("*********",url+"**********"+jsonStr);
//        Request request = new Request.Builder().url(getUrl)
//                .get()
//                .addHeader("Content-Type", StaticValues.CONTENT)
//                .addHeader(token, SharedPreferencesUtils.getToken(MyApplication.getmContext()))
//                .build();
//        if(isShowLoading){
//            Utils.ShowProgressDialog(context);
//        }
//
//        mOkHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, final IOException e) {
//                mHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        if(isShowLoading){
//                            Utils.DismissProgressDialog();
//                        }
//                        callBack.onError(e);
//                    }
//                });
//            }
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String str = response.body().string();
//                Log.i("*********",url+"**********"+jsonStr);
//                if(isShowLoading){
//                    Utils.DismissProgressDialog();
//                }
//                if(TextUtils.isEmpty(str)){
//
//                    mHandler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            callBack.onError(null);
//                            Utils.showShortToast(MyApplication.getmContext().getResources().getString(R.string.net_error));
//                        }
//                    });
//                    return;
//                }
//                final T result;
//
//
//                try {
//                    result = mGson.fromJson(str, tClass);
//                    if(null==result){
//                        Utils.showShortToast(MyApplication.getmContext().getResources().getString(R.string.net_error));
//
//                        return;
//                    }
//                    mHandler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            callBack.onSuccess(result);
//                        }
//                    });
//                } catch (final Throwable e) {
//                    mHandler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            callBack.onError(e);
//                            Utils.showShortToast(MyApplication.getmContext().getResources().getString(R.string.net_error));
//                        }
//                    });
//                }
//            }
//        });
//    }
//    *
//     * @param url
//     * @param argsMap
//     * @return String
//
//    public static String formatGetParameter(String url, Map<String, String> argsMap) {
//        if (url != null && url.length() > 0) {
//            if (!url.endsWith("?")) {
//                url = url + "?";
//            }
//
//            if (argsMap != null && !argsMap.isEmpty()) {
//                Set<Map.Entry<String, String>> entrySet = argsMap.entrySet();
//                Iterator<Map.Entry<String, String>> iterator = entrySet.iterator();
//                while (iterator.hasNext()) {
//                    Map.Entry<String, String> entry = iterator.next();
//                    if (entry != null) {
//                        String key = entry.getKey();
//                        String value = (String) entry.getValue();
//                        url = url + key + "=" + value;
//                        if (iterator.hasNext()) {
//                            url = url + "&";
//                        }
//                    }
//                }
//            }
//        }
//        return url;
//    }
//}
