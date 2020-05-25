package com.lost.administrator.md.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.lost.administrator.md.R;
import com.lost.administrator.md.entity.CollectionBean;
import com.lost.administrator.md.entity.Store;

import java.util.List;

/**
 * Created
 */
public class RecyclerViewCollAdapter extends RecyclerView.Adapter<RecyclerViewCollAdapter.ViewHolder> {
    private LayoutInflater mLayoutInflater;
    protected List<CollectionBean> mListContentData;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;
    //定义接口
    public interface OnItemClickListener {
        void onItemClick(ViewHolder holder, CollectionBean store);

    }
    public interface OnItemLongClickListener {
        void onItemLongClick(ViewHolder holder, CollectionBean store);

    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.onItemLongClickListener = listener;
    }

    public RecyclerViewCollAdapter(Context context, List<CollectionBean> datas) {
        this.mListContentData = datas;
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);

    }

    public void updateAll(List<CollectionBean> mList) {
        this.mListContentData = mList;
        notifyDataSetChanged();
    }

    //创建ViewHolder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("TAG", "Hellow");
        View v = mLayoutInflater.inflate(R.layout.item_menu_content, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    //绑定ViewHolder
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        CollectionBean store = mListContentData.get(position);

//设置图片圆角角度
        RoundedCorners roundedCorners = new RoundedCorners(20);
//通过RequestOptions扩展功能
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(800, 800);

        Glide.with(mContext).load(store.getpicture()).apply(options).into(holder.mImageView);
        holder.mTitle.setText(store.getName());
        holder.mYueSale.setText(store.getType());
        holder.mPrice.setText(store.getprice());
        setOnListtener(holder, store);
    }

    //触发
    protected void setOnListtener(final ViewHolder holder, final CollectionBean store) {
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder, store);
                }
            });

        }
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemLongClickListener != null) {
                    onItemLongClickListener.onItemLongClick(holder, store);
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListContentData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView, mImgJia, mImgJian;
        public TextView mTitle, mYueSale, mPrice, mNumber;
        public RatingBar mRatingBar;

        public ViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.item_menu_content_img);
            mTitle = (TextView) itemView.findViewById(R.id.item_menu_content_title);

            mYueSale = (TextView) itemView.findViewById(R.id.item_menu_content_sale);
            mPrice = (TextView) itemView.findViewById(R.id.item_menu_content_price);

        }
    }
}
