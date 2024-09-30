package com.spring.batch.Batch.listener;

import com.spring.batch.Batch.entity.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.SkipListener;

public class StepSkipListener implements SkipListener<Customer,Number> {
    Logger logger= LoggerFactory.getLogger(StepSkipListener.class);
    @Override
    public void onSkipInRead(Throwable throwable) {
    logger.info("A failure on read:{} "+throwable.getMessage());
    }

    @Override
    public void onSkipInWrite(Number item, Throwable throwable) {
        logger.info("A failure on write:{} ,{} "+throwable.getMessage(),item);
    }

    @Override
    public void onSkipInProcess(Customer customer, Throwable throwable) {
        logger.info("A failure on skip:{} "+throwable.getMessage());
    }
}
