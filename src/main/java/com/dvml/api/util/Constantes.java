package com.dvml.api.util;

public class Constantes {


    public static String getObsTriagemManchester(String cor) {

        System.out.println("COR: " +cor);
        switch (cor.toLowerCase()) {
            case "vermelho":
                return "Necessita de atendimento imediato";
            case "laranja":
                return "Necessita de atendimento praticamente imediato";
            case "amarelo":
                return "Necessita de atendimento rápido mas podem aguardar";
            case "verde":
                return "Podem aguardar antendimento ou serem encaminhados para outros serviços de saúde";
            case "azul":
                return "Podem aguardar antendimento ou serem encaminhados para outros serviços de saúde";

            default:

                return "n/a";
        }
    }
}
