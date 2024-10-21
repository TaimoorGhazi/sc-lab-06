package twitter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Extract {

    /**
     * Get usernames mentioned in a list of tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return the set of usernames who are mentioned in the text of the tweets.
     *         A username-mention is "@" followed by a Twitter username (as
     *         defined by Tweet.getAuthor()'s spec).
     *         The username-mention cannot be immediately preceded or followed by any
     *         character valid in a Twitter username.
     *         For this reason, an email address like bitdiddle@mit.edu does NOT 
     *         contain a mention of the username mit.
     *         Twitter usernames are case-insensitive, and the returned set may
     *         include a username at most once.
     */
    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
        Set<String> mentionedUsers = new HashSet<>();
        
        // Regular expression to match Twitter usernames preceded by '@' and with valid username characters
        String mentionRegex = "(?<!\\w)@(\\w+)";
        Pattern pattern = Pattern.compile(mentionRegex);

        for (Tweet tweet : tweets) {
            String text = tweet.getText();
            Matcher matcher = pattern.matcher(text);

            while (matcher.find()) {
                // Extract the username after '@' and add it in lowercase form to the set
                String mentionedUser = matcher.group(1).toLowerCase();
                mentionedUsers.add(mentionedUser);
            }
        }

        return mentionedUsers;
    }
}
