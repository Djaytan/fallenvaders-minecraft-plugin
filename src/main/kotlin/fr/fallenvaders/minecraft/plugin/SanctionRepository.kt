package fr.fallenvaders.minecraft.plugin

import org.springframework.data.repository.CrudRepository

interface SanctionRepository : CrudRepository<Sanction, Long>
