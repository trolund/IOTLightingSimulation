package com.MerchantApp;

import com.CustomerApp.CustomerApp;
import com.dto.Token;

public class MerchantApp {

    CustomerApp ca = new CustomerApp();

    public Token merchantRequestsToken(String cid){
        return ca.CustomerRequestsToken(cid);
    }

}
