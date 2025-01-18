package fun.justdevelops.ui.listeners;

import fun.justdevelops.ui.View;

public interface OnPerformTransactionClickListener {
    void click(View.TRANSACTION_TYPE type, String category, String amount, String comment);
}
