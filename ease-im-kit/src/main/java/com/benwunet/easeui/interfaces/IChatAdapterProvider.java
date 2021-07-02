package com.benwunet.easeui.interfaces;

import com.benwunet.easeui.adapter.EaseMessageAdapter;
import com.hyphenate.chat.EMMessage;
import com.benwunet.easeui.adapter.EaseBaseMessageAdapter;

public interface IChatAdapterProvider {
    /**
     * provide chat message's adapter
     * if is null , will use default {@link EaseMessageAdapter}
     * @return
     */
    EaseBaseMessageAdapter<EMMessage> provideMessageAdaper();
}
