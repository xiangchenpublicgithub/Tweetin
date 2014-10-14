package io.github.mthli.Tweetin.Tweet.Mention;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.AsyncTask;
import io.github.mthli.Tweetin.Database.Mention.MentionAction;
import io.github.mthli.Tweetin.Mention.MentionActivity;
import io.github.mthli.Tweetin.Mention.MentionFragment;
import io.github.mthli.Tweetin.R;
import io.github.mthli.Tweetin.Tweet.Base.Tweet;
import io.github.mthli.Tweetin.Tweet.Base.TweetAdapter;
import io.github.mthli.Tweetin.Unit.Flag;
import twitter4j.Twitter;

import java.util.List;

public class MentionRetweetTask extends AsyncTask<Void, Integer, Boolean> {
    private MentionFragment mentionFragment;
    private Context context;
    private long useId;

    private Twitter twitter;
    private TweetAdapter tweetAdapter;
    private List<Tweet> tweetList;
    private int position = 0;
    private Tweet tweet;
    private Tweet newTweet;

    private NotificationManager notificationManager;
    private Notification.Builder builder;
    private static final int POST_ID = Flag.POST_ID;

    public MentionRetweetTask(
            MentionFragment mentionFragment,
            int position
    ) {
        this.mentionFragment = mentionFragment;
        this.position = position;
    }

    @Override
    protected void onPreExecute() {
        context = mentionFragment.getContentView().getContext();
        useId = mentionFragment.getUseId();

        twitter = ((MentionActivity) mentionFragment.getActivity()).getTwitter();
        tweetAdapter = mentionFragment.getTweetAdapter();
        tweetList = mentionFragment.getTweetList();
        tweet = tweetList.get(position);

        notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        builder = new Notification.Builder(context);
        builder.setSmallIcon(R.drawable.ic_post_notification);
        builder.setTicker(
                context.getString(R.string.tweet_retweet_ing)
        );
        builder.setContentTitle(
                context.getString(R.string.tweet_retweet_ing)
        );
        builder.setContentText(tweet.getText());
        Notification notification = builder.build();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(POST_ID, notification);
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            twitter.retweetStatus(tweet.getTweetId());

            newTweet = new Tweet();
            newTweet.setTweetId(tweet.getTweetId());
            newTweet.setUserId(tweet.getUserId());
            newTweet.setAvatarUrl(tweet.getAvatarUrl());
            newTweet.setCreatedAt(tweet.getCreatedAt());
            newTweet.setName(tweet.getName());
            newTweet.setScreenName(tweet.getScreenName());
            newTweet.setProtect(tweet.isProtected());
            newTweet.setText(tweet.getText());
            newTweet.setCheckIn(tweet.getCheckIn());
            newTweet.setRetweet(true);
            newTweet.setRetweetedByName(context.getString(R.string.tweet_retweeted_by_me));
            newTweet.setRetweetedById(useId);
            newTweet.setReplyTo(tweet.getReplyTo());

            MentionAction action = new MentionAction(context);
            action.opewDatabase(true);
            action.updateByMe(tweet.getTweetId(), newTweet);
            action.closeDatabase();

            builder.setSmallIcon(R.drawable.ic_post_notification);
            builder.setTicker(
                    context.getString(R.string.tweet_retweet_successful)
            );
            builder.setContentTitle(
                    context.getString(R.string.tweet_retweet_successful)
            );
            builder.setContentText(tweet.getText());
            Notification notification = builder.build();
            notification.flags = Notification.FLAG_AUTO_CANCEL;
            notificationManager.notify(POST_ID, notification);
            notificationManager.cancel(POST_ID);
        } catch (Exception e) {
            builder.setSmallIcon(R.drawable.ic_post_notification);
            builder.setTicker(
                    context.getString(R.string.tweet_retweet_failed)
            );
            builder.setContentTitle(
                    context.getString(R.string.tweet_retweet_failed)
            );
            builder.setContentText(tweet.getText());
            Notification notification = builder.build();
            notification.flags = Notification.FLAG_AUTO_CANCEL;
            notificationManager.notify(POST_ID, notification);

            return false;
        }

        if (isCancelled()) {
            return false;
        }
        return true;
    }

    @Override
    protected void onCancelled() {
        /* Do nothing */
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        /* Do nothing */
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (result) {
            tweetList.remove(position);
            tweetList.add(position, newTweet);
            tweetAdapter.notifyDataSetChanged();
        }
    }
}