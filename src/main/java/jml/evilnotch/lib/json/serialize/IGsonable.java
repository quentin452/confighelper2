package jml.evilnotch.lib.json.serialize;

import com.google.gson.JsonElement;

public interface IGsonable {

    /**
     * convert the json into a JsoneElement from gson
     */
    public JsonElement toGson();

    /**
     * insert json mappings into a json
     */
    public void fromGson(JsonElement elements);

}
