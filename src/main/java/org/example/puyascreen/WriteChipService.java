package org.example.puyascreen;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.function.Supplier;

public class WriteChipService implements Supplier<Boolean> {

    private String directoryPath = "C:\\CAMINHO\\OPENCD\\BIN";
    private String serialProgrammer;
    private String firmwarePath;
    private Process externProcess;
    private volatile boolean canceled = false;

    public WriteChipService(String serialProgrammer, String firmwarePath) {
        this.serialProgrammer = serialProgrammer;
        this.firmwarePath = firmwarePath;
    }

    @Override
    public Boolean get() {
        if (canceled) {
            System.out.println("[" + serialProgrammer + "]: Gravação abortada antes de iniciar.");
            return false;
        }
        String fullPath = directoryPath + File.separator + "openocd.exe";
        boolean success = false;

        ProcessBuilder pb = new ProcessBuilder(
                fullPath,
                "ADICIONAR AQUI O COMANDO PARA O CHIP"
        );

        pb.directory(new File(directoryPath));
        pb.redirectErrorStream(true);
        try {
            externProcess = pb.start();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(externProcess.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {

                    if(canceled){
                        System.out.println("[" + serialProgrammer + "]: Interrompendo a leitura do OpenOCD...");
                        break;
                    }

                    System.out.println("[" + serialProgrammer + "]: " + line);
                    if (line.contains("Programming Finished")) {
                        success = true;
                    }
                }
            }
            int exitCode = externProcess.waitFor();
            if(success && !canceled) {
                System.out.println("[" + serialProgrammer + "]: " + "SUCESSO!");
            } else {
                System.out.println("[" + serialProgrammer + "]: " + "FALHA OU CANCELADO!");
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("[" + serialProgrammer + "]: Operação interrompida ou erro de I/O.");
        }
        return success && !canceled;
    }

    public void cancelar(){
        this.canceled = true;
        if(externProcess != null && externProcess.isAlive()){
            System.out.println("Sinal de parada recebido! Destruindo o processo de gravação!");
            externProcess.destroyForcibly();
        }
    }

}
