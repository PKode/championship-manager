package fr.pk.championshipmanagerapp.blackbox

import org.springframework.stereotype.Component

@Component
class ScenarioContext {
    val context: HashMap<ContextKey, Any> = hashMapOf()

    fun put(key: ContextKey, value: Any) {
        this.context[key] = value
    }

    fun get(key: ContextKey) = context[key]
    fun replacePlaceHolders(map: MutableMap<String, String>): Map<String, String> {
        return map.map { (key, value) ->
            if(value.startsWith(VARIABLE_DELIMITER)) key to get(enumValueOf(value.substringAfter(VARIABLE_DELIMITER))).toString()
            else key to value
        }.toMap()
    }

    private val VARIABLE_DELIMITER = "$"
}

enum class ContextKey {
    LAST_CHAMPIONNAT_ID,
    LAST_EQUIPE_ID
}
