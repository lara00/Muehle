import java.io.{ByteArrayOutputStream, PrintStream}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import de.htwg.se.Muehle.model.Main

class MainSpec extends AnyFlatSpec with Matchers {
  "The Main object" should "print 'Play Muehle' when main method is called" in {
    val outContent = new ByteArrayOutputStream()
    Console.withOut(new PrintStream(outContent)) {
      Main.main(Array())
    }
    outContent.toString should include("Play Muehle")
  }
}
