package com.dong.frameproject.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabWidget;

import java.util.ArrayList;

/**
 * 修改v4包下的FragmentTabHost
 * 使Fragment不必重新实例化
 *
 * 注意事项：
 * 里面的FrameLayout的id必须是android:id="@android:id/tabcontent"
 */
public class FragmentTabHost extends TabHost implements TabHost.OnTabChangeListener {
    private final ArrayList<FragmentTabHost.TabInfo> mTabs = new ArrayList<>();
    private FrameLayout mRealTabContent;
    private Context mContext;
    private FragmentManager mFragmentManager;
    private int mContainerId;
    private OnTabChangeListener mOnTabChangeListener;
    private FragmentTabHost.TabInfo mLastTab;
    private boolean mAttached;

    public FragmentTabHost(Context context) {
        super(context,null);
        this.initFragmentTabHost(context, null);
    }

    public FragmentTabHost(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initFragmentTabHost(context, attrs);
    }

    private void initFragmentTabHost(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, new int[]{ android.R.attr.inflatedId }, 0, 0);
        this.mContainerId = a.getResourceId(0, 0);
        a.recycle();
        super.setOnTabChangedListener(this);
    }

    private void ensureHierarchy(Context context) {
        if (this.findViewById(android.R.id.tabs) == null) {
            LinearLayout ll = new LinearLayout(context);
            ll.setOrientation(LinearLayout.VERTICAL);
            this.addView(ll, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            TabWidget tw = new TabWidget(context);
            tw.setId(android.R.id.tabs);
            tw.setOrientation(TabWidget.HORIZONTAL);
            ll.addView(tw, new android.widget.LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.0F));

            FrameLayout fl = new FrameLayout(context);
            fl.setId(android.R.id.tabcontent);
            ll.addView(fl, new android.widget.LinearLayout.LayoutParams(0, 0, 0.0F));

            this.mRealTabContent = fl = new FrameLayout(context);
            this.mRealTabContent.setId(this.mContainerId);
            ll.addView(fl, new android.widget.LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1.0F));
        }

    }

    /** @deprecated */
    @Deprecated
    public void setup() {
        throw new IllegalStateException("Must call setup() that takes a Context and FragmentManager");
    }

    public void setup(Context context, FragmentManager manager) {
        this.ensureHierarchy(context);
        super.setup();
        this.mContext = context;
        this.mFragmentManager = manager;
        this.ensureContent();
    }

    public void setup(Context context, FragmentManager manager, int containerId) {
        this.ensureHierarchy(context);
        super.setup();
        this.mContext = context;
        this.mFragmentManager = manager;
        this.mContainerId = containerId;
        this.ensureContent();
        this.mRealTabContent.setId(containerId);
        if (this.getId() == View.NO_ID) {
            this.setId(android.R.id.tabhost);
        }

    }

    private void ensureContent() {
        if (this.mRealTabContent == null) {
            this.mRealTabContent = (FrameLayout)this.findViewById(this.mContainerId);
            if (this.mRealTabContent == null) {
                throw new IllegalStateException("No tab content FrameLayout found for id " + this.mContainerId);
            }
        }

    }

    public void setOnTabChangedListener(OnTabChangeListener l) {
        this.mOnTabChangeListener = l;
    }

    public void addTab(@NonNull TabSpec tabSpec, @NonNull Class<?> clss, @Nullable Bundle args) {
        tabSpec.setContent(new FragmentTabHost.DummyTabFactory(this.mContext));
        String tag = tabSpec.getTag();
        FragmentTabHost.TabInfo info = new FragmentTabHost.TabInfo(tag, clss, args);
        if (this.mAttached) {
            info.fragment = this.mFragmentManager.findFragmentByTag(tag);
            if (info.fragment != null && !info.fragment.isDetached()) {
                FragmentTransaction ft = this.mFragmentManager.beginTransaction();
                ft.hide(info.fragment);
//                ft.detach(info.fragment);
                ft.commit();
            }
        }

        this.mTabs.add(info);
        this.addTab(tabSpec);
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        String currentTag = this.getCurrentTabTag();
        FragmentTransaction ft = null;
        int i = 0;

        for(int count = this.mTabs.size(); i < count; ++i) {
            FragmentTabHost.TabInfo tab = (FragmentTabHost.TabInfo)this.mTabs.get(i);
            tab.fragment = this.mFragmentManager.findFragmentByTag(tab.tag);
            if (tab.fragment != null && !tab.fragment.isDetached()) {
                if (tab.tag.equals(currentTag)) {
                    this.mLastTab = tab;
                } else {
                    if (ft == null) {
                        ft = this.mFragmentManager.beginTransaction();
                    }

                    ft.hide(tab.fragment);
//                    ft.detach(tab.fragment);
                }
            }
        }

        this.mAttached = true;
        ft = this.doTabChanged(currentTag, ft);
        if (ft != null) {
            ft.commit();
            this.mFragmentManager.executePendingTransactions();
        }

    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mAttached = false;
    }

    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        FragmentTabHost.SavedState ss = new FragmentTabHost.SavedState(superState);
        ss.curTab = this.getCurrentTabTag();
        return ss;
    }

    protected void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof FragmentTabHost.SavedState)) {
            super.onRestoreInstanceState(state);
        } else {
            FragmentTabHost.SavedState ss = (FragmentTabHost.SavedState)state;
            super.onRestoreInstanceState(ss.getSuperState());
            this.setCurrentTabByTag(ss.curTab);
        }
    }

    public void onTabChanged(String tabId) {
        if (this.mAttached) {
            FragmentTransaction ft = this.doTabChanged(tabId, (FragmentTransaction)null);
            if (ft != null) {
                ft.commit();
            }
        }

        if (this.mOnTabChangeListener != null) {
            this.mOnTabChangeListener.onTabChanged(tabId);
        }

    }

    @Nullable
    private FragmentTransaction doTabChanged(@Nullable String tag, @Nullable FragmentTransaction ft) {
        FragmentTabHost.TabInfo newTab = this.getTabInfoForTag(tag);
        if (this.mLastTab != newTab) {
            if (ft == null) {
                ft = this.mFragmentManager.beginTransaction();
            }

            if (this.mLastTab != null && this.mLastTab.fragment != null) {
                ft.hide(this.mLastTab.fragment);
//                ft.detach(this.mLastTab.fragment);
            }

            if (newTab != null) {
                if (newTab.fragment == null) {
                    newTab.fragment = Fragment.instantiate(this.mContext, newTab.clss.getName(), newTab.args);
                    ft.add(this.mContainerId, newTab.fragment, newTab.tag);
                } else {
                    ft.show(newTab.fragment);
//                    ft.attach(newTab.fragment);
                }
            }

            this.mLastTab = newTab;
        }

        return ft;
    }

    @Nullable
    private FragmentTabHost.TabInfo getTabInfoForTag(String tabId) {
        int i = 0;

        for(int count = this.mTabs.size(); i < count; ++i) {
            FragmentTabHost.TabInfo tab = (FragmentTabHost.TabInfo)this.mTabs.get(i);
            if (tab.tag.equals(tabId)) {
                return tab;
            }
        }

        return null;
    }

    static class SavedState extends BaseSavedState {
        String curTab;
        public static final Creator<FragmentTabHost.SavedState> CREATOR = new Creator<FragmentTabHost.SavedState>() {
            public FragmentTabHost.SavedState createFromParcel(Parcel in) {
                return new FragmentTabHost.SavedState(in);
            }

            public FragmentTabHost.SavedState[] newArray(int size) {
                return new FragmentTabHost.SavedState[size];
            }
        };

        SavedState(Parcelable superState) {
            super(superState);
        }

        SavedState(Parcel in) {
            super(in);
            this.curTab = in.readString();
        }

        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeString(this.curTab);
        }

        public String toString() {
            return "FragmentTabHost.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " curTab=" + this.curTab + "}";
        }
    }

    static class DummyTabFactory implements TabContentFactory {
        private final Context mContext;

        public DummyTabFactory(Context context) {
            this.mContext = context;
        }

        public View createTabContent(String tag) {
            View v = new View(this.mContext);
            v.setMinimumWidth(0);
            v.setMinimumHeight(0);
            return v;
        }
    }

    static final class TabInfo {
        @NonNull
        final String tag;
        @NonNull
        final Class<?> clss;
        @Nullable
        final Bundle args;
        Fragment fragment;

        TabInfo(@NonNull String _tag, @NonNull Class<?> _class, @Nullable Bundle _args) {
            this.tag = _tag;
            this.clss = _class;
            this.args = _args;
        }
    }
}