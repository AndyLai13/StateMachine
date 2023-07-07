class State(val flag: Int) {
    override fun toString(): String {
        return "STATE $flag"
    }
}

val State1 = State(1)
val State2 = State(2)
val State3 = State(3)

object StateMachine {
    var state = State1
        set(value) {
            field = value
            callbacks.forEach { it.onStateChange(field) }
        }
    var callbacks = mutableListOf<Callback>()

    fun addCallbacks(vararg callbacks: Callback) {
        callbacks.forEach { this.callbacks.add(it) }
    }

    interface Callback {
        fun onStateChange(state: State)
    }
}

class User(var state: State) : StateMachine.Callback {
    override fun onStateChange(state: State) {
        this.state = state
    }
}

fun main() {
    val user1 = User(StateMachine.state)
    println("user1 state = ${user1.state}")
    val user2 = User(StateMachine.state)
    println("user2 state = ${user2.state}")

    StateMachine.addCallbacks(user1, user2)

    println("Chang State")
    StateMachine.state = State2
    println("user1 state = ${user1.state}")
    println("user2 state = ${user2.state}")
    StateMachine.state = State3
    println("Chang State")
    println("user1 state = ${user1.state}")
    println("user2 state = ${user2.state}")
}