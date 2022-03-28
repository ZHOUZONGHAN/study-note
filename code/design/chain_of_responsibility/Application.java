package design.chain_of_responsibility;

/**
 * @author zhouzonghan
 */
public class Application {

    public static void main(String[] args) {
        test();
        test2();
    }

    public static void test() {
        HandlerChain chain = new HandlerChain();
        chain.addHandler(new HandlerA());
        chain.addHandler(new HandlerB());
        System.out.println(chain.handle());
    }

    public static void test2() {
        HandlerChain chain = new HandlerChain();
        chain.addHandler2(new HandlerA());
        chain.addHandler2(new HandlerB());
        System.out.println(chain.handle2());
    }
}