package net.yszero.picbrowse;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.ys.utils.ImgTypeCast;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by YS on 2016/1/12.
 */
public class PicPageOptionView extends LinearLayout implements
        View.OnTouchListener,
        ViewPager.OnPageChangeListener,
        View.OnClickListener {


    private Context mContext;
    private ViewPager viewPager;
    private LinearLayout ll;
    private HorizontalScrollView scrollView;
    private int width; //每张图片的长度

    /**
     * 标识
     */
    private TextView[] tvs = new TextView[5];
    private LinearLayout llTvs;

    /**
     * 标识ID
     */
    private int[] ids = new int[]{R.id.view_tv_01, R.id.view_tv_02, R.id.view_tv_03, R.id.view_tv_04, R.id.view_tv_05};
    /**
     * pageview所需图片
     */
    private ImageView[] ivs = new ImageView[15];

    public PicPageOptionView(Context context) {
        this(context, null);
    }

    public PicPageOptionView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PicPageOptionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.mContext = context;

        //  导入布局，不需要再使用view.findviewbyid.... 直接使用findviewbyid
        LayoutInflater.from(context).inflate(R.layout.custom_view_group, this, true);


        llTvs = (LinearLayout) findViewById(R.id.temp1);


        /**初始化 五个 标签 加上点击效果*/
        for (int i = 0; i < ids.length; i++) {
            tvs[i] = (TextView) findViewById(ids[i]);
            tvs[i].setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int j = 0; j < ids.length; j++) {
                        if (ids[j] == v.getId()) {
                            viewPager.setCurrentItem(j * 3, true); //带切换效果，不设默认就是true

                            break;
                        }
                    }
                }
            });
        }


        scrollView = (HorizontalScrollView) findViewById(R.id.horizontalScrollView);


        scrollView.setOnTouchListener(this); //禁止touch滑动,但如果使用代码设置滚动，又会被解开,需要在每次滚动后重新设置监听

        viewPager = (ViewPager) findViewById(R.id.main_viewPager);
        viewPager.setOnPageChangeListener(this);

        ll = (LinearLayout) findViewById(R.id.ll);


        ViewGroup.LayoutParams p = viewPager.getLayoutParams();
        p.height = ((Activity) context).getWindowManager().getDefaultDisplay().getWidth() * 2 / 3;
        viewPager.setLayoutParams(p);

        initData(context);

        //设置默认选择项
        tvs[0].setTextColor(0xffff0000);
        defaultItem(0);
    }


    public void showTvs(boolean isShow) {
        if (isShow) {
            llTvs.setVisibility(View.VISIBLE);
        } else {
            llTvs.setVisibility(View.GONE);
        }
    }


    private List<View> mPageList;
    private ViewPagerAdapter mViewPagerAdapter;

    /***
     * 设置默认显示的图片
     * 及点击效果
     *
     * @param context
     */
    private void initData(Context context) {
        Display d = ((Activity) context).getWindowManager().getDefaultDisplay();

        mPageList = new ArrayList<View>();


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        /**大图_viewPage*/
        for (int i = 0; i < 15; i++) {
            View view = inflater.inflate((R.layout.iv_item_view), null);

            //viewpage包含的必须是一个个的页面，不能是单独的控件,就是说必须为viewGroup
            /*ImageView iv = new ImageView(context);
            iv.setImageResource(R.drawable.ys);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            ViewGroup.LayoutParams templ = iv.getLayoutParams();
            templ.height = ViewGroup.LayoutParams.MATCH_PARENT;
            templ.width = ViewGroup.LayoutParams.MATCH_PARENT;
            iv.setLayoutParams(templ);
            list.add(iv);*/

            view.setOnClickListener(this);
            mPageList.add(view);
        }
        mViewPagerAdapter = new ViewPagerAdapter(mPageList);
        viewPager.setAdapter(mViewPagerAdapter);
        viewPager.setCurrentItem(0);


        /**小图*/
        for (int i = 0; i < 15; i++) {
            ivs[i] = new ImageView(context);
            ivs[i].setId(10000 + i);

            ivs[i].setScaleType(ImageView.ScaleType.FIT_XY);

            ViewGroup.LayoutParams temp = ll.getLayoutParams();


            temp.width = d.getWidth() / 5 + d.getWidth() % 5;
            temp.height = temp.width;

            width = temp.width;

            ivs[i].setLayoutParams(temp);

            ivs[i].setBackgroundResource(R.drawable.default_img);

            ivs[i].setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = v.getId();
                    int position = id - 10000;
                    //Toast.makeText(context, "" + position, Toast.LENGTH_SHORT).show();
                    //点击小图切换到指定页
                    viewPager.setCurrentItem(position, true);   //切换到指定页
                }
            });

            ll.addView(ivs[i]);

        }


    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    int last = 0;

    @Override //0~14  //页面改变后，调用
    public void onPageSelected(int position) {
        int n = 0;
        if (position != 0) {
            n = position / 3;
        }
        setAllTvColor();
        tvs[n].setTextColor(0xffff0000);


        ivs[position].setImageResource(R.color.iv_item_color2);

        if (position - last > 0) { //>0右移     //<0左移
            Log.e("yygyso", "1");
            if (position > 2) {
                scrollView.smoothScrollTo(position * width - width * 2, 0);
            }

        } else if (position - last < 0) {
            Log.e("yygyso", "-1");
            if (position < 12) {
                scrollView.smoothScrollTo(position * width - width * 2, 0);
            }

        } else {
            Log.e("yygyso", "0");
            scrollView.smoothScrollTo(position * width, 0);
        }
        last = position;

    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    public void setAllTvColor() {
        for (int i = 0; i < tvs.length; i++) {
            tvs[i].setTextColor(0xff000000);

        }
        for (int i = 0; i < ivs.length; i++) {
            ivs[i].setImageResource(R.color.iv_item_color1);
        }

    }

    private void defaultItem(int item) {
        for (int i = 0; i < ivs.length; i++) {
            if (item == i) {
                ivs[item].setImageResource(R.color.iv_item_color2);
            } else {
                ivs[i].setImageResource(R.color.iv_item_color1);
            }
        }
    }


    /**
     * 一次性设置全部
     * 传入图片的资源ID
     * 传入图片类型：integer resource id
     *
     * @param list
     */
    public void setImageSrcId(List<Integer> list) {
        viewPager.removeAllViews();
        List<View> pageList = new ArrayList<View>();
        for (int i = 0; i < 15; i++) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.iv_item_view, null);
            ImageView iv = (ImageView) view.findViewById(R.id.img);

            WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            int screenWidth = wm.getDefaultDisplay().getWidth();
            int screenHeight = wm.getDefaultDisplay().getWidth() * 2 / 3;

            ViewGroup.LayoutParams params = iv.getLayoutParams();
            iv.setMaxWidth(screenWidth);
            iv.setMaxHeight(screenHeight);

            iv.setImageResource(list.get(i));
            iv.setOnClickListener(this);

            pageList.add(view);
        }
        mViewPagerAdapter = new ViewPagerAdapter(mPageList);
        viewPager.setAdapter(mViewPagerAdapter);

        for (int i = 0; i < 15; i++) {
            ivs[i].setBackgroundResource(list.get(i));
        }
    }

    /**
     * 一次性设置全部
     * 传入bitmap对象
     * 传入图片类型：bitmap
     *
     * @param list
     */
    public void setImageSrcBit(List<Bitmap> list) {
        if (list == null || list.size() != 15) {
            Toast.makeText(mContext, "传入List为空或，数量不满15", Toast.LENGTH_SHORT).show();
            return;
        }

        viewPager.removeAllViews();
        List<View> pageList = new ArrayList<View>();
        for (int i = 0; i < 15; i++) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.iv_item_view, null);
            ImageView iv = (ImageView) view.findViewById(R.id.img);

            WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            int screenWidth = wm.getDefaultDisplay().getWidth();
            int screenHeight = wm.getDefaultDisplay().getWidth() * 2 / 3;

            ViewGroup.LayoutParams params = iv.getLayoutParams();
            iv.setMaxWidth(screenWidth);
            iv.setMaxHeight(screenHeight);

            iv.setImageBitmap(list.get(i));
            iv.setOnClickListener(this);

            pageList.add(view);
        }
        mViewPagerAdapter = new ViewPagerAdapter(mPageList);
        viewPager.setAdapter(mViewPagerAdapter);

        for (int i = 0; i < 15; i++) {
            Drawable drawable = ImgTypeCast.bitToDraw(mContext, list.get(i));
            ivs[i].setBackground(drawable);
            //ivs[i].setImageBitmap(list.get(i));
        }
    }

    /***
     * 单次传入bitmap对象
     *
     * @param bit
     * @param location
     */
    public void setImageSrcBit(Bitmap bit, int location) {

        if (location >= 15) {
            Toast.makeText(mContext, "越界了", Toast.LENGTH_SHORT).show();
            return;
        }

        int n = location;

        View view = mPageList.get(n);
        ImageView iv = (ImageView) view.findViewById(R.id.img);

        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        int screenWidth = wm.getDefaultDisplay().getWidth();
        int screenHeight = wm.getDefaultDisplay().getWidth() * 2 / 3;

        ViewGroup.LayoutParams params = iv.getLayoutParams();
        iv.setMaxWidth(screenWidth);
        iv.setMaxHeight(screenHeight);

        iv.setImageBitmap(bit); //设置大图
        Drawable drawable = ImgTypeCast.bitToDraw(mContext, bit);
        ivs[n].setBackground(drawable); //设置小图
        mViewPagerAdapter.notifyDataSetChanged();

    }

    private int tempNum = 0;

    public void addImageSrcBit(Bitmap bit) {

        if (tempNum >= 15) {
            Toast.makeText(mContext, "越界了", Toast.LENGTH_SHORT).show();
            return;
        }

        int n = tempNum;

        View view = mPageList.get(n);
        ImageView iv = (ImageView) view.findViewById(R.id.img);

        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        int screenWidth = wm.getDefaultDisplay().getWidth();
        int screenHeight = wm.getDefaultDisplay().getWidth() * 2 / 3;

        ViewGroup.LayoutParams params = iv.getLayoutParams();
        iv.setMaxWidth(screenWidth);
        iv.setMaxHeight(screenHeight);

        iv.setImageBitmap(bit); //设置大图

        Drawable drawable = ImgTypeCast.bitToDraw(mContext, bit);
        ivs[n].setBackground(drawable); //设置小图
        mViewPagerAdapter.notifyDataSetChanged();

        tempNum++;
    }


    @Override
    public void onClick(View v) {
        if (mListener != null) {
            mListener.pageOption(viewPager.getCurrentItem());
        }
    }

    /***
     * 设置图片页选择接口
     *
     * @param listener
     */
    public void setOnPicPageOptionListener(onPicPageOptionListener listener) {
        this.mListener = listener;
    }

    private onPicPageOptionListener mListener;

    public interface onPicPageOptionListener {
        public void pageOption(int item);
    }

}
