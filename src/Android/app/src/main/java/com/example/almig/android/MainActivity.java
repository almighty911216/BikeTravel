package com.example.almig.android;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.almig.android.adapter.DrawerAdapter;
import com.example.almig.android.fragment.BicycleRoadFragment;
import com.example.almig.android.fragment.DashboardFragment;
import com.example.almig.android.fragment.HomeFragment;
import com.example.almig.android.fragment.ParkingLotFragment;
import com.example.almig.android.fragment.SocialFragment;
import com.example.almig.android.model.DrawerItem;
import com.example.almig.android.util.ImageUtil;
import com.example.contextmenu.ContextMenuDialogFragment;
import com.example.contextmenu.MenuObject;
import com.example.contextmenu.MenuParams;
import com.example.contextmenu.interfaces.OnMenuItemClickListener;
import com.example.contextmenu.interfaces.OnMenuItemLongClickListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMenuItemClickListener, OnMenuItemLongClickListener {
    private ImageLoader mImageLoader;
    private Toolbar mToolbar;
    private ContextMenuDialogFragment mMenuDialogFragment;
    private Resources mRes;
    private ListView mDrawerList;
    private List<DrawerItem> mDrawerItems;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private Handler mHandler;
    private boolean mShouldFinish = false;

    private void initBinding() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerList = (ListView) findViewById(R.id.lv_menu_list);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_main);
//        mTvToolbarTitle = (TextView) findViewById(R.id.tb_title);

        mHandler = new Handler();
        mRes = getResources();
    }

    private void initImageLoader() {
        mImageLoader = ImageLoader.getInstance();
        if (!mImageLoader.isInited()) {
            mImageLoader.init(ImageLoaderConfiguration.createDefault(this));
        }
    }

    private void initMenuFragment() {
        MenuParams menuParams = new MenuParams();
        menuParams.setActionBarSize((int) getResources().getDimension(R.dimen.tool_bar_height));
        menuParams.setMenuObjects(getMenuObjects());
        menuParams.setClosableOutside(false);
        mMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
        mMenuDialogFragment.setItemClickListener(this);
        mMenuDialogFragment.setItemLongClickListener(this);
    }

    private List<MenuObject> getMenuObjects() {
        // You can use any [resource, bitmap, drawable, color] as image:
        // item.setResource(...)
        // item.setBitmap(...)
        // item.setDrawable(...)
        // item.setColor(...)
        // You can set image ScaleType:
        // item.setScaleType(ScaleType.FIT_XY)
        // You can use any [resource, drawable, color] as background:
        // item.setBgResource(...)
        // item.setBgDrawable(...)
        // item.setBgColor(...)
        // You can use any [color] as text color:
        // item.setTextColor(...)
        // You can set any [color] as divider color:
        // item.setDividerColor(...)

        List<MenuObject> menuObjects = new ArrayList<>();

        MenuObject close = new MenuObject();
        close.setResource(R.drawable.icn_close);

        MenuObject send = new MenuObject("Send message");
        send.setResource(R.drawable.icn_1);

        MenuObject like = new MenuObject("Like profile");
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.icn_2);
        like.setBitmap(b);

        MenuObject addFr = new MenuObject("Add to friends");
        BitmapDrawable bd = new BitmapDrawable(getResources(),
                BitmapFactory.decodeResource(getResources(), R.drawable.icn_3));
        addFr.setDrawable(bd);

        MenuObject addFav = new MenuObject("Add to favorites");
        addFav.setResource(R.drawable.icn_4);

        MenuObject block = new MenuObject("Block user");
        block.setResource(R.drawable.icn_5);

        menuObjects.add(close);
        menuObjects.add(send);
        menuObjects.add(like);
        menuObjects.add(addFr);
        menuObjects.add(addFav);
        menuObjects.add(block);
        return menuObjects;
    }

    private void initNavDrawerItems() {
        mDrawerItems = new ArrayList<>();
        mDrawerItems.add(new DrawerItem(getDrawable(R.drawable.ic_bicycle), R.string.activity_title_home, DrawerItem.DRAWER_ITEM_TAG_HOME));
        mDrawerItems.add(new DrawerItem(getDrawable(R.drawable.ic_speedometer), R.string.activity_title_dashboard, DrawerItem.DRAWER_ITEM_TAG_DASHBOARD));
        mDrawerItems.add(new DrawerItem(getDrawable(R.drawable.ic_bicycle), R.string.activity_title_pathfinding, DrawerItem.DRAWER_ITEM_TAG_PATHFINDING));
        mDrawerItems.add(new DrawerItem(getDrawable(R.drawable.ic_parking_lot), R.string.activity_title_parking_lot, DrawerItem.DRAWER_ITEM_TAG_PARKING_LOT));
        mDrawerItems.add(new DrawerItem(getDrawable(R.drawable.ic_social_32), R.string.activity_title_social, DrawerItem.DRAWER_ITEM_TAG_SOCIAL));
        mDrawerItems.add(new DrawerItem(getDrawable(R.drawable.ic_stamp), R.string.activity_title_stamp_auth, DrawerItem.DRAWER_ITEM_TAG_STAMP_AUTH));
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
//        mTvToolbarTitle.setText(R.string.app_name);
//        mTvToolbarTitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/custom2.ttf"));
        getSupportActionBar().setTitle(mRes.getString(R.string.app_name));
    }

    private void initDrawerToggle() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                supportInvalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                supportInvalidateOptionsMenu();
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
    }

    private View initHeaderView(int layoutRes, String url, String email) {
        View headerView = getLayoutInflater().inflate(layoutRes, mDrawerList, false);
        ImageView iv = (ImageView) headerView.findViewById(R.id.iv_profile);
        TextView tv = (TextView) headerView.findViewById(R.id.rtv_email);

        ImageUtil.displayRoundImage(iv, url, null);
        tv.setText(email);

        return headerView;
    }

    private void initAdapter() {
        View headerView = initHeaderView(R.layout.header_nav_drawer, "http://pengaja.com/uiapptemplate/newphotos/profileimages/0.jpg", "almighty911216@gmail.com");
        BaseAdapter adapter = new DrawerAdapter(this, mDrawerItems);
        mDrawerList.addHeaderView(headerView);// Add header before adapter (for// pre-KitKat)
        mDrawerList.setAdapter(adapter);
    }

    private void selectItem(int position, int drawerTag) {
        Fragment fragment = getFragmentByDrawerTag(drawerTag);
        commitFragment(fragment);

        mDrawerList.setItemChecked(position, true);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0: // Header를 클릭할 경우
                    return;

                case DrawerItem.DRAWER_ITEM_TAG_HOME:
                    setTitle(mRes.getString(R.string.app_name));
                    break;

                default:
                    setTitle(mDrawerItems.get(position - 1).getTitle());
                    break;
            }

            selectItem(position, mDrawerItems.get(position - 1).getTag());
        }
    }

    private Fragment getFragmentByDrawerTag(int drawerTag) {
        Fragment fragment;

        switch (drawerTag) {
            case DrawerItem.DRAWER_ITEM_TAG_DASHBOARD:
                fragment = DashboardFragment.newInstance();
                break;

            case DrawerItem.DRAWER_ITEM_TAG_PATHFINDING:
                fragment = BicycleRoadFragment.newInstance();
                break;

            case DrawerItem.DRAWER_ITEM_TAG_PARKING_LOT:
                fragment = ParkingLotFragment.newInstance();
                break;

            case DrawerItem.DRAWER_ITEM_TAG_SOCIAL:
                fragment = SocialFragment.newInstance();
                break;
//
//            case DRAWER_ITEM_TAG_STAMP_AUTH:
////                fragment = StampAuthFragment.newInstance();
//                break;

            default:
                fragment = HomeFragment.newInstance();
        }

        mShouldFinish = false;

        return fragment;
    }

    private class CommitFragmentRunnable implements Runnable {

        private Fragment fragment;

        public CommitFragmentRunnable(Fragment fragment) {
            this.fragment = fragment;
        }

        @Override
        public void run() {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fl_content, fragment).commit();
        }
    }

    public void commitFragment(Fragment fragment) {
        // Using Handler class to avoid lagging while
        // committing fragment in same time as closing
        // navigation drawer
        mHandler.post(new CommitFragmentRunnable(fragment));
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initBinding();
        initImageLoader();
        initToolbar();
        initDrawerToggle();
        initMenuFragment();

        mTitle = mDrawerTitle = getTitle();

        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        initNavDrawerItems();
        initAdapter();
        mDrawerList.setAdapter(new DrawerAdapter(this, mDrawerItems));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        selectItem(1, DrawerItem.DRAWER_ITEM_TAG_HOME);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.context_menu:
                mMenuDialogFragment.show(getSupportFragmentManager(), ContextMenuDialogFragment.TAG);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMenuItemClick(View clickedView, int position) {
        Toast.makeText(this, "Clicked on position: " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMenuItemLongClick(View clickedView, int position) {
        Toast.makeText(this, "Long clicked on position: " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setTitle(int titleId) {
        setTitle(getString(titleId));
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        if (!mShouldFinish && !mDrawerLayout.isDrawerOpen(mDrawerList)) {
            Toast.makeText(getApplicationContext(), R.string.confirm_exit, Toast.LENGTH_SHORT).show();
            mShouldFinish = true;
            mDrawerLayout.openDrawer(mDrawerList);
        } else if (!mShouldFinish && mDrawerLayout.isDrawerOpen(mDrawerList)) {
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            super.onBackPressed();
        }
    }
}