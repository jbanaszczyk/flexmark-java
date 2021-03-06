package com.vladsch.flexmark.ext.emoji.internal;

import com.vladsch.flexmark.ext.emoji.EmojiExtension;
import com.vladsch.flexmark.ext.emoji.EmojiImageType;
import com.vladsch.flexmark.ext.emoji.EmojiShortcutType;
import com.vladsch.flexmark.util.data.DataHolder;

public class EmojiOptions {
    public final String rootImagePath;
    public final EmojiShortcutType useShortcutType;
    public final EmojiImageType useImageType;
    public final String attrImageSize;
    public final String attrAlign;
    public final String attrImageClass;

    public EmojiOptions(DataHolder options) {
        this.useShortcutType = EmojiExtension.USE_SHORTCUT_TYPE.get(options);
        this.attrAlign = EmojiExtension.ATTR_ALIGN.get(options);
        this.attrImageSize = EmojiExtension.ATTR_IMAGE_SIZE.get(options);
        this.rootImagePath = EmojiExtension.ROOT_IMAGE_PATH.get(options);
        this.useImageType = EmojiExtension.USE_IMAGE_TYPE.get(options);
        this.attrImageClass = EmojiExtension.ATTR_IMAGE_CLASS.get(options);
    }
}
