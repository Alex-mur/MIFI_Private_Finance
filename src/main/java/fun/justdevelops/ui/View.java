package fun.justdevelops.ui;

import fun.justdevelops.ui.listeners.*;

import java.util.List;

public interface View {

    enum TRANSACTION_TYPE {
        IN,
        OUT
    }

    void showLoginForm(
            OnLoginButtonClickListener onLoginButtonClickListener,
            OnRegisterButtonClickListener onRegisterButtonClickListener);

    void showMainForm(
            OnMainFormLoaded onMainFormLoaded,
            OnExitButtonClickListener onExitButtonClickListener,
            OnAddIncomeClickListener onAddIncomeClickListener,
            OnAddOutcomeClickListener onAddOutcomeClickListener,
            OnEditInCategoryClickListener onEditInCategoryClickListener,
            OnEditOutCategoryClickListener onEditOutCategoryClickListener);

    void showTransactionForm(
            TRANSACTION_TYPE type,
            List<String> categories,
            OnPerformTransactionClickListener onPerformTransactionClickListener,
            OnCancelTransactionClickListener onCancelTransactionClickListener);

    void showCategoryForm(
            long id,
            String name,
            View.TRANSACTION_TYPE type,
            String budget,
            OnSaveCategoryClickListener onSaveCategoryClickListener,
            OnCancelSavingCategoryClickListener onCancelSavingCategoryClickListener);

    void closeLoginForm();

    void closeMainForm();

    void closeTransactionForm();

    void closeCategoryForm();

    void showErrorMessage(String message);

    void setWalletValue(String text);

    void updateIncomeTable(List<String[]> rows, String sum);

    void updateOutcomeTable(List<String[]> rows, String sum);

    void updateInCategoriesTable(List<String[]> rows);

    void updateOutCategoriesTable(List<String[]> rows);
}
