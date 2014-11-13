package io.github.mthli.Tweetin.Task.Timeline;

import android.content.Context;
import android.os.AsyncTask;
import io.github.mthli.Tweetin.Fragment.TimelineFragment;
import io.github.mthli.Tweetin.Unit.Database.DatabaseUnit;
import io.github.mthli.Tweetin.Unit.Notification.NotificationUnit;
import io.github.mthli.Tweetin.Unit.Tweet.Tweet;
import io.github.mthli.Tweetin.Unit.Tweet.TweetAdapter;
import twitter4j.Twitter;

import java.util.List;

public class TimelineDeleteTask extends AsyncTask<Void, Integer, Boolean> {
    private TimelineFragment timelineFragment;
    private Context context;
    private Twitter twitter;

    private TweetAdapter tweetAdapter;
    private List<Tweet> tweetList;
    private Tweet oldTweet;
    private int position;

    private NotificationUnit notificationUnit;

    public TimelineDeleteTask(
            TimelineFragment timelineFragment,
            int position
    ) {
        this.timelineFragment = timelineFragment;
        this.position = position;
    }

    @Override
    protected void onPreExecute() {
        context = timelineFragment.getContentView().getContext();
        twitter = timelineFragment.getTwitter();

        tweetAdapter = timelineFragment.getTweetAdapter();
        tweetList = timelineFragment.getTweetList();
        oldTweet = tweetList.get(position);

        notificationUnit = new NotificationUnit(context, oldTweet);
        notificationUnit.deleting();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            twitter.destroyStatus(oldTweet.getStatusId());

            DatabaseUnit.deleteRecord(context, oldTweet);
            notificationUnit.deleteSuccessful();
        } catch (Exception e) {
            notificationUnit.deleteFailed();

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
            tweetAdapter.notifyDataSetChanged();
        }
    }
}