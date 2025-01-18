package fun.justdevelops.controller;


import fun.justdevelops.model.data.Category;
import fun.justdevelops.model.data.Transaction;
import fun.justdevelops.model.data.Wallet;
import fun.justdevelops.model.data.User;
import fun.justdevelops.model.repo.Repo;
import fun.justdevelops.ui.View;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class FinanceController implements Controller {
    private STATE state;
    private User currentUser;
    private Wallet userWallet;
    private ArrayList<Category> categories;
    private ArrayList<Transaction> transactions;
    private final Repo repo;
    private final View view;

    private enum STATE {
        LOGIN,
        MAIN
    }

    public FinanceController(View view, Repo repo) {
        this.view = view;
        this.repo = repo;
    }

    public void startApplication() {
        changeState(STATE.LOGIN);
    }

    private void onLoginButtonClick(String login, String pass) {
        User user = repo.findUser(login);
        if (user == null) {
            view.showErrorMessage("Пользователь не найден");
            return;
        }
        if (user.getPass().equals(pass)) {
            currentUser = user;
            loadUserData(user);
            changeState(STATE.MAIN);
        } else {
            view.showErrorMessage("Неверный пароль");
        }
    }

    private void onRegisterButtonClick(String login, String pass) {
        User user = repo.findUser(login);
        if (user != null) {
            view.showErrorMessage("Пользователь с таким логином уже существует");
            return;
        }
        user = repo.createUser(login, pass);
        if (user == null) {
            view.showErrorMessage("Не удалось создать пользователя");
        } else {
            currentUser = user;
            loadUserData(user);
            changeState(STATE.MAIN);
        }
    }

    private void loadUserData(User user) {
        userWallet = repo.getUserWallet(user.getId());
        categories = repo.getUserCategories(user.getId());
        transactions = repo.getUserTransactions(user.getId());
    }

    private void onExitButtonClick() {
        changeState(STATE.LOGIN);
        currentUser = null;
        userWallet = null;
    }

    private void changeState(STATE newState) {
        switch (newState) {
            case LOGIN -> {
                view.closeMainForm();
                view.showLoginForm(this::onLoginButtonClick, this::onRegisterButtonClick);
            }
            case MAIN -> {
                view.closeLoginForm();
                view.showMainForm(
                        this::onMainFormLoaded,
                        this::onExitButtonClick,
                        this::onAddIncome,
                        this::onAddOutcome,
                        this::onEditInCategory,
                        this::onEditOutCategory);
            }
        }
    }

    private void onEditInCategory(long id) {
        showEditCategoryForm(id, View.TRANSACTION_TYPE.IN);
    }

    private void onEditOutCategory(long id) {
        showEditCategoryForm(id, View.TRANSACTION_TYPE.OUT);
    }

    private void showEditCategoryForm(long id, View.TRANSACTION_TYPE type) {
        view.closeCategoryForm();
        if (id == 0) {
            view.showCategoryForm(id, "", type, "0.0", this::onSaveCategory, this::onCloseCategoryEditor);
        } else {
            Category category = categories.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
            if (category == null) {
                view.showErrorMessage("Ошибка при открытии категории");
                return;
            }
            view.showCategoryForm(
                    category.getId(),
                    category.getName(),
                    type,
                    Double.toString(category.getBudget()),
                    this::onSaveCategory,
                    this::onCloseCategoryEditor
            );
        }
    }

    private void onSaveCategory(long id, String name, View.TRANSACTION_TYPE type, String budget) {
        try {
            Category.TYPE categoryType;
            double budgetValue = Double.parseDouble(budget);
            Category category;
            if (id == 0) {
                if (type == View.TRANSACTION_TYPE.IN) {
                    categoryType = Category.TYPE.IN;
                } else categoryType = Category.TYPE.OUT;
                category = repo.addCategory(name, categoryType, currentUser.getId(), budgetValue);

            } else {
                category = repo.updateCategory(id, name, budgetValue);
            }
            if (category == null) {
                throw new Exception();
            }
            loadUserData(currentUser);
            updateMainForm();
            view.closeCategoryForm();
        } catch (NumberFormatException ee) {
            ee.printStackTrace();
            view.showErrorMessage(ee.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            view.showErrorMessage("Не удалось сохранить категорию");
        }
    }

    private void onCloseCategoryEditor() {
        view.closeCategoryForm();
    }

    private void onAddIncome() {
        view.closeTransactionForm();
        List<String> inCategories = categories.stream()
                .filter(c -> c.getType() == Category.TYPE.IN)
                .map(Category::getName)
                .toList();
        view.showTransactionForm(View.TRANSACTION_TYPE.IN, inCategories, this::performTransaction, this::cancelTransaction);
    }

    private void onAddOutcome() {
        view.closeTransactionForm();
        List<String> inCategories = categories.stream()
                .filter(c -> c.getType() == Category.TYPE.OUT)
                .map(Category::getName)
                .toList();
        view.showTransactionForm(View.TRANSACTION_TYPE.OUT, inCategories, this::performTransaction, this::cancelTransaction);
    }

    private void performTransaction(View.TRANSACTION_TYPE type, String category, String amount, String comment) {
        try {
            Category.TYPE categoryType;
            if (type == View.TRANSACTION_TYPE.IN) categoryType = Category.TYPE.IN;
            else categoryType = Category.TYPE.OUT;
            double transactionAmount = calculateAmount(amount);
            if (categoryType == Category.TYPE.IN) {
                userWallet.setMoneyAmount(userWallet.getMoneyAmount() + transactionAmount);
            } else {
                userWallet.setMoneyAmount(userWallet.getMoneyAmount() - transactionAmount);
            }
            Category currentCategory = categories.stream()
                    .filter(c -> c.getType() == categoryType)
                    .filter(c -> c.getName().equals(category)).findFirst().get();
            repo.updateWallet(userWallet);
            Transaction transaction = repo.addTransaction(
                    currentUser.getId(),
                    currentCategory.getId(),
                    transactionAmount,
                    getTimestamp(),
                    comment);
            transactions.add(transaction);
            view.setWalletValue(Double.toString(userWallet.getMoneyAmount()));
            updateMainForm();
            view.closeTransactionForm();
        } catch (NumberFormatException e) {
            e.printStackTrace();
            view.showErrorMessage("Не удалось совершить транзакцию. Убедитесь, что вы указали верную сумму");
        } catch (Exception e) {
            e.printStackTrace();
            view.showErrorMessage(e.getMessage());
        }
    }

    private double calculateAmount(String amountString) throws Exception {
        double amount = Double.parseDouble(amountString);
        if (amount >= 1000000000000.0) throw new Exception("Сумма транзакции должна быть менее 1 триллиона");
        return roundMoney(amount);
    }

    private void cancelTransaction() {
        view.closeTransactionForm();
    }

    private void onMainFormLoaded() {
        updateMainForm();
    }

    private void updateMainForm() {
        view.setWalletValue(String.valueOf(userWallet.getMoneyAmount()));
        updateInCategoriesTable();
        updateOutCategoriesTable();
        updateIncomeTable();
        updateOutcomeTable();
    }

    private void updateIncomeTable() {
        if (currentUser == null || categories == null || transactions == null) return;
        ArrayList<String[]> rows = new ArrayList<>();
        transactions.forEach(t -> {
            Category currentCategory = categories.stream().filter(c -> c.getId() == t.getCategoryId()).findFirst().orElse(null);
            if (currentCategory == null) return;
            if (currentCategory.getType() == Category.TYPE.IN) {
                rows.add(new String[]{
                        currentCategory.getName(),
                        Double.toString(t.getAmount()),
                        t.getComment(),
                        t.getTimestamp()
                });
            }
        });
        String sumString = Double.toString(rows.stream().map(r -> Double.parseDouble(r[1])).mapToDouble(d -> d).sum());
        view.updateIncomeTable(rows, sumString);
    }

    private void updateOutcomeTable() {
        if (currentUser == null || categories == null || transactions == null) return;
        ArrayList<String[]> rows = new ArrayList<>();
        transactions.stream().forEach(t -> {
            Category currentCategory = categories.stream().filter(c -> c.getId() == t.getCategoryId()).findFirst().orElse(null);
            if (currentCategory == null) return;
            if (currentCategory.getType() == Category.TYPE.OUT) {
                rows.add(new String[]{
                        currentCategory.getName(),
                        Double.toString(t.getAmount()),
                        t.getComment(),
                        t.getTimestamp()
                });
            }
        });
        String sumString = Double.toString(rows.stream().map(r -> Double.parseDouble(r[1])).mapToDouble(d -> d).sum());
        view.updateOutcomeTable(rows, sumString);
    }

    private void updateInCategoriesTable() {
        if (currentUser == null || categories == null || transactions == null) return;
        ArrayList<String[]> rows = new ArrayList<>();
        categories.stream().filter(category -> category.getType() == Category.TYPE.IN).forEach(category -> {
        rows.add(new String[] {
            category.getName(),
            Double.toString(category.getBudget()),
            Double.toString(getCategoryTransactionsSum(category.getId(), transactions))});
        });
        view.updateInCategoriesTable(rows);
    }

    private void updateOutCategoriesTable() {
        if (currentUser == null || categories == null || transactions == null) return;
        ArrayList<String[]> rows = new ArrayList<>();
        categories.stream().filter(category -> category.getType() == Category.TYPE.OUT).forEach(category -> {
            rows.add(new String[] {
                    category.getName(),
                    Double.toString(category.getBudget()),
                    Double.toString(getCategoryTransactionsSum(category.getId(), transactions))});
        });
        view.updateOutCategoriesTable(rows);
    }

    private double getCategoryTransactionsSum(long categoryId, List<Transaction> transactions) {
        return transactions.stream()
            .filter(t -> t.getCategoryId() == categoryId)
            .mapToDouble(Transaction::getAmount)
            .sum();
    }

    private double roundMoney(Double money) {
        return new BigDecimal(money).setScale(2, RoundingMode.CEILING).doubleValue();
    }

    private String getTimestamp() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }
}
