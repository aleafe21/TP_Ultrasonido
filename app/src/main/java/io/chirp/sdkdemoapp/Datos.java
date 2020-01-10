package io.chirp.sdkdemoapp;

public class Datos {
    String mensajeid, mensajes;

    public Datos(String mensajeid, String mensajes) {
        this.mensajeid = mensajeid;
        this.mensajes = mensajes;
    }

    public String getMensajeid() {
        return mensajeid;
    }

    public String getMensajes() {
        return mensajes;
    }
}
