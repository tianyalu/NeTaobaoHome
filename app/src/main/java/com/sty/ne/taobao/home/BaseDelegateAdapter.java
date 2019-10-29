package com.sty.ne.taobao.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;

/**
 * Created by tian on 2019/10/29.
 */

public class BaseDelegateAdapter extends DelegateAdapter.Adapter<BaseViewHolder> {
    private LayoutHelper mLayoutHelper;
    private int mCount = -1;
    private int mLayoutId = -1;
    private Context mContext;
    private int mViewTypeItem = -1;

    public BaseDelegateAdapter( Context mContext, LayoutHelper mLayoutHelper, int mLayoutId,
                                int mCount, int mViewTypeItem) {
        this.mContext = mContext;
        this.mLayoutHelper = mLayoutHelper;
        this.mLayoutId = mLayoutId;
        this.mCount = mCount;
        this.mViewTypeItem = mViewTypeItem;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return mLayoutHelper;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == mViewTypeItem) {
            return new BaseViewHolder(LayoutInflater.from(mContext).inflate(mLayoutId, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {

    }

    /**
     * 必须重写，不然会出现滑动不流畅的问题
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return mViewTypeItem;
    }

    @Override
    public int getItemCount() {
        return mCount;
    }
}
