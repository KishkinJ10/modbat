package modbat.trace

import java.lang.reflect.Field

import modbat.dsl.Transition
import modbat.mbt.ModelInstance

class RecordedTransition(val model: ModelInstance,
                         val transition: Transition,
                         val recordedAction: StackTraceElement = null,
                         /* state override result (if any) that was recorded */
                         val transToNextState: Transition = null,
                         /* exception type (if any) that was recorded */
                         val exceptionType: String = null) {
  var randomTrace: Array[Int] = null
  var debugTrace: Array[String] = null
  var updates: List[(Field, Any)] = Nil

  var recordedChoices: List[RecordedChoice] = _ // record choices -Rui

  /* transToNextState should override default successor state */
  def dest = {
    if (transToNextState == null) {
      transition.dest
    } else {
      transToNextState.dest
    }
  }

  /* transition that was effectively taken */
  def trans = {
    if (transToNextState == null) {
      transition
    } else {
      transToNextState
    }
  }

  override def toString = trans.toString
  // TODO: Support exceptionType
}

/** RecordedChoice */ // record choices -RUI
trait RecordedChoice {
  val recordedChoice: Any
}
//case class RecordedChoice(var recordedChoice: Any)
case class FuncChoice(recordedChoice: () => Any) extends RecordedChoice
case class BoolChoice(recordedChoice: Boolean) extends RecordedChoice
case class NumChoice(recordedChoice: Int) extends RecordedChoice
case class MaybeChoice(recordedChoice: Boolean) extends RecordedChoice
