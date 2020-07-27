package modbat.mbt

import java.nio.channels.ClosedChannelException
import scala.util.matching.Regex
import org.scalatest._

class MBTTest extends FlatSpec with Matchers {
  "ClosedChannelException" should "match" in {
    MBT.expected(List(new Regex("ClosedCh.*Exc")),
                 new ClosedChannelException()) shouldBe true
  }
}
