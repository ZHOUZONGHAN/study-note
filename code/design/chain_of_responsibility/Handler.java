package design.chain_of_responsibility;

/**
 * @author zhouzonghan
 */
public abstract class Handler {

    protected Handler successor = null;
    protected int order = 0;

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void setSuccessor(Handler successor) {
        this.successor = successor;
    }

    public final String handle() {
        String handled = doHandle();
        if (successor != null && handled == null) {
            handled = successor.handle();
        }
        return handled;
    }

    /**
     * 核型处理方法
     *
     * @return 处理结果
     */
    protected abstract String doHandle();
}