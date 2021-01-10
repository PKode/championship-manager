package fr.pk.championshipmanagerapp.blackbox

import org.springframework.stereotype.Component
import org.springframework.util.PropertyPlaceholderHelper

@Component
class ScenarioContext {
    val context: HashMap<ContextKey, Any> = hashMapOf()
    private val propertyPlaceholderHelper = PropertyPlaceholderHelper("\${", "}")

    operator fun get(key: ContextKey) = context.getValue(key)

    fun put(key: ContextKey, value: Any) {
        this.context[key] = value
    }

    fun replace(payload: String): String {
        return propertyPlaceholderHelper.replacePlaceholders(payload) {
            this[enumValueOf(it)].toString()
        }
    }

    fun replace(payload: Map<String, String>): Map<String, String> {
        return payload.mapValues { replace(it.value) }
    }
}

enum class ContextKey {
    LAST_CHAMPIONNAT_ID,
    LAST_EQUIPE_ID,
    LAST_EQUIPE_ID_BY_NAME,
    LAST_CALENDAR,
    LAST_JOUEUR_ID
}
