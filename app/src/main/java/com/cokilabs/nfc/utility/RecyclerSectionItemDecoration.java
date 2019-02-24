package com.cokilabs.nfc.utility;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cokilabs.nfc.R;
import com.cokilabs.nfc.model.Jadwal;


public class RecyclerSectionItemDecoration extends RecyclerView.ItemDecoration {

    private final int             headerOffset;
    private final boolean         sticky;
    private final SectionCallback sectionCallback;

    private View headerView;
    private TextView header, ipk, tahunAjaran;

    public RecyclerSectionItemDecoration(int headerHeight, boolean sticky, @NonNull SectionCallback sectionCallback) {
        headerOffset = headerHeight;
        this.sticky = sticky;
        this.sectionCallback = sectionCallback;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int pos = parent.getChildAdapterPosition(view);
        if (sectionCallback.isSection(pos)) {
            outRect.top = headerOffset;
        }

//        if (sectionCallback.isSection(pos+1)) {
//            outRect.bottom = headerOffset/2;
//        }


    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        if (headerView == null) {
            headerView = inflateHeaderView(parent);
            header = (TextView) headerView.findViewById(R.id.tv_semester);
//            tahunAjaran = (TextView) headerView.findViewById(R.id.tv_header);
//            ipk = (TextView) headerView.findViewById(R.id.tv_ipk);
            fixLayoutSize(headerView, parent);
        }


        CharSequence previousHeader = "";
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            final int position = parent.getChildAdapterPosition(child);

            CharSequence title = sectionCallback.getSectionHeader(position);
            Jadwal nilai = sectionCallback.getObject(position);
            header.setText(title);
//            ipk.setText(ipks);
//            tahunAjaran.setText(nilai.getTahunAjaran());
            if (!previousHeader.equals(title) || sectionCallback.isSection(position)) {
                drawHeader(c, child, headerView);
                previousHeader = title;
            }
        }
    }

    private void drawHeader(Canvas c, View child, View headerView) {
        c.save();
        if (sticky) {
            c.translate(0, Math.max(0, child.getTop() - headerView.getHeight()));
        } else {
            c.translate(0, child.getTop() - headerView.getHeight());
        }
        headerView.draw(c);
        c.restore();
    }

    private View inflateHeaderView(RecyclerView parent) {
        return LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_header_jadwal, parent, false);
    }

    private void fixLayoutSize(View view, ViewGroup parent) {
        int widthSpec = View.MeasureSpec.makeMeasureSpec(parent.getWidth(),
                View.MeasureSpec.EXACTLY);
        int heightSpec = View.MeasureSpec.makeMeasureSpec(parent.getHeight(),
                View.MeasureSpec.UNSPECIFIED);

        int childWidth = ViewGroup.getChildMeasureSpec(widthSpec,
                parent.getPaddingLeft() + parent.getPaddingRight(),
                view.getLayoutParams().width);
        int childHeight = ViewGroup.getChildMeasureSpec(heightSpec,
                parent.getPaddingTop() + parent.getPaddingBottom(),
                view.getLayoutParams().height);


        Log.e("width_parent", String.valueOf(widthSpec));
        Log.e("height_parent", String.valueOf(heightSpec));
        Log.e("width_child", String.valueOf(childWidth));
        Log.e("height_child", String.valueOf(childHeight));

        view.measure(childWidth, childHeight);

        Log.e("w", String.valueOf(view.getMeasuredWidth()));
        Log.e("h", String.valueOf(view.getMeasuredHeight()));

        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
    }

    public interface SectionCallback {

        boolean isSection(int position);

        CharSequence getSectionHeader(int position);
        Jadwal getObject(int position);
    }
}
