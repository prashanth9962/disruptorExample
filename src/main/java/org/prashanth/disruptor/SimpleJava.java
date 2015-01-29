package org.prashanth.disruptor;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

import com.lmax.disruptor.EventHandler;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by prashanth on 1/29/15.
 */
public class SimpleJava {
    public  static  void  main(String[] args){
        ExecutorService executorService = Executors.newCachedThreadPool();
        Disruptor<ValueEvent> disruptor = new Disruptor<ValueEvent>(ValueEvent.EVENT_FACTORY,1024,executorService);
        final EventHandler<ValueEvent> handler = new EventHandler<ValueEvent>() {
            public void onEvent(final ValueEvent event, final long sequence, final boolean endOfBatch) throws Exception {
                System.out.println("Sequence: " + sequence);
                System.out.println("ValueEvent: " + event.getValue());
            }
        };
        disruptor.handleEventsWith(handler);
        RingBuffer<ValueEvent> ringBuffer = disruptor.start();

        for (long i = 1; i < 10; i++) {
            String uuid = UUID.randomUUID().toString();
            // Two phase commit. Grab one of the 1024 slots
            long seq = ringBuffer.next();
            System.out.println("seq"+seq);
            ValueEvent valueEvent = ringBuffer.get(seq);
            valueEvent.setValue(uuid);
            ringBuffer.publish(seq);
        }
        disruptor.shutdown();
        executorService.shutdown();

    }
}
