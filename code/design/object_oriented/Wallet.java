package design.object_oriented;

import java.math.BigDecimal;

public class Wallet {

    // 钱包唯一编号
    private String id;
    // 钱包创建的时间
    private long createTime;
    // 钱包中的余额
    private BigDecimal balance;
    // 上次钱包余额变更的时间
    private long balanceLastModifiedTime;
    // ...省略其他属性...

    public Wallet() {
//        this.id = IdGenerator.getInstance().generate();
        this.createTime = System.currentTimeMillis();
        this.balance = BigDecimal.ZERO;
        this.balanceLastModifiedTime = System.currentTimeMillis();
    }


    public void increaseBalance(BigDecimal increasedAmount) throws Exception {
        if (increasedAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new Exception("...");
//      throw new InvalidAmountException("...");
        }
        this.balance.add(increasedAmount);
        this.balanceLastModifiedTime = System.currentTimeMillis();
    }

    public void decreaseBalance(BigDecimal decreasedAmount) throws Exception {
        if (decreasedAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new Exception("...");
//      throw new InvalidAmountException("...");
        }
        if (decreasedAmount.compareTo(this.balance) > 0) {
            throw new Exception("...");
//      throw new InsufficientAmountException("...");
        }
        this.balance.subtract(decreasedAmount);
        this.balanceLastModifiedTime = System.currentTimeMillis();
    }

    public String getId() {
        return this.id;
    }

    public long getCreateTime() {
        return this.createTime;
    }

    public BigDecimal getBalance() {
        return this.balance;
    }

    public long getBalanceLastModifiedTime() {
        return this.balanceLastModifiedTime;
    }
}