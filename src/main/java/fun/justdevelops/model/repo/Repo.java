package fun.justdevelops.model.repo;

import fun.justdevelops.model.data.Category;
import fun.justdevelops.model.data.Transaction;
import fun.justdevelops.model.data.Wallet;
import fun.justdevelops.model.data.User;

import java.util.ArrayList;

public interface Repo {
    User findUser(String login);

    User createUser(String login, String pass);

    Wallet getUserWallet(long userId);

    void updateWallet(Wallet wallet);

    ArrayList<Category> getUserCategories(long userId);

    ArrayList<Transaction> getUserTransactions(long userId);

    Transaction addTransaction(long userId, long categoryId, double amount, String timestamp, String comment);

    Category addCategory(String name, Category.TYPE type, long userId, double budget);

    Category updateCategory(long id, String name, double budget);
}
