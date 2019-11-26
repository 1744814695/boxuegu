package boxuegu.com.boxuegu.view;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;

import boxuegu.com.boxuegu.R;
import boxuegu.com.boxuegu.fragment.AdBannerFragment;

public class MyAdBanner {
    private View view;
    private List<AdBannerFragment> adList;
    private int count;
    private ViewPager vp_advertBanner;
    private LinearLayout ll_dots;
    private ImageView[] dots;
    private AppCompatActivity context;
    private int newDotIndex;//表示新的蓝色小圆点序号
    private int preDotIndex;//表示前一个蓝色小圆点
    private int index;//表示当前广告图片序号
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {//这里是主线程执行，分析子线程发来的消息，更改外观
            if(msg.what==1){
                vp_advertBanner.setCurrentItem(index,true);
                autoPlay();
            }
        }
    };

    public MyAdBanner(AppCompatActivity context, List<AdBannerFragment> list) {
        this.adList=list;
        this.count=list.size();
        this.context=context;

        //下面为数据源首末添加两张缓冲碎片
        AdBannerFragment fragment=AdBannerFragment.newInstance("banner_3");
        this.adList.add(0,fragment);
        fragment=AdBannerFragment.newInstance("banner_1");
        this.adList.add(fragment);

        LayoutInflater layoutInflater=LayoutInflater.from(context);
        view=layoutInflater.inflate(R.layout.main_adbanner,null);
        initView();
    }

    private void initView(){
        //用来初始化界面。1、找到界面元素
        vp_advertBanner=view.findViewById(R.id.vp_advertBanner);
        AdBannerAdApter adApter=new AdBannerAdApter(context.getSupportFragmentManager(),this.adList);
        vp_advertBanner.setAdapter(adApter);
        vp_advertBanner.setCurrentItem(1,false);
        vp_advertBanner.setOffscreenPageLimit(1);

        vp_advertBanner.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //监听用户的侧滑动作
            @Override
            public void onPageScrolled(int i, float v, int i1) {//当手在屏幕滑动过程中多次执行

            }

            @Override
            public void onPageSelected(int i) {//页面切换已经成功
                if(i==count+1){//已经侧滑到末尾
                    index=1;
                    newDotIndex=0;
                    vp_advertBanner.setCurrentItem(index,false);
                }else if(i==0){//已经侧滑到末尾
                    index=count;
                    newDotIndex=count-1;
                    vp_advertBanner.setCurrentItem(index,false);
                }else {
                    index=i;
                    newDotIndex=i-1;
                }
                setCurrentDot();
            }

            @Override
            public void onPageScrollStateChanged(int i) {//侧滑动作，手已经离开

            }
        });


        ll_dots=view.findViewById(R.id.ll_dots);
        initDots();
        preDotIndex=newDotIndex=0;
        setCurrentDot();

        autoPlay();
    }

    private void autoPlay(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                index++;
                //发送消息给主线程，通知主线程更改外观（通过handler发送）
                handler.sendEmptyMessageDelayed(1,3000);
            }
        }.start();
    }

    private void setCurrentDot(){
        dots[preDotIndex].setImageResource(R.drawable.indicator_off);
        dots[newDotIndex].setImageResource(R.drawable.indicator_on);
        preDotIndex=newDotIndex;
    }

    private void initDots(){
        dots=new ImageView[count];
        for(int i=0;i<count;i++){
            dots[i]=new ImageView(context);
            dots[i].setImageResource(R.drawable.indicator_off);
            dots[i].setPadding(5,0,5,0);
            ll_dots.addView(dots[i]);
        }
    }
    public View getView(){
        return this.view;
    }

    class AdBannerAdApter extends FragmentStatePagerAdapter{//作用是联系viewpager和广告内容数据源
        private List<AdBannerFragment> ads;

        public AdBannerAdApter(FragmentManager fm,List<AdBannerFragment> ads) {
            super(fm);
            this.ads=ads;
        }

        @Override
        public int getCount() {
            if(ads==null) return 0;
            return ads.size();
        }

        @Override
        public Fragment getItem(int i) {
            if(ads==null) return null;
            return ads.get(i);
        }
    }
}
