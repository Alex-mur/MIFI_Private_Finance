package fun.justdevelops.model.data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "categories")
public class Category {
    public enum TYPE {
        IN,
        OUT
    }

    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField(canBeNull = false)
    private String name;

    @DatabaseField(canBeNull = false)
    private TYPE type;

    @DatabaseField(canBeNull = false)
    private long userId;

    @DatabaseField()
    private double budget;

    Category() {}

    public Category(String name, TYPE type, long userId, double budget) {
        this.name = name;
        this.type = type;
        this.userId = userId;
        this.budget = budget;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public TYPE getType() {
        return type;
    }

    public long getUserId() {
        return userId;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public void setName(String name) {
        this.name = name;
    }
}
