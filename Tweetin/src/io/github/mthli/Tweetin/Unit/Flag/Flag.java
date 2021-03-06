package io.github.mthli.Tweetin.Unit.Flag;

public class Flag {
    public static final int IN_TIMELINE_FRAGMENT = 0x100;
    public static final int IN_MENTION_FRAGMENT = 0x101;
    public static final int IN_FAVORITE_FRAGMENT = 0x102;
    public static final int IN_DISCOVERY_FRAGMENT = 0x103;
    public static final int IN_SETTING_FRAGMENT = 0x104;

    public static final int TIMELINE_TASK_RUNNING = 0x200;
    public static final int TIMELINE_TASK_IDLE = 0x201;
    public static final int MENTION_TASK_RUNNING = 0x202;
    public static final int MENTION_TASK_IDLE = 0x203;
    public static final int FAVORITE_TASK_RUNNING = 0x204;
    public static final int FAVORITE_TASK_IDLE = 0x205;
    public static final int DISCOVERY_TASK_RUNNING = 0x206;
    public static final int DISCOVERY_TASK_IDLE = 0x207;
    public static final int DETAIL_TASK_RUNNING = 0x208;
    public static final int DETAIL_TASK_IDLE = 0x209;

    public static final int NOTIFICATION_PROGRESS_ID = 0x400;
    public static final int NOTIFICATION_MENTION_ID = 0x401;

    public static final int POST_ORIGINAL = 0x500;
    public static final int POST_REPLY = 0x501;
    public static final int POST_QUOTE = 0x502;
    public static final int POST_PHOTO = 0x503;
    public static final int POST_RESEND = 0x504;
    public static final int POST_ADVICE = 0x505;
    public static final int POST_SHARE = 0x506;

    public static final int CONTEXT_MENU_ITEM_REPLY = 0x600;
    public static final int CONTEXT_MENU_ITEM_DELETE = 0x601;
    public static final int CONTEXT_MENU_ITEM_RETWEET = 0x602;
    public static final int CONTEXT_MENU_ITEM_QUOTE = 0x603;
    public static final int CONTEXT_MENU_ITEM_FAVORITE = 0x604;
    public static final int CONTEXT_MENU_ITEM_CANCEL = 0x605;
    public static final int CONTEXT_MENU_ITEM_DETAIL = 0x606;
    public static final int CONTEXT_MENU_ITEM_COPY = 0x607;
}
