package com.spotapp.mobile.domain.model.ext

import com.spotapp.mobile.data.Result as DataResult
import com.spotapp.mobile.domain.model.Result as DomainResult

internal inline fun <reified I, O> DataResult<I>.asDomainResult(crossinline dataAsDomain: (I) -> O): DomainResult<O> =
    when (this) {
        is DataResult.Error -> DomainResult.Error(exception)
        is DataResult.Loading -> DomainResult.Loading
        is DataResult.Success -> DomainResult.Success(dataAsDomain(data))
    }
