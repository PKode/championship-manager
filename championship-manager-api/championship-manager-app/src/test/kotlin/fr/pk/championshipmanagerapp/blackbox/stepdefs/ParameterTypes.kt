package fr.pk.championshipmanagerapp.blackbox.stepdefs

import com.fasterxml.jackson.module.kotlin.readValue
import fr.pk.championshipmanagerapp.blackbox.ScenarioContext
import fr.pk.championshipmanagerapp.blackbox.jacksonObjectMapper
import fr.pk.championshipmanagerapplication.dto.EquipeDto
import fr.pk.championshipmanagerapplication.dto.JoueurDto
import io.cucumber.java.DataTableType
import io.cucumber.java.DefaultDataTableCellTransformer
import io.cucumber.java.DefaultDataTableEntryTransformer
import io.cucumber.java.DefaultParameterTransformer
import java.lang.reflect.Type

class ParameterTypes(private val scenarioContext: ScenarioContext) {
    private val objectMapper = jacksonObjectMapper

    @DefaultParameterTransformer
    @DefaultDataTableEntryTransformer
    @DefaultDataTableCellTransformer
    fun transformer(fromValue: Any, toValueType: Type): Any {
        val asString = scenarioContext.replace(jacksonObjectMapper.writeValueAsString(fromValue))
        val asMap = jacksonObjectMapper.readValue<Map<String, Any>>(asString)
        return objectMapper.convertValue(asMap, objectMapper.constructType(toValueType))
    }

    @DataTableType
    fun joueurEntry(entry: Map<String, String>): JoueurDto {
        val processMap = scenarioContext.replace(entry)
        return JoueurDto(
                entry["id"]?.let { scenarioContext.replace(it).toInt() },
                entry.getValue("nom"),
                entry["prenom"],
                entry.getValue("poste"),
                entry.getValue("nationalite"),
                entry.getValue("dateNaissance"),
                entry.getValue("taille").toInt(),
                entry.getValue("poids").toInt(),
                processMap["equipe"]?.let { jacksonObjectMapper.readValue<EquipeDto>(it) }
        )
    }
}
