package com.canoo.dp.impl.platform.client.session;

import com.canoo.platform.client.PlatformClient;
import com.canoo.platform.client.session.ClientSessionStore;
import com.canoo.platform.core.PlatformConfiguration;
import com.canoo.platform.core.http.HttpURLConnectionHandler;
import com.canoo.platform.core.http.spi.ResponseHandlerProvider;

public class ClientSessionResponseHandlerProvider implements ResponseHandlerProvider {

    @Override
    public HttpURLConnectionHandler getHandler(PlatformConfiguration configuration) {
        return new ClientSessionResponseHandler(PlatformClient.getService(ClientSessionStore.class));
    }
}
