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

package com.companion.api.controller

import com.companion.api.model.*
import com.companion.api.util.getBaseURL
import org.apache.commons.io.IOUtils
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import springfox.documentation.annotations.ApiIgnore
import java.io.InputStream
import java.sql.Timestamp
import javax.servlet.http.HttpServletRequest

/**
 * Companion rest controller.
 *
 * @author Andreas Sekulski, Dimitri Kotlovsky
 */
@RestController
class CompanionController {

    /**
     * Default rest site controller.
     * @return Welcome message.
     */
    @ApiIgnore
    @RequestMapping(value = ["/"])
    fun redirectToSwagger(request : HttpServletRequest): String {
        return "Welcome to Companion API"
    }

    /**
     * Get all companion models to search for objects as JSON.
     * @return JSON CompanionData which contains all data to search for objects.
     */
    @RequestMapping(value = ["/getCompanionData"], method = [RequestMethod.GET, RequestMethod.POST])
    fun getCompanionData(request: HttpServletRequest): CompanionData {

        val url: String = getBaseURL(request)

        val artist0 = Artist(
                0,
                Timestamp(1518105139),
                "David Bothe",
                url + "/getImage?image=" + "artist0"
        )

        val artist1 = Artist(
                1,
                Timestamp(1518105139),
                "Marco Almbauer",
                url + "/getImage?image="
        )

        val artist2 = Artist(
                2,
                Timestamp(1518105139),
                "Jacques Louis David",
                url + "/getImage?image=" + "artist2"
        )

        val video0 = Video(
                0,
                Timestamp(1518105139),
                url + "/getVideo?video=" + "video0",
                url + "/getImage?image=" + "video0",
                "Expertenkommentar",
                "03:14"
        )

        val audio0 = Audio(
                0,
                Timestamp(1518105139),
                url + "/getAudio?audio=" + "audio0",
                "Cybärcast Folge Null"
        )

        val art0 = Artwork(
                0,
                Timestamp(1518105139),
                url + "/getImage?image=" + "art0",
                "Sicheres Passwort-Hashing",
                0,
                "2016",
                "Info Poster",
                "<size=85><b>Sicheres Passwort-Hashing</b></size>\r\nMit der Infografik möchte das if(is) die Nutzer anregen, in Zukunft auch weiterhin verantwortungsvoll mit Passwörtern im Internet umzugehen und erklärt das Thema der sicheren Passwort Speicherung anhand anschaulicher Bilder und Erklärungen. Betreiber von Internetseiten und Services sollen daran erinnert werden, dass das Vertrauen der Nutzer in ihre Dienste eine große Verantwortung mit sich zieht, und sie diesem Vertrauen gerecht werden sollten.",
                listOf(video0.videoID),
                emptyList()
        )

        val art1 = Artwork(
                1,
                Timestamp(1518105139),
                url + "/getImage?image=" + "art1",
                "Erfolgreiche Authentifikation",
                0,
                "2017",
                "Info Poster",
                "<size=85><b>Erfolgreiche Authentifikation</b></size>\r\nMit der Infografik möchte das if(is) die Nutzer anregen, in Zukunft auch weiterhin verantwortungsvoll mit dem Thema Authentifikation umzugehen und erklärt anhand anschaulicher Bilder und Erklärungen die verschiedenen Verfahren.",
                emptyList(),
                listOf(audio0.audioID)
        )

        val art2 = Artwork(
                2,
                Timestamp(1518105139),
                url + "/getImage?image=" + "art2",
                "Kylemore Abbey Church",
                1,
                "2016",
                "Photography",
                "<size=85><b>Kylemore Abbey & Neo-Gothic Church</b></size>\r\nKylemore Abbey Neo-Gothic Church was built in the style of a fourteenth-century English Cathedral in memory of the wife of Mitchell Henry, who owned the neighbouring Kylemore Abbey / Castle.",
                emptyList(),
                emptyList()
        )

        val art3 = Artwork(
                3,
                Timestamp(1518105139),
                url + "/getImage?image=" + "art3",
                "Napoleon Bonaparte",
                2,
                "1812",
                "Painting",
                "<size=85><b>The Emperor Napoleon in His Study at the Tuileries</b></size>\r\nCareful examination of the details embedded in this portrait reveals the key to David's success as a painter during the time of Louis XVI, Robespierre, and Napoleon: the artist's ability to transform his subjects into politically powerful icons.",
                emptyList(),
                emptyList()
        )

        return CompanionData(
                Timestamp(1518105139),
                listOf(artist0, artist1, artist2),
                listOf(art0, art1, art2, art3),
                listOf(video0),
                listOf(audio0)
        )
    }

    /**
     * Obtain image from companion model for example a artist or object. If the image does not exists, 404 will be returned.
     * @param image Image name to obtain.
     * @return Sends byte array from image if exists otherwise 404.
     */
    @RequestMapping(value = ["/getImage"], method = [RequestMethod.GET, RequestMethod.POST], produces = [MediaType.IMAGE_JPEG_VALUE])
    fun getImage(@RequestParam("image") image: String): ResponseEntity<ByteArray> {

        val classloader: ClassLoader = Thread.currentThread().contextClassLoader

        val resource: InputStream = classloader.getResourceAsStream("images/$image.jpg")
                ?: return ResponseEntity.notFound().build()

        return ResponseEntity.ok(IOUtils.toByteArray(resource))
    }

    /**
     * Obtain video from companion model. If the video does not exists, 404 will be returned.
     * @param video Video name to obtain.
     * @return Sends byte array from video if exists otherwise 404.
     */
    @RequestMapping(value = ["/getVideo"], method = [RequestMethod.GET, RequestMethod.POST], produces = [MediaType.APPLICATION_OCTET_STREAM_VALUE]) // "video/mp4"
    fun getVideo(@RequestParam("video") video: String): ResponseEntity<ByteArray> {

        val classloader: ClassLoader = Thread.currentThread().contextClassLoader

        val resource: InputStream = classloader.getResourceAsStream("videos/$video.mp4")
                ?: return ResponseEntity.notFound().build()

        return ResponseEntity.ok(IOUtils.toByteArray(resource))
    }

    /**
     * Obtain audio from companion model. If the audio does not exists, 404 will be returned.
     * @param audio Audio name to obtain.
     * @return Sends byte array from audio if exists otherwise 404.
     */
    @RequestMapping(value = ["/getAudio"], method = [RequestMethod.GET, RequestMethod.POST], produces = [MediaType.APPLICATION_OCTET_STREAM_VALUE]) // "audio/ogg"
    fun getAudio(@RequestParam("audio") audio: String): ResponseEntity<ByteArray> {

        val classloader: ClassLoader = Thread.currentThread().contextClassLoader

        val resource: InputStream = classloader.getResourceAsStream("audio/$audio.ogg")
                ?: return ResponseEntity.notFound().build()

        return ResponseEntity.ok(IOUtils.toByteArray(resource))
    }
}
