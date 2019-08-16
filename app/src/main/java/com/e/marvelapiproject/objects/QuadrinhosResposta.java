package com.e.marvelapiproject.objects;

import com.google.gson.annotations.SerializedName;

public class QuadrinhosResposta extends Result {

    @SerializedName("data")
    public Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    private String attributionText;

    public String getAttributionText() {
        return attributionText;
    }

    public void setAttributionText(String attributionText) {
        this.attributionText = attributionText;
    }

    @Override
    public String toString() {
        return "QuadrinhosResposta{" +
                "data=" + data +
                ", attributionText='" + attributionText + '\'' +
                '}';
    }

}
