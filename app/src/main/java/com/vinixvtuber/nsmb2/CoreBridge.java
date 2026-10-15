package com.vinixvtuber.nsmb2;

public class CoreBridge {
    // Carregar a biblioteca nativa compilada (.so)
    static {
        System.loadLibrary("nsmb2_port");
    }

    // Declaração do método nativo para inicializar o motor do jogo
    public static native boolean initEngine();
}
