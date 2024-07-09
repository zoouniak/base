package com.zoouniak.yoursell.login.infra.oauthProvider;

import com.zoouniak.yoursell.login.infra.oauthUserInfo.OAuthUserInfo;
import org.springframework.stereotype.Component;

@Component
public interface OAuthProvider {
     boolean equal(String provider);

     OAuthUserInfo getUserInfo(String code);

}
