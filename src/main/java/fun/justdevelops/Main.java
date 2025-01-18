package fun.justdevelops;

import fun.justdevelops.controller.FinanceController;
import fun.justdevelops.model.repo.SQLiteRepo;
import fun.justdevelops.ui.SwingView;

public class Main {

    public static void main(String[] args) {
        try {
            new FinanceController(new SwingView(), new SQLiteRepo()).startApplication();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}