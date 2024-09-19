package sakuratech.sakurachat

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SakuraChatApplication

fun main(args: Array<String>) {
	runApplication<SakuraChatApplication>(*args)
}
