fun main() {
    val stores = listOf(CityStore("Москва"), CityStore("Санкт-Петербург"))
    var continueShopping = true

    while (continueShopping) {
        println("Добро пожаловать в сеть магазинов по продаже телефонов!")
        println("Выберите город для покупок:")
        stores.forEachIndexed { index, store -> println("${index + 1}. ${store.city}") }

    }


}