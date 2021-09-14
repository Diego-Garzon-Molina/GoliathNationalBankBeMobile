package diego.garzon.domain.mappers

abstract class Mapper<V, D> {
    abstract fun domainToViewModel(entryData: D): V
    abstract fun viewModelToDomain(entryData: V): D

    fun listDomainToViewModel(entryData: ArrayList<D>): ArrayList<V> {
        val returnList = arrayListOf<V>()
        entryData?.forEach { it?.let { returnList.add(domainToViewModel(it)) } }
        return returnList
    }

    fun listViewModelToDomain(entryData: ArrayList<V?>?): ArrayList<D> {
        val returnList = arrayListOf<D>()
        entryData?.forEach { it?.let { returnList.add(viewModelToDomain(it)) } }
        return returnList
    }
}