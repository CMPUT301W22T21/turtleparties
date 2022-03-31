package com.example.turtlepartiesapp;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert.*;

public class ScoreQRTest {

    private ScoreQrcode createQR(){
        ScoreQrcode QR1 = new ScoreQrcode("helloworld");
        return QR1;

    }

    @Test
    public void testScoring(){
        ScoreQrcode QR = createQR();
        // the score for the QR code with helloworld should be 196 from the hash of 936a185caaa266bb9cbe981e9e05cb78cd732b0b3280eb944412bb6f8f8f07af
        assertEquals(QR.getScore(),196);
    }

    @Test
    public void testSha1(){
        ScoreQrcode QR1 = new ScoreQrcode("ilovecomputers.com");
        assertEquals(QR1.sha256(),"ba2c215555e6da10cc6af64522729d06a3451093795452f6b7e30a5c9c53efaa");
    }
    @Test
    public void testSha2(){
        ScoreQrcode QR1 = new ScoreQrcode("science");
        assertEquals(QR1.sha256(),"790fad4744a9da8d301e23c83b386f1baabd229611dbe5265df62a97b5357393");
    }
    @Test
    public void testSha3(){
        ScoreQrcode QR1 = new ScoreQrcode("projects");
        assertEquals(QR1.sha256(),"2577c0f557b2e4b591e9587374c6330c9c64de8017d5a848a682a135251a6f6f");
    }
    @Test
    public void testSha4(){
        ScoreQrcode QR1 = new ScoreQrcode("testcases");
        assertEquals(QR1.sha256(),"654220a96d5137de48915fcc63784f3089751eb2af4e30efbc381f3e744d92b8");
    }

    @Test
    public void testSha5(){
        ScoreQrcode QR1 = new ScoreQrcode("lovebaskets");
        assertEquals(QR1.sha256(),"335fcbc7c6eabe24a842935687a57ceeb4743d53e2cd6511f44509604ae79dcc");
    }

    @Test
    public void testSha6(){
        ScoreQrcode QR1 = new ScoreQrcode("apples");
        assertEquals(QR1.sha256(),"f5903f51e341a783e69ffc2d9b335048716f5f040a782a2764cd4e728b0f74d9");
    }

    @Test
    public void testScoring2(){
        ScoreQrcode QR1 = new ScoreQrcode("beans");
        // the score for the QR code with helloworld should be 196 from the hash of 936a185caaa266bb9cbe981e9e05cb78cd732b0b3280eb944412bb6f8f8f07af
        assertEquals(QR1.getScore(),89);
    }

    @Test
    public void testScoring3(){
        ScoreQrcode QR1 = new ScoreQrcode("pasdasdk");
        // the score for the QR code with helloworld should be 196 from the hash of 936a185caaa266bb9cbe981e9e05cb78cd732b0b3280eb944412bb6f8f8f07af
        assertEquals(QR1.getScore(),81);
    }

    @Test
    public void testScoring4(){
        ScoreQrcode QR1 = new ScoreQrcode("appplesalaad");
        // the score for the QR code with helloworld should be 196 from the hash of 936a185caaa266bb9cbe981e9e05cb78cd732b0b3280eb944412bb6f8f8f07af
        assertEquals(QR1.getScore(),86);
    }

    @Test
    public void testScoring5(){
        ScoreQrcode QR1 = new ScoreQrcode("pokimon");
        // the score for the QR code with helloworld should be 196 from the hash of 936a185caaa266bb9cbe981e9e05cb78cd732b0b3280eb944412bb6f8f8f07af
        assertEquals(QR1.getScore(),88);
    }








}
