package my.common

import groovy.json.JsonOutput

class JsonWriter implements Serializable {
    String toJson() {
        def outObject = this.getClass().declaredFields
                .findAll { !it.synthetic }
                .inject([:]) { m, v ->
                    if (this[v.name] == null || v.name.startsWith("__")) return m
                    m << [(v.name): this[v.name]]
                }
        return JsonOutput.toJson(outObject)
    }
}
