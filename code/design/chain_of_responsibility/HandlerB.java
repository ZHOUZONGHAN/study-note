package design.chain_of_responsibility;

/**
 * @author zhouzonghan
 */
public class HandlerB extends Handler {

    public HandlerB() {
        super.setOrder(1);
    }

    @Override
    protected String doHandle() {
        return "B";
    }
}