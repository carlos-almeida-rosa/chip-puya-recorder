package org.example.puyascreen;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.function.Supplier;

public class EraseChipService implements Supplier<Boolean> {
    private String directoryPath = "C:\\CAMINHO\\OPENCD\\BIN";
    private String serialProgrammer;

    public EraseChipService(String serialProgrammer) {
        this.serialProgrammer = serialProgrammer;
    }

    @Override
    public Boolean get() {
        String fullPath = directoryPath + File.separator + "openocd.exe";
        ProcessBuilder pb = new ProcessBuilder(
                fullPath,
                "ADICIONAR AQUI O COMANDO PARA O CHIP"
        );
        pb.directory(new File(directoryPath));
        pb.redirectErrorStream(true);
        boolean success = false;
        try{
            Process p = pb.start();

            try(BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()))){
                String line;
                while((line = reader.readLine()) != null){
                    System.out.println("[" + serialProgrammer + "]: " + line);
                    if(line.contains("py32x mass erase complete")) {
                        success = true;
                    }
                }
            }
            int exitCode = p.waitFor();
            if(success){
                System.out.println("[" + serialProgrammer + "]: " + "SUCESSO!");
            } else {
                System.out.println("[" + serialProgrammer + "]: " + "FALHA!");
            }

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return success;
    }
}
