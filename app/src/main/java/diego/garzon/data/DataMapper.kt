package diego.garzon.data

abstract class DataMapper<X, D> {
    abstract fun dataToDomain(entryData: X?): D?
    abstract fun domainToData(entryData: D?): X?

    fun listDomainToData(entryData: ArrayList<D?>?): List<X> {
        val returnList = arrayListOf<X>()
        entryData?.forEach { it?.let { domainToData(it)?.let { it1 -> returnList.add(it1) } } }
        return returnList
    }

    fun listDataToDomain(entryData: List<X?>?): ArrayList<D> {
        val returnList = arrayListOf<D>()
        entryData?.forEach { it?.let { dataToDomain(it)?.let { it1 -> returnList.add(it1) } } }
        return returnList
    }
}