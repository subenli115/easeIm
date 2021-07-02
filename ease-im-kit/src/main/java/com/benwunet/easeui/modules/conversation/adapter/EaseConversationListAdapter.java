package com.benwunet.easeui.modules.conversation.adapter;

import com.benwunet.easeui.adapter.EaseBaseDelegateAdapter;
import com.hyphenate.easeui.R;
import com.benwunet.easeui.modules.conversation.model.EaseConversationInfo;

public class EaseConversationListAdapter extends EaseBaseDelegateAdapter<EaseConversationInfo> {
    private int emptyLayoutId;

    @Override
    public int getEmptyLayoutId() {
        return emptyLayoutId != 0 ? emptyLayoutId : R.layout.ease_layout_default_no_conversation_data;
    }

    /**
     * set empty layout
     * @param layoutId
     */
    public void setEmptyLayoutId(int layoutId) {
        this.emptyLayoutId = layoutId;
        notifyDataSetChanged();
    }

}

