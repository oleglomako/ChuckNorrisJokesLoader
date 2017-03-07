package com.example.oleg.recyclerviewandfragments;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * одна активити,
 * в ней два фрагмента.
 * оба на полный экран.
 * первый - со списком айтемов.
 * во втором - детали айтема.
 * второй заменяет первый и открывается на весь экран после нажатия на айтем
 *
 * да давай пока без кнопки, с фрагментами и просто с ресайклером надо разобраться сначала
 *
 * там в документации ретрофита написано,
 * с какими библиотеками для парсинга json он умеет из коробки работать.
 * там есть специальные адаптеры
 *
 * самая простая, наверное, - это moshi. попробуй с ней
 */

public class MainActivity extends FragmentActivity {

    private JsonLoader jsonLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //убрать заголовок окна
        setContentView(R.layout.activity_main);
        jsonLoader = new JsonLoader();
        jsonLoader.execute();
    }

    public void onClickButton(View view) {
        jsonLoader = new JsonLoader();
        jsonLoader.execute();

        // находим список
        ListView lvMain = (ListView) findViewById(R.id.listView);

        // создаем адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, JsonLoader.content);

        // присваиваем адаптер списку
        lvMain.setAdapter(adapter);
    }


}
