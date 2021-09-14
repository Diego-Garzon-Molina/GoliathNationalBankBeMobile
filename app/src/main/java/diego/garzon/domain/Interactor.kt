package diego.garzon.domain

interface Interactor<Request, Response> {

    operator fun invoke(request: Request): Either<Exception, Response>
}