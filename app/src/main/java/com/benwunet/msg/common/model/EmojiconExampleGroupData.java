package com.benwunet.msg.common.model;

import com.benwunet.msg.DemoApplication;
import com.benwunet.msg.R;
import com.benwunet.easeui.domain.EaseEmojicon;
import com.benwunet.easeui.domain.EaseEmojicon.Type;
import com.benwunet.easeui.domain.EaseEmojiconGroupEntity;

import java.util.Arrays;

public class EmojiconExampleGroupData {
    
    private static int[] icons = new int[]{
        R.drawable.icon_002_cover,
    };
    
    private static int[] bigIcons = new int[]{
        R.drawable.icon_002,
    };
    
    
    private static final EaseEmojiconGroupEntity DATA = createData();
    
    private static EaseEmojiconGroupEntity createData(){
        EaseEmojiconGroupEntity emojiconGroupEntity = new EaseEmojiconGroupEntity();
        EaseEmojicon[] datas = new EaseEmojicon[icons.length];
        for(int i = 0; i < icons.length; i++){
            datas[i] = new EaseEmojicon(icons[i], null, Type.BIG_EXPRESSION);
            //you can replace this to any you want
            datas[i].setName(DemoApplication.getInstance().getApplicationContext().getString(R.string.emojicon_test_name)+ (i+1));
            datas[i].setIdentityCode("em"+ (1000+i+1));
        }
        emojiconGroupEntity.setEmojiconList(Arrays.asList(datas));
        emojiconGroupEntity.setIcon(R.drawable.icon_002_cover);
        emojiconGroupEntity.setType(Type.BIG_EXPRESSION);
        return emojiconGroupEntity;
    }
    
    
    public static EaseEmojiconGroupEntity getData(){
        return DATA;
    }
}
