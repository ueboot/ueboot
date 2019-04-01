package com.ueboot.cryption;

import com.alibaba.fastjson.JSONObject;

class JSONObjectWrapper {
    private JSONObject jsonObject;

    JSONObjectWrapper(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    JSONObject getJSONObject() {
        return jsonObject;
    }
}