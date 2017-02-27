package net.yszero.picbrowse.test;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Toast;

import net.yszero.picbrowse.PicPageOptionView;
import net.yszero.ysutils.utils.ImgTypeCast;


public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        PicPageOptionView view = (PicPageOptionView) findViewById(R.id.pic_page_option);

        //设置标题
        view.setTitles(new String[]{"神魔", "热血", "魔法", "国产", "励志"});

        //设置点击监听事件
        view.setOnPicPageOptionListener(new PicPageOptionView.onPicPageOptionListener() {
            @Override
            public void pageOption(int item) {
                Toast.makeText(MainActivity.this, "第 " + item + " 张图片", Toast.LENGTH_SHORT).show();
            }
        });

        int[] ids = {R.drawable.d1, R.drawable.d2, R.drawable.d3, R.drawable.d4, R.drawable.d5,
                R.drawable.d6, R.drawable.d7, R.drawable.d8, R.drawable.d9, R.drawable.d10,
                R.drawable.d11, R.drawable.d12, R.drawable.d13, R.drawable.d14, R.drawable.d15};


        //添加图片
        for (int i = 0; i < ids.length; i++) {
            Drawable d = getResources().getDrawable(ids[i]);
            Bitmap b = ImgTypeCast.drawToBit(d);
            view.addImageSrcBit(b);
        }

    }


}
