import java.util.*;

public class VotesJava {
    private static class Vote {
        public String p1, p2, p3;
        public Vote(String p1, String p2, String p3) {
            this.p1 = p1;
            this.p2 = p2;
            this.p3 = p3;
        }
    }

    private static class Candidate implements Comparable<Candidate> {

        public String name;
        public Integer voteCount;

        public Candidate(String name, Integer voteCount) {
            this.name = name;
            this.voteCount = voteCount;
        }

        @Override
        public int compareTo(Candidate o) {
            return this.voteCount.compareTo(o.voteCount);
        }
    }

    private List<String> findWinner0(List<Vote> votes) {
        Map<String, Integer> voteMap = new HashMap<>();
        votes.forEach(v -> {
            voteMap.put(v.p1, voteMap.getOrDefault(v.p1, 0) + 3);
            voteMap.put(v.p2, voteMap.getOrDefault(v.p2, 0) + 2);
            voteMap.put(v.p3, voteMap.getOrDefault(v.p3, 0) + 1);
        });
        List<Candidate> results = new ArrayList<>();
        voteMap.forEach((c, v) -> {
            results.add(new Candidate(c, v));
        });
        results.sort(Comparator.comparing(o -> -o.voteCount));
        List<String> ordNames = new ArrayList<>();
        results.forEach(r -> {
            ordNames.add(r.name);
        });
        return ordNames;
    }

    private List<String> findWinner1(List<Vote> votes) {
        Map<String, Integer> voteMap = new HashMap<>();
        votes.forEach(v -> {
            voteMap.put(v.p1, voteMap.getOrDefault(v.p1, 0) + 3);
            voteMap.put(v.p2, voteMap.getOrDefault(v.p2, 0) + 2);
            voteMap.put(v.p3, voteMap.getOrDefault(v.p3, 0) + 1);
        });
        PriorityQueue<Candidate> queue = new PriorityQueue<>();
        voteMap.forEach((c, v) -> {
            queue.add(new Candidate(c, v));
        });
        List<String> ordNames = new ArrayList<>();
        queue.forEach(c -> {
            ordNames.add(c.name);
        });
        Collections.reverse(ordNames);
        return ordNames;
    }

    private void test() {
        List<Vote> votes = Arrays.asList(
            new Vote("a", "b", "c"), // a=3 b=2 c=1 d=0
            new Vote("b", "a", "c"), // a=5 b=5 c=2 d=0
            new Vote("d", "a", "c"), // a=7 b=5 c=3 d=3
            new Vote("a", "c", "b")  // a=10 b=6 c=5 d=3
        );
        List<String> ordNames = findWinner1(votes);
        ordNames.forEach(System.out::println);
    }

    public static void main(String[] args) {
        VotesJava v = new VotesJava();
        v.test();
    }

}
