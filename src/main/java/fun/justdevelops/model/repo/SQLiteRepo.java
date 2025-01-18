package fun.justdevelops.model.repo;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.table.TableUtils;
import fun.justdevelops.model.data.Wallet;
import fun.justdevelops.model.data.Category;
import fun.justdevelops.model.data.Transaction;
import fun.justdevelops.model.data.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SQLiteRepo implements Repo {
    private final Dao<User,Long> userDao;
    private final Dao<Wallet,Long> walletDao;
    private final Dao<Category, Long> categoryDao;
    private final Dao<Transaction, Long> transactionDao;


    public SQLiteRepo() throws Exception {
        JdbcPooledConnectionSource connection = new JdbcPooledConnectionSource("jdbc:sqlite:finance.db");
        TableUtils.createTableIfNotExists(connection, User.class);
        userDao = DaoManager.createDao(connection, User.class);
        TableUtils.createTableIfNotExists(connection, Wallet.class);
        walletDao = DaoManager.createDao(connection, Wallet.class);
        TableUtils.createTableIfNotExists(connection, Category.class);
        categoryDao = DaoManager.createDao(connection, Category.class);
        TableUtils.createTableIfNotExists(connection, Transaction.class);
        transactionDao = DaoManager.createDao(connection, Transaction.class);
    }

    @Override
    public User findUser(String login) {
        if (userDao == null) return null;
        try {
            return userDao.queryForEq("login", login).stream().findFirst().orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public User createUser(String login, String pass) {
        if (userDao == null) return null;
        if (walletDao == null) return null;
        try {
            User user = userDao.createIfNotExists(new User(login, pass));
            Wallet wallet = walletDao.createIfNotExists(new Wallet(user.getId()));
            createDefaultCategories(user.getId());
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Wallet getUserWallet(long userId) {
        if (walletDao == null) return null;
        try {
            Wallet wallet = walletDao.queryForEq("userId", userId).stream().findFirst().orElse(null);
            if (wallet == null) {
                wallet = walletDao.createIfNotExists(new Wallet(userId));
                createDefaultCategories(userId);
            }
            return wallet;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ArrayList<Category> getUserCategories(long userId) {
        if (categoryDao == null) return null;
        try {
            return new ArrayList<>(categoryDao.queryForEq("userId", userId));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ArrayList<Transaction> getUserTransactions(long userId) {
        if (transactionDao == null) return null;
        try {
            return new ArrayList<>(transactionDao.queryForEq("userId", userId));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Transaction addTransaction(long userId, long categoryId, double amount, String timestamp, String comment) {
        if (transactionDao == null) return null;
        try {
            return transactionDao.createIfNotExists(new Transaction(userId, categoryId, amount, timestamp, comment));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void updateWallet(Wallet wallet) {
        if (walletDao == null) return;
        try {
            walletDao.update(wallet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Category addCategory(String name, Category.TYPE type, long userId, double budget) {
        if (categoryDao == null) return null;
        try {
            return categoryDao.createIfNotExists(new Category(name, type, userId, budget));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Category updateCategory(long id, String name, double budget) {
        if (categoryDao == null) return null;
        try {
            Category category = categoryDao.queryForId(id);
            if (category == null) return null;
            category.setName(name);
            category.setBudget(budget);
            categoryDao.update(category);
            return category;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void createDefaultCategories(long userId) throws Exception {
        List<String> outCats = List.of("Платежи", "Продукты", "Кафе и рестораны", "Дом", "Учеба", "Техника", "Другое");
        List<String> inCats = List.of("Зарплата", "Финансовые операции", "Другое");
        for(String name : outCats) {
            categoryDao.createIfNotExists(new Category(name, Category.TYPE.OUT, userId, 0));
        }
        for(String name : inCats) {
            categoryDao.createIfNotExists(new Category(name, Category.TYPE.IN, userId, 0));
        }
    }
}
