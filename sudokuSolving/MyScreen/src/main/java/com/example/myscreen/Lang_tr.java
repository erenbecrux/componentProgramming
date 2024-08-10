package com.example.myscreen;

import java.util.ListResourceBundle;

public class Lang_tr extends ListResourceBundle {

    @Override
    protected Object[][] getContents() {
        Object[][] contents = new Object[3][3];

        contents[0][0] = "Author 1";
        contents[0][1] = "Eren Erguven";
        contents[1][0] = "Author 2";
        contents[1][1] = "Bahar Salman";
        contents[2][0] = "Authors";
        contents[2][1] = "Yazarlar:";


        return contents;
    }
}