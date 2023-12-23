package edu.skku.cs.pa2

data class GetName(var username: String ?= null)
data class IsSuccess(var success: Boolean)
data class MazeList(var name: String ?= null, var size: String ?= null)
data class MazeInfo(var maze: String ?= null)