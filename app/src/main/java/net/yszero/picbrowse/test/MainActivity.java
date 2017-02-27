package net.yszero.picbrowse.test;

import android.os.Bundle;

import net.ys.base.BaseActivity;
import net.yszero.picbrowse.PicPageOptionView;


public class MainActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        PicPageOptionView view = (PicPageOptionView) findViewById(R.id.pic_page_option);

        view.setOnPicPageOptionListener(new PicPageOptionView.onPicPageOptionListener() {
            @Override
            public void pageOption(int item) {
                showToast(item);
            }
        });


    }


}
