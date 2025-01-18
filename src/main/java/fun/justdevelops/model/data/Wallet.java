package fun.justdevelops.model.data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "wallets")
public class Wallet {

    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField(unique = true, canBeNull = false)
    private long userId;

    @DatabaseField(canBeNull = false, defaultValue = "0")
    private double moneyAmount;

    public Wallet() {}

    public Wallet(long userId) {
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }

    public double getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(double moneyAmount) {
        this.moneyAmount = moneyAmount;
    }
}
