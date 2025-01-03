import java.io.File

abstract class Store(val city: String) {
    protected val inventory = mutableMapOf<String, Pair<Int, Double>>()

    init {
        initializeSalesStatisticsFile()
    }

    abstract fun repairService(): String

    fun showPhones() {
        println("Телефоны доступные в магазине $city:")
        var availablePhones = false
        inventory.forEach { (model, pair) ->
            if (pair.first > 0) {
                println("Модель: $model, Цена: ${pair.second} руб.")
                availablePhones = true
            }
        }
        if (!availablePhones) {
            println("На данный момент все модели распроданы.")
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

    private fun initializeSalesStatisticsFile() {
        val file = File("sales_statistics.txt")
        if (!file.exists()) {
            file.createNewFile()
        }
    }

    private fun updateSalesStatistics(model: String, price: Double) {
        val file = File("sales_statistics.txt")
        val currentStats = file.readLines()
        val statsMap = mutableMapOf<String, Pair<Int, Double>>()
        for (line in currentStats) {
            val parts = line.split(":")
            if (parts.size == 3) {
                val existingModel = parts[0]
                val count = parts[1].toInt()
                val totalPrice = parts[2].toDouble()
                statsMap[existingModel] = Pair(count, totalPrice)
            }
        }
        val currentData = statsMap[model] ?: Pair(0, 0.0)
        statsMap[model] = Pair(currentData.first + 1, currentData.second + price)
        file.writeText(
            statsMap.entries.joinToString("\n") { "${it.key}:${it.value.first}:${it.value.second}" }
        )
    }

    fun showSalesStatistics() {
        val file = File("sales_statistics.txt")
        if (!file.exists() || file.readText().isBlank()) {
            println("Нет данных о продажах.")
            return
        }
        println("Статистика продаж:")
        file.readText().lines().filter { it.isNotBlank() }.forEach {
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