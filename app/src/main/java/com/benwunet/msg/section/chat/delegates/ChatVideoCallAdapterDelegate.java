package com.benwunet.msg.section.chat.delegates;

import android.view.View;
import android.view.ViewGroup;

import com.benwunet.easeui.delegate.EaseMessageAdapterDelegate;
import com.benwunet.easeui.interfaces.MessageListItemClickListener;
import com.benwunet.easeui.viewholder.EaseChatRowViewHolder;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easecallkit.base.EaseCallType;
import com.hyphenate.easecallkit.utils.EaseMsgUtils;
import com.benwunet.msg.section.chat.viewholder.ChatVideoCallViewHolder;
import com.benwunet.msg.section.chat.views.ChatRowVideoCall;
import com.benwunet.easeui.widget.chatrow.EaseChatRow;

import static com.hyphenate.chat.EMMessage.Type.TXT;

public class ChatVideoCallAdapterDelegate extends EaseMessageAdapterDelegate<EMMessage, EaseChatRowViewHolder> {
    @Override
    public boolean isForViewType(EMMessage item, int position) {
        boolean isRtcCall =item.getStringAttribute(EaseMsgUtils.CALL_MSG_TYPE,"").equals(EaseMsgUtils.CALL_MSG_INFO)?true:false;
        boolean isVideoCall = item.getIntAttribute(EaseMsgUtils.CALL_TYPE,0) == EaseCallType.SINGLE_VIDEO_CALL.code?true:false;
        return item.getType() == TXT && isRtcCall && isVideoCall;
    }

    @Override
    protected EaseChatRow getEaseChatRow(ViewGroup parent, boolean isSender) {
        return new ChatRowVideoCall(parent.getContext(), isSender);
    }

    @Override
    protected EaseChatRowViewHolder createViewHolder(View view, MessageListItemClickListener itemClickListener) {
        return new ChatVideoCallViewHolder(view, itemClickListener);
    }
}
