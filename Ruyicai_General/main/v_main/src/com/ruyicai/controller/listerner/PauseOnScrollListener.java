package com.ruyicai.controller.listerner;

import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

public class PauseOnScrollListener implements OnScrollListener {

    private OnListViewOnScrollChangListener onListViewOnScrollChangListener;
    private final boolean pauseOnScroll;
    private final boolean pauseOnFling;
    public PauseOnScrollListener(OnListViewOnScrollChangListener onListViewOnScrollChangListener,boolean pauseOnScroll, boolean pauseOnFling) {
        this.onListViewOnScrollChangListener=onListViewOnScrollChangListener;
        this.pauseOnScroll = pauseOnScroll;
        this.pauseOnFling = pauseOnFling;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
            case OnScrollListener.SCROLL_STATE_IDLE:
            	onListViewOnScrollChangListener.onIdle();
                break;
            case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                if (pauseOnScroll) {
                	onListViewOnScrollChangListener.onFling();
                }
                break;
            case OnScrollListener.SCROLL_STATE_FLING:
                if (pauseOnFling) {
                	onListViewOnScrollChangListener.onTouchScroll();
                }
                break;
        }
    }
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
    }
}