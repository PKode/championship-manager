package fr.pk.championshipmanagerapp.blackbox

import org.springframework.stereotype.Component

@Component
class ScenarioContext {
    val context: HashMap<ContextKey, Any> = hashMapOf()

    fun put(key: ContextKey, value: Any) {
        this.context[key] = value
    }

    fun get(key: ContextKey) = context[key]

    fun replacePlaceHolders(variables: MutableMap<String, Any>): Map<String, Any> {
        return variables.map { (key, value) ->
            if(value.toString().startsWith(VARIABLE_DELIMITER)) key to get(enumValueOf(value.toString().substringAfter(VARIABLE_DELIMITER))).toString()
            else key to value
        }.toMap()
    }

    fun replacePlaceHolders(payload: String) : String {
        return payload.replace("\\$[a-zA-Z_]+".toRegex()) {
            get(enumValueOf(it.value.substringAfter(VARIABLE_DELIMITER))).toString()
        }
    }

    private val VARIABLE_DELIMITER = "$"
}

enum class ContextKey {
    LAST_CHAMPIONNAT_ID,
    LAST_EQUIPE_ID,
    LAST_EQUIPE_ID_BY_NAME,
    LAST_CALENDAR,
    LAST_JOUEUR_ID
}
