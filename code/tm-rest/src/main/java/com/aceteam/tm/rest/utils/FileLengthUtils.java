package com.aceteam.tm.rest.utils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: haoran
 */


@Component
@Data
@Slf4j
public class FileLengthUtils {
    /**
     * The maximum value allowed for uploading a source file must not
     * be greater than fileLength
     */
    @Value("${file.source.length}")
    private long fileMaxLength;

    /**
     * Is the file too large
     *
     * @param bytes
     * @return
     */
    public Boolean isFileNotTooBig(byte[] bytes) {
        // Current file size
        long currentFileSize = bytes.length;
        if (currentFileSize <= fileMaxLength) {
            return true;
        } else {
            return false;
        }
    }

}

