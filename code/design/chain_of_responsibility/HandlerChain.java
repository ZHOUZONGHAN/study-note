package design.chain_of_responsibility;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author zhouzonghan
 */
public class HandlerChain {

    private Handler head = null;
    private Handler tail = null;
    private final List<Handler> list = new ArrayList<>();

    public void addHandler(Handler handler) {
        handler.setSuccessor(null);
        if (head == null) {
            head = handler;
            tail = handler;
            return;
        }
        tail.setSuccessor(handler);
        tail = handler;
    }

    public String handle() {
        String result = head.handle();
        if (head != null) {
            return result;
        }
        throw new RuntimeException();
    }

    public void addHandler2(Handler handler) {
        list.add(handler);
        list.sort(Comparator.comparing(Handler::getOrder));
    }

    public String handle2() {
        for (Handler handler : list) {
            String handle = handler.handle();
            if (handle != null) {
                return handle;
            }
        }
        throw new RuntimeException();
    }
}