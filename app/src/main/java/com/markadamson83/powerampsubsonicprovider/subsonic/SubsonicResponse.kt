package com.markadamson83.powerampsubsonicprovider.subsonic

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root

@Root(name = "subsonic-response", strict = false)
open class SubsonicResponse @JvmOverloads constructor (
    /**
     * @return the status
     */
    /**
     * @param status the status to set
     */
    @field:Attribute(name = "status")
    @param:Attribute(name = "status")
    var status: String? = null,

    /**
     * @return the version
     */
    /**
     * @param version the version to set
     */
    @field:Attribute(name = "version")
    @param:Attribute(name = "version")
    var version: String? = null) {
}