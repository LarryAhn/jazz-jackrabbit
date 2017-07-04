package com.hackeriahn;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Larry.Ahn on 2017. 7. 4..
 */
@Component
@Slf4j
public class Receiver {

    private CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage(String message) {
        log.info("Received Message : <{}>", message);
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }

}
