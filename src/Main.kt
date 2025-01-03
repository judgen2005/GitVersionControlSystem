fun main() {
    val stores = listOf(CityStore("Москва"), CityStore("Санкт-Петербург"))
    var continueShopping = true

    while (continueShopping) {
        println("Добро пожаловать в сеть магазинов по продаже телефонов!")
        println("Выберите город для покупок:")
        stores.forEachIndexed { index, store -> println("${index + 1}. ${store.city}") }
        println("0. Выйти из приложения")
        val cityChoice = readInt()
        if (cityChoice == 0) {
            continueShopping = false
            break
        }
        if (cityChoice !in 1..stores.size) {
            println("Выберите правильный город.")
            continue
        }
        val store = stores[cityChoice - 1]
        println("Вы находитесь в магазине в городе ${store.city}")
        store.showPhones()
        println("Выберите действие:")
        println("1. Купить телефон")
        println("2. Показать статистику покупок")
        println("3. Воспользоваться сервисом ремонта")
        println("0. Назад")
        when (readInt()) {
            1 -> {
                println("Введите модель телефона для покупки:")
                val model = readln().trim()
                store.sellPhone(model)
            }
            2 -> store.showSalesStatistics()
            3 -> println(store.repairService())
            0 -> continue
            else -> println("Выберите правильное действие.")
        }
    }
    println("Спасибо за использование нашего сервиса!")
}

fun readInt(): Int {
    return try {
        readln().toInt()
    } catch (e: NumberFormatException) {
        println("Неверный формат ввода, пожалуйста, введите число.")
        -1
    }
}
