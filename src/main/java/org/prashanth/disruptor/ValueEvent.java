package org.prashanth.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * Created by prashanth on 1/29/15.
 */
public class ValueEvent {
    private  String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public  final  static EventFactory<ValueEvent> EVENT_FACTORY= new EventFactory<ValueEvent>() {
        @Override
        public ValueEvent newInstance() {
            return new ValueEvent();
        }
    };


}
