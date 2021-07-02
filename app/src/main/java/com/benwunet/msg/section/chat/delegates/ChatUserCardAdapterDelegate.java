package com.benwunet.msg.section.chat.delegates;

import android.view.View;
import android.view.ViewGroup;

import com.benwunet.easeui.delegate.EaseMessageAdapterDelegate;
import com.benwunet.easeui.interfaces.MessageListItemClickListener;
import com.benwunet.easeui.viewholder.EaseChatRowViewHolder;
import com.benwunet.msg.common.constant.DemoConstant;
import com.hyphenate.chat.EMCustomMessageBody;
import com.hyphenate.chat.EMMessage;
import com.benwunet.msg.section.chat.viewholder.ChatUserCardViewHolder;
import com.benwunet.msg.section.chat.views.chatRowUserCard;
import com.benwunet.easeui.widget.chatrow.EaseChatRow;


public class ChatUserCardAdapterDelegate extends EaseMessageAdapterDelegate<EMMessage, EaseChatRowViewHolder> {
    @Override
    public boolean isForViewType(EMMessage item, int position) {
        if(item.getType() == EMMessage.Type.CUSTOM){
            EMCustomMessageBody messageBody = (EMCustomMessageBody) item.getBody();
            String event = messageBody.event();
            return event.equals(DemoConstant.USER_CARD_EVENT)?true:false;
        }
        return false;
    }

    @Override
    protected EaseChatRow getEaseChatRow(ViewGroup parent, boolean isSender) {
        return new chatRowUserCard(parent.getContext(), isSender);
    }

    @Override
    protected EaseChatRowViewHolder createViewHolder(View view, MessageListItemClickListener itemClickListener) {
        return new ChatUserCardViewHolder(view, itemClickListener);
    }
}

