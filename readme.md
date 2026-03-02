# Puya Chip Recorder

Ferramenta para gravação e apagamento de chips utilizando OpenOCD via interface gráfica em JavaFX.

---

## 🔄 Fluxo de Configuração

```mermaid
flowchart TD
    A[Clonar Repositório] --> B[Configurar Serial do Gravador]
    B --> C[Configurar Caminho do Firmware]
    C --> D[Configurar Diretório do OpenOCD]
    D --> E[Configurar Comandos no ProcessBuilder]
    E --> F[Executar Aplicação]
```

---

## 🏗 Arquitetura de Execução

```mermaid
flowchart TD
    UI[Interface JavaFX] --> Service[WriteChipService / EraseChipService]
    Service --> PB[ProcessBuilder]
    PB --> OpenOCD[OpenOCD Local]
    OpenOCD --> Chip[Chip Alvo]
```

---

## ⚙️ Configuração Obrigatória Após Clonar

Cada usuário deve configurar:

- ✔ Serial do próprio gravador
- ✔ Caminho do firmware local
- ✔ Diretório do OpenOCD instalado na máquina
- ✔ Comandos específicos do chip no ProcessBuilder

---

## 🚫 Não Versionar

Nunca commitar:

- Firmware (.hex, .bin, .elf)
- Número de série real de gravadores
- Caminhos internos de rede
- Scripts proprietários

Adicionar ao `.gitignore`:

```
*.hex
*.bin
*.elf
```

---