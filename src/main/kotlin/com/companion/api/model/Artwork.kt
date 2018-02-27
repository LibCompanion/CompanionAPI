/*
 * This program is an api service written with spring-boot.
 * Copyright (c) 2018 Andreas Sekulski, Dimitri Kotlovsky
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.companion.api.model

import java.sql.Timestamp

/**
 * Artwork data class.
 *
 * @author Andreas Sekulski, Dimitri Kotlovsky
 */
data class Artwork(val artworkID: Long,
                   val timestamp: Timestamp,
                   val image: String,
                   val title: String,
                   var artistID: Long,
                   val year: String,
                   val artStyle: String,
                   val detailedInfo: String,
                   val videos: List<Long>,
                   val audio: List<Long>)
