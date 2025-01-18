package fun.justdevelops.model.data;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "transactions")
public class Transaction {

    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField(canBeNull = false)
    private long userId;

    @DatabaseField(canBeNull = false)
    private long categoryId;

    @DatabaseField(canBeNull = false)
    private double amount;

    @DatabaseField(canBeNull = false)
    private String timestamp;

    @DatabaseField()
    private String comment;

    Transaction() {};

    public Transaction(long userId, long categoryId, double amount, String timestamp, String comment) {
        this.userId = userId;
        this.categoryId = categoryId;
        this.amount = amount;
        this.timestamp = timestamp;
        this.comment = comment;
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public double getAmount() {
        return amount;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getComment() {
        return comment;
    }
}
