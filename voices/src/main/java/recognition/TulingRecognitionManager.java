package recognition;

import android.content.Context;

import com.fge.voice.util.FileWriteLog;
import com.turing.androidsdk.InitListener;
import com.turing.androidsdk.SDKInit;
import com.turing.androidsdk.SDKInitBuilder;
import com.turing.androidsdk.TuringApiManager;

/**
 * Created by slxpro on 16/2/17.
 */
public class TulingRecognitionManager {

    public TuringApiManager mTuringApiManager;
    public static int status=0;

    private TulingRecognitionManager  tulingRecognitionManager;

   private Context context;


    public TulingRecognitionManager(Context context ) {

        this.context = context;
    }


//    public static TulingRecognitionManager getInstance(Context context){
//
//        if(null != tulingRecognitionManager )
//        {
//            return tulingRecognitionManager ;
//        }else{
//            tulingRecognitionManager = new TulingRecognitionManager(context.getApplicationContext());
//            return tulingRecognitionManager ;
//        }
//    }
    public void initTulingApiManager(final MyHttpRequestWatcher myHttpRequestWatcher){
        initTulingApiManager("c0f3f8dfc2f4437197a572b4908fc4b8","88477ad9ce7170bf","131313131",myHttpRequestWatcher);
    }
    public void initTulingApiManager(String tulingAppid,String tulingSecret,String uniqueId,final MyHttpRequestWatcher myHttpRequestWatcher) {
        // turingSDK初始化
        if(status==1){
            mTuringApiManager = new TuringApiManager(context);
            mTuringApiManager.setHttpListener(myHttpRequestWatcher);
            return;
        }
        final SDKInitBuilder builder = new SDKInitBuilder(context)
               .setTuringKey(tulingAppid).setSecret(tulingSecret).setUniqueId(uniqueId);
        SDKInit.init(builder,new InitListener() {
            @Override
            public void onFail(String error) {
                    FileWriteLog.writeLog("图灵初始化====" + error);
//                SDKInit.init(builder,this);
            }
            @Override
            public void onComplete() {
                status=1;
                FileWriteLog.writeLog("图灵初始化成功");
                // 获取userid成功后，才可以请求Turing服务器，需要请求必须在此回调成功，才可正确请求
                mTuringApiManager = new TuringApiManager(context);
                mTuringApiManager.setHttpListener(myHttpRequestWatcher);
            }
        });
    }


    public void recognitionApi (String speenResult) {
        mTuringApiManager.requestTuringAPI(speenResult);

    }

}
