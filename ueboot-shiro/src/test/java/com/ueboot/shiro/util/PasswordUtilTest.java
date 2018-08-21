package com.ueboot.shiro.util;

import org.junit.Assert;
import org.junit.Test;

public class PasswordUtilTest {

   @Test
    public void sha512() {
       Assert.assertEquals("d02db260d58a26d777bfa1c8dd41b2a0492024e64cd69c3401223dcea60c8b3d2a44276748e0de9e763b630e84ef1a362b8ed370a9849a2346df769a25c3f8f3",PasswordUtil.sha512("admin","111111"));
    }
}
