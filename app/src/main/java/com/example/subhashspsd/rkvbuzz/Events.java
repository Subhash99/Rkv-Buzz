package com.example.subhashspsd.rkvbuzz;

/**
 * Created by SubhashSpsd on 28-Sep-18.
 */

public class Events {
    private String publicLink;
    private  String localLink;
    private String header;
    private String description;
    private String date;
    private String url;
    public Events()
    {
    }
    public Events(String publicLink,String localLink,String header,String description,String date,String url)
    {
        this.publicLink=publicLink;
        this.localLink=localLink;
        this.header=header;
        this.description=description;
        this.date=date;
        this.url=url;
    }

    public String getUrl() {
        return url;
    }

    public String getPublicLink() {
        return publicLink;
    }

    public String getLocalLink() {
        return localLink;
    }

    public String getHeader() {
        return header;
    }

    public String getDescription() {
        return description;
    }

    public  String getDate() {return date; }
}
