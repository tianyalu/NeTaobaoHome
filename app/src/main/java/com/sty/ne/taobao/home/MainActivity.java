package com.sty.ne.taobao.home;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.bumptech.glide.Glide;
import com.sunfusheng.marqueeview.MarqueeView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    interface Config {
        //不同item必须使用不同的viewType
        int BANNER_VIEW_TYPE = 1;
        int MENU_VIEW_TYPE = 2;
        int NEWS_VIEW_TYPE = 3;
        int TITLE_VIEW_TYPE = 4;
        int GRID_VIEW_TYPE = 5;
    }

    private RecyclerView mRecyclerView;
    //应用
    String[] ITEM_NAMES = {"天猫", "聚划算", "天猫国际", "外卖", "天猫超市", "充值中心", "飞猪旅行",
            "领金币", "拍卖", "分类"};
    int[] IMG_URLS = {R.drawable.ic_tian_mao, R.drawable.ic_ju_hua_suan, R.drawable.ic_tian_mao_guoji,
        R.drawable.ic_waimai, R.drawable.ic_chaoshi, R.drawable.ic_voucher_center, R.drawable.ic_travel,
        R.drawable.ic_tao_gold, R.drawable.ic_auction, R.drawable.ic_classify};
    //高颜值商品位
    int[] ITEM_URL = {R.drawable.item1, R.drawable.item2, R.drawable.item3, R.drawable.item4,
            R.drawable.item5 };
    int[] GRID_URL = {R.drawable.flashsale1, R.drawable.flashsale2, R.drawable.flashsale3,
            R.drawable.flashsale4};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.recycler_view);
        VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(this);
        mRecyclerView.setLayoutManager(virtualLayoutManager);
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        mRecyclerView.setRecycledViewPool(viewPool);
        //对type=1的item设置了复用池的大小，如果你的页面有多种type，需要为每一种类型的分别调整复用池大小参数
        viewPool.setMaxRecycledViews(Config.BANNER_VIEW_TYPE, 10);
        viewPool.setMaxRecycledViews(Config.MENU_VIEW_TYPE, 10);
        viewPool.setMaxRecycledViews(Config.NEWS_VIEW_TYPE, 10);
        viewPool.setMaxRecycledViews(Config.TITLE_VIEW_TYPE, 10);
        viewPool.setMaxRecycledViews(Config.GRID_VIEW_TYPE, 10);
//        mRecyclerView.setAdapter(new BannerAdapter(this));

        //Banner布局
        BaseDelegateAdapter bannerAdapter = new BaseDelegateAdapter(this, new LinearLayoutHelper(),
                R.layout.vlayout_banner, 1, Config.BANNER_VIEW_TYPE){
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                ArrayList<Integer> arrayList = new ArrayList<>();
                for (int i = 0; i < ITEM_URL.length; i++) {
                    arrayList.add(ITEM_URL[i]);
                }
//            arrayList.add("http://pic1.win4000.com/wallpaper/a/584e4a680d25b.jpg");
//            arrayList.add("http://pic1.win4000.com/wallpaper/a/584e4a6d1214d.jpg");
//            arrayList.add("http://pic1.win4000.com/wallpaper/a/584e4a71e25ac.jpg");
//            arrayList.add("http://pic1.win4000.com/wallpaper/2/584e47a023e36.jpg");
//            arrayList.add("http://pic1.win4000.com/wallpaper/2/584e47a2822ae.jpg");
//            arrayList.add("http://pic1.win4000.com/wallpaper/2/584e47aa43ffb.jpg");
                //绑定数据
                Banner mBanner = holder.getView(R.id.banner);
                //设置banner样式
                mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
                //设置图片集合
                mBanner.setImages(arrayList);
                //设置banner动画效果
                mBanner.setBannerAnimation(Transformer.DepthPage);
                //设置标题集合（当banner样式有显示title时）
                //mBanner.setBannerTitles(titles);
                //设置自动轮播，默认为true
                mBanner.isAutoPlay(true);
                //设置轮播时间
                mBanner.setDelayTime(3000);
                //设置指示器位置
                mBanner.setIndicatorGravity(BannerConfig.CENTER);
                //设置图片加载器
                mBanner.setImageLoader(new GlideImageLoader());
                //banner设置方法全部调用完毕后调用
                mBanner.start();

                mBanner.setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        Toast.makeText(getApplicationContext(), "banner点击了 " + position, Toast.LENGTH_SHORT).show();
                    }
                });

                super.onBindViewHolder(holder, position);
            }
        };

        //Grid布局
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(5);
        gridLayoutHelper.setPadding(0, 16, 0, 0);
        gridLayoutHelper.setVGap(10); //控制子元素之间的竖直间距
        gridLayoutHelper.setHGap(0); //控制子元素之间的水平间距
        BaseDelegateAdapter menuAdapter = new BaseDelegateAdapter(this, gridLayoutHelper,
                R.layout.vlayout_menu, 10, Config.MENU_VIEW_TYPE){
            @Override
            public void onBindViewHolder(BaseViewHolder holder, final int position) {
                holder.setText(R.id.tv_menu_title_home, ITEM_NAMES[position] + "");
                holder.setImageResource(R.id.iv_menu_home, IMG_URLS[position]);
                holder.getView(R.id.ll_menu_home).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), ITEM_NAMES[position], Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };

        //marquee布局
        BaseDelegateAdapter newsAdapter = new BaseDelegateAdapter(this, new LinearLayoutHelper(),
                R.layout.vlayout_news, 1, Config.NEWS_VIEW_TYPE) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                MarqueeView marqueeView1 = holder.getView(R.id.marquee_view1);
                MarqueeView marqueeView2 = holder.getView(R.id.marquee_view2);

                List<String> info1 = new ArrayList<>();
                info1.add("天猫超市最近发布大活动啦，快来看啊");
                info1.add("没有最便宜，只有更便宜！");

                List<String> info2 = new ArrayList<>();
                info2.add("这个是用来搞笑的，不用在意这些细节");
                info2.add("啦啦啦啦啦啦， 我就是来搞笑的...");

                marqueeView1.startWithList(info1);
                marqueeView2.startWithList(info2);
                //在代码里面设置自己的动画
                marqueeView1.startWithList(info1, R.anim.anim_bottom_in, R.anim.anim_top_out);
                marqueeView2.startWithList(info1, R.anim.anim_bottom_in, R.anim.anim_top_out);

                marqueeView1.setOnItemClickListener(new MarqueeView.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position, TextView textView) {
                        Toast.makeText(getApplicationContext(), textView.getText().toString(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
                marqueeView2.setOnItemClickListener(new MarqueeView.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position, TextView textView) {
                        Toast.makeText(getApplicationContext(), textView.getText().toString(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };

        //当hasConsistItemType=true的时候，不论是不是属于同一个子adapter，相同类型的item都能复用。
        //表示它们共享一个类型。 当hasConsistItemType=false的时候，不同子adapter之间的类型不共享
        DelegateAdapter delegateAdapter = new DelegateAdapter(virtualLayoutManager, true);
        delegateAdapter.addAdapter(bannerAdapter);
        delegateAdapter.addAdapter(menuAdapter);
        delegateAdapter.addAdapter(newsAdapter);


        //商品Items
        for (int i = 0; i < ITEM_URL.length; i++) {
            final int finalI = i;
            BaseDelegateAdapter titleAdapter = new BaseDelegateAdapter(this,
                    new LinearLayoutHelper(), R.layout.vlayout_title, 1, Config.TITLE_VIEW_TYPE){
                @Override
                public void onBindViewHolder(BaseViewHolder holder, int position) {
                    super.onBindViewHolder(holder, position);
                    holder.setImageResource(R.id.iv_title, ITEM_URL[finalI]);
                }
            };

            GridLayoutHelper gridHelper = new GridLayoutHelper(2);
            BaseDelegateAdapter gridAdapter = new BaseDelegateAdapter(this, gridHelper,
                    R.layout.vlayout_grid, 4, Config.GRID_VIEW_TYPE){
                @Override
                public void onBindViewHolder(final BaseViewHolder holder, final int position) {
                    int item = GRID_URL[position];
                    ImageView ivContent = holder.getView(R.id.iv_content);
                    Glide.with(getApplicationContext()).load(item).into(ivContent);
                    ivContent.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(getApplicationContext(), "item " + position,
                                    Toast.LENGTH_SHORT).show();
                        }
                    });

                    super.onBindViewHolder(holder, position);
                }
            };

            delegateAdapter.addAdapter(titleAdapter);
            delegateAdapter.addAdapter(gridAdapter);
        }
        mRecyclerView.setAdapter(delegateAdapter);
    }

//    class BannerAdapter extends DelegateAdapter.Adapter<BaseViewHolder> {
//        private Context mContext;
//
//        public BannerAdapter(Context mContext) {
//            this.mContext = mContext;
//        }
//
//        @Override
//        public LayoutHelper onCreateLayoutHelper() {
//            return new LinearLayoutHelper();
//        }
//
//        @Override
//        public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            return new BaseViewHolder(LayoutInflater.from(mContext).inflate(R.layout.vlayout_banner,
//                    parent, false));
//        }
//
//        @Override
//        public void onBindViewHolder(BaseViewHolder holder, int position) {
////            ArrayList<String> arrayList = new ArrayList<>();
//            ArrayList<Integer> arrayList = new ArrayList<>();
//            for (int i = 0; i < ITEM_URL.length; i++) {
//                arrayList.add(ITEM_URL[i]);
//            }
////            arrayList.add("http://pic1.win4000.com/wallpaper/a/584e4a680d25b.jpg");
////            arrayList.add("http://pic1.win4000.com/wallpaper/a/584e4a6d1214d.jpg");
////            arrayList.add("http://pic1.win4000.com/wallpaper/a/584e4a71e25ac.jpg");
////            arrayList.add("http://pic1.win4000.com/wallpaper/2/584e47a023e36.jpg");
////            arrayList.add("http://pic1.win4000.com/wallpaper/2/584e47a2822ae.jpg");
////            arrayList.add("http://pic1.win4000.com/wallpaper/2/584e47aa43ffb.jpg");
//            //绑定数据
//            Banner mBanner = holder.getView(R.id.banner);
//            //设置banner样式
//            mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
//            //设置图片集合
//            mBanner.setImages(arrayList);
//            //设置banner动画效果
//            mBanner.setBannerAnimation(Transformer.DepthPage);
//            //设置标题集合（当banner样式有显示title时）
//            //mBanner.setBannerTitles(titles);
//            //设置自动轮播，默认为true
//            mBanner.isAutoPlay(true);
//            //设置轮播时间
//            mBanner.setDelayTime(3000);
//            //设置指示器位置
//            mBanner.setIndicatorGravity(BannerConfig.CENTER);
//            //设置图片加载器
//            mBanner.setImageLoader(new GlideImageLoader());
//            //banner设置方法全部调用完毕后调用
//            mBanner.start();
//
//            mBanner.setOnBannerListener(new OnBannerListener() {
//                @Override
//                public void OnBannerClick(int position) {
//                    Toast.makeText(getApplicationContext(), "banner点击了 " + position, Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
//
//        @Override
//        public int getItemCount() {
//            return 1;
//        }
//    }
}
