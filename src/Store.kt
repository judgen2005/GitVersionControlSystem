import java.io.File

abstract class Store(val city: String) {
    protected val inventory = mutableMapOf<String, Pair<Int, Double>>()

    abstract fun repairService(): String

    fun showPhones() {
        println("Телефоны доступные в магазине $city:")
        inventory.forEach { (model, pair) ->
            println("Модель: $model, Цена: ${pair.second} руб.")
        }
    }

    fun sellPhone(model: String) {
        val phoneInfo = inventory[model]
        if (phoneInfo != null && phoneInfo.first > 0) {
            inventory[model] = Pair(phoneInfo.first - 1, phoneInfo.second)
            println("Телефон $model был успешно куплен!")
            updateSalesStatistics(model, phoneInfo.second)
        } else {
            println("Телефон $model недоступен для покупки.")
        }
    }

    private fun updateSalesStatistics(model: String, price: Double) {
        val file = File("sales_statistics.txt")
        val currentStats = file.readText().lines().map { it.split(":") }
        val newStats = mutableListOf<Pair<String, Pair<Int, Double>>>()
        for (line in currentStats) {
            if (line.size == 3) {
                val existingModel = line[0]
                val count = line[1].toInt()
                val totalPrice = line[2].toDouble()
                if (existingModel == model) {
                    newStats.add(
                        Pair(existingModel, Pair(count + 1, totalPrice + price))
                    )
                } else {
                    newStats.add(Pair(existingModel, Pair(count, totalPrice)))
                }
            }
        }
        file.writeText(
            newStats.joinToString("\n") { "${it.first}:${it.second.first}:${it.second.second}" }
        )
    }

    fun showSalesStatistics() {
        val file = File("sales_statistics.txt")
        if (!file.exists()) {
            println("Нет данных о продажах.")
            return
        }
        println("Статистика продаж:")
        file.readText().lines().forEach {
            val parts = it.split(":")
            if (parts.size == 3) {
                val model = parts[0]
                val count = parts[1]
                val totalPrice = parts[2]
                println("Модель $model: Продано $count шт. на сумму $totalPrice руб.")
            }
        }
    }
}