package fun.justdevelops.ui;


import fun.justdevelops.ui.listeners.*;

import javax.swing.*;
import java.util.List;

public class SwingView implements View {

    private LoginForm loginForm;
    private MainForm mainForm;
    private TransactionForm transactionForm;
    private CategoryForm categoryForm;

    @Override
    public void showLoginForm(OnLoginButtonClickListener onLoginButtonClickListener,
                              OnRegisterButtonClickListener onRegisterButtonClickListener) {
        SwingUtilities.invokeLater(() -> {
            loginForm = new LoginForm(onLoginButtonClickListener, onRegisterButtonClickListener);
            loginForm.setVisible(true);
        });
    }

    @Override
    public void showMainForm(
            OnMainFormLoaded onMainFormLoaded,
            OnExitButtonClickListener onExitButtonClickListener,
            OnAddIncomeClickListener onAddIncomeClickListener,
            OnAddOutcomeClickListener onAddOutcomeClickListener,
            OnEditInCategoryClickListener onEditInCategoryClickListener,
            OnEditOutCategoryClickListener onEditOutCategoryClickListener) {

        SwingUtilities.invokeLater(() -> {
            mainForm = new MainForm(
                    onExitButtonClickListener,
                    onAddIncomeClickListener,
                    onAddOutcomeClickListener,
                    onEditInCategoryClickListener,
                    onEditOutCategoryClickListener);
            mainForm.setVisible(true);
            onMainFormLoaded.done();
        });
    }

    @Override
    public void showTransactionForm(TRANSACTION_TYPE type,
                                    List<String> categories,
                                    OnPerformTransactionClickListener onPerformTransactionClickListener,
                                    OnCancelTransactionClickListener onCancelTransactionClickListener) {
        SwingUtilities.invokeLater(() -> {
            transactionForm = new TransactionForm(type, categories, onPerformTransactionClickListener, onCancelTransactionClickListener);
            transactionForm.setVisible(true);
        });
    }

    @Override
    public void closeLoginForm() {
        if (loginForm != null) {
            loginForm.setVisible(false);
            loginForm.dispose();
            loginForm = null;
        }
    }

    @Override
    public void closeMainForm() {
        if (mainForm != null) {
            mainForm.setVisible(false);
            mainForm.dispose();
            mainForm = null;
        }
    }

    @Override
    public void closeTransactionForm() {
        if (transactionForm != null) {
            transactionForm.setVisible(false);
            transactionForm.dispose();
            transactionForm = null;
        }
    }

    @Override
    public void showErrorMessage(String message) {
        if (loginForm != null && loginForm.isVisible()) {
            loginForm.showErrorMessage(message);
            return;
        }
        if (transactionForm != null && transactionForm.isVisible()) {
            transactionForm.showErrorMessage(message);
        }
    }

    @Override
    public void setWalletValue(String text) {
        if (mainForm == null) return;
        mainForm.setWalletValue(text);
    }

    @Override
    public void updateIncomeTable(List<String[]> rows, String sum) {
        mainForm.updateIncomeTable(rows, sum);
    }

    @Override
    public void updateOutcomeTable(List<String[]> rows, String sum) {
        mainForm.updateOutcomeTable(rows, sum);
    }

    @Override
    public void updateInCategoriesTable(List<String[]> rows) {
        mainForm.updateInCategoriesTable(rows);
    }

    @Override
    public void updateOutCategoriesTable(List<String[]> rows) {
        mainForm.updateOutCategoriesTable(rows);
    }

    @Override
    public void showCategoryForm(
            long id,
            String name,
            TRANSACTION_TYPE type,
            String budget,
            OnSaveCategoryClickListener onSaveCategoryClickListener,
            OnCancelSavingCategoryClickListener onCancelSavingCategoryClickListener) {
        SwingUtilities.invokeLater(() -> {
            categoryForm = new CategoryForm(
                    id, name, type, budget, onSaveCategoryClickListener, onCancelSavingCategoryClickListener);
            categoryForm.setVisible(true);
        });
    }

    @Override
    public void closeCategoryForm() {
        if (categoryForm != null) {
            categoryForm.setVisible(false);
            categoryForm.dispose();
            categoryForm = null;
        }
    }
}
