package com.benwunet.msg.section.contact.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;

import com.benwunet.easeui.interfaces.OnItemClickListener;
import com.benwunet.easeui.widget.EaseTitleBar;
import com.benwunet.msg.DemoHelper;
import com.benwunet.msg.common.constant.DemoConstant;
import com.benwunet.msg.common.interfaceOrImplement.OnResourceParseCallback;
import com.benwunet.msg.common.livedatas.LiveDataBus;
import com.benwunet.msg.section.base.BaseInitActivity;
import com.benwunet.msg.section.contact.adapter.BlackContactAdapter;
import com.benwunet.msg.R;
import com.benwunet.msg.section.contact.viewmodels.ContactBlackViewModel;
import com.benwunet.msg.section.search.SearchBlackActivity;
import com.benwunet.easeui.domain.EaseUser;
import com.benwunet.easeui.model.EaseEvent;
import com.benwunet.easeui.widget.EaseRecyclerView;
import com.benwunet.easeui.widget.EaseSearchTextView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

public class ContactBlackListActivity extends BaseInitActivity implements OnRefreshListener, EaseTitleBar.OnBackPressListener, View.OnClickListener, OnItemClickListener {
    private EaseTitleBar titleBar;
    private SmartRefreshLayout srlRefresh;
    private EaseRecyclerView rvList;
    private EaseSearchTextView searchBlack;
    private BlackContactAdapter adapter;
    private ContactBlackViewModel viewModel;

    public static void actionStart(Context context) {
        Intent starter = new Intent(context, ContactBlackListActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.demo_activity_contact_black_list;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        titleBar = findViewById(R.id.title_bar);
        srlRefresh = findViewById(R.id.srl_refresh);
        rvList = findViewById(R.id.rv_list);
        searchBlack = findViewById(R.id.search_black);

        rvList.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new BlackContactAdapter();
        rvList.setAdapter(adapter);

        registerForContextMenu(rvList);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.demo_black_list_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int position = ((EaseRecyclerView.RecyclerViewContextMenuInfo)item.getMenuInfo()).position;
        EaseUser user = adapter.getItem(position);
        switch (item.getItemId()) {
            case R.id.action_friend_unblock :
                unBlock(user);
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void initListener() {
        super.initListener();
        titleBar.setOnBackPressListener(this);
        srlRefresh.setOnRefreshListener(this);
        searchBlack.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
        viewModel = new ViewModelProvider(this).get(ContactBlackViewModel.class);
        viewModel.blackObservable().observe(this, response -> {
            parseResource(response, new OnResourceParseCallback<List<EaseUser>>() {
                @Override
                public void onSuccess(List<EaseUser> data) {
                    adapter.setData(data);
                }

                @Override
                public void hideLoading() {
                    super.hideLoading();
                    finishRefresh();
                }
            });
        });
        viewModel.resultObservable().observe(this, response -> {
            parseResource(response, new OnResourceParseCallback<Boolean>() {
                @Override
                public void onSuccess(Boolean data) {
                    showToast(R.string.em_friends_move_out_blacklist_success);
                    LiveDataBus.get().with(DemoConstant.REMOVE_BLACK).postValue(EaseEvent.create(DemoConstant.REMOVE_BLACK, EaseEvent.TYPE.CONTACT));
                }
            });
        });

        LiveDataBus.get().with(DemoConstant.REMOVE_BLACK, EaseEvent.class).observe(this, event -> {
            if(event == null) {
                return;
            }
            if(event.isContactChange()) {
                viewModel.getBlackList();
            }
        });

        LiveDataBus.get().with(DemoConstant.CONTACT_CHANGE, EaseEvent.class).observe(this, event -> {
            if(event == null) {
                return;
            }
            if(event.isContactChange()) {
                viewModel.getBlackList();
            }
        });

        viewModel.getBlackList();

        adapter.setOnItemClickListener(this);
    }

    private void unBlock(EaseUser user) {
        viewModel.removeUserFromBlackList(user.getUsername());
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        viewModel.getBlackList();
    }

    @Override
    public void onBackPress(View view) {
        onBackPressed();
    }

    private void finishRefresh() {
        if(srlRefresh != null) {
            srlRefresh.finishRefresh();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_black :
                SearchBlackActivity.actionStart(mContext);
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        EaseUser user = adapter.getItem(position);
        ContactDetailActivity.actionStart(mContext, user, DemoHelper.getInstance().getModel().isContact(user.getUsername()));
    }
}
