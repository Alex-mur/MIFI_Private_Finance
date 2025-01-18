package fun.justdevelops.ui.listeners;

import fun.justdevelops.ui.View;

public interface OnSaveCategoryClickListener {
    void click(long id, String name, View.TRANSACTION_TYPE type, String budget);
}
