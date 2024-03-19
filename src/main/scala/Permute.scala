import Util._

object Permute {

  private def permutations(str: String): Unit = {
    permutations(str, "")
  }

  private def permutations(str: String, prefix: String): Unit = {
    // base case
    if (str.isEmpty) {
      println(prefix + str)
    } else {
      // for each char in string
      for (i <- 0 until str.length) {
        // exclude the char from str and add it to prefix
        val rem = str.substring(0, i) + str.substring(i + 1, str.length)
        // recursively get permutations on the remainder of the string with new prefix
        println(permutations(rem, prefix + str.substring(i, i + 1)))
      }
    }
  }

  private def permutations2(str: String): Unit = {
    permutations2(str.toCharArray, 0, str.length - 1)
  }

  private def permutations2(str: Array[Char], l: Int, r: Int): Unit = {
    // base case
    if (l == r) {
      println(new String(str))
    } else {
      // for each remaining chars
      for (i <- l to r) {
        swap(str, i, l)
        permutations2(str, l + 1, r)
        swap(str, l, i)
      }
    }
  }

  private def swap(str: Array[Char], i: Int, j: Int): Unit = {
    if (i != j) {
      val temp = str(i)
      str(i) = str(j)
      str(j) = temp
    }
  }

  def main(): Unit = {
    time(permutations("abcdef"))
    time(permutations2("abcdef"))
  }
}
