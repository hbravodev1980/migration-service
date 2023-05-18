package pe.com.ci.migration.migrationservice.entity;

import lombok.Data;

@Data
public class ArchivoInput {
    private String archivoBytes;
    private Byte[] bytes;
    private String nombreArchivo;
    private String urlArchivo;
    private String urlArchivoSas;
    private String nroEncuentro;
    private String tipoDocumentoId;
    private String tipoDocumentoDesc;
    private String estadoArchivo;
    private String msjError;
    private Boolean existe;
    private Boolean error;
}