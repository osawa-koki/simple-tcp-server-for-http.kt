fun main(args: Array<String>) {
  try {
    val server = Server()
    server.start()
    server.join()
    System.exit(0)
  } catch (e: Exception) {
    e.printStackTrace()
    System.exit(1)
  }
}
