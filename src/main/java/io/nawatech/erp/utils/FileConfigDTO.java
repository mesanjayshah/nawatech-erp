package io.nawatech.erp.utils;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@NoArgsConstructor
public class FileConfigDTO {

    public FileConfigDTO(int max, Long maxAttachmentSize, Long maxImageSize) {
        this.max = max;
        this.maxAttachmentSize = maxAttachmentSize;
        this.maxImageSize = maxImageSize;
    }

    private int max;
    private Long maxAttachmentSize;
    private Long maxImageSize;

}
