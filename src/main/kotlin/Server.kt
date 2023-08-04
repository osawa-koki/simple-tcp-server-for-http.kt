import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket

class Server : Thread() {
  override fun run() {
    try {
      println("===== Server started! =====")
      try {
        ServerSocket(8000).use { server ->
          while (true) {
            try {
              val sc = server.accept()
              println("=====> クライアントからの接続がありました。")
              var reader: BufferedReader? = null
              var writer: PrintWriter? = null
              try {
                reader = BufferedReader(InputStreamReader(sc!!.getInputStream()))
                writer = PrintWriter(sc.getOutputStream(), true)
                var line: String? = null
                writer.println("HTTP/1.1 200 OK")
                writer.println("Content-Type: text/plain; charset=utf-8")
                writer.println("Server: simple-tcp-server-for-http.java")
                writer.println("")
                println("=====> (REQUEST)")
                while (reader.ready()) {
                  line = reader.readLine()
                  println("=====> $line")
                  writer.println(line)
                }
                println("=====> (EOF)")
              } catch (e: Exception) {
                e.printStackTrace()
              } finally {
                // リソースの解放。
                reader?.close()
                writer?.close()
                sc?.close()
                println("=====> クライアントとの接続を閉じました。")
              }
            } catch (ex: Exception) {
              ex.printStackTrace()
              break
            }
          }
        }
      } catch (e: Exception) {
        e.printStackTrace()
      }
      println("===== Server stopped! =====")
    } catch (e: Exception) {
      e.printStackTrace()
    }
  }
}
