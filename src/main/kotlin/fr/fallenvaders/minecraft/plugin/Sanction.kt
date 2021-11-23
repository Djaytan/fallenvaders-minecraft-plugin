package fr.fallenvaders.minecraft.plugin

import javax.persistence.*

@Entity
class Sanction(
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  val id: Long,
  val name: String,
  val description: String
)
