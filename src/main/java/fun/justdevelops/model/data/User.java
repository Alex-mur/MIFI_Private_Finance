package fun.justdevelops.model.data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "users")
public class User {

    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField(canBeNull = false, unique = true)
    private String login;

    @DatabaseField(canBeNull = false)
    private String pass;

    public User() {

    }

    public User(String login, String pass) {
        this.login = login;
        this.pass = pass;
    }

    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
