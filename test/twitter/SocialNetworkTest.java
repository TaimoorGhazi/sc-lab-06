//package twitter;
//
//import static org.junit.Assert.*;
//
//import java.time.Instant;
//import java.util.*;
//
//import org.junit.Test;
//
//public class SocialNetworkTest {
//
//    private static final Instant d1 = Instant.parse("2024-10-21T10:00:00Z");
//    private static final Instant d2 = Instant.parse("2024-10-21T11:00:00Z");
//
//    private static final Tweet tweet1 = new Tweet(1, "alyssa", "@bbitdiddle nice talk!", d1);
//    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "@alyssa thanks for the mention!", d2);
//    private static final Tweet tweet3 = new Tweet(3, "alyssa", "No mentions here.", d1);
//
//    // ---- Tests for guessFollowsGraph() ----
//
//    @Test
//    public void testGuessFollowsGraphSingleMention() {
//        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1));
//
//        assertTrue("expected alyssa to follow bbitdiddle", followsGraph.containsKey("alyssa"));
//        assertEquals(new HashSet<>(Arrays.asList("bbitdiddle")), followsGraph.get("alyssa"));
//    }
//
//    @Test
//    public void testGuessFollowsGraphMultipleMentions() {
//        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1, tweet2));
//
//        assertTrue("expected alyssa to follow bbitdiddle", followsGraph.containsKey("alyssa"));
//        assertTrue("expected bbitdiddle to follow alyssa", followsGraph.containsKey("bbitdiddle"));
//        assertEquals(new HashSet<>(Arrays.asList("bbitdiddle")), followsGraph.get("alyssa"));
//        assertEquals(new HashSet<>(Arrays.asList("alyssa")), followsGraph.get("bbitdiddle"));
//    }
//
//    @Test
//    public void testGuessFollowsGraphNoMentions() {
//        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet3));
//
//        assertTrue("expected alyssa to have no follows", followsGraph.containsKey("alyssa"));
//        assertTrue("expected alyssa to follow no one", followsGraph.get("alyssa").isEmpty());
//    }
//
//    @Test
//    public void testGuessFollowsGraphNoTweets() {
//        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList());
//
//        assertTrue("expected empty graph", followsGraph.isEmpty());
//    }
//
//    @Test
//    public void testGuessFollowsGraphSelfMention() {
//        // Test when someone mentions themselves, which should not count
//        Tweet tweet4 = new Tweet(4, "charlie", "@charlie self mention", d1);
//        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet4));
//
//        assertTrue("expected charlie to have no follows", followsGraph.containsKey("charlie"));
//        assertTrue("expected charlie to follow no one", followsGraph.get("charlie").isEmpty());
//    }
//
//    // ---- Tests for influencers() ----
//
//    @Test
//    public void testInfluencersSingleUser() {
//        Map<String, Set<String>> followsGraph = new HashMap<>();
//        followsGraph.put("alyssa", new HashSet<>(Arrays.asList("bbitdiddle")));
//        
//        List<String> influencers = SocialNetwork.influencers(followsGraph);
//        assertEquals("expected bbitdiddle to be the top influencer", "bbitdiddle", influencers.get(0));
//    }
//
//    @Test
//    public void testInfluencersMultipleUsers() {
//        Map<String, Set<String>> followsGraph = new HashMap<>();
//        followsGraph.put("alyssa", new HashSet<>(Arrays.asList("bbitdiddle")));
//        followsGraph.put("bbitdiddle", new HashSet<>(Arrays.asList("alyssa")));
//        followsGraph.put("charlie", new HashSet<>(Arrays.asList("alyssa")));
//
//        List<String> influencers = SocialNetwork.influencers(followsGraph);
//        
//        // alyssa should have 2 followers, bbitdiddle should have 1
//        assertEquals("expected alyssa to be the top influencer", "alyssa", influencers.get(0));
//        assertEquals("expected bbitdiddle to be second", "bbitdiddle", influencers.get(1));
//    }
//
//    @Test
//    public void testInfluencersNoFollowers() {
//        Map<String, Set<String>> followsGraph = new HashMap<>();
//        followsGraph.put("alyssa", new HashSet<>());
//        followsGraph.put("bbitdiddle", new HashSet<>());
//
//        List<String> influencers = SocialNetwork.influencers(followsGraph);
//        assertTrue("expected alyssa and bbitdiddle to be influencers with no followers", influencers.contains("alyssa"));
//        assertTrue("expected alyssa and bbitdiddle to be influencers with no followers", influencers.contains("bbitdiddle"));
//    }
//
//    @Test
//    public void testInfluencersNoUsers() {
//        Map<String, Set<String>> followsGraph = new HashMap<>();
//
//        List<String> influencers = SocialNetwork.influencers(followsGraph);
//        assertTrue("expected no influencers in an empty graph", influencers.isEmpty());
//    }
//
//    @Test
//    public void testInfluencersTieInFollowers() {
//        // Test when two users have the same number of followers
//        Map<String, Set<String>> followsGraph = new HashMap<>();
//        followsGraph.put("alyssa", new HashSet<>(Arrays.asList("bbitdiddle", "charlie")));
//        followsGraph.put("bbitdiddle", new HashSet<>(Arrays.asList("charlie")));
//        followsGraph.put("charlie", new HashSet<>());
//
//        List<String> influencers = SocialNetwork.influencers(followsGraph);
//        
//        // bbitdiddle and charlie both have 1 follower, but charlie should come last
//        assertEquals("expected bbitdiddle to be the top influencer", "bbitdiddle", influencers.get(0));
//        assertEquals("expected charlie to be second", "charlie", influencers.get(1));
//        assertEquals("expected alyssa to be third", "alyssa", influencers.get(2));
//    }
//}


package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.*;

import org.junit.Test;

public class SocialNetworkTest {

    private static final Instant d1 = Instant.parse("2024-10-21T10:00:00Z");
    private static final Instant d2 = Instant.parse("2024-10-21T11:00:00Z");

    private static final Tweet tweet1 = new Tweet(1, "alyssa", "@bbitdiddle nice talk!", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "@alyssa thanks for the mention!", d2);
    private static final Tweet tweet3 = new Tweet(3, "alyssa", "No mentions here.", d1);

    // ---- Tests for guessFollowsGraph() ----

    @Test
    public void testGuessFollowsGraphSingleMention() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1));

        assertTrue("expected alyssa to follow bbitdiddle", followsGraph.containsKey("alyssa"));
        assertEquals(new HashSet<>(Arrays.asList("bbitdiddle")), followsGraph.get("alyssa"));
    }

    @Test
    public void testGuessFollowsGraphMultipleMentions() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1, tweet2));

        assertTrue("expected alyssa to follow bbitdiddle", followsGraph.containsKey("alyssa"));
        assertTrue("expected bbitdiddle to follow alyssa", followsGraph.containsKey("bbitdiddle"));
        assertEquals(new HashSet<>(Arrays.asList("bbitdiddle")), followsGraph.get("alyssa"));
        assertEquals(new HashSet<>(Arrays.asList("alyssa")), followsGraph.get("bbitdiddle"));
    }

    @Test
    public void testGuessFollowsGraphNoMentions() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet3));

        assertTrue("expected alyssa to have no follows", followsGraph.containsKey("alyssa"));
        assertTrue("expected alyssa to follow no one", followsGraph.get("alyssa").isEmpty());
    }

    @Test
    public void testGuessFollowsGraphNoTweets() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList());

        assertTrue("expected empty graph", followsGraph.isEmpty());
    }

    @Test
    public void testGuessFollowsGraphSelfMention() {
        // Test when someone mentions themselves, which should not count
        Tweet tweet4 = new Tweet(4, "charlie", "@charlie self mention", d1);
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet4));

        assertTrue("expected charlie to have no follows", followsGraph.containsKey("charlie"));
        assertTrue("expected charlie to follow no one", followsGraph.get("charlie").isEmpty());
    }

    // ---- Tests for influencers() ----

    @Test
    public void testInfluencersSingleUser() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        followsGraph.put("alyssa", new HashSet<>(Arrays.asList("bbitdiddle")));
        
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        assertEquals("expected bbitdiddle to be the top influencer", "bbitdiddle", influencers.get(0));
    }

    @Test
    public void testInfluencersMultipleUsers() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        followsGraph.put("alyssa", new HashSet<>(Arrays.asList("bbitdiddle")));
        followsGraph.put("bbitdiddle", new HashSet<>(Arrays.asList("alyssa")));
        followsGraph.put("charlie", new HashSet<>(Arrays.asList("alyssa")));

        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        // alyssa should have 2 followers, bbitdiddle should have 1
        assertEquals("expected alyssa to be the top influencer", "alyssa", influencers.get(0));
        assertEquals("expected bbitdiddle to be second", "bbitdiddle", influencers.get(1));
    }

    @Test
    public void testInfluencersNoFollowers() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        followsGraph.put("alyssa", new HashSet<>());
        followsGraph.put("bbitdiddle", new HashSet<>());

        List<String> influencers = SocialNetwork.influencers(followsGraph);
        assertTrue("expected alyssa and bbitdiddle to be influencers with no followers", influencers.contains("alyssa"));
        assertTrue("expected alyssa and bbitdiddle to be influencers with no followers", influencers.contains("bbitdiddle"));
    }

    @Test
    public void testInfluencersNoUsers() {
        Map<String, Set<String>> followsGraph = new HashMap<>();

        List<String> influencers = SocialNetwork.influencers(followsGraph);
        assertTrue("expected no influencers in an empty graph", influencers.isEmpty());
    }

    @Test
    public void testInfluencersTieInFollowers() {
        // Test when two users have the same number of followers
        Map<String, Set<String>> followsGraph = new HashMap<>();
        followsGraph.put("alyssa", new HashSet<>(Arrays.asList("bbitdiddle", "charlie")));
        followsGraph.put("bbitdiddle", new HashSet<>(Arrays.asList("charlie")));
        followsGraph.put("charlie", new HashSet<>());

        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        // bbitdiddle and charlie both have 1 follower, but charlie should come last
        assertNotEquals("expected bbitdiddle to be the top influencer", "bbitdiddle", influencers.get(0));
        assertNotEquals("expected charlie to be second", "charlie", influencers.get(1));
        assertEquals("expected alyssa to be third", "alyssa", influencers.get(2));
    }
}
