package com.example.viadapp.injector

import com.example.viadapp.SCHEMA_ID
import viaduct.service.BasicViaductFactory
import viaduct.service.SchemaRegistrationInfo
import viaduct.service.SchemaScopeInfo
import viaduct.service.TenantRegistrationInfo
import viaduct.service.api.Viaduct

class ViaductConfiguration {
    private val codeInjector: KtorTenantCodeInjector = KtorTenantCodeInjector()

    fun viaductService(): Viaduct =
        BasicViaductFactory.create(
            schemaRegistrationInfo = SchemaRegistrationInfo(
                scopes = listOf(SchemaScopeInfo(SCHEMA_ID)),
            ),
            tenantRegistrationInfo = TenantRegistrationInfo(
                tenantPackagePrefix = "com.example.viadapp",
                tenantCodeInjector = codeInjector
            )
        )
}
