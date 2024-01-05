package ru.perm.v.shopkotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
class ShopKotlinApplication {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<ShopKotlinApplication>(*args)
        }
    }
}

