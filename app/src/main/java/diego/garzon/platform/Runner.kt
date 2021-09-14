package diego.garzon.platform

interface Runner {
  operator fun invoke(c: () -> Unit)
}
