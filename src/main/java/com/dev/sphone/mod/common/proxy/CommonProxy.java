package com.dev.sphone.mod.common.proxy;

public class CommonProxy {

    public void preInit() {
        // Inicialização comum (lado servidor/cliente)
    }

    public void init() {
        // Inicialização comum (lado servidor/cliente)
    }

    // Adicione este método para abrir a GUI do rádio, mas não faz nada no lado servidor.
    public void openRadioGui() {
        // Intencionalmente vazio - só faz algo no ClientProxy
    }
}
