package info.glennengstrand.news

import java.util.Date
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import info.glennengstrand.io._

/** helper functions for inbound object creation */
object Inbound {
  val log = LoggerFactory.getLogger("info.glennengstrand.news.Inbound")
  val reader: PersistentDataStoreReader = new CassandraReader
  val cache: CacheAware = new MockCache
  class InboundBindings extends PersistentDataStoreBindings {
    def entity: String = {
      "Inbound"
    }
    def fetchInputs: Iterable[String] = {
      List("participantID")
    }
    def fetchOutputs: Iterable[(String, String)] = {
      List(("occurred", "Date"), ("fromParticipantID", "Int"), ("subject", "String"), ("story", "String"))
    }
    def upsertInputs: Iterable[String] = {
      List("participantID", "fromParticipantID", "occurred", "subject", "story")
    }
    def upsertOutputs: Iterable[(String, String)] = {
      List()
    }
    def fetchOrder: Map[String, String] = {
      Map("occurred" -> "desc")
    }
  }
  val bindings = new InboundBindings
  def apply(id: Int) : InboundFeed = {
    val criteria: Map[String, Any] = Map("participantID" -> id)
    new InboundFeed(id, IO.cacheAwareRead(bindings, criteria, reader, cache)) with CassandraWriter with MockCacheAware
  }
  def apply(state: String): Inbound = {
    val s = IO.fromFormPost(state)
    new Inbound(s("participantID").asInstanceOf[String].toInt, IO.df.parse(s("occurred").asInstanceOf[String]), s("fromParticipantID").asInstanceOf[String].toInt, s("subject").asInstanceOf[String], s("story").asInstanceOf[String]) with CassandraWriter with MockCacheAware
  }
}

case class InboundState(participantID: Int, occurred: Date, fromParticipantID: Int, subject: String, story: String)

/** represents a news item as it appears in your inbound feed */
class Inbound(participantID: Int, occurred: Date, fromParticipantID: Int, subject: String, story: String) extends InboundState(participantID, occurred, fromParticipantID, subject, story) with MicroServiceSerializable {
  this: PersistentDataStoreWriter with CacheAware =>

  def getState: Map[String, Any] = {
    Map(
      "participantID" -> participantID,
      "occurred" -> occurred,
      "fromParticipantID" -> fromParticipantID,
      "subject" -> subject,
      "story" -> story
    )
  }

  /** save item to db */
  def save: Unit = {
    val criteria: Map[String, Any] = Map(
      "participantID" -> participantID
    )
    write(Inbound.bindings, getState, criteria)
    invalidate(Inbound.bindings, criteria)
  }

  override def toJson: String = {
    IO.toJson(getState)
  }

  override def toJson(factory: FactoryClass): String = toJson

}

/** represents a user's inbound collection of news items */
class InboundFeed(id: Int, state: Iterable[Map[String, Any]]) extends Iterator[Inbound] with MicroServiceSerializable {
  val i = state.iterator
  def hasNext = i.hasNext
  def next() = {
    val kv = i.next()
    Inbound.log.debug("kv = " + kv)
    new Inbound(id, IO.convertToDate(kv("occurred")), IO.convertToInt(kv("fromParticipantID")), kv("subject").toString, kv("story").toString) with CassandraWriter with MockCacheAware
  }
  override def toJson: String = {
    "[" +  map(f => f.toJson).reduce(_ + "," + _) + "]"
  }

  override def toJson(factory: FactoryClass): String = toJson
}

