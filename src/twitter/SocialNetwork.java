

package twitter;

import java.util.*;

public class SocialNetwork {

    /**
     * Guess who might follow whom, from evidence found in tweets.
     * 
     * @param tweets a list of tweets providing the evidence, not modified by this
     *               method.
     * @return a social network (as defined above) in which Ernie follows Bert
     *         if and only if there is evidence for it in the given list of
     *         tweets. One kind of evidence that Ernie follows Bert is if Ernie
     *         @-mentions Bert in a tweet. This must be implemented. Other kinds
     *         of evidence may be used at the implementor's discretion.
     *         All the Twitter usernames in the returned social network must be
     *         either authors or @-mentions in the list of tweets.
     */
    public static Map<String, Set<String>> guessFollowsGraph(List<Tweet> tweets) {
        Map<String, Set<String>> followsGraph = new HashMap<>();

        for (Tweet tweet : tweets) {
            String author = tweet.getAuthor().toLowerCase();
            Set<String> mentionedUsers = Extract.getMentionedUsers(List.of(tweet));

            // Remove the author from the mentioned users to avoid self-following
            mentionedUsers.remove(author);

            // If the author is already in the map, add the mentioned users to the existing set
            followsGraph.putIfAbsent(author, new HashSet<>());
            followsGraph.get(author).addAll(mentionedUsers);
        }

        return followsGraph;
    }

    /**
     * Find the influencers in the social network.
     * 
     * @param followsGraph a social network, as described above.
     * @return a list of influencers in the network, in decreasing order of
     *         influence (most followed first), where the influence of a user is
     *         determined by how many others follow them.
     */
    public static List<String> influencers(Map<String, Set<String>> followsGraph) {
        Map<String, Integer> followersCount = new HashMap<>();

        // Count the number of followers each user has
        for (Set<String> followedUsers : followsGraph.values()) {
            for (String user : followedUsers) {
                followersCount.put(user, followersCount.getOrDefault(user, 0) + 1);
            }
        }

        // Ensure all users are included in the follower count map
        for (String user : followsGraph.keySet()) {
            followersCount.putIfAbsent(user, 0);
        }

        // Sort users by number of followers, breaking ties alphabetically by username
        List<String> influencers = new ArrayList<>(followersCount.keySet());
        influencers.sort((user1, user2) -> {
            int followerComparison = Integer.compare(followersCount.get(user2), followersCount.get(user1));
            if (followerComparison != 0) {
                return followerComparison; // Sort by follower count
            } else {
                return user1.compareTo(user2); // Sort lexicographically by username if tied
            }
        });

        return influencers;
    }
}
