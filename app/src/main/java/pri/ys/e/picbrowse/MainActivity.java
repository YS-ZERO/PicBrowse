package pri.ys.e.picbrowse;

import android.os.Bundle;

import net.ys.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import ys.picbrowse.view.PicPageOptionView;

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

        List<String> list = new ArrayList<String>();
        for (int i = 0; i < 15; i++) {
            list.add("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png");
        }



    }


}
