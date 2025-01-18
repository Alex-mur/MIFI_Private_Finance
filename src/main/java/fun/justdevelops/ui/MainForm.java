package fun.justdevelops.ui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import fun.justdevelops.ui.listeners.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.List;
import java.util.Locale;

public class MainForm extends JFrame {
    private JPanel mainPanel;
    private JTabbedPane navigationPane;
    private JPanel accountTab;
    private JPanel outcomeTab;
    private JPanel incomeTab;
    private JPanel incomeCategoriesTab;
    private JPanel outcomeCategoriesTab;
    private JPanel logoutTab;
    private JLabel walletAmountLabel;
    private JLabel walletAmountValue;
    private JTable outcomeTable;
    private JButton addOutcomeButton;
    private JTable incomeTable;
    private JButton addIncomeButton;
    private JTable inCategoriesTable;
    private JTable outCategoriesTable;
    private JLabel outSumLabel;
    private JLabel outSumValue;
    private JLabel inSumLabel;
    private JLabel inSumValue;
    private JButton addInCategoryButton;
    private JButton addOutCategoryButton;
    private DefaultTableModel outcomeTableModel;
    private DefaultTableModel incomeTableModel;
    private DefaultTableModel inCategoriesTableModel;
    private DefaultTableModel outCategoriesTableModel;

    MainForm(
            OnExitButtonClickListener onExitButtonClickListener,
            OnAddIncomeClickListener onAddIncomeClickListener,
            OnAddOutcomeClickListener onAddOutcomeClickListener,
            OnEditInCategoryClickListener onEditInCategoryClickListener,
            OnEditOutCategoryClickListener onEditOutCategoryClickListener) {

        setTitle("Управление финансами");
        setMinimumSize(new Dimension(800, 600));
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(mainPanel);
        pack();
        setLocationRelativeTo(null);
        initTables();
        initListeners(onExitButtonClickListener, onAddIncomeClickListener, onAddOutcomeClickListener, onEditInCategoryClickListener, onEditOutCategoryClickListener);
    }

    private void initTables() {
        outcomeTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        outcomeTableModel.setColumnIdentifiers(new String[]{"Категория", "Сумма", "Комментарий", "Дата"});
        outcomeTable.setModel(outcomeTableModel);
        incomeTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        incomeTableModel.setColumnIdentifiers(new String[]{"Категория", "Сумма", "Комментарий", "Дата"});
        incomeTable.setModel(incomeTableModel);
        inCategoriesTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        inCategoriesTableModel.setColumnIdentifiers(new String[]{"Категория", "Бюджет", "Доход"});
        inCategoriesTable.setModel(inCategoriesTableModel);
        outCategoriesTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        outCategoriesTableModel.setColumnIdentifiers(new String[]{"Категория", "Бюджет", "Потрачено"});
        outCategoriesTable.setModel(outCategoriesTableModel);
    }

    private void initListeners(
            OnExitButtonClickListener onExitButtonClickListener,
            OnAddIncomeClickListener onAddIncomeClickListener,
            OnAddOutcomeClickListener onAddOutcomeClickListener,
            OnEditInCategoryClickListener onEditInCategoryClickListener,
            OnEditOutCategoryClickListener onEditOutCategoryClickListener) {

        navigationPane.addChangeListener(e -> {
            JTabbedPane pane = (JTabbedPane) e.getSource();
            if (pane.getTitleAt(pane.getSelectedIndex()).equals("Выход")) {
                onExitButtonClickListener.click();
            }
        });
        addIncomeButton.addActionListener(e -> {
            onAddIncomeClickListener.click();
        });
        addOutcomeButton.addActionListener(e -> {
            onAddOutcomeClickListener.click();
        });
        addInCategoryButton.addActionListener(e -> {
            onEditInCategoryClickListener.click(0);
        });
        addOutCategoryButton.addActionListener(e -> {
            onEditOutCategoryClickListener.click(0);
        });
    }

    void setWalletValue(String text) {
        walletAmountValue.setText(text);

    }

    void updateIncomeTable(List<String[]> rows, String sum) {
        incomeTableModel.setRowCount(0);
        for (String[] row : rows) {
            incomeTableModel.addRow(row);
        }
        inSumValue.setText(sum);
    }

    void updateOutcomeTable(List<String[]> rows, String sum) {
        outcomeTableModel.setRowCount(0);
        for (String[] row : rows) {
            outcomeTableModel.addRow(row);
        }
        outSumValue.setText(sum);
    }

    void updateInCategoriesTable(List<String[]> rows) {
        inCategoriesTableModel.setRowCount(0);
        for (String[] row : rows) {
            inCategoriesTableModel.addRow(row);
        }
    }

    void updateOutCategoriesTable(List<String[]> rows) {
        outCategoriesTableModel.setRowCount(0);
        for (String[] row : rows) {
            outCategoriesTableModel.addRow(row);
        }
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        navigationPane = new JTabbedPane();
        mainPanel.add(navigationPane, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        accountTab = new JPanel();
        accountTab.setLayout(new GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        Font accountTabFont = this.$$$getFont$$$("Fira Code", -1, 14, accountTab.getFont());
        if (accountTabFont != null) accountTab.setFont(accountTabFont);
        navigationPane.addTab("Кошелёк", accountTab);
        walletAmountLabel = new JLabel();
        Font walletAmountLabelFont = this.$$$getFont$$$(null, -1, 16, walletAmountLabel.getFont());
        if (walletAmountLabelFont != null) walletAmountLabel.setFont(walletAmountLabelFont);
        walletAmountLabel.setText("Баланс:");
        accountTab.add(walletAmountLabel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        walletAmountValue = new JLabel();
        Font walletAmountValueFont = this.$$$getFont$$$(null, -1, 16, walletAmountValue.getFont());
        if (walletAmountValueFont != null) walletAmountValue.setFont(walletAmountValueFont);
        walletAmountValue.setText("-");
        accountTab.add(walletAmountValue, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        accountTab.add(spacer1, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        accountTab.add(spacer2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        outcomeTab = new JPanel();
        outcomeTab.setLayout(new GridLayoutManager(3, 3, new Insets(0, 0, 0, 0), -1, -1));
        navigationPane.addTab("Расходы", outcomeTab);
        addOutcomeButton = new JButton();
        addOutcomeButton.setText("Добавить расход");
        outcomeTab.add(addOutcomeButton, new GridConstraints(2, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        outcomeTab.add(scrollPane1, new GridConstraints(0, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        outcomeTable = new JTable();
        scrollPane1.setViewportView(outcomeTable);
        outSumLabel = new JLabel();
        outSumLabel.setText("Всего:");
        outcomeTab.add(outSumLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        outSumValue = new JLabel();
        outSumValue.setText("-");
        outcomeTab.add(outSumValue, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        outcomeTab.add(spacer3, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        incomeTab = new JPanel();
        incomeTab.setLayout(new GridLayoutManager(3, 3, new Insets(0, 0, 0, 0), -1, -1));
        navigationPane.addTab("Доходы", incomeTab);
        final JScrollPane scrollPane2 = new JScrollPane();
        incomeTab.add(scrollPane2, new GridConstraints(0, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        incomeTable = new JTable();
        scrollPane2.setViewportView(incomeTable);
        addIncomeButton = new JButton();
        addIncomeButton.setText("Добавить доход");
        incomeTab.add(addIncomeButton, new GridConstraints(2, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        inSumLabel = new JLabel();
        inSumLabel.setText("Всего:");
        incomeTab.add(inSumLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        inSumValue = new JLabel();
        inSumValue.setText("-");
        incomeTab.add(inSumValue, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        incomeTab.add(spacer4, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        incomeCategoriesTab = new JPanel();
        incomeCategoriesTab.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        navigationPane.addTab("Категории доходов", incomeCategoriesTab);
        final JScrollPane scrollPane3 = new JScrollPane();
        incomeCategoriesTab.add(scrollPane3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        inCategoriesTable = new JTable();
        scrollPane3.setViewportView(inCategoriesTable);
        addInCategoryButton = new JButton();
        addInCategoryButton.setText("Добавить категорию дохода");
        incomeCategoriesTab.add(addInCategoryButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        outcomeCategoriesTab = new JPanel();
        outcomeCategoriesTab.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        navigationPane.addTab("Категории расходов", outcomeCategoriesTab);
        final JScrollPane scrollPane4 = new JScrollPane();
        outcomeCategoriesTab.add(scrollPane4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        outCategoriesTable = new JTable();
        scrollPane4.setViewportView(outCategoriesTable);
        addOutCategoryButton = new JButton();
        addOutCategoryButton.setText("Добавить категорию расхода");
        outcomeCategoriesTab.add(addOutCategoryButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        logoutTab = new JPanel();
        logoutTab.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        navigationPane.addTab("Выход", logoutTab);
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }


}
