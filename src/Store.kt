abstract class Store(val city: String) {
    protected val inventory = mutableMapOf<String, Pair<Int, Double>>()



}