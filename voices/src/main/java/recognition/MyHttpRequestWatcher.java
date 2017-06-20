package recognition;

import turing.os.http.core.ErrorMessage;
import turing.os.http.core.HttpConnectionListener;
import turing.os.http.core.RequestResult;

public class MyHttpRequestWatcher  implements HttpConnectionListener {


	@Override
	public void onError(ErrorMessage errorMessage) {

	}

	@Override
	public void onSuccess(RequestResult requestResult) {

	}
}
