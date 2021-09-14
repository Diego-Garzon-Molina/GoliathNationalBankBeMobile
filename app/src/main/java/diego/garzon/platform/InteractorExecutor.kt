package diego.garzon.platform

import diego.garzon.domain.Interactor

interface InteractorExecutor {

    operator fun <Request, Response> invoke(
        interactor: Interactor<Request, Response>,
        request: Request,
        onError: (Exception) -> Unit = {},
        onSuccess: (Response) -> Unit = {}
    )
}