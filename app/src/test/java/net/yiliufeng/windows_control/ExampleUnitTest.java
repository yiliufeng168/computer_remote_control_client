package net.yiliufeng.windows_control;

import com.google.gson.internal.$Gson$Preconditions;

import net.yiliufeng.windows_control.MyUtils.RsaUtil;

import org.junit.Test;

import java.util.Base64;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() {
        String pubkey = "-----BEGIN PUBLIC KEY-----\r\n"
                +"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwhTFmmIzpM0AlEdqIB0X\r\n"
                +"1/pXeGj/yFEMn2aGWvz+m+WaGpPftDDU0XtDvy8txXjLp1miQ/iaMX85pg1PayE2\r\n"
                +"5OIJnLcWPMGus7Bs2s2h7UCCLDMeFACeLdaI6RM2mFCglGauHekAhOvYF30Ee5g6\r\n"
                +"/b6BHIi7gaIXJLYoD/6Ngm0Z+Q/EyUBS0nOk1i0fk68PlMytjo7JD7yPSp8l6Bgc\r\n"
                +"qQ29Uk8yRqYnACaoNfoprxqD07kXqHOJsqSMHJao9OfLoV7FWJXEeyBKk4lggq5g\r\n"
                +"QrtsHnC+yarNuu5dMhmSerX/r6i+8pMW3h79GzF/COObs+3+T0eguwJ+tsYhMivr\r\n"
                +"TwIDAQAB\r\n"
                +"-----END PUBLIC KEY-----";
        pubkey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwhTFmmIzpM0AlEdqIB0X1/pXeGj/yFEMn2aGWvz+m+WaGpPftDDU0XtDvy8txXjLp1miQ/iaMX85pg1PayE25OIJnLcWPMGus7Bs2s2h7UCCLDMeFACeLdaI6RM2mFCglGauHekAhOvYF30Ee5g6/b6BHIi7gaIXJLYoD/6Ngm0Z+Q/EyUBS0nOk1i0fk68PlMytjo7JD7yPSp8l6BgcqQ29Uk8yRqYnACaoNfoprxqD07kXqHOJsqSMHJao9OfLoV7FWJXEeyBKk4lggq5gQrtsHnC+yarNuu5dMhmSerX/r6i+8pMW3h79GzF/COObs+3+T0eguwJ+tsYhMivrTwIDAQAB";
        String hello = RsaUtil.encryptByPublic("hello");
        System.out.println(hello);
    }
}