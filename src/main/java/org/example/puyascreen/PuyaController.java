package org.example.puyascreen;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.concurrent.CompletableFuture;

public class PuyaController {
    @FXML
    private Label writeInformationText1;

    @FXML
    private Label writeInformationText2;

    @FXML
    private Label eraseInformationText1;

    @FXML
    private Label eraseInformationText2;

    private WriteChipService threadGravarCI1;

    private CompletableFuture<Void> currentTask1;

    private WriteChipService threadGravarCI2;

    private CompletableFuture<Void> currentTask2;


    @FXML
    protected void onWriteButton1Click() {
        writeInformationText1.setText("Gravando no slot 1!");
        String serialProgrammer1 = "SERIAL DO GRAVADOR";
        String diretorioFirmware = "CAMINHO/FIRMWARE.hex";
        threadGravarCI1 = new WriteChipService(serialProgrammer1, diretorioFirmware);
        currentTask1 = CompletableFuture.supplyAsync(threadGravarCI1).thenAccept(sucesso -> {
            Platform.runLater(() -> {
                if(sucesso) {
                    writeInformationText1.setText("Chip do slot 1 gravado com sucesso!");
                } else {
                    writeInformationText1.setText("Falha ao gravar o chip do slot 1!");
                }
            });
        });
    }

    @FXML
    protected void onWriteButton2Click(){
        writeInformationText2.setText("Gravando no slot 2!");
        String serialProgrammer2 = "SERIAL DO GRAVADOR";
        String diretorioFirmware = "CAMINHO/FIRMWARE.hex";
        threadGravarCI2 = new WriteChipService(serialProgrammer2, diretorioFirmware);
        currentTask2 = CompletableFuture.supplyAsync(threadGravarCI2).thenAccept(sucesso -> {
            Platform.runLater(() -> {
                if(sucesso) {
                    writeInformationText2.setText("Chip do slot 2 gravado com sucesso!");
                } else {
                    writeInformationText2.setText("Falha ao gravar o chip do slot 2!");
                }
            });
        });
    }

    @FXML
    protected void onEraseButton1Click(){
        eraseInformationText1.setText("Apagando chip no slot 1!");
        String serialProgrammer1 = "SERIAL DO GRAVADOR";
        EraseChipService threadApagarCI1 = new EraseChipService(serialProgrammer1);
        CompletableFuture.supplyAsync(threadApagarCI1).thenAccept(sucesso -> {
            Platform.runLater(() -> {
                if(sucesso) {
                    eraseInformationText1.setText("Chip do slot 1 apagado com sucesso!");
                } else {
                    eraseInformationText1.setText("Falha ao apagar o chip do slot 1!");
                }
            });
        });
    }

    @FXML
    protected void onEraseButton2Click(){
        eraseInformationText2.setText("Apagando chip no slot 2!");
        String serialProgrammer2 = "SERIAL DO GRAVADOR";
        EraseChipService threadApagarCI2 = new EraseChipService(serialProgrammer2);
        CompletableFuture.supplyAsync(threadApagarCI2).thenAccept(sucesso -> {
            Platform.runLater(() -> {
                if(sucesso) {
                    eraseInformationText2.setText("Chip do slot 2 apagado com sucesso!");
                } else {
                    eraseInformationText2.setText("Falha ao apagar o chip do slot 2!");
                }
            });
        });
    }

    @FXML
    protected void onCancelButton1Click(){
        if(threadGravarCI1 != null) {
            writeInformationText1.setText("Gravação no slot 1 cancelada...");
            threadGravarCI1.cancelar();
            if(currentTask1 != null){
                currentTask1.cancel(true);
            }
        }
    }

    @FXML
    protected void onCancelButton2Click(){
        if(threadGravarCI2 != null){
            writeInformationText2.setText("Gravação no slot 2 cancelada...");
            threadGravarCI2.cancelar();
            if(currentTask2 != null) {
                currentTask2.cancel(true);
            }
        }
    }
}