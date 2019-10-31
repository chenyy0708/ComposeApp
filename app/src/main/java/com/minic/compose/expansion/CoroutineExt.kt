package com.minic.kt.ext

import com.minic.kt.data.model.BResponse
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * 描述: 协程扩展
 * 作者: ChenYy
 * 日期: 2019-10-23 15:01
 */

suspend fun <T> BResponse<T>.awaitResponse(catchBlock: (Throwable) -> Unit = {}): T? {
    var result: T? = null
    try {
        result = suspendCancellableCoroutine<T> { cont ->
            if (null == this) {
                cont.resumeWithException(Throwable("No data"))
            } else {
                if (this.errorCode == 0) {
                    cont.resume(this.data)
                } else {
                    cont.resumeWithException(Throwable(this.errorMsg))
                }
            }
        }
    } catch (e: Throwable) {
        catchBlock(e)
        return result
    }
    return result
}

