package com.benwunet.easeui.delegate;

import android.view.View;
import android.view.ViewGroup;

import com.hyphenate.chat.EMMessage;
import com.benwunet.easeui.interfaces.MessageListItemClickListener;
import com.benwunet.easeui.model.styles.EaseMessageListItemStyle;
import com.benwunet.easeui.viewholder.EaseChatRowViewHolder;
import com.benwunet.easeui.viewholder.EaseFileViewHolder;
import com.benwunet.easeui.widget.chatrow.EaseChatRow;
import com.benwunet.easeui.widget.chatrow.EaseChatRowFile;

/**
 * 文件代理类
 */
public class EaseFileAdapterDelegate extends EaseMessageAdapterDelegate<EMMessage, EaseChatRowViewHolder> {

    public EaseFileAdapterDelegate() {
    }

    public EaseFileAdapterDelegate(MessageListItemClickListener itemClickListener, EaseMessageListItemStyle itemStyle) {
        super(itemClickListener, itemStyle);
    }

    @Override
    public boolean isForViewType(EMMessage item, int position) {
        return item.getType() == EMMessage.Type.FILE;
    }

    @Override
    protected EaseChatRow getEaseChatRow(ViewGroup parent, boolean isSender) {
        return new EaseChatRowFile(parent.getContext(), isSender);
    }

    @Override
    protected EaseChatRowViewHolder createViewHolder(View view, MessageListItemClickListener itemClickListener) {
        return new EaseFileViewHolder(view, itemClickListener);
    }
}
