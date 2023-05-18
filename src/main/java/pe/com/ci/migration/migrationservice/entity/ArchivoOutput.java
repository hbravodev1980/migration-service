package pe.com.ci.migration.migrationservice.entity;

import lombok.Data;

@Data
public class ArchivoOutput {
    private String nombreArchivo;
    private String urlArchivo;
    private String urlArchivoSas;
    private String estadoArchivo;
    private String archivoBytes;
    private String nroEncuentro;
    private Byte[] bytes;
    private Boolean existe;
    private String tipoDocumentoId;
    private String tipoDocumentoDesc;
    private Boolean error;
    private String msjError;
}