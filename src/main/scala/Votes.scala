import scala.collection.mutable
import Util._

/*

* For a list of votes, return an ordered set of candidate in descending
order of their votes.
* Vote is a class that contains list of candidate names(as String)

List<String> findWinner(List<Vote> votes)

We pass in a list of votes and we are returned a list of names in the
descending order of the score that each candidate received.
Assume that we extract the candidates' names from the votes as we process them.

A voter is allowed to vote for three different candidates.
The order of the votes is important. The first vote that a voter places is worth three points. The second vote is worth two points. The third vote is worth one point.

The function should return a list of candidates in descending order
of the total number of points received by the candidate.
 */

case class Vote(p1: String, p2: String, p3: String)
case class Candidate(name: String, voteCount: Int = 0)

object Votes {
  implicit val candidateOrdering: Ordering[Candidate] = Ordering.by[Candidate, Int](_.voteCount).reverse

  // no custom data structure, sorting O(n
  private def findWinner0(votes: Seq[Vote]): Seq[String] = {
    // insert into map O(n)
    val voteMap = new mutable.HashMap[String, Int]()
    votes.foreach(v => {
      Seq((v.p1, 3), (v.p2, 2), (v.p3, 1)).foreach(pr => {
        voteMap.put(pr._1, voteMap.getOrElse(pr._1, 0) + pr._2)
      })
    })

    // sort O(n * log(n))
    voteMap.toSeq.sortWith(_._2 < _._2).map(_._1)
  }

  // no custom data structure, priority queue
  private def findWinner1(votes: Seq[Vote]): Seq[String] = {
    // insert into map O(n)
    val voteMap = new mutable.HashMap[String, Int]()
    votes.foreach(v => {
      Seq((v.p1, 3), (v.p2, 2), (v.p3, 1)).foreach(pr => {
          voteMap.put(pr._1, voteMap.getOrElse(pr._1, 0) + pr._2)
      })
    })

    // insert into priority queue O(n * log(n))
    val queue = new mutable.PriorityQueue[Candidate]()
    voteMap.foreach(pr => {
      queue.enqueue(Candidate(pr._1, pr._2))
    })

    // return
    queue.map(_.name).toList
  }

  private def test(func: Function[Seq[Vote], Seq[String]]): Unit = {
    val votes = List(
      Vote("a", "b", "c"), // a=3 b=2 c=1 d=0
      Vote("b", "a", "c"), // a=5 b=5 c=2 d=0
      Vote("d", "a", "c"), // a=7 b=5 c=3 d=3
      Vote("a", "c", "b")  // a=10 b=6 c=5 d=3
    )
    val res = time(func(votes))
    res.foreach(println(_))
  }

  def main(): Unit = {
    test(findWinner0)
    test(findWinner1)
  }

}
