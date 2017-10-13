package no.ern.game.api.controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import no.ern.game.api.domain.converters.EntityConverter
import no.ern.game.api.domain.dto.EntityDto
import no.ern.game.api.repository.EntityRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*


@Api(value = "/entities", description = "API for entities data.")
@RequestMapping(
        path = arrayOf("/entities"),
        produces = arrayOf(MediaType.APPLICATION_JSON_VALUE)
)
@RestController
@Validated
class EntityController{

    @Autowired
    private lateinit var crud: EntityRepository

    @ApiOperation("Retrieve all entities")
    @GetMapping
    fun getEntities() : ResponseEntity<List<EntityDto>> {
        return ResponseEntity.ok(EntityConverter.transform(crud.findAll()))
    }

    @ApiOperation("Create an entity")
    @PostMapping(consumes = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    @ApiResponse(code = 201, message = "The id of newly entity")
    fun createEntity(
            @ApiParam("The question and answer options")
            @RequestBody dto: EntityDto) : ResponseEntity<Long>{

        if (dto.field1 == null || dto.field2 == null || dto.id != null)
            return ResponseEntity.status(400).build()

        val id: Long?
        try{
            id = crud.createEntity(dto.field1!!, dto.field2!!)
        }catch (e: Exception){
            return ResponseEntity.status(400).build()
        }

        if (id==null){
            // BUG !!!
            return ResponseEntity.status(500).build()
        }

        return ResponseEntity.status(201).body(id)
    }

}
