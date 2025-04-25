package Flow.hot_flows

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

object Repository {

    private val _flow = MutableSharedFlow<Int>()
    val flow = _flow.asSharedFlow()
    val dispatcher = Executors.newCachedThreadPool().asCoroutineDispatcher()
    val scope = CoroutineScope(dispatcher)


    init {
        scope.launch {
            var seconds = 0
            while (true) {
                _flow.emit(seconds++)
                delay(1000)
            }
        }


    }
}