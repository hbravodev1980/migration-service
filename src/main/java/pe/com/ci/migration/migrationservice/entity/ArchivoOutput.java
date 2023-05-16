package pe.com.ci.migration.migrationservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
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