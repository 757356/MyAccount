package cn.itcast.myaccount;

public class AccountItem {
    private int money;
    private int resource_id;

    public AccountItem( int money, int resource_id ) {
        this.money = money;
        this.resource_id = resource_id;
    }

    public void setMoney( int money ) {
        this.money = money;
    }

    public int getMoney() { return this.money; }

    public int getResourceId() { return this.resource_id; }

}
