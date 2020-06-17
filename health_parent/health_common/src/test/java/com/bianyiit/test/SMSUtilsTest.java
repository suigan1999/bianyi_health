package com.bianyiit.test;

import com.aliyuncs.exceptions.ClientException;
import com.bianyiit.utils.SMSUtils;
import org.junit.Test;

public class SMSUtilsTest {
    @Test
    public void test() throws ClientException {
        SMSUtils.sendShortMessage("SMS_193248525","18692495637","08845");
    }
}
