class CityStore(city: String) : Store(city) {
    override fun repairService() = if (city == "Москва") "Ремонт телефона проводится" else "Ремонтная мастерская недоступна"


}