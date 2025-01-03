import java.io.File

class CityStore(city: String) : Store(city) {
    override fun repairService() =
        if (city == "Москва") "Ремонт телефона проводится" else "Ремонтная мастерская недоступна"

    init {
        readInventory("inventory_$city.txt")
    }

    private fun readInventory(fileName: String) {
        val file = File(fileName)
        if (!file.exists()) {
            println("Файл $fileName не найден.")
            return
        }

        file.readLines().forEach { line ->
            val parts = line.split(":")
            if (parts.size == 3) {
                val model = parts[0]
                val count = parts[1].toInt()
                val price = parts[2].toDouble()
                inventory[model] = Pair(count, price)
            }
        }
    }

}