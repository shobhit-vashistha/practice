
object Util {

  def time[R](block: => R): R = {
    val t0 = System.nanoTime()
    val result = block // call-by-name
    val t1 = System.nanoTime()
    val d = (t1 - t0) / 1e9d
    println(s"Time taken: ${d} seconds")
    result
  }

}
