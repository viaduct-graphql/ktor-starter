package com.example.viadapp.injector

import javax.inject.Provider
import viaduct.service.api.spi.TenantCodeInjector

class KtorTenantCodeInjector : TenantCodeInjector {
    override fun <T> getProvider(clazz: Class<T>): Provider<T> {
        return Provider {
            clazz.getDeclaredConstructor().newInstance()
        }
    }
}
