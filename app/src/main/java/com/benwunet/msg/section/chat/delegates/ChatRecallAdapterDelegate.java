package com.benwunet.msg.section.chat.delegates;

import android.view.View;
import android.view.ViewGroup;

import com.benwunet.easeui.delegate.EaseMessageAdapterDelegate;
import com.benwunet.easeui.interfaces.MessageListItemClickListener;
import com.benwunet.easeui.viewholder.EaseChatRowViewHolder;
import com.benwunet.msg.common.constant.DemoConstant;
import com.benwunet.msg.section.chat.viewholder.ChatRecallViewHolder;
import com.hyphenate.chat.EMMessage;
import com.benwunet.msg.section.chat.views.ChatRowRecall;
import com.benwunet.easeui.widget.chatrow.EaseChatRow;

import static com.hyphenate.chat.EMMessage.Type.TXT;

public class ChatRecallAdapterDelegate extends EaseMessageAdapterDelegate<EMMessage, EaseChatRowViewHolder> {

    @Override
    public boolean isForViewType(EMMessage item, int position) {
        return item.getType() == TXT && item.getBooleanAttribute(DemoConstant.MESSAGE_TYPE_RECALL, false);
    }

    @Override
    protected EaseChatRow getEaseChatRow(ViewGroup parent, boolean isSender) {
        return new ChatRowRecall(parent.getContext(), isSender);
    }

    @Override
    protected EaseChatRowViewHolder createViewHolder(View view, MessageListItemClickListener itemClickListener) {
        return new ChatRecallViewHolder(view, itemClickListener);
    }
}
