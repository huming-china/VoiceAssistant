package recognition;

import com.turing.androidsdk.InitListener;

public class MyInitListener implements InitListener {

	@Override
	public void onComplete() {

		//只有第一次初始化时，会向服务器请求userid,获取userid成功，会将userid保存本地，只有第一次会请求
		//之后初始化，会跳过请求，在本地获取userid

		//有userid的情况下，才支持主动请求，上下文模式等功能
	}

	@Override
	public void onFail(String s) {

	}

}
