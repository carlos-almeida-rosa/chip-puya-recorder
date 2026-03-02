flowchart TD
    A[Clonar Repositório] --> B[Configurar Serial do Gravador]
    B --> C[Configurar Caminho do Firmware]
    C --> D[Configurar Diretório do OpenOCD]
    D --> E[Configurar Comandos do Chip]
    E --> F[Executar Aplicação]

flowchart LR
    S1[Editar Serial do Gravador] --> S2[Editar Caminho do Firmware]
    S2 --> S3[Editar Diretório OpenOCD]
    S3 --> S4[Editar Comandos no ProcessBuilder]