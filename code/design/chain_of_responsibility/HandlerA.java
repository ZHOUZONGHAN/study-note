package design.chain_of_responsibility;

/**
 * @author zhouzonghan
 */
public class HandlerA extends Handler {

    public HandlerA() {
        super.setOrder(2);
    }

    @Override
    protected String doHandle() {
        return "A";
    }
}