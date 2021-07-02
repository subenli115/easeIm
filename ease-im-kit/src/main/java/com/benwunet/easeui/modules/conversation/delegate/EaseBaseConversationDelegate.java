package com.benwunet.easeui.modules.conversation.delegate;

import com.benwunet.easeui.adapter.EaseAdapterDelegate;
import com.benwunet.easeui.modules.conversation.model.EaseConversationSetStyle;
import com.benwunet.easeui.adapter.EaseBaseRecyclerViewAdapter;

public abstract class EaseBaseConversationDelegate<T, VH extends EaseBaseRecyclerViewAdapter.ViewHolder<T>> extends EaseAdapterDelegate<T, VH> {
    public EaseConversationSetStyle setModel;

    public void setSetModel(EaseConversationSetStyle setModel) {
        this.setModel = setModel;
    }

    public EaseBaseConversationDelegate(EaseConversationSetStyle setModel) {
        this.setModel = setModel;
    }
}

